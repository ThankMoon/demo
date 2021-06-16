package kafka

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import scalikejdbc._
import scalikejdbc.config.DBs

/*
 * Spark Streaming整合Kafka的偏移量管理
 * @author zjh
 * @version v1.0
 * @date 2019/3/21
 * java.lang.NoClassDefFoundError: org/apache/spark/SparkConf
 * <dependency> <groupId>org.apache.spark</groupId> <artifactId>spark-core_2.10</artifactId> <version>${spark.version}</version> <!--<scope>provided</scope>--> </dependency>
注释掉scope或将provided改为compile，因为最后打包提交的时候要用到这个jar包。
 */
object OffsetApp {
  def main(args: Array[String]): Unit = {
    val sc = new SparkConf().setMaster("local[2]").setAppName("OffsetApp")
    val ssc = new StreamingContext(sc, Seconds(10))
    //Sparking Streaming 对接Kafka
    val kafkaParams = Map(
      "metadata.broker.list" -> "192.168.247.129:9092",
      "group.id" -> "my.group.id",
      "auto.offset.reset" -> "smallest" //最小的
      //"auto.offset.reset" -> "largest" 最新的
    )
    val topics = "test".split(",").toSet
    //接收Kafka数据
    //获取偏移量（读取MySql/zk/kafka/hbase/redis表中存放的offset数据）
    DBs.setup()
    val fromOffsets = DB.readOnly(implicit session => {
      SQL("select * from test_offset").map(rs => {
        (TopicAndPartition(rs.string("topic"), rs.int("partition")), rs.long("offset"))
      }).list().apply()
    }).toMap
    //获取offset
    val stream = if (fromOffsets.size == 0 ) { //第一次开始运行，从smallest开始处理
      KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    } else { //已经处理过的（已存在offset）
      val fromOffsets = Map[TopicAndPartition, Long]()
      val messageHandler = (mm: MessageAndMetadata[String, String]) => (mm.key(), mm.message())
      KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc, kafkaParams, fromOffsets, null)
    }
    //业务逻辑处理
    stream.foreachRDD(rdd => {
      println(rdd.count())
      //TODO :保存offset
/*      val insertResult: Int = DB.autoCommit { implicit session =>
            SQL("insert into test_offset(topic, groupid,partition,offset) values(?,?,?,?)").bind("test01", "test01",1,2).update().apply()
      }
      println(insertResult)*/

      DB.localTx{ implicit session =>
        SQL("UPDATE test_offset SET offset = ? where topic = ?").bind(rdd.count(),"test").update().apply()
      }
    })
    ssc.start()
    ssc.awaitTermination()
  }
}

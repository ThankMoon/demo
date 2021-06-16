package spark

import org.apache.spark.{SparkConf, SparkContext}

object LogCount {
  def main(args: Array[String]): Unit = {
    //创建一个SparkContext对象
    val conf = new SparkConf().setAppName("LogCount").setMaster("local")
    val sc = new SparkContext(conf)
    println("Hello")
    //读取数据，当成第一个RDD
    val rdd1 = sc.textFile("D:\\Desktop\\log.txt")
    //进行字符串的截取  ***.jsp
    val rdd2 = rdd1.map(line => {
      //解析字符串1找到双引号的中间的数据
      val index1 = line.indexOf("\"")
      val index2 = line.lastIndexOf("\"")
      val line1 = line.substring(index1 + 1, index2)
      //GET /MyDemoWeb/head.jsp HTTP/1.1
      //2找到空格之间的数据
      val index3 = line1.indexOf(" ")
      val index4 = line1.lastIndexOf(" ")
      val line2 = line1.substring(index3 + 1, index4) // /MyDemoWeb/head.jsp

      //得到Jsp名字
      val jspName = line2.substring(line2.lastIndexOf("/") + 1) //***.jsp
      //返回
      (jspName, 1)
    })
    //按照jsp的名字进行聚合操作，类似Wordcount
    val rdd3 = rdd2.reduceByKey(_ + _)
    //（***.jsp，5）
    //排序  按照value进行降序排序
    val rdd4 = rdd3.sortBy(_._2, false)
    rdd4.saveAsTextFile("D:\\桌面\\RDD4")
  }
}

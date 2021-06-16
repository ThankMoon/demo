package hBase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

public class TestHbaseDemo {
    @Test
    public void testCreateTable() throws Exception{
        //创建表
        //配置ZooKeeper地址
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.65.200");

        //得到一个HBase的客户端
        HBaseAdmin client = new HBaseAdmin(conf);

        //采用：面向对象的思想来建表
        //1、指定表的描述符
        HTableDescriptor htd = new HTableDescriptor(TableName.valueOf("mytable"));

        //2、指定列族
        htd.addFamily(new HColumnDescriptor("info"));
        htd.addFamily(new HColumnDescriptor("grade"));

        //创建表
        client.createTable(htd);

        //关闭客户端
        client.close();
    }
    @Test
    //插入数据
    public void testPutData () throws Exception{
        //配置Zookeeper地址
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.65.200");
        //得到一个客户端
        HTable client = new HTable(conf,"mytable");
        //构造一个Put对象：一条数据
        Put put = new Put(Bytes.toBytes("id001"));
        put.addColumn(Bytes.toBytes("info"),  //列族的名字
                Bytes.toBytes("name"), //列的名字
                Bytes.toBytes("Tom"));//值
        //一次插入多条记录，可以用  client.put（list）
        client.put(put);
        client.close();
    }
    @Test
    //指定rowkey查询数据
    public void TestGetData()throws Exception{
        //配置zookeeper
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.65.200");
        //得到一个客户端
        HTable client = new HTable(conf,"mytable");
        //构造一个get对象
        Get get = new Get(Bytes.toBytes("id001"));
        //执行查询  相当于 select * from mytable where rowkey=???
        Result r = client.get(get);
        //输出：注意：hbase中，没有数据的类型，所有的类型都是二进制
        //   要指定列主 和列名
        String name = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")));

        System.out.println("名字是："+name);
        client.close();
    }
    @Test
    //scan 查询数据
    public void testScanData() throws Exception{
        //配置Zookeeper
        Configuration conf = new Configuration();
        conf.set("hbase.zookeeper.quorum", "192.168.65.200");
        //得到一个客户端
        HTable client = new HTable(conf,"mytable");
        //定义一个扫描器
        Scan scan  = new Scan();
        //过滤器 scan.setFilter(filter);

        //通过扫描器查询所有的数据
        ResultScanner rs = client.getScanner(scan);
        for(Result r:rs){
            String name = Bytes.toString(r.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")));
            System.out.println("名字是："+name);

        }
        client.close();
    }
}
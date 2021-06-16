package movie.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 大数据分布式文件系统操作核心工具类
 * @author zjh
 */
public class HdfsUtil {
    private static FileSystem fs = null;
    static{
        // 声明配置
        Configuration conf = new Configuration();
        // 赋值
        conf.set("fs.defaultFS","hdfs://192.168.213.25:8020/");
        conf.set("dfs.client.use.datanode.hostname", "true");
        //conf.set("fs.default.name","hdfs://192.168.247.129:9000/");
        // 操作分布式文件系统
        try {
            fs = FileSystem.get(new URI("hdfs://192.168.213.25:8020/"),conf,"hdfs");
            //fs = FileSystem.get(new URI("hdfs://192.168.247.129:9000/"),conf,"hdfs");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

     /**
      * HDFS 分布式文件系统操作核心方法
      * @author zjh
      * @return void 无
      */
     @Test
    public void uploadFile(){
        try {
            fs.copyFromLocalFile(new Path("D:\\Desktop\\BigData\\movie.txt"),new Path("/movie"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     /**
      * HDFS 分布式文件系统操作核心方法
      * @author zjh
      * @param path 本地路径，hdfsPath hdfs路径
      * @return void 无
      */
    public void uploadFile(String path,String hdfsPath){
        try {
            fs.copyFromLocalFile(new Path(path),new Path(hdfsPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

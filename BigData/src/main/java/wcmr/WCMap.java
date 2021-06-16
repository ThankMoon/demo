package wcmr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

//                           k1(一行越长偏移量越大)  v1      k2        v2
public class WCMap extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable k1, Text v1, Context context) throws IOException, InterruptedException {
        /*
        * context:代表reduce的上下文
        * 上文：HDFS
        * 下文：reduce
        * */
        //获取得到这一行数据
        String s = v1.toString();
        //分词操作，分完词后的所有单词放到一个数组当中
        String  words[] = s.split(" ");
        //循环的输出<k2,v2>
        for(String w :words){
            //输出          k2                 v2
            context.write(new Text(w),new IntWritable(1));
        }
    }
}

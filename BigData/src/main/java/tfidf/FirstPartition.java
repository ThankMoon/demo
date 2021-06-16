package tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;

/**
* 第一个MR自定义分区
*/
public class FirstPartition extends HashPartitioner<Text,IntWritable> {
    @Override                   //   k2           v2           reduce个数
    public int getPartition(Text key, IntWritable value, int reduceCount) {
        if(key.equals(new Text("count"))) {
            return 3;
        } else {
            return super.getPartition(key,value,reduceCount-1);
        }
    }
}

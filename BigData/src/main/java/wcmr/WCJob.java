package wcmr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WCJob {
    public static  void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.创建一个任务
        Job job = Job.getInstance(new Configuration());
    //  指定任务的入口
        job.setJarByClass(WCMap.class);

        //2.指定任务的Map和Map的输出类型
        job.setMapperClass(WCMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //3.指定任务的Reduce和Reduce的输出类型
        job.setReducerClass(WCReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //4.指定任务的输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //5.执行任务
        job.waitForCompletion(true);
    }
}

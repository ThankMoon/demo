package tfidf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class FirstJob {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://192.168.247.129:9000");
        configuration.set("yarn.resourcemanager.hostname","192.168.247.129:8032");
        try{
            FileSystem fs  = FileSystem.get(configuration);

            Job job = Job.getInstance(new Configuration());
            job.setJarByClass(FirstJob.class);
            job.setJobName("weibo1");

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            //设置reduce个数
            job.setNumReduceTasks(4);
            job.setPartitionerClass(FirstPartition.class);
            job.setMapperClass(FirstMapper.class);
            job.setReducerClass(FirstReduce.class);
            job.setCombinerClass(FirstReduce.class);

            FileInputFormat.addInputPath(job, new Path("/src/main/java/weibo/weibo.txt"));
            Path path = new Path("/src/main/java/weibo/output1");
            if(fs.exists(path)){
                fs.delete(path,true);
            }
            FileOutputFormat.setOutputPath(job,path);

            job.waitForCompletion(true);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

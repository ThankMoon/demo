package tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FirstReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable i : values) {
            sum += sum + i.get();
        }
        if (key.equals(new Text("count"))) {
            System.out.println(key.toString() + "________" + sum);
        }
        //            k4      计算得到的求和的值
        context.write(key, new IntWritable(sum));
    }

}

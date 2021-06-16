package wcmr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

//                                        k3      v3         k4       v4
public class WCReduce extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text k3, Iterable<IntWritable> v3, Context context) throws IOException, InterruptedException {
        /*
        * context:reduce上下文
        * 上文：Map
        * 下文：Reduce
        * */
        int sum = 0;
        //累加v3里面所有的值，作为v4
        for (IntWritable v : v3) {
            sum += v.get();
        }
        //输出         k4         v4
        context.write(k3, new IntWritable(sum));
    }
}

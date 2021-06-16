package tfidf;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/*
 * 第一个MR，计算TF和计算N（微博总数）
 * */
//                                            k1          v1    k2     v2
public class FirstMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取这一行数据，并且以制表符进行拆分，成为两个数据放到数组当中
        //eg. {3823890201582094 今天我约了豆浆，油条，约...}
        String[] v = value.toString().trim().split("\t");
        if (v.length >= 2) {
            //得到ID号
            String id = v[0].trim();
            //得到微博内容
            String content = v[1].trim();
            //分词器，将微博内容放入分词器中
            StringReader sr = new StringReader(content);
            IKSegmenter ikSegmenter = new IKSegmenter(sr, true);
            Lexeme word = null;
            while ((word = ikSegmenter.next()) != null) {
                String w = word.getLexemeText();
                //输出内容，eg.{今天_3823890201582094,1}
                //                           k2                   v2
                context.write(new Text(w + "_" + id), new IntWritable(1));
            }
            //输入内容(count,1),计算微博总数
            context.write(new Text("count"), new IntWritable(1));
        } else {
            System.out.println(value.toString() + "--------");
        }
    }
}

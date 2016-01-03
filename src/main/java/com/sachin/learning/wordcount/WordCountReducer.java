package com.sachin.learning.wordcount;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends
        Reducer<Text, IntWritable, Text, IntWritable> {

    private static final Log LOG = LogFactory.getLog(WordCountReducer.class);

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
//        int sum = 0;
        LOG.info("key passed to reducer is - " + key.toString());
        System.out.println("key passed to reducer is - "+key.toString());
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
//            sb.append("["+value+"]");
        }
        Text out = new Text();
//        out.set(sb.toString());
        context.write(key, new IntWritable(sum));
    }
}
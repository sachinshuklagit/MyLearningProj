package com.sachin.learning.join.reducesidejoin;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
//cc JoinRecordMapper Mapper for tagging weather records for a reduce-side join
//vv JoinRecordMapper

/**
 * Reference: http://hadoopbook.com/
 *
 * */
public class JoinRecordMapper extends
        Mapper<LongWritable, Text, TextPair, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (!line.startsWith("#")) {
            String[] fields = line.split("\t");
            if (fields.length == 3) {
                context.write(new TextPair(fields[0], "1"), value);
            }
        }
    }

}
package com.sachin.learning.join.reducesidejoin;

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

// vv JoinStationMapper
//cc JoinStationMapper Mapper for tagging station records for a reduce-side join
/**
 * Reference: http://hadoopbook.com/
 *
 * */
public class JoinStationMapper extends
        Mapper<LongWritable, Text, TextPair, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        if (!line.startsWith("#")) {
            String[] words = line.split("\t");
            if (words.length == 2) {
                context.write(new TextPair(words[0], "0"), new Text(words[1]));
            }
        }
    }
}
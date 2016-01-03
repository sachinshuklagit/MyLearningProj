package com.sachin.learning.wordcount;


import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends
        Mapper<Object, Text, Text, IntWritable> {

    private static final Log LOG = LogFactory.getLog(WordCountMapper.class);
    private final IntWritable ONE = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        LOG.info("Inside WordCountMapper#map()...key=" + key);
        String[] csv = value.toString().split(" ");
        for (String str : csv) {
            word.set(str);
            context.write(word, ONE);
        }

    }
}

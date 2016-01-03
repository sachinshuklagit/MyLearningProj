package com.sachin.learning.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCount {
        // mvn clean package dependency:copy-dependencies
        //unset HADOOP_CLASSPATH
        //export HADOOP_CLASSPATH=~/sachin/softwares/workspace/MyLearningProj/target/dependency/*
        // hadoop jar ~/sachin/softwares/workspace//MyLearningProj/target/MyLearningProj-1.0-SNAPSHOT.jar  com.sachin.learning.wordcount.WordCount /work/input/wordcount/sample_data.txt /work/output/wordcount

    public static void main(String[] args) throws IOException,
            InterruptedException, ClassNotFoundException {

        Path inputPath = new Path(args[0]);
        Path outputDir = new Path(args[1]);

        // Create configuration
        Configuration conf = new Configuration(true);
        conf.set("key.value.separator.in.input.line","@@");
        // Create job
        Job job = new Job(conf, "WordCount");
        job.setJarByClass(WordCountMapper.class);
        JobClient dfdf;

        // Setup MapReduce
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        job.setNumReduceTasks(1);

        // Specify key / value
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


//        InputFormat

        // Input
        FileInputFormat.addInputPath(job, inputPath);
//        job.setInputFormatClass(SequenceFileInputFormat.class);
//        job.setInputFormatClass(KeyValueTextInputFormat.class);

        // Output
        FileOutputFormat.setOutputPath(job, outputDir);
        job.setOutputFormatClass(TextOutputFormat.class);

        // Delete output if exists
        FileSystem hdfs = FileSystem.get(conf);
        if (hdfs.exists(outputDir))
            hdfs.delete(outputDir, true);

        // Execute job
        int code = job.waitForCompletion(true) ? 0 : 1;
        System.exit(code);

    }

}
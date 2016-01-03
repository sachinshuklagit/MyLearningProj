package com.sachin.learning.join.distributecache;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


// hadoop jar ~/sachin/softwares/workspace/MyLearningProj/target/MyLearningProj-1.0-SNAPSHOT.jar com.sachin.learning.join.distributecache.MapJoinDriver /work/input/employee.txt /work/output7
public class MapJoinDriver extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.printf("Two parameters are required- <input file> <output dir>n");
            return -1;
        }

        Job job = new Job(getConf());
        Configuration conf = job.getConfiguration();
        job.setJobName("Map-side join with text lookup file in DCache");
        DistributedCache.addCacheFile(new URI("/work/input/department.txt"), conf);

        job.setJarByClass(MapJoinDriver.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(MapJoinDistributedCacheMapper.class);

        job.setNumReduceTasks(0);

        boolean success = job.waitForCompletion(true);
        return success ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new Configuration(),new MapJoinDriver(), args);
        System.exit(exitCode);
    }
}
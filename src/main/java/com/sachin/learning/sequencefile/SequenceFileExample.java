package com.sachin.learning.sequencefile;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

/**
 * Created by sachin on 11/22/2015.
 */

//mvn exec:java -Dexec.mainClass="com.sachin.learning.sequencefile.SequenceFileExample" -Dexec.args="/tmp/sachin.seq"
public class SequenceFileExample {

    private static final Log LOG = LogFactory.getLog(SequenceFileExample.class);

    public static void main(String[] args) throws Exception{
        seqFileCreateAndRead(args[0], 100);
    }

    private static void seqFileCreateAndRead(String filePathStr, int lines) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Path filePath=new Path(filePathStr);
        Configuration conf = new Configuration();
//        conf.setInt("io.seqfile.compress.blocksize",blockSize);
        FileSystem fs=FileSystem.get(conf);
//        LOG.info("Creating SequenceFile with codec \"" + codecClass + "\"");
        SequenceFile.Writer writer=SequenceFile.createWriter(fs,conf,filePath,Text.class,Text.class);

        LOG.info("Writing to SequenceFile...");
        for (int i=0; i < lines; i++) {
            Text key=new Text("key" + i);
            Text value=new Text("value" + i);
            writer.append(key,value);
        }
        writer.close();
        LOG.info("Reading from the SequenceFile...");
        SequenceFile.Reader reader=new SequenceFile.Reader(fs,filePath,conf);
        Writable key=(Writable)reader.getKeyClass().newInstance();
        Writable value=(Writable)reader.getValueClass().newInstance();
        long lc=reader.getPosition();;
        try {
            while (reader.next(key,value)) {
                    LOG.info("key="+key+", value="+value);
                lc = reader.getPosition();;
            }
        }
        finally {
            reader.close();
        }
    }
}



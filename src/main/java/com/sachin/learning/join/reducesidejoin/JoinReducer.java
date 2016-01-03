package com.sachin.learning.join.reducesidejoin;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// vv JoinReducer
//cc JoinReducer Reducer for joining tagged station records with tagged weather records
/**
 * Reference: http://hadoopbook.com/
 *
 * */
public class JoinReducer extends Reducer<TextPair, Text, Text, Text> {

    @Override
    protected void reduce(TextPair key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        Iterator<Text> iter = values.iterator();
        Text stationName = new Text(iter.next());
        while (iter.hasNext()) {
            Text record = iter.next();
            Text outValue = new Text(stationName.toString() + "\t"
                    + record.toString());
            context.write(key.getFirst(), outValue);
        }
    }
}
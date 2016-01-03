package com.sachin.learning.storm.basic;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

/**
 * Created by Shukla, Sachin. on 12/9/15.
 */

public class LearningStormBolt extends BaseBasicBolt
{
    private static final long serialVersionUID = 1L;
    public void execute(Tuple input, BasicOutputCollector collector) {
        // fetched the field "site" from input tuple.
        String test = input.getStringByField("site");
        // print the value of field "site" on console.
        System.out.println("LearningStormBolt#execute()..."+Thread.currentThread().getName()+"Name of input site is : " + test);

        org.apache.zookeeper.server.NIOServerCnxn dfdf;
    }
    public void declareOutputFields(OutputFieldsDeclarer
                                            declarer) {
    }
}

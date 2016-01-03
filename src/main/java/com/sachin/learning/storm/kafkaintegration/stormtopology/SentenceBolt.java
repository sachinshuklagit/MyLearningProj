package com.sachin.learning.storm.kafkaintegration.stormtopology;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shukla, Sachin. on 12/13/15.
 */
public class SentenceBolt extends BaseBasicBolt {
    private List<String> words = new ArrayList<String>();

    public void execute(Tuple input, BasicOutputCollector collector) {
        System.out.println("SentenceBolt#execute().."+input.getString(0));
    }
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }
}
package com.sachin.learning.storm.basic;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.sachin.learning.storm.basic.LearningStormBolt;
import com.sachin.learning.storm.basic.LearningStormSpout;

/**
 * Created by Shukla, Sachin. on 12/9/15.
 */


// ~/softwares/storm/bin/storm jar /home/sachin/softwares/workspace/MyLearningProj/target/MyLearningProj-1.0-SNAPSHOT-jar-with-dependencies.jar com.sachin.learning.storm.basic.LearningStormSingleNodeTopology LearningStormSingleNodeTopology
// ~/softwares/storm/bin/storm kill LearningStormSingleNodeTopology
public class LearningStormSingleNodeTopology {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("LearningStormSpout", new LearningStormSpout(), 4);
        builder.setBolt("LearningStormBolt", new LearningStormBolt(), 2)
                .fieldsGrouping("LearningStormSpout", new Fields("site"));
        Config conf = new Config();
        conf.setNumWorkers(3);
        try {
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        }catch(AlreadyAliveException alreadyAliveException) {
            System.out.println(alreadyAliveException);
        } catch
                (InvalidTopologyException invalidTopologyException) {
            System.out.println(invalidTopologyException);
        }
    }
}
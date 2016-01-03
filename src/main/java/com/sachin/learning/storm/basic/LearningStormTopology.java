package com.sachin.learning.storm.basic;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

/**
 * Created by Shukla, Sachin. on 12/9/15.
 */

// mvn compile exec:java -Dexec.classpathScope=compile -Dexec.mainClass=com.sachin.learning.storm.basic.LearningStormTopology

public class LearningStormTopology {
    public static void main(String[] args) throws
            AlreadyAliveException, InvalidTopologyException {
        System.out.println("LearningStormTopology started.....");
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("LearningStormSpout", new LearningStormSpout(), 2);
        builder.setBolt("LearningStormBolt",  new LearningStormBolt(), 4).shuffleGrouping  ("LearningStormSpout");
        Config conf = new Config();
        conf.setDebug(true);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("LearningStormToplogy", conf,
        builder.createTopology());
        try {
            Thread.sleep(10000);
        } catch (Exception exception) {
            System.out.println("Thread interrupted exception : "
                    + exception);
        }
        cluster.killTopology("LearningStormToplogy");
        cluster.shutdown();
    } }
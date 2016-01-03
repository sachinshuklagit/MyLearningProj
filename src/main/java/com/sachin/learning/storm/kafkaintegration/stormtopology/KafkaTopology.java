package com.sachin.learning.storm.kafkaintegration.stormtopology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.netflix.curator.CuratorZookeeperClient;
import com.sachin.learning.storm.kafkaintegration.kafkaproducer.KafkaMain;
import storm.kafka.*;

import java.util.UUID;

/**
 * Created by Shukla, Sachin. on 12/13/15.
 */

// ~/softwares/storm/bin/storm jar /home/sachin/softwares/workspace/MyLearningProj/target/MyLearningProj-1.0-SNAPSHOT-jar-with-dependencies.jar com.sachin.learning.storm.kafkaintegration.stormtopology.KafkaTopology

public class KafkaTopology {
    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        String zkConnString = "192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181";

        BrokerHosts hosts = new ZkHosts(zkConnString);
        SpoutConfig spoutConfig = new SpoutConfig(hosts, KafkaMain.TOPIC, "/" + KafkaMain.TOPIC, UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("KafkaSpout", kafkaSpout, 1);


        builder.setBolt("SentenceBolt", new SentenceBolt(), 1).globalGrouping("KafkaSpout");
        Config conf = new Config();
        conf.setNumWorkers(1);
        try {
            StormSubmitter.submitTopology("KafkaTopology", conf, builder.createTopology());
        } catch (AlreadyAliveException alreadyAliveException) {
            System.out.println(alreadyAliveException);
        } catch
                (InvalidTopologyException invalidTopologyException) {
            System.out.println(invalidTopologyException);
        }
    }
}
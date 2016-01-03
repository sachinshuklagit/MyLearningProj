package com.sachin.learning.kafka;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shukla, Sachin. on 3/16/15.
 */

//mvn exec:java -Dexec.mainClass="com.sachin.learning.kafka.KafkaMain"
public class KafkaMain {

    private static final String TOPIC = "test1";
    public static void main(String[] args) throws Exception{

        ExecutorService manager = Executors.newFixedThreadPool(10);
        manager.submit(new KafkaConsumer("192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181", "g1", TOPIC));
        Thread.sleep(500);
        manager.submit(new KafkaProducer(TOPIC, "192.168.1.115:9092,192.168.1.210:9092,192.168.1.21:9092"));
        manager.shutdown();
        manager.awaitTermination(20, TimeUnit.SECONDS);
        System.out.println("Main prog Done!!");
    }
}

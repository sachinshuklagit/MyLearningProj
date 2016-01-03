package com.sachin.learning.storm.kafkaintegration.kafkaproducer;


import com.sachin.learning.kafka.KafkaConsumer;
import kafka.producer.KeyedMessage;
import util.GenUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shukla, Sachin. on 3/16/15.
 */

// mvn exec:java -Dexec.mainClass="com.sachin.learning.storm.kafkaintegration.kafkaproducer.KafkaMain"
public class KafkaMain {

    public static final String TOPIC = "storm-kafka-topic-01";
    public static void main(String[] args) throws Exception{
        final KafkaProducer kafkaProducer = new KafkaProducer(TOPIC, "192.168.1.115:9092,192.168.1.210:9092,192.168.1.21:9092");
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String msg = GenUtil.getStrDate(new Date()) + "-" + GenUtil.getRandomWord(4);
                kafkaProducer.send(new KeyedMessage<String, String>(
                        TOPIC,
                        msg,
                        msg));

            }
        }, 100,200);
    }
}

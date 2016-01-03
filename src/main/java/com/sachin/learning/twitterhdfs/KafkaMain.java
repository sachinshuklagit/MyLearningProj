package com.sachin.learning.twitterhdfs;


import com.sachin.learning.kafka.KafkaConsumer;
import kafka.producer.KeyedMessage;
import util.GenUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Shukla, Sachin. on 3/16/15.
 */

// mvn exec:java -Dexec.mainClass="com.sachin.learning.twitterhdfs.KafkaMain"
public class KafkaMain {

    private static final String TOPIC = "test-twitter-topic-2";
    public static void main(String[] args) throws Exception{
        KafkaProducer kafkaProducer = new KafkaProducer(TOPIC, "192.168.1.115:9092,192.168.1.210:9092,192.168.1.21:9092");

        long startTime = System.currentTimeMillis();
        long endTime = startTime + 10 * 60 * 1000;
        String[] randomLine = null;
        String key = null;
        String msg = null;
        for(;System.currentTimeMillis() < endTime;){
            randomLine = getRandomLine();
            key = randomLine[0];
            msg = GenUtil.getDelimSeparatedStr(randomLine);
            kafkaProducer.send(new KeyedMessage<String, String>(TOPIC,key,msg));
            Thread.sleep(500);
        }
        System.out.println("Prog exited....");

    }

    private static String[] getRandomLine(){
        String[] sArr = new String[4];
        sArr[0] = RANDOM_WORDS[GenUtil.getRandomNumber(0,RANDOM_WORDS.length)];
        sArr[1] = RANDOM_WORDS[GenUtil.getRandomNumber(0,RANDOM_WORDS.length)];
        sArr[2] = RANDOM_WORDS[GenUtil.getRandomNumber(0,RANDOM_WORDS.length)];
        sArr[3] = RANDOM_WORDS[GenUtil.getRandomNumber(0,RANDOM_WORDS.length)];
        return sArr;

    }

    private static final String[] RANDOM_WORDS = new String[]{
            "With", "Kafka", "0.8+", "you", "get", "replication", "of", "your", "event", "data.", "If", "you", "lose", "a", "broker", "node,", "others", "will", "take", "up", "the", "slack", "to", "delivery", "your", "events", "without", "loss",
            "With", "Flume", "&", "FlumeNG,", "and", "a", "File", "channel,", "if", "you", "loose", "a", "broker", "node", "you", "will", "lose", "access", "to", "those", "events", "until", "you", "recover", "that", "disk.", "The", "database",
            "channel", "with", "Flume", "is", "reported", "too", "slow", "for", "any", "production", "use", "cases", "at", "volume", "To", "me,", "event", "availability", "is", "a", "huge", "consideration", "in", "designing", "an", "real-time",
            "architecture", "that", "will", "be", "always", "on", "Additional", "considerations",  "6.", "For", "enterprises,", "they", "very", "often", "want", "commercial", "support", "and", "this", "is", "an", "important", "consideration.",
            "Flume", "is", "supported", "by", "a", "number", "of", "Hadoop", "distribution", "providers", "7.", "Flume", "supports", "some", "content", "based", "event", "routing",
            "8.", "Flume", "has", "a", "number", "of", "pre-built", "collectors.", "Kafka", "provides", "just", "the", "messaging", "Both", "systems", "support", "high", "volumes", "of", "data",
            "It", "often", "boils", "down", "to", "choosing", "either:",
            "a)", "Commercially", "supported", "out", "of", "the", "box", "offering", "sinking", "data", "to", "HDFS", "vs.",
            "b)", "Multi-consumer", "ultra-high", "availability", "messaging", "system"
    };
}

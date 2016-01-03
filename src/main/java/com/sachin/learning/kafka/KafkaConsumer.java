package com.sachin.learning.kafka;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Shukla, Sachin. on 3/11/15.
 */
public class KafkaConsumer implements Callable<Void>{

    private final ConsumerConnector consumer;
    private final String topic;
    private KafkaStream<byte[], byte[]> m_stream = null;
    private String groupId = null;
    private static AtomicInteger consumerId = new AtomicInteger(0);
    private String consumerName = null;

    public KafkaConsumer(String a_zookeeper, String a_groupId, String a_topic) {
        this.groupId = a_groupId;
        consumerName = "Consumer-" + String.valueOf(consumerId.addAndGet(1));
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig(a_zookeeper, a_groupId));
        this.topic = a_topic;
        init();
        System.out.println("Consumer ["+consumerName+"] started successfully!!!!");
    }

    private void init(){
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        m_stream = streams.get(0);
    }


    private ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }

    @Override
    public Void call() throws Exception {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        String consumerAndGrpInfo = consumerName+"@"+groupId;
        while (it.hasNext()){
            MessageAndMetadata<byte[], byte[]> metadata = it.next();
            System.out.println("**** {"+consumerAndGrpInfo+"}= " + new String(metadata.message())+", partition = "+metadata.partition()+", thread name="+Thread.currentThread().getName());
        }
        return null;
    }
}

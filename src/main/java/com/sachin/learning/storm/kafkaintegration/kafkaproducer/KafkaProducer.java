package com.sachin.learning.storm.kafkaintegration.kafkaproducer;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaProducer{


    private final String topic;
	private Producer<String, String> producer;
	private String brokers;
	private String defaultPartitioner = "kafka.producer.DefaultPartitioner";
    private static final int produceMsgTimeDelay = 1000;
    private static AtomicInteger producerId = new AtomicInteger(0);
    private String producerName = null;



	public KafkaProducer(String topic, String brokers)	{
        producerName = "Producer -" + String.valueOf(producerId.addAndGet(1));
        this.topic = topic;
		this.brokers = brokers;
        init();
        System.out.println("Producer started successfully!!!!");
	}

	public void init()	{
		try {
			Properties props = new Properties();
			props.put("metadata.broker.list", this.brokers);
			props.put("serializer.class", "kafka.serializer.StringEncoder");
			props.put("key.serializer.class", "kafka.serializer.StringEncoder");
			props.put("partitioner.class", this.defaultPartitioner);
//			props.put("partitioner.class", "com.sachin.learning.kafka.KafkaCustomPartitioner");
			props.put("request.required.acks", "1");
			props.put("message.send.max.retries", "5");
			props.put("retry.backoff.ms", "200");

			ProducerConfig config = new ProducerConfig(props);
			producer = new Producer<String, String>(config);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void close()	{
		if (null != producer) {
			producer.close();
		}
	}
	
	public void send(KeyedMessage<String, String> data)	{
		try {
			producer.send(data);

		} catch (Exception e) {
            e.printStackTrace();
		}
	}

}

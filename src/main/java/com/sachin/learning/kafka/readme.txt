~/softwares/kafka/bin/kafka-topics.sh --create --zookeeper 192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181 --replication-factor 2 --partitions 1 --topic my-replicated-topic


~/softwares/kafka/bin/kafka-console-producer.sh --broker-list 192.168.1.115:9092,192.168.1.210:9092,192.168.1.21:9092 --topic my-replicated-topic


~/softwares/kafka/bin/kafka-console-consumer.sh --zookeeper 192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181 --topic my-replicated-topic --from-beginning

-------

~/softwares/kafka/bin/kafka-topics.sh --describe --zookeeper 192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181 --topic my-replicated-topic


~/softwares/kafka/bin/kafka-topics.sh --list --zookeeper 192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181

~/softwares/kafka/bin/kafka-topics.sh --zookeeper 192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181 --delete --topic my-replicated-topic





########## USING FLUME TO READ FROM KAFKA AND SAVE IN HDFS ################

~/softwares/flume/bin/flume-ng agent -c conf -f ~/softwares/flume/sachin/conf/flume-kafka.conf -n tier1 -Dflume.root.logger=INFO,console

------
## flume-kafka.conf file content....
 tier1.sources  = source1
 tier1.channels = channel1
 tier1.sinks = sink1

 tier1.sources.source1.type = org.apache.flume.source.kafka.KafkaSource
 tier1.sources.source1.zookeeperConnect = 192.168.1.115:2181,192.168.1.210:2181,192.168.1.21:2181
 tier1.sources.source1.topic = my-replicated-topic
 tier1.sources.source1.groupId = g1
 tier1.sources.source1.channels = channel1
 tier1.sources.source1.interceptors = i1
 tier1.sources.source1.interceptors.i1.type = timestamp
 tier1.sources.source1.kafka.consumer.timeout.ms = 100

 tier1.channels.channel1.type = memory
 tier1.channels.channel1.capacity = 10000
 tier1.channels.channel1.transactionCapacity = 1000

 tier1.sinks.sink1.type = hdfs
 tier1.sinks.sink1.hdfs.path = /tmp/kafka/%{topic}/%y-%m-%d
 tier1.sinks.sink1.hdfs.rollInterval = 3600
 tier1.sinks.sink1.hdfs.rollSize = 0
 tier1.sinks.sink1.hdfs.rollCount = 0
 tier1.sinks.sink1.hdfs.fileType = DataStream
 tier1.sinks.sink1.channel = channel1
 ####################################################

 ####################################################

~/softwares/flume/bin/flume-ng agent -c conf -f ~/softwares/flume/sachin/conf/kafka_sink.conf -n tier1 -Dflume.root.logger=INFO,console

 tier1.sources  = source1
 tier1.channels = channel1
 tier1.sinks = sink1

 tier1.sources.source1.type = exec
 tier1.sources.source1.command = tail -f /var/log/apache2/access.log
 tier1.sources.source1.channels = channel1
 tier1.sources.source1.interceptors = i1
 tier1.sources.source1.interceptors.i1.type = timestamp

 tier1.channels.channel1.type = memory
 tier1.channels.channel1.capacity = 10000
 tier1.channels.channel1.transactionCapacity = 1000

 tier1.sinks.sink1.type = org.apache.flume.sink.kafka.KafkaSink
 tier1.sinks.sink1.topic = sink1
 tier1.sinks.sink1.brokerList = 192.168.1.115:9092,192.168.1.210:9092,192.168.1.21:9092
 tier1.sinks.sink1.channel = channel1
 tier1.sinks.sink1.batchSize = 20

  ####################################################
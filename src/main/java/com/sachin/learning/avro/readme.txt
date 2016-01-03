

#Command to convert json file to avro format.
java -jar /var/lib/jenkins/sachin/softwares/hadoop/sachin/avro-tools-1.7.7.jar fromjson --schema-file /var/lib/jenkins/sachin/softwares/workspace/MyLearningProj/src/main/java/com/sachin/learning/avro/twitter.avsc /var/lib/jenkins/sachin/softwares/workspace/MyLearningProj/src/main/java/com/sachin/learning/avro/twitter.json > twitter.avro


#Command to convert avro file back to json.
java -jar /var/lib/jenkins/sachin/softwares/hadoop/sachin/avro-tools-1.7.7.jar tojson /var/lib/jenkins/sachin/softwares/hadoop/sachin/twitter.avro > twitter.json


downloaded avro-tools jar file from below command.
wget http://www.eu.apache.org/dist/avro/stable/java/avro-tools-1.7.7.jar
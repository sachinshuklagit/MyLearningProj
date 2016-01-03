---------------------------------------------------------------------------------------------------------------
hadoop jar ~/sachin/softwares/workspace//MyLearningProj/target/MyLearningProj-1.0-SNAPSHOT.jar  com.sachin.learning.WordCount /work/input/wordcount/sample_data.txt /work/output/wordcount

hadoop jar ~/sachin/softwares/hadoop/contrib/streaming/hadoop-streaming-1.2.1.jar -input /work/input/wordcount/sample_data2.txt -output /work/outputt -mapper 'wc -l' -reducer -Dmapred.reduce.tasks=0


 mvn exec:java -Dexec.mainClass="temp.ReadTwitterStreamMain" -Dexec.args="arg0 arg1 arg2"


 -----------------------
 SQOOP
 -----------------------
 hadoop fs -rmr /user/jenkins/SECTION_ASSIGNMENT_XREF

sqoop import --bindir ./  --connect jdbc:oracle:thin:@192.168.1.15:1521/XE --username spark --password spark --table SECTION_ASSIGNMENT_XREF --m 2 --compress

sqoop import-all-tables --bindir ./  --connect jdbc:oracle:thin:@192.168.1.15:1521/XE --username spark --password spark  --m 8 --compress

 sqoop import --hive-import --connect jdbc:oracle:thin:@192.168.1.15:1521/XE --username spark --password spark --table SECTION_ASSIGNMENT_XREF

 -----------------------
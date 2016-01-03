vi 1.txt
#add below lines
helo this is connect
how to connect to people
welcome...
####

hadoop fs -mkdir /work
hadoop fs -put 1.txt /work
hadoop jar /home/sachin/softwares/hadoop/share/hadoop/tools/lib/hadoop-streaming-2.7.1.jar -D mapred.reduce.tasks=0 -input /work/1.txt -output /work/result -mapper "bash -c 'grep -e connect'"
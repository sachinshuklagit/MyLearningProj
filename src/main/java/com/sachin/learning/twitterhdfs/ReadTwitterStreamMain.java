package com.sachin.learning.twitterhdfs;

import kafka.producer.KeyedMessage;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import util.GenUtil;
import util.ReadProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sachin on 11/14/2015.
 */
public class ReadTwitterStreamMain {

    private static final String STREAM_URI = "https://stream.twitter.com/1.1/statuses/filter.json";
    private static final String TOPIC = "twitter-topic-02";

    // mvn exec:java -Dexec.mainClass="com.sachin.learning.twitterhdfs.ReadTwitterStreamMain" -Dexec.args="aamir intolerance tolerance"
    public static void main(String[] args) {
        try{
            KafkaProducer kafkaProducer = new KafkaProducer(TOPIC, "192.168.1.115:9092,192.168.1.210:9092,192.168.1.21:9092");
            System.out.println("Starting Twitter public stream consumer thread.");

            // Enter your consumer key and secret below
            OAuthService service = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(ReadProperties.getProp("twitter.consumer.key"))
                    .apiSecret(ReadProperties.getProp("twitter.consumer.secret"))
                    .build();

            Token accessToken = new Token(ReadProperties.getProp("twitter.access.token"), ReadProperties.getProp("twitter.access.secret"));

            System.out.println("Connecting to Twitter Public Stream");
            OAuthRequest request = new OAuthRequest(Verb.POST, STREAM_URI);
            request.addHeader("version", "HTTP/1.1");
            request.addHeader("host", "stream.twitter.com");
            request.addHeader("user-agent", "Twitter Stream Reader");
            request.addBodyParameter("track", GenUtil.getDelimSeparatedStr(args));
            service.signRequest(accessToken, request);
            Response response = request.send();


            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getStream()));

            String line;
            System.out.println("Reading the stream......");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                kafkaProducer.send(new KeyedMessage<String, String>(TOPIC,getKeyFromMsg(line, args),line));
            }
        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

    private static String getKeyFromMsg(String line, String[] args){
        for(String s : args){
            if(line.contains(s)){
                return s;
            }
        }
        return "NULL";
    }
}

package com.sachin.learning.twitterhdfs;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * Created by Shukla, Sachin. on 3/16/15.
 */
public class KafkaCustomPartitioner implements Partitioner {

    public KafkaCustomPartitioner(VerifiableProperties props){
//        super(props);
    }

    @Override
    public int partition(Object o, int i) {

//        if(o instanceof String){
//            //System.out.println("key is instance of String");
//            String s = (String)o;
//            char ch = s.charAt(0);
//            if (ch >= 'a' && ch <= 'n'){
//                return 0;
//            }else if (ch >= 'o' && ch <= 'z'){
//                return 1;
//            }else{
//                return 2;
//            }
//
//        }else{
//            System.out.println("key is NOT instance of String");
//        }
        return 0;
    }
}

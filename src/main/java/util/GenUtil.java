package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by sachin on 11/14/2015.
 */
public class GenUtil {

    public static final String EMPTY_STR = "";
    public static final String SPACE_STR = " ";
    public static boolean isNullOREmptyString(String s){
        return (s == null || s.trim().equals(""));
    }
    public static String DATE_FORMATTER =  "yyyy-MM-dd HH:mm:ss.SSS";

    public static String getDelimSeparatedStr(String[] args){
        return getDelimSeparatedStr(args, SPACE_STR);
    }
    public static String getDelimSeparatedStr(String[] args, String delim){
        delim = (delim == null)? SPACE_STR : delim;
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<args.length;i++){
            if(i != 0) {
                sb.append(delim);
            }
            sb.append(args[i]);

        }

        return sb.toString();
    }

    //Includes a but excludes b;
    public static final int getRandomNumber(int a, int b){
        return a + ((int) (Math.random()*(b-a) ) );
    }

    public static final String getRandomWord(int n){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            sb.append((char)getRandomNumber('a', 'z'+1));
        }
        return sb.toString();
    }

    public static String getStrDate(Date d){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMATTER);
        return sdf.format(d);
    }

    public static void main(String[] args){
       // System.out.println("uuid="+ UUID.randomUUID().toString()+", hex = "+Integer.toHexString((int)( System.currentTimeMillis()/1000 ) ));

        final PriorityBlockingQueue<Integer> pq = new PriorityBlockingQueue<Integer>(20);
        Timer t1 = new Timer();
        t1.schedule(new TimerTask() {
            @Override
            public void run() {
                int num = (int)(Math.random() * 100);
                System.out.println("adding num - "+num+", size of queue="+pq.size());
                pq.offer(num);
            }
        },100,5000);

        Timer t2 = new Timer();
        t2.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("Retrieving num - " + pq.take() + ", size of queue=" + pq.size());
                }catch(Exception ex){
                    System.err.println(ex);
                }
            }
        },400,1000);

    }

    public static void printObjWithThreadInfo(String s){
        System.out.println(getStrDate(new Date())+":"+Thread.currentThread().getId()+":"+Thread.currentThread().getName()+": - "+s);
    }

    public static void sleepInSeconds(int n){
       sleepInMilliSeconds(1000 * n);
    }
    public static void sleepInMilliSeconds(long n){
        if(n<=0){
            return;
        }
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

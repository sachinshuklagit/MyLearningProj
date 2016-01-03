package util;


import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Shukla, Sachin. on 12/26/15.
 */
public class MyConnectionPool {

    private AtomicInteger ai = new AtomicInteger(0);
    private int initSize = 10;
    private int minSize = 4;
    private int maxSize = 15;
    private int minIdle = 2;
    private BlockingQueue<String> connPoll = null;
    private final List<String> connInUse = Collections.synchronizedList(new ArrayList<String>());
    private static final MyConnectionPool pool = new MyConnectionPool();


    private static final Timer timer = new Timer();
    public static class GetConnTask extends TimerTask {
        @Override
        public void run() {
            int delay = (new Random().nextInt(5)) * 1000;
            pool.getConnection();
            timer.schedule(new GetConnTask(), delay);
        }
    }

    public static class ReleaseConnTask extends TimerTask {

        @Override
        public void run() {
            int delay = (new Random().nextInt(5)) * 1000;
            pool.releaseConn();
            timer.schedule(new ReleaseConnTask(), delay);
        }

    }
    public static void main(String[] args) {
        timer.schedule(new GetConnTask(),0);
        timer.schedule(new ReleaseConnTask(),0);
    }

    private synchronized String getConnection(){
        String conn = null;
        try {
            conn = connPoll.poll(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(conn == null){
            if(connInUse.size() < maxSize){
                conn = "poll_new-"+ai.addAndGet(1);
                connInUse.add(conn);
            }
        }else{
            connInUse.add(conn);
        }
        System.out.println("G ConnPoll - "+connPoll);
        System.out.println("G Conn in Use - "+connInUse);
        System.out.println();
        return conn;
    }

    private synchronized void releaseConn(){

        if(!connInUse.isEmpty()){
            int n = (int) Math.random()*connInUse.size();
            String conn = connInUse.get(n);
            connInUse.remove(n);
            connPoll.offer(conn);
        }else{
            System.out.println("No conn used");
        }

        System.out.println("R ConnPoll - "+connPoll);
        System.out.println("R Conn in Use - "+connInUse);
        System.out.println();
    }

    public MyConnectionPool(){
        init();

    }

    private void init(){
        ai.addAndGet(initSize);
        connPoll = new PriorityBlockingQueue<String>(ai.get());
        for(int i=0;i<ai.get();i++){
            connPoll.add("pool-"+(i+1));
        }
    }

    public BlockingQueue<String> getConnPoll() {
        return connPoll;
    }

    public List<String> getConnInUse() {
        return connInUse;
    }
}

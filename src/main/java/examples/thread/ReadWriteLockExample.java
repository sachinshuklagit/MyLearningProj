package examples.thread;

import util.GenUtil;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Shukla, Sachin. on 1/2/16.
 */

//When a thread acquires the write lock of a read-write-lock then other read threads are not able to read until the write operation is over.
public class ReadWriteLockExample {

    public static void main(String...args) throws Exception{
        ReadWriteLockExample obj = new ReadWriteLockExample();
        obj.init();
    }

    public void init() throws Exception{
        final SharedResource sr = new SharedResource();

        ExecutorService manager = Executors.newFixedThreadPool(6);
        manager.submit(new WriteProcess(sr));
        for(int i=0;i<5;i++){
            manager.submit(new ReadProcess(sr));
        }
        manager.shutdown();

        manager.awaitTermination(1, TimeUnit.MINUTES);
        GenUtil.printObjWithThreadInfo("Done!!");

    }

    public static class WriteProcess implements Runnable{

        private SharedResource sharedResource;
        public WriteProcess(SharedResource sharedResource){
            this.sharedResource = sharedResource;
        }

        @Override
        public void run(){

            for(int i=0;i<10000;i++){
                sharedResource.updateArr();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            GenUtil.printObjWithThreadInfo("Write operation over");
            System.exit(0);
        }
    }

    public static class ReadProcess implements Runnable{

        private SharedResource sharedResource;
        public ReadProcess(SharedResource sharedResource){
            this.sharedResource = sharedResource;
        }

        @Override
        public void run(){
            for(int i=0;i<10000;i++){
                sharedResource.readArr();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            GenUtil.printObjWithThreadInfo("Read operation over");
        }
    }

    public static class SharedResource{
        private final ReadWriteLock rwl = new ReentrantReadWriteLock();

        int[] a = new int[]{1,1,1,1,1,1,1,1,1,1};

        public void readArr(){
            rwl.readLock().lock();
//            GenUtil.printObjWithThreadInfo("Read Lock acquired...");
            try{
                StringBuilder sb = new StringBuilder();
                for(int i : a){
                    sb.append(i+" ");
                }
                GenUtil.printObjWithThreadInfo(sb.toString());
            }finally {
                rwl.readLock().unlock();
//                GenUtil.printObjWithThreadInfo("Read Lock released...");
            }
        }

        public void updateArr(){
            rwl.writeLock().lock();
//            GenUtil.printObjWithThreadInfo("Write Lock acquired...");
            try {
                boolean b = new Random().nextBoolean();
                int x = new Random().nextInt(5);
                x = (b) ? x : -x;

                for (int i = 0; i < a.length; i++) {
                    a[i] += x;
                }
            }finally {
                rwl.writeLock().unlock();
//                GenUtil.printObjWithThreadInfo("Write Lock released...");
            }
        }

    }
}

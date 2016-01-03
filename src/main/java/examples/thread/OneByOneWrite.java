package examples.thread;

import util.GenUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Shukla, Sachin. on 1/2/16.
 */
public class OneByOneWrite {
    public static void main(String...args) throws Exception{
        OneByOneWrite obj = new OneByOneWrite();
//        obj.methodOne();
//        obj.methodTwo();
        obj.volatileCheck();
    }

    private void volatileCheck() throws Exception{
        VolatileCheckClass volatileCheckClass = new VolatileCheckClass(100);
        Runnable rIncreaseValue = new MyRunnableForVolatileCheck(volatileCheckClass, 1);
        Runnable rDecreaseValue = new MyRunnableForVolatileCheck(volatileCheckClass, 2);
        Thread tIncreaseValue = new Thread(rIncreaseValue);

        Thread tDecreaseValue = new Thread(rDecreaseValue);

        tIncreaseValue.start();
        tDecreaseValue.start();

        tIncreaseValue.join();
        tDecreaseValue.join();

        GenUtil.printObjWithThreadInfo("Final value = "+volatileCheckClass.getD());
        GenUtil.printObjWithThreadInfo("Done!!");

    }

    public void methodOne() throws Exception{

        Runnable r = new MyRunnable("sachinshukla");
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        Thread t3 = new Thread(r);

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        GenUtil.printObjWithThreadInfo("Done!!");
    }
    public void methodTwo() throws Exception{
        Runnable r = new MyRunnable2("MyNameIsSachinKumarShukla");
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        GenUtil.printObjWithThreadInfo("Done!!");
    }

    public static class MyRunnable implements Runnable{

        private String str = null;
        private AtomicInteger counter = new AtomicInteger(0);
        private Lock lock = new ReentrantLock(true);

        public MyRunnable(String str){
            this.str = str;
        }

        @Override
        public void run(){

            for(;counter.get() < str.length();counter.addAndGet(1)){
                lock.lock();
                if(counter.get() == str.length()){
                    return;
                }
                try{
                    GenUtil.printObjWithThreadInfo(String.valueOf(str.charAt(counter.get())));
                    GenUtil.sleepInMilliSeconds(200);
                }finally {
                    lock.unlock();
                }
            }

        }
    }

    public static class MyRunnable2 implements Runnable{

        private String str = null;
        private AtomicInteger counter = new AtomicInteger(0);
        private Lock lock = new ReentrantLock(true);
        private Condition condition = lock.newCondition();
        private long firstThreadId=0,secondThreadId=0;

        public MyRunnable2(String str){
            this.str = str;
        }

        @Override
        public void run(){

            for(;counter.get() < str.length();){

                try {
                    lock.lock();
                    if(counter.get() == str.length()){
                        return;
                    }
                    if (firstThreadId == 0 || Thread.currentThread().getId() == firstThreadId) {
                        firstThreadId = Thread.currentThread().getId();
                    } else if (secondThreadId == 0) {
                        secondThreadId = Thread.currentThread().getId();
                    }

                    while (Thread.currentThread().getId() == firstThreadId && counter.get() % 2 != 0) {
                        condition.await();
                    }
                    while (Thread.currentThread().getId() == secondThreadId && counter.get() % 2 != 1) {
                        condition.await();
                    }

                    GenUtil.printObjWithThreadInfo(String.valueOf(str.charAt(counter.get())));
                    condition.signalAll();
                    counter.addAndGet(1);
//                    GenUtil.sleepInMilliSeconds(200);
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
                finally {
                    lock.unlock();
                }
            }

        }
    }

    public static class MyRunnableForVolatileCheck implements  Runnable{

        private VolatileCheckClass volatileCheckClass;
        private int  flow;
        public MyRunnableForVolatileCheck(VolatileCheckClass volatileCheckClass, int flow){
            this.volatileCheckClass = volatileCheckClass;
            this.flow = flow;
        }

        @Override
        public void run(){

            int x = 0;
            if(flow == 1){
                x = 5;
            }else{
                x = -5;
            }
            for(int i=0;i<10000;i++){
                volatileCheckClass.incrVal(x);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    public static class VolatileCheckClass{

        private volatile int d;

        public VolatileCheckClass(int d){
            this.d = d;
        }

        public int getD() {
            return d;
        }

        public void setD(int d) {
            this.d = d;
        }

        public void incrVal(int x){
             d += x;
        }
    }

}

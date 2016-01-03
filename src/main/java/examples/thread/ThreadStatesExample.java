package examples.thread;

import util.GenUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Shukla, Sachin. on 1/3/16.
 */
public class ThreadStatesExample {

    public static void main(String...args) throws Exception{
        ThreadStatesExample obj = new ThreadStatesExample();
        obj.init();
    }

    private void init() throws Exception{
        final Thread t1 = new Thread(new MyRunnableOne());
        Thread t2 = new Thread(new MyRunnableTwo());

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                GenUtil.printObjWithThreadInfo("Thread t1 state  - "+t1.getState());
                if("TERMINATED".equalsIgnoreCase(t1.getState().toString())){
                    GenUtil.printObjWithThreadInfo("Good Bye!!");
                    System.exit(0);
                }
            }
        },0,1000);

        Thread.currentThread().sleep(2000);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        GenUtil.printObjWithThreadInfo("Thread t1 state at the end - "+t1.getState());
        GenUtil.printObjWithThreadInfo("Done!!!!");
    }

    public static class MyRunnableOne implements Runnable{

        @Override
        public void run(){
            GenUtil.printObjWithThreadInfo("MyRunnableOne#run()...doing some processing....");
            long begin = System.currentTimeMillis();
            for(;System.currentTimeMillis()-begin < 10 * 1000;){
            }

            GenUtil.printObjWithThreadInfo("Done with processing, going to sleep for sometime....");
            GenUtil.sleepInSeconds(5);

            GenUtil.printObjWithThreadInfo("Done with sleeping..., going to hold of an Object which some other thread already holds");

            synchronized (ThreadStatesExample.class){
                GenUtil.printObjWithThreadInfo("MyRunnableOne#run()...synchronized block....start");
                GenUtil.printObjWithThreadInfo("MyRunnableOne#run()...synchronized block....end");
            }
            GenUtil.printObjWithThreadInfo("finished MyRunnableOne#run()...");
        }

    }
    public static class MyRunnableTwo implements Runnable{

        @Override
        public void run(){

            synchronized (ThreadStatesExample.class){
                GenUtil.printObjWithThreadInfo("MyRunnableTwo#run()...synchronized block....start");
                GenUtil.sleepInSeconds(25);
                GenUtil.printObjWithThreadInfo("MyRunnableTwo#run()...synchronized block....end");
            }

        }

    }

}

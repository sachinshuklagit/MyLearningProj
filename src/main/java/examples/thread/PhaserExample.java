package examples.thread;

import util.GenUtil;

import java.util.concurrent.Phaser;

/**
 * Created by Shukla, Sachin. on 1/1/16.
 */
public class PhaserExample {

    public static void main(String...args) throws Exception{
        PhaserExample obj = new PhaserExample();
        obj.init();
    }

    public void init() throws Exception{
        Phaser phaser = new Phaser(2);

        Thread t1 = new Thread(new MyRunnable(phaser,1));
        Thread t2 = new Thread(new MyRunnable(phaser,5));
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        GenUtil.printObjWithThreadInfo("Done!!");

    }


    public static class MyRunnable implements Runnable{

        Phaser phaser = null;
        int sleep = 0;

        public MyRunnable(Phaser phaser, int sleep){
            this.phaser = phaser;
            this.sleep = sleep;
        }

        @Override
        public void run(){
            GenUtil.printObjWithThreadInfo("Entering run() method, going to sleep for \""+sleep+"\" seconds");
            GenUtil.sleepInSeconds(sleep);
            GenUtil.printObjWithThreadInfo("Done with sleeping.....but this thread can prosceed only if all the number of threads" +
                    " of phase has been initiated, as the call would be blocked by phaser's arriveAndAwaitAdvance() method.");
            phaser.arriveAndAwaitAdvance();
            GenUtil.printObjWithThreadInfo("Main processing started after arrive and await call of phaser when all the threads have joined.");

            process();

            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();
//            GenUtil.sleepInSeconds(1);
            GenUtil.printObjWithThreadInfo("Deregister done successfully - "+phaser.isTerminated());
        }

        private void process(){
            GenUtil.sleepInSeconds(10 - sleep);
            GenUtil.printObjWithThreadInfo("processing done, took time in seconds : "+ (10 - sleep));

        }
    }
}

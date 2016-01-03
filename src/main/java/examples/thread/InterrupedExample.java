package examples.thread;

import java.util.Random;

/**
 * Created by Shukla, Sachin. on 1/1/16.
 */
public class InterrupedExample {

    public static void main(String[] args) throws Exception{

        Thread t1 = new Thread(new MyTask());
        t1.start();

        Thread.currentThread().sleep(1000 * 2);
        t1.interrupt();
        System.out.println("Thread interruption invoked....");
    }

    public static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println("Inside MyTask#run()....start");
                try {
//                    Thread.currentThread().sleep(100);
                    doSomething(10);
                } catch (InterruptedException e) {
                    System.out.println("sleep disturbed...."+e.getMessage());
//
            }

            if(Thread.currentThread().isInterrupted()){
                System.out.println("interruped....");
            }
            System.out.println("Inside MyTask#run()....end");
        }

        private void doSomething(int n) throws InterruptedException{

            long sum = 0;
            for(long l=0;l<100000000;l++){
                sum += l;
            }
            System.out.println("Going to do something better.....");
            doSomething(n+1);
        }
    }
}

package examples.thread;

import java.util.concurrent.locks.*;

/**
 * Created by Shukla, Sachin. on 1/1/16.
 */
public class DeadLockExample {

    Lock l1 = new ReentrantLock();

    public static void main(String...args) throws Exception{
        DeadLockExample obj = new DeadLockExample();
        obj.init();
    }

    public void init() throws Exception{
        MyDeadLockClass myClass = new MyDeadLockClass();

        Thread t1 = new Thread(new MyRunnable(myClass,1));
        Thread.sleep(100);
        Thread t2 = new Thread(new MyRunnable(myClass,2));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static class MyRunnable implements Runnable{
        private MyDeadLockClass myClass;
        private int flow;

        public MyRunnable(MyDeadLockClass myClass, int flow){
            this.myClass = myClass;
            this.flow = flow;
        }

        @Override
        public void run(){
            if(flow == 1){
                myClass.methodX();
            }else if (flow == 2){
                myClass.methodY();
            }

        }
    }

    public static class MyDeadLockClass{

        private Lock lockOne = new ReentrantLock();
        private Lock lockTwo = new ReentrantLock();

        public void methodX(){

            lockOne.lock();
            System.out.println(Thread.currentThread().getName()+": Entering method X");

            try{
                System.out.println(Thread.currentThread().getName()+": Method X processing.......");
                Thread.sleep(1000);
                this.methodY();
            }catch(InterruptedException e){
                e.printStackTrace();
            } finally{
                lockOne.unlock();
            }

            System.out.println(Thread.currentThread().getName()+": Exiting method X");
        }

        public void methodY(){

            lockTwo.lock();
            System.out.println(Thread.currentThread().getName()+": Entering method Y");

            try{
                System.out.println(Thread.currentThread().getName()+": Method Y processing.......");
                Thread.sleep(1000);
                methodX();
            }catch(InterruptedException e){
                e.printStackTrace();
            }finally {
                lockOne.unlock();
            }

            System.out.println(Thread.currentThread().getName()+": Exiting method Y");
        }
    }


}

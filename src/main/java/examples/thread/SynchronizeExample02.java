package examples.thread;

/**
 * Created by Shukla, Sachin. on 1/1/16.
 */

// If a thread is accessing a method with synchronized keyword then it gets lock on all synchronized methods and
    // none of the other threads can enter other synchronized method until its finished.
public class SynchronizeExample02 {

    public static void main(String...args){
        SynchronizeExample02 obj = new SynchronizeExample02();
        obj.init();
    }

    public void init(){
        MyClass myClass = new MyClass();
        Thread t1 = new Thread(new MyRunnable(myClass,1));
        Thread t2 = new Thread(new MyRunnable(myClass,2));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done!!!!");
    }


    public static class MyRunnable implements Runnable{

        private MyClass myClass;
        private int methodToRun;

        public MyRunnable(MyClass myClass, int methodToRun){
            this.myClass = myClass;
            this.methodToRun = methodToRun;
        }

        @Override
        public void run() {
            if(methodToRun == 1){
                myClass.methodONe();
            }else if (methodToRun == 2){
                myClass.methodTwo();
            }
        }
    }

    public static class MyClass{

        public synchronized void methodONe(){
            System.out.println("Entering methodOne....");

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Exiting methodOne....\n");
        }

        public synchronized void methodTwo(){
            System.out.println("Entering methodTwo....");


            System.out.println("Exiting methodTwo....");
        }
    }


}

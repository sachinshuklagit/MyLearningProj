package examples.thread;

import util.GenUtil;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Shukla, Sachin. on 1/1/16.
 */
public class BankTransferExample {

    public static void main(String...args) throws Exception{
        BankTransferExample obj = new BankTransferExample();
        obj.init();
    }

    public void init() throws Exception{

        final Account account = new Account();
//        Thread t1 = new Thread(new MyRunnable(account,true));
//        Thread t2 = new Thread(new MyRunnable(account,false));
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(new MyRunnable(account,true));
        executor.submit(new MyRunnable(account,false));
        executor.shutdown();
//        t1.start();
//        t2.start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                account.printAccInfo();
            }
        },0,2000);

        executor.awaitTermination(15, TimeUnit.SECONDS);
//        t1.join();
//        t2.join();
        GenUtil.printObjWithThreadInfo(account.toString());
        GenUtil.printObjWithThreadInfo("Done!!");
        System.exit(0);
    }

    public static class MyRunnable implements Runnable {
        private Account account;
        private boolean transferFlag;

        // if transfer flag is true then transfer A to B else B to A.
        public MyRunnable(Account account, boolean transferFlag){
            this.account = account;
            this.transferFlag = transferFlag;
        }

        @Override
        public void run() {
            for(int i=0;i<10000;i++){
                int x = new Random().nextInt(50);
                x = transferFlag?x:-x;
                account.transfer(x);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            GenUtil.printObjWithThreadInfo("run method finished...");
        }
    }

    public static class Account{
        private int balanceA = 1000;
        private int balanceB = 1000;
        private Lock lock = new ReentrantLock(false); //non-fair mode.
        private Condition insufficientCondition = lock.newCondition();

        @Override
        public String toString(){
            return "Account:balanceA="+balanceA+", balanceB="+balanceB+", total="+(balanceA + balanceB);
        }

        public void printAccInfo(){
            lock.lock();
            try{
                GenUtil.printObjWithThreadInfo(this.toString());
            }finally {
                lock.unlock();
            }
        }

        // Transfer x amount from A to B.
        public  void transfer(int x){
            lock.lock();

            try {
                while ((x > 0 && balanceA < x) || (x < 0 && balanceB < -x)) {
                    insufficientCondition.await();
//                    insufficientCondition.await(2, TimeUnit.SECONDS);
                }
                balanceA -= x;
                balanceB += x;
                insufficientCondition.signalAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            finally{
                lock.unlock();
            }
        }

        public int getBalanceA() {
            return balanceA;
        }

        public void setBalanceA(int balanceA) {
            this.balanceA = balanceA;
        }

        public int getBalanceB() {
            return balanceB;
        }

        public void setBalanceB(int balanceB) {
            this.balanceB = balanceB;
        }
    }
}

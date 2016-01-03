package examples.thread.other;

/**
 * Created by Shukla, Sachin. on 1/1/16.
 */
public class SynchronizeExample {

    public static void main(String...args){
        Account acc = new Account();
        Thread t1 = new Thread(new Company(acc));
        Thread t2 = new Thread(new Bank(acc));

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After operation, final balance is : "+acc.getBalance());
    }

    public static class Account {
        private int balance = 1000;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public  synchronized void addAmount(int amount){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance += amount;
        }
        public synchronized void substractAmount(int amount){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance -= amount;
        }
    }

    public static class Company implements Runnable{
        Account acc = null;
        public Company(Account acc){
            this.acc = acc;
        }
        @Override
        public void run(){
            for(int i=0;i<100;i++){
                acc.addAmount(1000);
            }

        }
    }

    public static class Bank implements Runnable{
        Account acc = null;
        public Bank(Account acc){
            this.acc = acc;
        }
        @Override
        public void run(){
            for(int i=0;i<100;i++){
                acc.substractAmount(1000);
            }

        }
    }

}

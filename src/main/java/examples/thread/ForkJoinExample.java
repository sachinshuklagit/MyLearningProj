package examples.thread;


import util.GenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Shukla, Sachin. on 1/2/16.
 */

//Fork-Join, A "Work-Steal" algorithm in Java7.
public class ForkJoinExample {

    public static void main(String...args) throws Exception{
        ForkJoinExample obj = new ForkJoinExample();
        obj.init();
    }

    public void init() throws Exception{
        List<Product> productList = getDummyProducts(10);

        GenUtil.printObjWithThreadInfo("Before-"+productList.toString());
        ForkJoinPool pool = new ForkJoinPool(); //no argument creates the same number of threads as of the number of processors.
        RecursiveTask<Float> task = new Task(productList,0,productList.size()-1);
        pool.execute(task);

        pool.awaitTermination(5, TimeUnit.SECONDS);
        GenUtil.printObjWithThreadInfo("After-"+productList.toString());
        GenUtil.printObjWithThreadInfo("Done!! - "+task.get());
    }


    public static class Task extends RecursiveTask<Float> {
        private List<Product> productList = null;
        private int start = 0;
        private int end = 0;

        public Task(List<Product> productList, int start, int end){
            this.productList = productList;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Float compute() {
            GenUtil.sleepInSeconds(1);
            GenUtil.printObjWithThreadInfo("start="+start+", end="+end);
            float a = 0;
            if(end-start<3){
                for(int i=start;i<=end;i++){
                    a = a + productList.get(i).getPrice();
                    productList.get(i).setPrice((float)( productList.get(i).getPrice()*1.2) ) ;
                }
                return a;
                //
            }else{
                int middle = (start + end)/2;
                Task t1 = new Task(productList, start, middle);
                Task t2 = new Task(productList, middle + 1, end);

                //This is a synchronous process, the worker-thread which started this task, divides it into two and once
                //when both are finished, then it joins. Also meanwhile when the child tasks are busy in their computation
                //this worker-thread (which initiates the child processes) utilizes its time to finish other pending task - this concept is known as work-steal algorithm.
                //its implemented by "ForkJoinPool" class if passed any "ForkJoinTask" task (e.g. Task in this case)..Also note, if passed Runnable/Callable then it doesn't apply work-steal algirthm.
                invokeAll(t1,t2);
                try {
                    return t1.get() +  t2.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            GenUtil.printObjWithThreadInfo("Done calculation for the case where, start="+start+", end="+end);
            return null;
        }
    }

    private List<Product> getDummyProducts(int numProducts){
        List<Product> list = new ArrayList<Product>(numProducts);
        for(int i=0;i<numProducts;i++){
            list.add(new Product("Product-"+(i+1),(i+1)));
        }
        return list;
    }

    public static class Product{
        private String productName;
        private float price;

        public Product(String productName, float price){
            this.productName = productName;
            this.price = price;
        }

        @Override
        public String toString(){
            return "productName="+productName+", price="+price;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }
}

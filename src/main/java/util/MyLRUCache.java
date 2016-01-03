package util;

import java.util.*;

/**
 * Created by Shukla, Sachin. on 1/2/16.
 */
public class MyLRUCache<K, V>  extends LinkedHashMap<K, V>{

    private final int cacheSize;

    public MyLRUCache(){
        this(5);
    }
    public MyLRUCache(int cacheSize){
        this.cacheSize = cacheSize;
    }

    public static void main(String[] args){
        MyLRUCache<String,String> lruCache = new MyLRUCache<String, String>(5);
        System.out.println("Enter -1 to exit....\n");
        Scanner sc = new Scanner(System.in);
        for(;;){
            String s = sc.nextLine();
            if("-1".equals(s)){
                System.out.println("Exiting..Good Bye!!..");
                return;
            }
            lruCache.put(s,"test value");
            System.out.println("Current Cache -> "+lruCache);
        }
    }

    @Override
    public V put(K key, V value){
        if(get(key) != null) {
            this.remove(key);
        }
        return super.put(key,value);
    }

    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return this.size() > cacheSize;
    }
}
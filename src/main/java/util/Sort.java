package util;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shukla, Sachin. on 12/25/15.
 */
public class Sort<T extends Comparable> {


    public static void main(String[] args) throws Exception{
        Integer[] a = new Integer[]{2, 5, -2, 6, -3, 8, 0, -7, -9, 4};
        printArr(a);
//        quickSort(a);
        insertionSort(a);
        printArr(a);
    }


    private static void insertionSort(Integer[] a){
        for(Integer i=1;i<a.length;i++){
            swapTillBalanced(a,i);
        }
    }

    private static void swapTillBalanced(Integer[] a, int p){
        if(p == 0){
            return;
        }
        if(a[p-1] > a[p]){
            swap(a,p-1,p);
            swapTillBalanced(a,p-1);
        }
    }

    public static <T extends Comparable> void quickSort(T[] a){
        quickSort(a,0,a.length-1);
    }


    private static <T extends Comparable<? super T>> void quickSort(T[] a, int start, int end){
        if(end <=start) {
            return;
        }
        if(end == start + 1) {
            if(a[end].compareTo(a[start]) < 0) {
                swap(a,start,end);
            }
            return;
        }
        T pivot = a[start];
        boolean foundLM = false, foundRM = false;
        int lm = start + 1, rm = end;
        for (;lm != rm;) {

            if(a[lm].compareTo(pivot) < 0){
                ++lm;
            }else{
                foundLM = true;
            }
            if(a[rm].compareTo(pivot) > 0 && rm > lm){
                --rm;
            }else{
                foundRM = true;
            }

            if(foundLM && foundRM && lm<rm){
                foundLM = false;
                foundRM = false;
                swap(a,lm,rm);
            }
        }
        if(a[lm].compareTo(pivot) > 0){
            lm = lm - 1;
            rm = lm;
        }
        swap(a,start,lm);
        quickSort(a,start,lm-1);
        quickSort(a,rm+1,end);
    }

    private static <T extends Comparable> void swap(T[] a, int pos1, int pos2){
        T tmp = a[pos1];
        a[pos1] = a[pos2];
        a[pos2] = tmp;
    }

    private static void printArr(Integer[] a) {
        for(int i : a) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }
}


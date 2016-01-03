package util;

/**
 * Created by Shukla, Sachin. on 12/26/15.
 */
public class KthMinElem {
    private static final int[] arr = new int[]{2, 5, -2, 6, -3, 8, 0, -7, -9, 4};
//    private static  int kthLargest = 2;
//    private static  int kthSmallest = 1 + arr.length - kthLargest;
    private static  int kthSmallest = 4;

    public static void main(String[] args){

        int kthMin = quickSort(arr, 0, arr.length - 1);
        System.out.println("kth min val = "+kthMin);
    }

    private static int  quickSort(int[] a, int start, int end){

        int pivot = a[start];
        int lm = start + 1;
        int rm = end;
        for(;lm<rm;){
            boolean foundLM = false;
            boolean foundRM = false;
            if(a[lm] < pivot){
                ++lm;
            }else{
                foundLM = true;
            }
            if(a[rm] > pivot && rm>lm){
                --rm;
            }else{
                foundRM = true;
            }

            if(foundLM && foundRM){
                swap(a,lm,rm);
            }
        }
        if(a[lm] > pivot){
            lm = lm-1;
            rm = lm;
        }
        swap(a,start,lm);
        if(lm == kthSmallest -1){
            System.out.println("Done..");
            return a[lm];
        }else if(lm > kthSmallest -1){
            return quickSort(a,start,lm-1);
        }else if(lm < kthSmallest -1){
            return quickSort(a,lm+1,end);
        }
        return Integer.MIN_VALUE;
    }

    private static void swap(int[] a, int pos1, int pos2){
        int tmp = a[pos1];
        a[pos1] = a[pos2];
        a[pos2] = tmp;
    }



}

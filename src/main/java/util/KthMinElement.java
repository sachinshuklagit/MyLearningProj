package util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Shukla, Sachin. on 12/31/15.
 */
public class KthMinElement <T extends Comparator> {

    Object[] objArr = null;

    public static void main(String[] args){

        Integer[] a = new Integer[]{3,6,5,2,8,1,7,-2}; //-2, 1,2,3,5,6,7,8 is the sorted value.

        Integer val = KthMinElement.getKthMinElem(1,Arrays.asList(a), new Comparator<Integer>(){

            @Override
            public int compare(Integer t1, Integer t2) {
                return 1 * (t1-t2);
            }
        });

        System.out.println(val);
    }

    public KthMinElement(){
    }
    public KthMinElement(List<T> list){
        objArr = list.toArray();
    }



    public static <T> T getKthMinElem(int k, List<T> list, Comparator comp){
        Object[] objArr = list.toArray();
        return (T)getKthMinElem(k,objArr, 0,objArr.length-1, comp);
    }

    private static Object getKthMinElem(int k, Object[] objArr, int start, int end, Comparator comp){
        Object pivot = objArr[start];
        int lm = start + 1;
        int rm = end;
        boolean foundLM = false, foundRM = false;

        for(;lm<rm;){
            if( comp.compare(objArr[lm], pivot) <= 0){
                ++lm;
            }else{
                foundLM = true;
            }
            if( (comp.compare(objArr[rm], pivot) >= 0 ) && (lm < rm)  ) {
                --rm;
            }else{
                foundRM = true;
            }

            if(foundLM && foundRM){
                foundLM = false;
                foundRM = false;
                swap(objArr, lm, rm);
            }
        }

        if(comp.compare(objArr[lm], pivot) >= 0 ){
            --lm;
        }
        swap(objArr, start, lm);
        if(lm == k-1){
            return objArr[lm];
        }
        else if (lm > k-1){
            return getKthMinElem(k, objArr, start,lm-1, comp);
        }else{
            return getKthMinElem(k, objArr, lm+1,end, comp);
        }

    }

    private static void swap(Object[] objArr, int p1, int p2){
        Object obj = objArr[p1];
        objArr[p1] = objArr[p2];
        objArr[p2] = obj;
    }

}

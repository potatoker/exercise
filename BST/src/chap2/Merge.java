package chap2;

import java.util.Comparator;

/**
 * Created by raymond on 6/23/16.
 */
public class Merge {

    private static Comparable[] aux;

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }


    public static void sort(Comparable[] a){
        aux = new Comparable[a.length];
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi){
        if(hi < lo)
            return;

        int mid = (lo+hi)/2;
        sort(a, lo, mid);
        sort(a, mid+1, hi);
        merge(a, lo, mid, hi);
    }



    public static void merge(Comparable[] a, int lo, int mid, int hi){

        int i = lo;
        int j = mid;

        //这里和之前写的不同的在于,这个类中构造了一个和a相同大小的静态辅助数组变量。
        for(int k = lo; k <= hi; k++){
            aux[k] = a[k];
        }

        for(int k = lo; k <= hi; k++){
            if(less(aux[i], aux[j]))
                a[k] = aux[i++];
            else if(i > mid)
                a[k] = aux[j++];
            else if(j > hi)
                a[k] = aux[i++];
            else
                a[k] = aux[j++];
        }

    }



}

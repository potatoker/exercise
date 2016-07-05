package chap2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by raymond on 6/23/16.
 */
public class Quick {



    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void sort(Comparable[] a){
        StdRandom.shuffle(a); //打乱数组
        sort(a, 0, a.length-1);
    }

    public static void sort(Comparable[] a, int lo, int hi){

        if(hi <= lo)
            return;

        int p = partition(a, lo,  hi);
        sort(a, lo, p-1);
        sort(a, p+1, hi);

    }


    public static int partition(Comparable[] a, int lo, int hi){
        //将数组切分为a[lo..i-1], a[i], a[i+1...hi]

        //枢值定为a[lo]
        int i = lo, j= hi+1;

        Comparable v = a[lo];

        while(true){
            while(less(a[++i], v)) if(i == hi) break;
            while(less(v, a[--j])) if(j == lo) break;

            if(i >= j)
                break;

            exch(a,i,j);
        }

        exch(a, lo, j);
        return j;
    }

    public static boolean isSorted(Comparable[] a){

        for(int i = 0; i < a.length; i++){
            if(less(a[i+1],a[i]))
                return false;
        }
        return true;

    }

    private static void show(Comparable[] a){
        for(int i = 0; i < a.length; i++){
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static void main(String[] args){
        String[] a = new String[]{"v","b","e","z","p"};
        sort(a);
        assert isSorted(a);
        show(a);
    }

}

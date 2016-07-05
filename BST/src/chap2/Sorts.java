package chap2;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by raymond on 16/5/18.
 */

// 在大多数情况下,排序到吗只会通过两个方法操作数据:less()方法对元素进行比较,exch()方法将元素交换位置,
    //所以各种排序算法那都有这两个共用的函数,另外还有其他一些公共函数,这里取一个模板
public class Sorts {

    public static void sort(Comparable[] a){
        //关键的排序算法部分
    }

    private static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void show(Comparable[] a){
        for(int i = 0; i < a.length; i++){
            StdOut.print(a[i] + " ");
        }
        StdOut.println();
    }

    public static boolean isSorted(Comparable[] a){

        for(int i = 0; i < a.length; i++){
            if(less(a[i+1],a[i]))
                return false;
        }
        return true;

    }


    public static void main(String[] args){
        String[] a = In.readStrings();
        sort(a);
        assert isSorted(a);
        show(a);
    }


    //排序成本模型是比较和交换的数量,其实这里严格意义上还不好叫时间复杂度,这里先规定后面的都同意分析比较和交换次数吧


    //选择排序,找到数组中的最小的元素,使他和第一个元素交换位置,然后在剩下的元素中找到最小的,和第二个元素交换位置
    //以此下去,在于不断地选择剩余元素中最小者

    public static void selectionSort(Comparable[] a){
        int min;
        for(int i = 0; i < a.length; i++){
                min = i;
            for(int j = i; j < a.length; j++){
                if (less(a[j], a[min]))
                    min = j;
            }
            exch(a, min, i);
        }
    }

    //选择排序需要N次交换,(N-1)+(N-2)+....+2+1 = N(N-1)/2~N^2/2次比较,与输入数组无关



    //插入排序,假设有一个乱序牌组,从第二张开始,在左边的有序数组中找到位置,为此,要将有序数组中比他大的元素都右移为其提供位置

    public static void insertionSort(Comparable[] a){
        for(int i = 1; i < a.length; i++){
            for(int j = i; j > 0 && less(a[j],a[j-1]); j--){
                exch(a,j-1,j);
            }
        }
    }

    //插入排序所需时间取决于输入中元素的初始顺序,
    //最坏情况,逆序时,需要~N^2/2次比较和交换
    //最好情况,即初始有序,需要N-1次比较和0次交换




    //希尔排序

    public static void shellSort(Comparable[] a){
        int N = a.length;
        int h = 1;

        while(h < N/3) h = 3*h + 1;

        while(h >= 1){
            for (int i = h; i < N; i++){
                for(int j = i; j>=h && less(a[j], a[j-h]); j = j-h)
                    exch(a, j-h, j);
            }
            h = h/3;
        }
    }

    //书中没有研究其性能,但一般认为是优于插入排序的














}

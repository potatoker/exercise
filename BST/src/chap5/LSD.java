package chap5;

/**
 * Created by raymond on 7/1/16.
 */
public class LSD {
    //前W个字符对字符数组a排序,指排序字符串的有效位数
    public static void sort(String[] a, int W){
        int N = a.length;
        //a中字符串都是由ascii字符组成的
        int R = 256;
        String[] aux = new String[N];
        for(int d = W-1; d >= 0; d--){

            int count[] = new int[R+1];
            for(int i = 0; i < N; i++){
                count[a[i].charAt(d)+1] += 1;
            }//count[c]表示key=c-1的集合的个数

            for(int r = 0; r < R+1; r++){
                count[r+1] = count[r+1] + count[r];
            }//count[r]表示key=r的元素集合在排序数组中的起始index

            for(int i = 0; i < N; i++){
                aux[count[a[i].charAt(d)]++] = a[i];
            }
            for(int i = 0; i < N; i++){
                a[i] = aux[i];
            }
        }
    }
}

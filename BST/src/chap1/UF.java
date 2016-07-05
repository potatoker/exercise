package chap1;

/**
 * Created by raymond on 6/22/16.
 */
public class UF {

    private int[] id; //分量标识符数组,id[i]表示触点i所在的连通分量标识符
    private int count; //连通分量的个数;

    public UF(int N){
        count = N;
        id = new int[N];

        //初始化连通分量,每个触点(0到N-1)的连通分量为i,表示初始时每个触点各自形成连通分量
        for (int i = 0; i < N; i++){
            id[i] = i;
        }
    }

    public int count(){
        return count;
    }


    public boolean connected(int p, int q){    //判断两个触点是否属于同一个连通分量
        return find1(p) == find1(q);
     }


    //version1 quick find algorithm

    //第一种构想,quick find, id[i]即为

    public int find1(int p){
        return id[p];
    }


    public void union1(int p, int q){     //连接p,q所在的两个连通分量
        int pID = find1(p);
        int qID = find1(q);

        if(pID == qID)
            return;

        //这里选择把p,q所在的两个连通分量融合,让这个新的分量的所有触点的标识符相等,这里都为p的原本的标识符,
        //这里遍历触点,把所有q原本在的连通分量的所有触点的标识符更新
        for(int i =0; i < id.length; i++)
            if(id[i] == qID)
                id[i] = pID;

        count --;
    }

    //version2 quick union algorithm

    public int find2(int p){
        int i = p;
        while(id[i] != i){
            i = id[i];
        }

        return i;
    }


    public void union2(int p, int q){
        int qRoot = find2(q);
        int pRoot = find2(p);

        if(pRoot == qRoot)
            return;

        id[qRoot] = pRoot;
        count --;
    }

    //version3, 加权quick-union
    // 与其像在quick-union中，随意将一棵树连接到另一棵树，这里记录每一棵树的大小，并总是将较小的树连接到加大的树的根上。
    //感觉这里加权二字不是我们平时所说的那种加权的感觉,


}

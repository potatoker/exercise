package chap1;

/**
 * Created by raymond on 6/22/16.
 */
public class WeightedQuickUnionUF {

    private int[] id;
    private int[] treeSize;
    private int count;

    public WeightedQuickUnionUF(int N){
        count = N;

        for(int i = 0; i < N; i++){
            id[i] = i;
        }

        //初始时,一个触点自成一树,有N棵深度为1的树
        for(int i = 0; i < N; i++){
            treeSize[i] = 1;
        }
    }

    public int size(){
        return count;
    }


    public int find(int p){
        int i = p;
        while(id[i] != i){
            i = id[i];
        }

        return i;
    }

    //对边quick-union,可以发现nothing special,只是根据树的大小来决定如果和融合连通分量

    public void union(int p, int q){

        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot == qRoot)
            return;

        if(treeSize[pRoot] < treeSize[qRoot]){
            id[pRoot] = qRoot;
            treeSize[pRoot] += treeSize[qRoot];
        }else{
            id[qRoot] = pRoot;
            treeSize[qRoot] += treeSize[pRoot];
        }
        count--;

    }

    //这里其实还可以再优化,书上的习题里就又提出了一种压缩路径的方法,维护id数组,使得能有更高的效率查找树根


    public int OptimizedFind(int p){
        int i = p;
        while(id[i] != i){
            i = id[i];
        }
        int j = p;
        while(id[j] != j){
            j = id[j];
            id[j] = j;
        }

        id[p] = i;

        return i;
    }

    //发现优化其实就是把从p到树根路径上的所有触点的id值做了维护,既然已经找到了,就把他们都挂到树根下面,大大缩小树的深度
    //而且由于树以开始很小,所以这样优化还是挺合理的


}

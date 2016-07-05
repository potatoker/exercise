package sort;

/**
 * Created by raymond on 6/23/16.
 */
public class BST<Key extends Comparable<Key>, Value> {


//    public int size;

    public Node root;


    private class Node{
        private Key key;
        private Value val;
        private Node left;
        private Node right;
        private int N;

        public Node(Key key, Value val, int size){
            this.key = key;
            this.val = val;
            this.N = size;
        }
    }



    private Value get(Node x, Key key){

        if(x == null)
            return null;

        Value val;
        int cmp = key.compareTo(x.key);

        if(cmp < 0){
            return get(x.left, key);
        }else if(cmp > 0){
            return get(x.right, key);
        }else{
            return x.val;
        }

    }

    public Value get(Key key){
        return get(root, key);
    }

//    private void put(Node x, Key key, Value val){
//        if(x == null) {
//            x = new Node(key, val, 1);
//            return;
//        }
//
//        int cmp = key.compareTo(x.key);
//
//        if(cmp < 0){
//            put(x.left, key, val);
//
//        }else if(cmp > 0){
//            put(x.right, key, val);
//        }else if(cmp == 0){
//            x.val = val;
//        }
//
//    }

//    上面是我写的一坨狗屎

    public int size(){
        return size(root);
    }

    public int size(Node x){
        if(x == null)
            return 0;

        else return x.N;
    }





    private Node put(Node x, Key key, Value val){
        if(x == null){
            return new Node(key, val, 1);
        }

        int cmp = key.compareTo(x.key);

        if(cmp < 0){
            x.left = put(x.left, key, val);
        }else if(cmp > 0){
            x.right = put(x.right, key, val);
        }else if(cmp == 0){
            x.val = val;
        }

        x.N = size(x.left) + size(x.right);
        return x;
    }



    //想一想为什么你会写出上面这坨狗屎?
    //现在先这样,假设你没办法在脑海中一下在就判断出该怎么写,然后你尝试性的写下了上面这坨狗屎,
    //首先最容易找到错误并检验的一件事,你应该好好检查最特殊的情况,x==null的情况,很明显,如果为null,你那坨狗屎根本不会使
    //新的Node和树产生任何关系,所以必须有返回值,至少这一点你应该想到

    public void put(Key key, Value val){
        put(root, key, val);
    }


    public Node deleteMin(Node x){

        if(x.left == null)
            return x.right;

        x.left = deleteMin(x.left);

        x.N = size(x.left) + size(x.right) + 1;

        return x;

    }

    public Node min(Node x){
        if(x.left == null)
            return x;
        return min(x.left);
    }


    public Node delete(Node x, Key key){
        if(x == null){
            return null;
        }

        int cmp = key.compareTo(x.key);

        if(cmp < 0){
            x.left = delete(x.left, key);
        }else if(cmp > 0){
            x.right = delete(x.right, key);
        }else{

            if(x.left == null) return x.right;
            if(x.right == null) return x.left;

            Node t = x;

            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


    private Node floor(Node x, Key key){
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            return floor(x.left, key);
        if(cmp == 0) return x;
        else{
            Node t = floor(x.right,key);
            if(t != null) return t;
            else return x;
        }
    }


    private Node select(Node x, int k){
        if(x==null)
            return null;
        if(size(x.left) < k)
            return select(x.right, k-size(x.left)-1);
        else if(size(x.left) == k)
            return x;
        else
            return select(x.left, k);
    }


    private int rank(Node x, Key key){
        if(x==null)
            return 0;

        int cmp = key.compareTo(x.key);
        if(cmp == 0)
            return size(x.left);
        else if(cmp < 0)
            return rank(x.left, key);
        else
            return size(x.left) + 1 + rank(x.right,key);
    }

}

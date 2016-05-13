/**
 * Created by raymond on 16/3/28.
 */
public class BST<Key extends  Comparable<Key>, Value> {

    private Node root;

    private class Node{
        private Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N){
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }



    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x==null)
            return 0;
        else
            return x.N;
    }

    public Value get(Key key){
        return get(root, key);
    }

    //recursive method to find the val
    private Value get(Node x, Key key){
        if(x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            return get(x.left, key);
        else if(cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    public void put(Key key, Value val){
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val){
        if(x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            x.left = put(x.left, key, val);
        else if(cmp > 0)
            x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left)+size(x.right);
        return x;
    }

    public Key min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left == null)
            return x;
        return min(x.left);
    }

    public Key floor(Key key){
        Node x = floor(root, key);
        if(x == null)
            return null;
        return x.key;
    }

    //floor表示树中最大的比给定key小的key值
    private Node floor(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)
            return x;
        //如果根节点的key大于给定Key,那么key的floor肯定在根节点左子树中,因为右子树肯定比Key要大
        if(cmp < 0)
            return floor(x.left, key);
        //如果根节点key小于给定Key,那么floor值要么就是根节点key要么就是在根的右子树中
        Node t = floor(x.right, key);
        if(t != null)
            return t;
        else
            return x;
    }

    public Key max(){
        return max(root).key;
    }

    private Node max(Node x){
        if(x.right == null)
            return x;
        return max(x.right);
    }

    public Key ceiling(Key key){
        Node x = ceiling(root, key);
        if(x == null)
            return null;
        return x.key;
    }

    //ceiling表示树中最小的比给定Key大的key值
    private Node ceiling(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)
            return x;
        if(cmp > 0)
            return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if(t != null)
            return t;
        else
            return x;
    }

    //返回树中的排在第k个的key值
    public Key select(int k){
        return select(root, k).key;
    }


    private Node select(Node x, int k){
        if(x == null)
            return  null;
        int t = size(x.left);
        //若果第k个key在左子树中,那么对于这可左子树来说,也是要找其第k个值
        if(t > k)
            return select(x.left, k);
        //如果第k个key在右子树中,那么对于这个右子树来说,就要找到其第k-t-1个key值
        else if(t < k)
            return select(x.right, k-t-1);
        //如果key相同,那么当前根就是要找的node
        else
            return x;
    }

    //返回指定key的排序位置
    public int rank(Key key){
        return rank(root, key);
    }

    private int rank(Node x, Key key){
        if(x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        //如果指定key小于根节点key,那么对应节点在根节点左子树中,其排序也是其左子树的排序值
        if(cmp < 0)
            return rank(x.left, key);
        //如果指定key大于根节点key,那么对应及诶单在根节点右子树中,
            // 指定key在右子树中的排序值加上size(x.left)+1就是当前根节点对应树中的排序值
        else if(cmp > 0)
            return size(x.left)+1+rank(x.right, key);
        else
            return size(x.left);
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node x){
        if(x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left)+ 1 + size(x.right);
        return x;
    }

    public void delete(Key key){
        root = delete(root, key);
    }

    private Node delete(Node x, Key key){
        if(x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)
            x.left = delete(x.left, key);
        else if(cmp > 0)
            x.right = delete(x.right, key);
        else{
            if(x.left == null)
                return x.right;
            if(x.right == null)
                return x.left;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + 1 + size(x.right);
        return x;
    }



}

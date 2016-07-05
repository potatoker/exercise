package chap3;

/**
 * Created by raymond on 16/4/5.
 */


public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private  Node root;

    private class Node{
        Key key;
        Value val;
        Node left, right;
        int N;
        boolean color;

        Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
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



    private boolean isRed(Node x){
        if(x == null)
            return false;
        return x.color;
    }

    private Node rotateLeft(Node h){
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }


    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    //左右节点都是红节点时要进行颜色翻转,其实就是把红节点上升,交给上面的处理,所以有h.color=RED这一句,无论h以前是什么节点
    void flipColors(Node h){
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    //根节点在这里维护,一定要是黑色
    public void put(Key key, Value val){
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value val){

        //如果是一个空树,直接返回一个新节点,并令其为红色(保证插入后高度不变),后面会有递归的维护
        if(h == null){
            return new Node(key, val, 1, RED);
        }

        int cmp = key.compareTo(h.key);
        if(cmp < 0)
            h.left = put(h.left, key, val);//如果比根节点小,递归的在左树中插入值
        else if(cmp > 0)
            h.right = put(h.right, key, val);//如果比根节点大,递归的在又数中插入值
        else h.val = val;//如果找到了,就更新节点值

        //如果右节点为红节点,要进行左旋,把红节点放到左边
        if(isRed(h.right)&&!isRed(h.left))
            h = rotateLeft(h);

        //先变成降低高度的4节点(右旋),后面会flipColor,如果忘了可以看一眼书
        if(isRed(h.left)&&isRed(h.left.left))
            h = rotateRight(h);

        //左右节点均为红节点,相当于4节点,要把红节点升上去
        if(isRed(h.left)&&isRed(h.right))
            flipColors(h);

        h.N = 1 + size(h.left) + size(h.right);
        return h;
    }


}

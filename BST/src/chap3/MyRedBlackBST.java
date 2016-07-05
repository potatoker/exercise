package chap3;


import edu.princeton.cs.algs4.Queue;

import java.util.NoSuchElementException;


/**
 * Created by cxy on 16-3-22.
 */
public class MyRedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color; //color of parent link
        private int N; //subtree count

        public Node(Key key, Value val, boolean color, int n) {
            this.key = key;
            this.val = val;
            this.color = color;
            N = n;
        }
    }

    public MyRedBlackBST() {
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    public int size() {
        return size(root);
    }


    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to get() is null");
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void put(Key key, Value val) {
        if (key == null) throw new NullPointerException("First argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        //每次插入之后，都需要将根节点变为黑色
        root.color = BLACK;
    }

    //当put新节点时，会在最底层的节点中添加红链
    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key,val,RED,1);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;

        //经过一次插入之后，需要判断节点是否平衡，需要做相应的旋转操作
        //为红色右链接，左链接为黑色适合，进行左旋操作
        if (isRed(h.right) && !isRed(h.left)) h = rorateLeft(h);
        //有两天连续的左链接时候,则进行一次右旋转，然后退色，父节点变红
        if (isRed(h.left) && isRed(h.left.left)) h = rorateRight(h);
        //左右链接都是红链接，则直接退色，父节点变红
        if (isRed(h.right) && isRed(h.left)) h = flipColors(h);
        h.N = size(h.right) + size(h.right) + 1;

        return h;
    }

    //Red-black tree helper functions
    private Node rorateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        //右旋
        x.color = x.right.color;
        x.right.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    //左旋
    private Node rorateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
        return h;
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null");
        if (!contains(key)) return ;

        if (!isRed(root.left) && isRed(root.right))
            root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }

        else {
            if (isRed(h.left))
                h = rorateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                Node x = min(h.right);
                h.key = x.key;
                h.val = x.val;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("chap3.BST underflow");

        //如果根节点的左子节点、右子节点都为黑色，将根节点变红
        //目的是？根节点是一个2-节点，让根节点变成非2-节点吗？
        if (!isRed(root.left) && !isRed(root.left)) {
            root.color = RED;
        }

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;

    }

    // delete the key-value pair with the maximum key rooted at h
    private Node deleteMax(Node h) {
        //为什么要右旋，上升左子节点？
        //因为删除的节点可能处在一个2-节点中，删除了它，另外一个节点就没有着落了?
        //因为在原始的红黑树中，不可能存在右红链，所以下面的判断就会出问题
        if (isRed(h.left))
            h = rorateRight(h);

        if (h.right == null)
            return null;

        //下一个节点是2-节点
        if (isRed(h.right) && isRed(h.right.left)) {
            h = moveRedRight(h);
        }

        h.right = deleteMax(h.right);
        return balance(h);
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        flipColors(h);

        if (isRed(h.left.left)) {
            h = rorateRight(h);
            flipColors(h);
        }
        return h;
    }


    //Remove the smallest key and associated value from the symbol table
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("chap3.BST underflow");

        //如果根节点的左子节点、右子节点都为黑色，将根节点变红
        //目的是？根节点是一个2-节点，让根节点变成非2-节点吗？
        if (!isRed(root.left) && !isRed(root.left)) {
            root.color = RED;
        }

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    public Node deleteMin(Node h) {
        //当到达最小节点是，直接返回null,并赋值给上一层节点
        //以达到删除节点的目的
        if (h.left == null)
            return null;

        //如果左子节点是2-节点，则需要对其进行变换，将它变为非2-节点
        //变换为将左子节点的亲兄弟节点中借一个过来，让左子节点变为非2-节点
        if (!isRed(h.left) && !isRed(h.left.left)) {
            //传入给moveRedLeft的h节点必然是红色节点，可以推导出
            h = moveRedLeft(h);
        }

        h.left = deleteMin(h.left);
        //需要回朔重新调整树，调整“删除”过程中临时生成的4-节点
        return balance(h);
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    //传入的节点h必然是红节点,左子节点必然是2-节点
    private Node moveRedLeft(Node h) {
        //1.改变颜色，尝试让根节点与左子节点构成一个非2-节点
        flipColors(h);

        //2.判断左子节点的亲兄弟是否也是一个2-节点
        //  如果是2-节点，则不进入该判断，直接将左子节点-根节点-亲兄弟合并成一个4-节点。
        //      因为在进入该层时，h.right肯定是黑色，若h.right.left也是黑色，那么h.right也是一个2-节点
        if (isRed(h.right.left)) {
            //如果左子节点的亲兄弟不是一个2-节点，则将左子节点-根节点进行合并，合并为一个3-节点
            //同时将亲兄弟节点非2-节点中找出最小值上升到根节点

            //将最小值上升到h.right,采用右旋操作
            h.right = rorateRight(h.right);
            //将最小值上升到根节点，同时让左子节点和根节点进行合并,采用左旋操作
            h = rorateLeft(h);
            //由于改变了上一层节点的树形，所以需要进行一次退色处理
            flipColors(h);
        }

        //3.如果左子节点的亲兄弟是2-节点，则直接返回，原来的2-节点变为一个4-节点
        //  如果左子节点的亲兄弟不是2-节点，则将2-节点变为一个3-节点
        return h;
    }

    //Restore red-black tree invariant
    //回朔修复“删除”过程中出现的4-节点
    private Node balance(Node h) {
        if (isRed(h.right)) h = rorateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rorateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
        return min(root).key;
    }

    // the smallest key in subtree rooted at x; null if no such key
    private Node min(Node x) {
        // assert x != null;
        if (x.left == null) return x;
        else                return min(x.left);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
        return max(root).key;
    }

    // the largest key in the subtree rooted at x; null if no such key
    private Node max(Node x) {
        // assert x != null;
        if (x.right == null) return x;
        else                 return max(x.right);
    }

    public Key floor(Key key) {
        if (key == null) throw new NullPointerException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("called floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) return null;
        else           return x.key;
    }

    // the largest key in the subtree rooted at x less than or equal to the given key
    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else           return x;
    }

    public Key ceiling(Key key) {
        if (key == null) throw new NullPointerException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("called ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) return null;
        else           return x.key;
    }

    // the smallest key in the subtree rooted at x greater than or equal to the given key
    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0)  return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else           return x;
    }

    public Key select(int k) {
        if (k < 0 || k >= size()) throw new IllegalArgumentException();
        Node x = select(root, k);
        return x.key;
    }

    // the key of rank k in the subtree rooted at x
    private Node select(Node x, int k) {
        // assert x != null;
        // assert k >= 0 && k < size(x);
        int t = size(x.left);
        if      (t > k) return select(x.left,  k);
        else if (t < k) return select(x.right, k-t-1);
        else            return x;
    }

    public int rank(Key key) {
        if (key == null) throw new NullPointerException("argument to rank() is null");
        return rank(key, root);
    }

    // number of keys less than key in the subtree rooted at x
    private int rank(Key key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else              return size(x.left);
    }

    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new NullPointerException("first argument to keys() is null");
        if (hi == null) throw new NullPointerException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        // if (isEmpty() || lo.compareTo(hi) > 0) return queue;
        keys(root, queue, lo, hi);
        return queue;
    }

    // add the keys between lo and hi in the subtree rooted at x
    // to the queue
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new NullPointerException("first argument to size() is null");
        if (hi == null) throw new NullPointerException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    private boolean isBalanced() {
        //统计每条链路上的层数，判断根节点到所有叶子节点的层数是否相等，相等则是平衡树
        int black = 0;
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root,black);
    }

    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.right,black) && isBalanced(x.left,black);
    }



}

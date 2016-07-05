package chap1;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by raymond on 6/21/16.
 */
public class NodeStack<Item> implements Iterable<Item> {

    //head 是栈顶,不知道哪里来的inception, 总是觉得头结点应该是null,但写了好几次链表,事实上都不需要这个多此一举.
    private Node<Item> head;
    private int len;


    public void push(Item item){
        Node<Item> oldHead = head;
        head = new Node<Item>();
        //其实这里有点犹豫,为什么内部类的private 变量可以被获取呢?还真有人这么问了..不过答案不太满意
        //http://stackoverflow.com/questions/1801718/why-can-outer-java-classes-access-inner-class-private-members
        head.item = item;
        head.next = oldHead;
        len ++;
    }

    public Item pop(){
        if(isEmpty())
            throw new NoSuchElementException("stack overflow");

        Item item = head.item;
        head = head.next;
        len --;
        return item;

    }

    public int size(){
        return len;
    }

    public boolean isEmpty(){
        return head == null;
    }


    private static class Node<Item>{

        private Node<Item> next;
        private Item item;
    }


    //这里注意一点就是这里完全可以引用外部类的Item泛型写成

//    private class Node2{
//        private Node2 next;
//        private Item item;
//    }

    //区别在与非静态内部类会有外部类的this引用,因此也可以随便引用他的泛型,但是静态内部内
    //就不行,既不可以引用其非静态的成员变量,也不可以引用其泛型

    public Item peek(){
        if(isEmpty())
            throw new NoSuchElementException("stack overflow");
        return head.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item>{

        Node<Item> pointer = head;

        @Override
        public boolean hasNext() {
            return pointer.next == null;
        }

        @Override
        public Item next() {
            Item item = pointer.item;
            pointer = pointer.next;
            return item;
        }

        @Override
        public void remove() {

        }
    }
}

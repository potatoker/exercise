package chap1;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

/**
 * Created by raymond on 6/21/16.
 */
public class MyQueue<Item> implements Iterable<Item> {

    //本来是想当然的是链表head进,tail出,写到出队才发现如果这样,贼要得到Node.pre这种引用,
    //显然使程序变得更复杂了。所以在写代码之前真的应该至少把最关键的部分,比如这里的enqueue和dequeue好好想想

    private int len;

    private Node<Item> head;
    private Node<Item> tail;


    private static class Node<Item>{
        Item item;
        Node<Item> next;
    }

    public boolean isEmpty(){
        return head == null;
    }


    //关于堆栈操作,其实其他的都很清除,唯一要注意的是

    public void enqueue(Item item){

        Node<Item> oldTail = tail;
        tail = new Node<Item>();
        tail.item = item;
        tail.next = null;
        if(isEmpty()){
            head = tail;
        }else{
            oldTail.next = tail;
        }

        len ++;
    }


    public Item dequeue(){
        if(isEmpty())
            throw new NoSuchElementException("Queue underflow");

        Item item = head.item;
        head = head.next;
        if(isEmpty())
            tail = null;
        len --;

        return item;

    }


    //overide这个方法就实现了iterable接口,就可以使用:
    // for(Item item : queue){}的语句了
    public Iterator<Item> iterator(){
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

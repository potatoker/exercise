package chap1;

import java.util.Iterator;

/**
 * Created by raymond on 6/21/16.
 */


//这里说要动态调整栈的大小,初始栈大小为1,如果栈中数据量大于某个阈值,则增大数组,并通过copy把原数据转移过去,同理小于某个阈值则
//    减小栈的大小,转移数据

public class ResizingArrayStack<Item> implements Iterable<Item> {


    private Item[] a = (Item[])new Object[1];
    private int N = 0; //栈中元素数量,也是表示栈顶元素

    public boolean isEmpty(){
        return N==0;
    }

    public int size(){
        return N;
    }

    private void resize(int size){

        Item[] temp = (Item[]) new Object[size];

        for(int i = 0; i < N; i++){
            temp[i] = a[i];
        }
        a = temp;
    }

    public Item pop(){
        Item item = a[--N];
        a[N] = null;

        //这里一开始写成了return a[--N];这样写表示我没有注意到要置空指针

        if(N>0 && N < a.length/4){
            resize(a.length/2);
        }

        return item;
    }

    public void push(Item item){
        if(N==a.length)
            resize(2*a.length);

        a[N++] = item;
    }






    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    public class ReverseArrayIterator implements Iterator<Item>{

        private int pointer = N;

        @Override
        public boolean hasNext() {
            return pointer > 0;
        }

        @Override
        public Item next() {
            return a[--pointer];
        }

        @Override
        public void remove() {

        }
    }
}

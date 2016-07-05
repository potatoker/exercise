package chap1;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;

/**
 * Created by raymond on 16/5/13.
 */

//dijkstra 双栈算术表达式求值算法
public class Evaluate {

    public static void main(String[] args){
        Stack<String> ops  = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();

        while(!StdIn.isEmpty()){
            String s = StdIn.readString();//读取的是空格分割的token

            //其实中间这么多else分支无非就是想表达所有运算符都入ops栈,写的好麻烦,感觉如果用python写应该会简洁很多
            //当然,还有括号,如果是正括号,没什么可做的,(这里这个程序肯定是默认你输入的是正确的括号匹配了)如果是反括号则表示该
            //evaluate一个运算了
            if(s.equals("("));
            else if(s.equals("+")) ops.push(s);
            else if(s.equals("-")) ops.push(s);
            else if(s.equals("*")) ops.push(s);
            else if(s.equals("/")) ops.push(s);
            else if(s.equals("sqrt")) ops.push(s);
            else if(s.equals(")")){
                //如果是反括号表示一个括号内的表达式可以被求值了,这里是把vals栈的两个数弹出来当做操作数,然后再弹一个ops栈里的
                //运算符,来做运算,结果再入vals栈2+(1+1)中,1+1被换为2入栈继续进行运算了。

                //其实这里就可以发现,这个方法没办法计算2*(1*2+3)这种的,所以可以看到书上例子举得也都是括号里只有两个数的运算
                String op = ops.pop();
                double val = vals.pop();

                if(op.equals("+"))
                    val = vals.pop() + val;
                else if(op.equals("-"))
                    val = vals.pop() - val;
                else if(op.equals("*"))
                    val = vals.pop() * val;
                else if(op.equals("/"))
                    val = vals.pop() / val;
                 vals.push(val);

            }
            //如果是数字就入vals栈
            else vals.push(Double.parseDouble(s));

        }
    }



}

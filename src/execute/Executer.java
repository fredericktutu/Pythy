package pythy.execute;
import pythy.parser.*;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.io.FileWriter;

//放在Pythy/src/execute下面，编译的语句如下(如果你的命令行处于此文件夹)
/*
>>> javac Executer.java -classpath ../../class -d ../../class
*/


public class Executer {
    /***
     * 前三行是创建一个执行器时，会初始化的内容，由编译提供
     */
    public ArrayList<Statement_AST> statement_list;  //语句ast的列表，第0个没放东西，从第1个开始
    public FileWriter file_writer;          //输出哈希表信息到文件，暂时不用实现
    public OutputStreamWriter console_writer;  // 输出控制台信息，暂时不用实现

    //符号表等需要的属性 由执行部分来定义

    public int pc;          //当前进行到的表达式行号，初始化为1
    public boolean end;         //是否结束，初始化为False

    public void execute_once() {}    //运行一行语句，按照pc来取语句，运行结束，计算出下一条指令的号码，并设置。
                                //如果已经到了最后一句，应该设置end为True 

    public void execute_all() {}     //运行所有语句，循环跳出的条件应该是end为True

    public Expression_AST eval(Expression_AST ast) {return null;}  //用来对表达式进行递归求值,返回封装好的值

    public void Reset() {}     //需要对执行器进行重启，就是恢复一开始的状态，重新设置一些属性
}
package pythy.test;
import java.io.*;
import java.util.ArrayList;
import pythy.parser.*;
import pythy.execute.*;
/*
测试二：编译、执行集成测试
执行器需要能够正确地执行执行三种赋值语句
并且能够在每一步，都正确地把符号表的内容打印在files文件夹下的output2.dot下

*/
public class Test2 {
    public static void main(String[] args) {
		//file reading
		FileReader reader;
        ArrayList<Statement_AST> lst;
		try{
			reader = new FileReader("files/test2.py");
		} catch(FileNotFoundException e) {
			System.out.println("file not found");
            return;
		}


		//begin parsing
		Parser parser = new Parser(reader);
		try {
            parser.Program();
            lst = parser.get_statement_list();
		} catch(ParseException e) {
			System.out.println("parse error, at" + e);
            return;
        }
        System.out.println("----------parse finish-------");
        for(int i=1;i<lst.size();i++) {
            System.out.println(lst.get(i).print_self());
        }
        System.out.println("-------start to execute------");
        
        //begin executing 
        Executer executer = new Executer();
        executer.statement_list = lst;      //将语句ast加入

        executer.execute_all();
        System.out.println("------execute finish---------");
        //目标1：每执行一步，print哈希表到控制台，看是否正确
        //目标2：将哈希表打印到文件

        
    }
}
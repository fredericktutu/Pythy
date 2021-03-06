package pythy.execute;
import pythy.parser.*;




import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.beans.Expression;
import java.io.File;

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
	public FileWriter console_writer;  // 输出控制台信息，暂时不用实现
	public File console_file;
	public Environment env;
    //符号表等需要的属性 由执行部分来定义

    public int pc;          //当前进行到的表达式行号，初始化为1
	public boolean end;         //是否结束，初始化为False
	
	public void stop() {
		try{
			this.console_writer.close();
		}catch(Exception e) {
			System.out.println(e);
		}

	}
    
    public Executer() {
    	this.pc = 1;
		this.end = false;
		this.env = new Environment(); 
		try{
			console_file = new File("files/console.txt");//这个文件用来装要输出到console的信息
			this.console_writer = new FileWriter(console_file ,false);//设定consoleWriter写Console.txt
			console_writer.write("Welcome to Pythy Interpreter!\n");
			console_writer.flush();
		}catch(Exception e) {
			System.out.println("file not found");
		}
		

 	}

    public void execute_once() {
		Statement_AST ast_now = statement_list.get(pc);
		Expression_AST exp;
		System.out.println("pc:" + pc + " type:" +ast_now.type);

    	try {
    		switch (ast_now.type) {
	    	case "=":
				env.put(ast_now.left,eval(ast_now.right));
				pc = ast_now.next;
				break;
			case "echo":
				exp = eval(ast_now.right);
				try{
					this.console_writer.write(exp.print_console());
					console_writer.flush();
				}catch(Exception e2) {
					System.out.println("os error");
				}
				pc = ast_now.next;
				break;
	    	case "if":
	    	case "while":
			case "null"://if while null暂未实现
				pc = pc + 1;
				break;
			case "end":
				this.end = true;
				break;
			}//end switch
				
    	}catch(PRTException e) {
			//System.out.println("An error happens at line " + ast_now.line + ":\n" + e.errmsg);
			try{
				this.console_writer.write("An error happens at line " + ast_now.line + ":\n" + e.errmsg + "\n");
				console_writer.flush();
			}catch(Exception e2) {
				System.out.println("os error");
			}

			this.end = true;
    	}
}
 //运行一行语句，按照pc来取语句，运行结束，计算出下一条指令的号码，并设置。
 //如果已经到了最后一句，应该设置end为True 

    public void execute_all() {
		while(end == false) {
			execute_once();
		}

	}	
  //运行所有语句，循环跳出的条件应该是end为True

	//用来对表达式进行递归求值,返回封装好的值
    public Expression_AST eval(Expression_AST ast) throws PRTException{
		Expression_AST ret;//switch的不同case同属于一个块
		Expression_AST expLeft;
		Expression_AST expRight;
    	switch(ast.type) {
	    case "bool":	    
	    	ret = new Expression_AST("bool");
	        ret.value_boolean = ast.value_boolean;
	        return ret;
		    
	    case "int":	    
	    	ret= new Expression_AST("int");
	        ret.value_int = ast.value_int;
	        return ret;
	        	    
	    case "float":	    
	    	ret= new Expression_AST("float");
	        ret.value_float = ast.value_float;
	        return ret;
	       
	    case "name":	    
	        try
	        {
	            ret = env.get(ast.value_name);
	            return ret;
	        }catch(PRTException e) {
	            throw e;
	        }
		case "+":
		case "-":
		case "*":
		case "/":
		{
			expLeft = eval(ast.left);
			expRight = eval(ast.right);
			if (expLeft.type == "int" && expRight.type == "int"){
				int result = operation(ast.type, expLeft.value_int, expRight.value_int);
				ret= new Expression_AST("int");
				ret.value_int = result;
				return ret;
			}
			float fresult = 0;
			if (expLeft.type == "int" && expRight.type == "float")
				fresult = operation(ast.type, expLeft.value_int, expRight.value_float);
			else if (expLeft.type == "float" && expRight.type == "int")
				fresult = operation(ast.type, expLeft.value_float, expRight.value_int);
			else if (expLeft.type == "float" && expRight.type == "float")
				fresult = operation(ast.type, expLeft.value_int, expRight.value_int);
			else if (expLeft.type == "bool" || expRight.type == "bool"){
				String op = "";
				switch(ast.type){
				case "+": op = "added";
				case "-": op = "subed";	
				case "*": op = "timed";
				case "/": op = "divided";
				}
				throw new PRTException(expLeft.type+" and "+expRight.type
					+ " can't be " + op);
			}
			ret= new Expression_AST("float");
			ret.value_float = fresult;
			return ret;
		}
		case "and":
		case "or":
		case "not":
		{
			if(ast.type.equals("not")) {
				expLeft = eval(ast.left);
				expRight = new Expression_AST("bool");
				expRight.value_boolean = true;
			} else{
				expLeft = eval(ast.left);
				expRight = eval(ast.right);
			}
			boolean leftBoolean, rightBoolean;
			leftBoolean = boolean_value(expLeft);
			rightBoolean = boolean_value(expRight);
			String result = booleanOperation(ast.type, leftBoolean, rightBoolean);
			switch(result){
			case "true":
				ret= new Expression_AST("bool");
				ret.value_boolean = true;
				return ret;
			case "false":
				ret= new Expression_AST("bool");
				ret.value_boolean = false;
				return ret;
			case "left":
				return expLeft;
			case "right":
				return expRight;
			default:
				return null;
			}
		}
		default:
		{
			return null;
		}
		}//end big switch
	}
	
	public boolean boolean_value(Expression_AST ast) {
		boolean res;
		switch (ast.type){
			case "bool": res = ast.value_boolean; break;
			case "int": 
				if (ast.value_int == 0)
					res = false;
				else
					res = true;
				break;
			case "float":
				if (ast.value_float == 0.0)
					res = false;
				else
					res = true;
				break;
			default:
				res = false;
				break;
			}
		return res;
	}
	public int operation(String op, int a, int b){
		switch(op){
		case"+": return a+b;
		case"-": return a-b;
		case"*": return a*b;
		case"/": return a/b;
		default: return 0;
		}
	}
	
	public float operation(String op, int a, float b){
		switch(op){
		case"+": return a+b;
		case"-": return a-b;
		case"*": return a*b;
		case"/": return a/b;
		default: return 0;
		}
	}
	public float operation(String op, float a, int b){
		switch(op){
		case"+": return a+b;
		case"-": return a-b;
		case"*": return a*b;
		case"/": return a/b;
		default: return 0;
		}
	}
	public float operation(String op, float a, float b){
		switch(op){
		case"+": return a+b;
		case"-": return a-b;
		case"*": return a*b;
		case"/": return a/b;
		default: return 0;
		}
	}
	
	public String booleanOperation(String op, boolean a, boolean b){
		switch (op){
		case "and":
			if(a == true && b == true)
				return "right";
			if(a == true && b == false)
				return "right";
			if(a == false && b == true)
				return "left";
			if(a == false && b == false)
				return "left";
		case "or":
			if(a == true && b == true)
				return "left";
			if(a == true && b == false)
				return "left";
			if(a == false && b == true)
				return "right";
			if(a == false && b == false)
				return "right";
		case "not":
			if(a == true)
				return "false";
			if(a == false)
				return "true";
		default:
			return "never reach";
		}
	}
	
    public void Reset() {}     //需要对执行器进行重启，就是恢复一开始的状态，重新设置一些属性
}
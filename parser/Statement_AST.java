
public class Statement_AST{
    String type; //"if" "where" "null" "="
    String left;//赋值运算的左值
    Expression_AST right; //赋值运算的右边值，if，where的条件表达式
    int line; //行号
    int in; //if，where语句为true时的下一行，未实现
    int out; //if,where语句为false时下一行，未实现
    int next;
    public Statement_AST(String type) {
        //初始化方法，同时对类型和行号初始化
        this.type = type;
    }
    public String print_self() {
        switch(this.type) {
            case "=" :
            {
                return ("(=)" + "[left:" + this.left + "]" + "[right:"+ this.right.print_self() + "]"
                        + "[line:"+ this.line  + "]" + "[next:" + this.next + "]");
            }
            case "null":
            {
                return "(null)";
            }
        }
        return "";
    }

}
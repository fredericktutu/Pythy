package pythy.parser;
import java.beans.Expression;

public class Expression_AST {
    public String type; //"name" "int" "float" "bool" "+" "-"
    public Expression_AST left;  
 
    public Expression_AST right;


    public int value_int;
    public float value_float;
    public boolean value_boolean;
    public String value_name;

    public Expression_AST(String type) {
        this.type = type;
    }
    public String print_console() {
        switch(this.type) {
            case "int" :
            {
                return "" + this.value_int + "\n";
            }
            case "float":
            {
                return "" + this.value_float + "\n";
            }
            case "bool":
            {
                if(this.value_boolean) {
                    return "True\n";
                } else {
                    return "False\n";
                }
            }
            default:
            {
                return "";
            }
        }





    }
    public String print_self() {
        switch(this.type) {
            case "int": 
            {
                return "<int:" + this.value_int + ">";
            }
            case "float":
            {
                return "<float:" + this.value_float + ">";
            }
            case "bool":
            {
                return "<bool:" + this.value_boolean + ">";
            }
            case "+":
            case "-":
            case "*":
            case "//":
            case "/":
            case "and":
            case "or":
            case "not":
            {
                return "<" + this.left.print_self() + " " + this.type + " "+  this.right.print_self() + ">";
            }
            default:
            {
                return "<" +this.type + ">";
            }
        }
        
    }
}
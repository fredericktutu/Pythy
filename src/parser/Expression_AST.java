package pythy.parser;
import java.beans.Expression;

public class Expression_AST {
    public String type; //"name" "int" "float" "bool" "+" "-"
    //+
    public Expression_AST left;  
    //1
    public Expression_AST right;
    //Expression_AST name value_name("x")

    public int value_int;
    public float value_float;
    public boolean value_boolean;
    public String value_name;

    public Expression_AST(String type) {
        this.type = type;
    }

    public String print_self() {
        switch(this.type) {
            case "int": 
            {
                return "<int" + this.value_int + ">";
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
            {
                return "";
            }
            default:
            {
                return "<" +this.type + ">";
            }
        }
        
    }
}
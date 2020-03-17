package pythy.parser;
import java.beans.Expression;

public class Expression_AST {
    String type; //"name" "int" "float" "bool" "+" "-"

    Expression_AST left;
    Expression_AST right;

    int value_int;
    float value_float;
    boolean value_boolean;
    String value_name;

    Expression_AST(String type) {
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
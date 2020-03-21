package pythy.execute;
import pythy.parser.*;

public class PRTException extends Exception{
    public String errmsg;
	public PRTException(String message){
        this.errmsg = message;
    }
}

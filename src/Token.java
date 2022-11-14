import java.util.HashMap;
import java.util.Map;

enum TokenType{
    RESERVED,IDENTIFIER,NUMBER,SPECIALSYMBOL,ASSIGN
}

public class Token {

    public TokenType tokenType;
    public String stringVal;
    public int  numVal;
    public static Map<String, String> reserved = new HashMap<String, String>() {{
        put("if","if");

        put("else","else");
        put("repeat","repeat");
        put("until","until");
        put("end","end");
        put("read","read");
        put("write","write");
        put("then","then");
    }};
    public static Map<String, String> symbols = new HashMap<String, String>() {{
        put("+","+");
        put("-","-");
        put("/","/");
        put("=","=");
        put("<","<");
        put("*","*");
        put("(","(");
        put(")",")");
        put(";",";");
    }};
    public static String assign=":=";
    public Token(){

    }

    public Token(String stringVal){
        this.stringVal=stringVal;

        if (reserved.containsKey(stringVal)){
            System.out.println(stringVal);
            this.tokenType=TokenType.RESERVED;
        }
        else if (symbols.containsKey(stringVal)){
            this.tokenType=TokenType.SPECIALSYMBOL;
        }
        else if (assign.equals(stringVal)){
            this.tokenType=TokenType.ASSIGN;
        }
        else if (stringVal.matches("[0-9]*")){
            this.tokenType=TokenType.NUMBER;
        }
        else{
            this.tokenType=TokenType.IDENTIFIER;
        }
    }



}

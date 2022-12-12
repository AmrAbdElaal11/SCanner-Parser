import java.util.HashMap;
import java.util.Map;

enum TokenType{
    RESERVED,IDENTIFIER,NUMBER,SPECIALSYMBOL,ASSIGN,IF , THEN, ELSE , END , READ , WRITE , UNTIL,REPEAT,
    SEMICOLON, LESSTHAN,GREATERTHAN, EQUAL, PLUS,MINUS , MULT, DIV ,OPENBRACKET,CLOSEDBRACKET

}





public class Token {

    public TokenType tokenType;
    public String stringVal;
    public int  numVal;
    Map<String,Enum<TokenType>> enumMap= new HashMap<String, Enum<TokenType>>();
    ;
    public static Map<String, Enum<TokenType>> reserved = new HashMap<String, Enum<TokenType>>() {{
        put("if",TokenType.IF);
        put("else",TokenType.ELSE);
        put("repeat",TokenType.REPEAT);
        put("until",TokenType.UNTIL);
        put("end",TokenType.END);
        put("read",TokenType.READ);
        put("write",TokenType.WRITE);
        put("then",TokenType.THEN);
    }};
    public static Map<String, Enum<TokenType>> symbols = new HashMap<String, Enum<TokenType>>() {{
        put("+",TokenType.PLUS);
        put("-",TokenType.MINUS);
        put("/",TokenType.DIV);
        put("=",TokenType.EQUAL);
        put(">",TokenType.GREATERTHAN);
        put("<",TokenType.LESSTHAN);
        put("*",TokenType.MULT);
        put("(",TokenType.OPENBRACKET);
        put(")",TokenType.CLOSEDBRACKET);
        put(";",TokenType.SEMICOLON);
    }};
    public static String assign=":=";
    public Token(){

    }

    public Token(String stringVal){
        this.stringVal=stringVal;

        if (reserved.containsKey(stringVal)){
           // System.out.println(stringVal);
            this.tokenType= (TokenType) reserved.get(stringVal);
        }
        else if (symbols.containsKey(stringVal)){
            this.tokenType=(TokenType) symbols.get(stringVal);
        }
        else if (assign.equals(stringVal)){
            this.tokenType=TokenType.ASSIGN;
        }
        else if (stringVal.matches("[0-9]*")){
            this.tokenType=TokenType.NUMBER;
            this.numVal=Integer.valueOf(stringVal);
        }
        else{
            this.tokenType=TokenType.IDENTIFIER;
        }
    }



}

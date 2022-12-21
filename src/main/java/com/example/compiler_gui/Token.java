package com.example.compiler_gui;
import java.util.HashMap;
import java.util.Map;







public class Token {
    public  enum TokenType{
        RESERVED,
        IDENTIFIER {
            @Override
            public String toString() {
                return "identifier";
            }
        },
        NUMBER{
            @Override
            public String toString() {
                return "number";
            }
        },
        SPECIALSYMBOL,
        ASSIGN{
            @Override
            public String toString() {
                return ":=";
            }
        },
        IF{
            @Override
            public String toString() {
                return "if";
            }
        },
        THEN{
            @Override
            public String toString() {
                return "then";
            }
        },
        ELSE{
            @Override
            public String toString() {
                return "else";
            }
        },
        END{
            @Override
            public String toString() {
                return "end";
            }
        },
        READ{
            @Override
            public String toString() {
                return "read";
            }
        },
        WRITE{
            @Override
            public String toString() {
                return "write";
            }
        },
        UNTIL{
            @Override
            public String toString() {
                return "until";
            }
        },
        REPEAT{
            @Override
            public String toString() {
                return "repeat";
            }
        },
        SEMICOLON {
            @Override
            public String toString() {
                return "semi-colon";
            }
        },
        LESSTHAN{
            @Override
            public String toString() {
                return "<";
            }
        },
        GREATERTHAN {
            @Override
            public String toString() {
                return ">";
            }
        },
        EQUAL{
            @Override
            public String toString() {
                return "=";
            }
        },
        PLUS{
            @Override
            public String toString() {
                return "+";
            }
        },
        MINUS{
            @Override
            public String toString() {
                return "-";
            }
        },
        MULT{
            @Override
            public String toString() {
                return "*";
            }
        },
        DIV{
            @Override
            public String toString() {
                return "/";
            }
        },
        OPENBRACKET{
            @Override
            public String toString() {
                return "(";
            }
        },
        CLOSEDBRACKET{
            @Override
            public String toString() {
                return ")";
            }
        }

    }
    public TokenType tokenType;
    public String stringVal;
    public int  numVal;
    Map<String,Enum<TokenType>> enumMap= new HashMap<String, Enum<TokenType>>();

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
    public TokenType getTokenType(){
        return this.tokenType;
    }

    public Token(String stringVal){
        this.stringVal=stringVal;

        if (reserved.containsKey(stringVal)){
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

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", stringVal='" + stringVal + '\'' +
                ", numVal=" + numVal +
                ", enumMap=" + enumMap +
                '}';
    }
}

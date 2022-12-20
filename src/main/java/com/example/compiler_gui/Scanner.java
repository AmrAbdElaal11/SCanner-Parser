package com.example.compiler_gui;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;


enum STATE{
    START,INCOMMENT,INID,INNUM,INASSIGN,DONE
}
public class Scanner {


    public static  Index i = new Index(0);
    public static ArrayList<Token> Tokens = new ArrayList<>();
    public static File inputFile ;
    public static File ouputFile;
    public Scanner(){}
    public Scanner(String code){
        getTokens(code);
    }
   //takes array of token and write it to the file in the form of value type pairs
    public static void writeTofile(Token token,File file,int k) throws IOException {
       String string = "\n : [TOKEN_Value  ,   TYPE]";
       if(k==0)
       Files.writeString(Paths.get(file.getAbsolutePath()),string , StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
       Files.writeString(Paths.get(file.getAbsolutePath()),"\n"+k+": ["+token.stringVal+" ,  "+token.tokenType.name()+"]", StandardOpenOption.APPEND);
    }
    public  void getTokens(String code){
        while(true){
            Token t = scan(code,i,Tokens);
            if(t==null) break;
        }
    }
public static Token  scan(String tiny,Index index,ArrayList<Token> tokens){
    Token token =new Token();
    boolean parserToken =false;
    STATE current = STATE.START;
    char[] charsInput = tiny.toCharArray();
    StringBuffer  tokenVal = new StringBuffer();

    for (;index.i<=charsInput.length;(index.i)++){
        String curr="";
        if(index.i < charsInput.length) {

             curr = String.valueOf(charsInput[index.i]);
        }
        else{
            return  null;
        }

        switch (current)
        {
            case START :

                if (curr.matches("\\{")) {
                current=STATE.INCOMMENT;
            }
                else if (curr.equals(" ") || curr.equals("\t")){
                   current=STATE.START;
                }
                else if (curr.matches("[0-9]")) {
                current = STATE.INNUM;
                tokenVal.append(curr);
            }
                else if(curr.matches("[a-zA-Z_]"))
            {
                current = STATE.INID;
                tokenVal.append(curr);
            }
                else if(curr.matches(":"))
            {
                current = STATE.INASSIGN;
                tokenVal.append(curr);
            }
                else{
                tokenVal.append(curr);
                current = STATE.DONE;
            }
            break;
            case INCOMMENT:
                if (curr.matches("\\}"))
                {
                    current=STATE.START;
                }
                break;
            case INNUM:
                if (curr.matches("[0-9]"))
                {
                    tokenVal.append(curr);
                    current = STATE.INNUM;
                }
                else {
                    current = STATE.DONE;
                    index.i--;
                }
                break;
            case INID:
                if(curr.matches("[a-zA-Z_]") )
                {
                    tokenVal.append(curr);
                    current = STATE.INID;
                }
                else
                {
                    current = STATE.DONE;
                    index.i--;
                }
                break;
            case INASSIGN:
                if (curr.matches("="))
                {
                    tokenVal.append(curr);
                    current = STATE.DONE;
                }
                else
                {
                    current = STATE.DONE;
                }
                break;
            case DONE:
                 //index.i--;
                 token = new Token(tokenVal.toString());
                 tokens.add(token );
                 parserToken =true;
                 current=STATE.START;
                 tokenVal=new StringBuffer();
                 break;
        }
       if (parserToken){
            break;
       }
    }
    return token;
}


    public static void clearTokens(){
        i.i = 0;
        Tokens.clear();
    }
    public void setCode(String code){
        clearTokens();
        getTokens(code);
    }
}

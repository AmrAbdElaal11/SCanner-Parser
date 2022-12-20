package com.example.compiler_gui;

import java.io.File;
import java.io.FileNotFoundException;

public class Util {
    public static String replaceNewLines(String str){
        StringBuilder str_result = new StringBuilder();
        for(int i = 0 ; i < str.length() ; i++){
            if(str.charAt(i) == '\n'){
                str_result.append(' ');
            }
            else {
                str_result.append(str.charAt(i));
            }
        }
        str_result.append(" ");
        return str_result.toString();
    }
    public static String readSrcFile(File file){
        StringBuffer input =new StringBuffer();
        try{
            java.util.Scanner scan = new java.util.Scanner(file);
            Scanner.clearTokens();
            String tempString;
            while(scan.hasNextLine()) {
                tempString = scan.nextLine();
                input.append(tempString);
                input.append("\n");
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        return input.toString();
    }
    public static void parseTokenTextInput(String inputStr){
        Scanner.clearTokens();
        String[] inputStrLines = inputStr.split("\n" , -1);
        String[] tokenAttr;
        for (int i = 0; i < inputStrLines.length; i++) {
            if (!inputStrLines[i].isBlank()){
                //TODO multiple spaces cause erros
                tokenAttr = inputStrLines[i].replaceAll(" " , "").split("," , 2);
                tokenAttr = inputStrLines[i].replaceAll("\t" , "").split("," , 2);
                Scanner.Tokens.add(new Token(tokenAttr[0]));
            }
        }
    }
    public static double max(double firstNumber , double secondNumber){
        if(firstNumber > secondNumber){
            return firstNumber;
        }
        else{
            return secondNumber;
        }
    }
}

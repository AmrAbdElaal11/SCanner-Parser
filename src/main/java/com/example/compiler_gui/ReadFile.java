package com.example.compiler_gui;

import java.io.File;
import java.io.FileNotFoundException;

public class ReadFile {
    public static String readFile(File file,StringBuffer s1){
        StringBuffer input =new StringBuffer();

        try{
            java.util.Scanner scan = new java.util.Scanner(file);
            String tempString=scan.nextLine();
            input.append(tempString);
            s1.append(tempString);


            while(scan.hasNextLine()){
                tempString=scan.nextLine();
                input.append(tempString);
                s1.append(tempString);
                input.append(" ");
                s1.append("\n");
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        return input.toString();
    }
}

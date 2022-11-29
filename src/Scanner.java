import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

enum STATE{
    START,INCOMMENT,INID,INNUM,INASSIGN,DONE
}
public class Scanner {


    public static  Index i = new Index(0);
    public static ArrayList<Token> Tokens = new ArrayList<>();
    public static File inputFile ;
    public static File ouputFile;
   //takes array of token and write it to the file in the form of value type pairs
    public static void writeTofile(ArrayList<Token> tokens,File file) throws IOException {
       String string = "\n : (TOKEN_Value  ,   TYPE)";
       Files.writeString(Paths.get(file.getAbsolutePath()),string , StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
       for (int k =0;k<tokens.size();k++){
           Token token = Tokens.get(k);
           Files.writeString(Paths.get(file.getAbsolutePath()),"\n"+k+": ("+token.stringVal+" ,  "+token.tokenType.name()+")", StandardOpenOption.APPEND);
       }

    }
public static void  scan(String tiny,Index index,ArrayList<Token> tokens){

    Token token =new Token();
    boolean parserToken =false;
    STATE current = STATE.START;
    char[] charsInput = tiny.toCharArray();
    StringBuffer  tokenVal = new StringBuffer();

    for (;index.i<=charsInput.length;(index.i)++){
        String curr="";
        if(index.i< charsInput.length) {
             curr = String.valueOf(charsInput[index.i]);
        }

        switch (current)
        {
            case START :

                if (curr.matches("\\{")) {
                current=STATE.INCOMMENT;
            }
                else if (curr.equals(" ")){
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

                 index.i--;
                 token = new Token(tokenVal.toString());
                 tokens.add(token );

                parserToken =true;
                current=STATE.START;
                tokenVal=new StringBuffer();

                break;
        }


//        if (parserToken){
//
//            break;}


    }
//    return token;
}





    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                    e.printStackTrace();
                }
                JFileChooser jfc = new JFileChooser();
                jfc.showOpenDialog(null);

                inputFile=jfc.getSelectedFile();
                System.out.println(inputFile.getParent());
                String code = ReadFile(inputFile);
                System.out.println(code);

                scan(code,i,Tokens);
                String path = inputFile.getParent()+"\\Tokens.txt";
                ouputFile= new File(path);
                try {

                    if (ouputFile.createNewFile()) {
                        System.out.println("File created: " + ouputFile.getName());
                    } else {
                        System.out.println("File already exists.");
                    }
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                try {
                    writeTofile(Tokens,ouputFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //File inputFile = new File("src/TINY.txt");




}

    public static String ReadFile(File file){
        StringBuffer input =new StringBuffer();
        
        try{
            java.util.Scanner scan = new java.util.Scanner(file);
            input.append(scan.nextLine());
            while(scan.hasNextLine()){
                input.append(scan.nextLine());
            }
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        return input.toString();
    }
}

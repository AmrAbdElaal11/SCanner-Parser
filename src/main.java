import java.io.File;

public class main {
    public static void main(String[] args) {
        File file = new File("A:\\1st_Senior2\\Compilers\\Parser project\\src\\tiny.txt");
        String input = Scanner.ReadFile(file);
        Scanner scanner=new Scanner(input);
        //for (int i =0;i<Scanner.Tokens.size();i++)
          //  System.out.println(Scanner.Tokens.get(i).getTokenType());
        Parser parser=new Parser();
        parser.program();
    }
}

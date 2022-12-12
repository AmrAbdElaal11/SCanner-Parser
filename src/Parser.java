public class Parser {
    // public static SyntaxTree tree = new
    Scanner scanner=new Scanner();
    public static Integer i=0;



    // to do retrun syntax tree
    void  program(){
        stmt_sequence();
    }
    void stmt_sequence(){

    }
 void statment(){

 }
 void exp(){}
 void factor (){
     Token token = Scanner.Tokens.get(i);
     switch (token.getTokenType()) {
         case OPENBRACKET:
            match(Token.TokenType.OPENBRACKET);
            // TODO brackets case?
            // left bracket node
            exp();
            // right bracket node
            match(Token.TokenType.CLOSEDBRACKET);
            break;
         case NUMBER:
             // node number
             match(Token.TokenType.NUMBER);
             break;
         case IDENTIFIER:
             // node id
             match(Token.TokenType.IDENTIFIER);
             break;
        }
 }
public void match(Token.TokenType type){
    Token current=Scanner.Tokens.get(i);
    if (current.getTokenType()==type){
        i++;
    }
    else{
        // raise exception

    }
}



}

public class main {
    public static void main(String[] args) {
        SyntaxTree s = new SyntaxTree();
        long n1 = s.addConstNode(1000);
        long n2 = s.addConstNode("3000");
        long n3 = s.addOperatorNode("+");
        long n4 = s.addIDNode("x");
        long n5 = s.addIfStmtNode();
        long n6 = s.addWriteStmtNode();
        long n7 = s.addRepeatStmtNode();
        s.addChild( n3 , n1);
        s.addChild(n3 , n2);
        s.addSibiling(n3 , n4);
        s.endGraph();
        s.writeImageToFile();
    }
}

import java.io.File;

public class SyntaxTree {
    private GraphViz graph;
    private long currentUID;
    enum Shape {
        ELLIPSE,
        RECTANGLE
    }
    private final String imageExtension = "png";
    private String outputImageFileName = "out";
    public  SyntaxTree(){
        //create an object from GraphViz using the dot executable path as well as a temp Dir
        //temp dir contains logging info in case of exceptions
        graph = new GraphViz("src/main/java/GraphVizLite/dot.exe" , ".");
        graph.addln(graph.start_graph());
        graph.add("edge [dir=none];");
        currentUID = 0;
    }
    public long addNode(String nodeLabel , Shape nodeShape){
        currentUID++;
        String labelString = currentUID + " [label= \"" + nodeLabel + "\"";
        if (nodeShape == Shape.RECTANGLE){
            graph.addln(labelString + " , shape =  \"rectangle\"];" );
        }else if(nodeShape == Shape.ELLIPSE){
            graph.addln(labelString + " , shape =  \"ellipse\"];" );
        }
        return currentUID;
    }
    public void addChild(long firstNodeName , long secondNodeName){
        graph.addln(firstNodeName + "->" + secondNodeName + ";");
    }
    public  void addSibiling(long firstNodeName , long secondNodeName){
        graph.addln("{rank = \"same\" " + firstNodeName + "->" + secondNodeName + "};");
    }
    public long addIDNode(String idName){
        return addNode("id\\n"+idName , Shape.ELLIPSE);
    }
    public long addConstNode(String constValue){
        return addNode("const\\n(" + constValue  + ")", Shape.ELLIPSE);
    }
    public long addConstNode(int constValue){
        return addNode("const\\n(" + constValue  + ")", Shape.ELLIPSE);
    }
    public long addOperatorNode (String operator){
        return addNode("op\\n (" + operator + ")", Shape.ELLIPSE);
    }
    public long addIfStmtNode(){
        return addNode("if" , Shape.RECTANGLE);
    }
    public long addAssignStmtNode(String assignedIDName ){
        return addNode("assign\\n(" + assignedIDName + ")" , Shape.RECTANGLE);
    }
    public long addRepeatStmtNode(){
        return addNode("repeat" , Shape.RECTANGLE);
    }
    public long addWriteStmtNode(){
        return addNode("write" , Shape.RECTANGLE);
    }
    public void endGraph(){
        graph.addln(graph.end_graph());
    }
    public void writeImageToFile(){
        File out = new File(outputImageFileName + "."+ imageExtension);
        graph.writeGraphToFile( graph.getGraph(graph.getDotSource(), imageExtension, "dot"), out );
    }
}

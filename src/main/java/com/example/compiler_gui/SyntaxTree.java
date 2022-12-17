package com.example.compiler_gui;
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
        graph = new GraphViz("E:\\Others\\El koleya el weskha\\Last year\\First Semster-20221101T162429Z-001\\First Semster\\Design of Compilers\\Project\\compiler_gui\\src\\main\\java\\com\\example\\compiler_gui\\GraphVizLite\\dot.exe" , ".");
        graph.addln(graph.start_graph());
        graph.addln("edge [dir=none];");
        graph.addln("graph [ordering= out,dpi=1000];");
        currentUID = 0;
    }
    private long addNode(String nodeLabel , Shape nodeShape){
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
        return addNode("id\\n("+idName+")" , Shape.ELLIPSE);
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
    public long addReadStmtNode(String x){
        return addNode("read\\n("+x+ ")", Shape.RECTANGLE);
    }
    public void endGraph(){
        graph.addln(graph.end_graph());
    }
    public void writeImageToFile(){
        File out = new File(outputImageFileName + "."+ imageExtension);
        graph.writeGraphToFile( graph.getGraph(graph.getDotSource(), imageExtension, "dot"), out );
    }
}

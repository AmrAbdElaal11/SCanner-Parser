package com.example.compiler_gui;

import java.io.IOException;

public class Parser {
    public static SyntaxTree tree;

    static {
        try {
            tree = new SyntaxTree();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Integer i = 0;
    void program() throws ParsingException, IOException {
        i = 0;
        tree.clearGraph();
//        tree = new SyntaxTree();
        long t=  stmt_sequence();
        tree.endGraph();
        tree.writeImageToFile();
    }

    long stmt_sequence() throws ParsingException{
        long root = statment();
        long stmt;
        long x =root;
        if (i >= Scanner.Tokens.size()) {
            return root;
        }
        Token token=Scanner.Tokens.get(i);
        while(Token.TokenType.SEMICOLON == token.getTokenType()){
            match(Token.TokenType.SEMICOLON);
            stmt=statment();
            tree.addSibiling(x,stmt);
            if (i >= Scanner.Tokens.size()) {
                return root;
            }
            token=Scanner.Tokens.get(i);
            x=stmt;
        }
        if (i + 1 < Scanner.Tokens.size() ) {
            if(token.getTokenType() != Token.TokenType.ELSE && token.getTokenType() != Token.TokenType.UNTIL && token.getTokenType() != Token.TokenType.END){
                throw new ParsingException("Missing semi-colon");
            }
        }
        return root;
    }

    long statment() throws ParsingException {
        Token token=Scanner.Tokens.get(i);
        long temp = 0;
        switch (token.tokenType){
            case IF :
                temp = if_stmt();
                break;
            case READ:
                temp = read_stmt();
                break;
            case REPEAT:
                temp= repeat_stmt();
                break;
            case IDENTIFIER:
                temp = assign_stmt();
                break;
            case WRITE:
                temp = write_stmt();
                break;
            default:
                throw new ParsingException("Expected a statement found \"" + token.getTokenType() + "\" instead");
        }
        return temp ;
    }

    long if_stmt() throws ParsingException{
        Token token = Scanner.Tokens.get(i);
        match(Token.TokenType.IF);
        long parent =tree.addIfStmtNode();
        long ex=exp();
        tree.addChild(parent,ex);
        Token token2 = Scanner.Tokens.get(i);
        match(Token.TokenType.THEN);
        long stmt=stmt_sequence();
        tree.addChild(parent, stmt);
        token=Scanner.Tokens.get(i);
        if (token.getTokenType()== Token.TokenType.ELSE) {
            match(Token.TokenType.ELSE);
            long seq = stmt_sequence();
            token = Scanner.Tokens.get(i);
            tree.addChild(parent,seq);
        }
        match(Token.TokenType.END);
        return parent;
    }

    long repeat_stmt() throws ParsingException {
        Token token = Scanner.Tokens.get(i);
        match(Token.TokenType.REPEAT);
        long parent = tree.addRepeatStmtNode();
        long t = stmt_sequence();
        token=Scanner.Tokens.get(i);
        match(Token.TokenType.UNTIL);
        long ex = exp();
        tree.addChild(parent,t);
        tree.addChild(parent,ex);
        return  parent;
    }

    long assign_stmt() throws ParsingException {
        Token token=Scanner.Tokens.get(i);
        match(Token.TokenType.IDENTIFIER);
        long parent = tree.addAssignStmtNode(token.stringVal);
        token=Scanner.Tokens.get(i);
        match(Token.TokenType.ASSIGN);
        long tempaya = exp();
        tree.addChild(parent,tempaya);
        return parent;
    }

    long write_stmt() throws ParsingException {
        Token token=Scanner.Tokens.get(i);
        match(Token.TokenType.WRITE);
        long temp =tree.addWriteStmtNode();
        long t = exp();
        tree.addChild(temp,t);
        return temp;
    }

    long read_stmt() throws ParsingException{
        Token token=Scanner.Tokens.get(i);
        match(Token.TokenType.READ);
        token=Scanner.Tokens.get(i);
        match(Token.TokenType.IDENTIFIER);
        return tree.addReadStmtNode(token.stringVal);
    }

    long term() throws ParsingException{
        long node = 0;
        long temp = factor();
        long t;
        Token token = Scanner.Tokens.get(i);
        while (Token.TokenType.MULT == token.getTokenType() || Token.TokenType.DIV == token.getTokenType()) {
            switch (token.getTokenType()) {
                case DIV:
                    match(Token.TokenType.DIV);
                    node = tree.addOperatorNode("/");
                    tree.addChild(node, temp);
                    break;
                case MULT:
                    match(Token.TokenType.MULT);
                    node = tree.addOperatorNode("*");
                    tree.addChild(node, temp);
                    break;
                default:
                    throw new ParsingException("Invalid arithmetic exception");
            }
            t = factor();
            tree.addChild(node, t);
            temp = node;
            token = Scanner.Tokens.get(i);
        }
        return temp;
    }

    long simple_exp() throws ParsingException{
        long temp = term();
        long node = 0;
        long t;
        Token token = Scanner.Tokens.get(i);
        while (Token.TokenType.PLUS == token.getTokenType() || Token.TokenType.MINUS == token.getTokenType()) {
            switch (token.getTokenType()) {
                case PLUS:
                    match(Token.TokenType.PLUS);
                    node = tree.addOperatorNode("+");
                    tree.addChild(node, temp);
                    break;
                case MINUS:
                    match(Token.TokenType.MINUS);
                    node = tree.addOperatorNode("-");
                    tree.addChild(node, temp);
                    break;
                default:
                    throw new ParsingException("Invalid arithmetic exception");
            }
            t = term();
            tree.addChild(node, t);
            temp = node;
            token = Scanner.Tokens.get(i);
        }
        return temp;
    }

    long exp() throws ParsingException{
        long tttt;
        long temp = simple_exp();
        Token token = Scanner.Tokens.get(i);
        long parent;
        switch (token.getTokenType()) {
            case LESSTHAN:
                match(Token.TokenType.LESSTHAN);
                parent = tree.addOperatorNode("<");
                tree.addChild(parent, temp);
                break;
            case EQUAL:
                match(Token.TokenType.EQUAL);
                parent = tree.addOperatorNode("=");
                tree.addChild(parent, temp);
                break;
            case GREATERTHAN:
                match(Token.TokenType.GREATERTHAN);
                parent = tree.addOperatorNode(">");
                tree.addChild(parent, temp);
                break;
            default:
                return temp;
        }
        tttt = simple_exp();
        tree.addChild(parent, tttt);
        return parent;
    }

    long factor() throws ParsingException{
        long child = 0;
        Token token = Scanner.Tokens.get(i);
        switch (token.getTokenType()) {
            case OPENBRACKET:
                match(Token.TokenType.OPENBRACKET);
                child = exp();
                match(Token.TokenType.CLOSEDBRACKET);
                break;
            case NUMBER:
                match(Token.TokenType.NUMBER);
                child = tree.addConstNode(token.stringVal);
                break;
            case IDENTIFIER:
                match(Token.TokenType.IDENTIFIER);
                child = tree.addIDNode(token.stringVal);
                break;
            default:
                throw new ParsingException("Invalid factor");
        }
        return child;
    }

    public void match(Token.TokenType type) throws ParsingException {

        Token current = Scanner.Tokens.get(i);
        if (current.getTokenType() == type) {
            if (i+1 >= Scanner.Tokens.size()) {
                return;
            }
            i++;
        }else{
            if(current == null || i == Scanner.Tokens.size() - 1){
                throw new ParsingException("Error! Expected a \"" + type +"\" ,but found nothing" + " instead.");
            }
            else{
                throw new ParsingException("Error! Expected a \"" + type +"\" ,but found \"" +  current.getTokenType() + "\" instead.");
            }
        }
    }


}

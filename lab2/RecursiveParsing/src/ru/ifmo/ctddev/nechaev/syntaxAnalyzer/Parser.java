package ru.ifmo.ctddev.nechaev.syntaxAnalyzer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/14.
 */
public class Parser {
    LexicalAnalyzer lexicalAnalyzer;

    Tree S() throws ParseException {
        switch (lexicalAnalyzer.getCurToken()) {
            case IDENT:
                // <type>
                Tree sub = W();
                lexicalAnalyzer.nextToken();
                Tree cont = S();
                return new Tree("S", sub, cont);
            case END:
                // eps
                return new Tree("S", new Tree("<end>"));
            default:
                throw new ParseException("`Ident` or `End` expected", lexicalAnalyzer.getStartPos());
        }
    }

    Tree W() throws ParseException {
        switch (lexicalAnalyzer.getCurToken()) {
            case IDENT:
                // <type>
                String name = lexicalAnalyzer.getCurToken().getValue();
                lexicalAnalyzer.nextToken();
                Tree cont = V();
                return new Tree("W", new Tree("<type>::" + name), cont);
            default:
                throw new ParseException("`Ident` expected", lexicalAnalyzer.getStartPos());
        }
    }

    Tree V() throws ParseException {
        String name;
        switch (lexicalAnalyzer.getCurToken()) {
            case POINTERS:
                String stars = lexicalAnalyzer.getCurToken().getValue();
                lexicalAnalyzer.nextToken();
                if (lexicalAnalyzer.getCurToken() != Token.IDENT) {
                    throw new ParseException("`Ident` expected", lexicalAnalyzer.getStartPos());
                }
                name = lexicalAnalyzer.getCurToken().getValue();
                lexicalAnalyzer.nextToken();
                switch (lexicalAnalyzer.getCurToken()) {
                    case SEMICOLON:
                        return new Tree("V", new Tree("<pointer's>::" + stars), new Tree("<name>::" + name), new Tree(";"));
                    case COMMA:
                        lexicalAnalyzer.nextToken();
                        Tree cont = V();
                        return new Tree("V", new Tree("<pointer's>::" + stars), new Tree("<name>::" + name), new Tree(","), cont);
                    default:
                        throw new ParseException("`;` or `,` expected at position", lexicalAnalyzer.getStartPos());
                }
            case IDENT:
                // <variable>
                name = lexicalAnalyzer.getCurToken().getValue();
                lexicalAnalyzer.nextToken();
                switch (lexicalAnalyzer.getCurToken()) {
                    case SEMICOLON:
                        return new Tree("V", new Tree("<variable>::" + name), new Tree(";"));
                    case COMMA:
                        lexicalAnalyzer.nextToken();
                        Tree cont = V();
                        return new Tree("V", new Tree("<variable>::" + name), new Tree(","), cont);
                    default:
                        throw new ParseException("`;` or `,` expected at position", lexicalAnalyzer.getStartPos());
                }
            default:
                throw new ParseException("`Pointers` or `Ident` expected", lexicalAnalyzer.getStartPos());
        }
    }

    public Tree parse(InputStream inputStream) throws ParseException {
        lexicalAnalyzer = new LexicalAnalyzer(inputStream);
        lexicalAnalyzer.nextToken();
        return S();
    }

    public Tree parse(String source) throws ParseException {
        return this.parse(new ByteArrayInputStream(source.getBytes()));
    }
}

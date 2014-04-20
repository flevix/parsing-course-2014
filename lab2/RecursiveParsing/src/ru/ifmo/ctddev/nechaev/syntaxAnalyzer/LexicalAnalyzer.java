package ru.ifmo.ctddev.nechaev.syntaxAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/14.
 */
public class LexicalAnalyzer {
    private InputStream inputStream;
    private int curChar;
    private int curPos;
    Token curToken;

    LexicalAnalyzer(InputStream inputStream) throws ParseException {
        this.inputStream = inputStream;
        this.curPos = 0;
        nextChar();
    }

    public Token getCurToken() {
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }

    public int getStartPos() {
        return curPos - curToken.getLength();
    }

    public void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        switch (curChar) {
            case ',':
                nextChar();
                curToken = Token.COMMA;
                break;
            case ';':
                nextChar();
                curToken = Token.SEMICOLON;
                break;
            case '*':
                StringBuilder stars = new StringBuilder(16);
                while (isStar(curChar)) {
                    nextChar();
                    stars.append('*');
                }
                curToken = Token.POINTERS;
                curToken.setValue(stars.toString());
                break;
            case -1:
                curToken = Token.END;
                break;
            default:
                StringBuilder name = new StringBuilder(16);
                if (isLetter(curChar)) {
                    do {
                        name.append((char) curChar);
                        nextChar();
                    } while (isLetter(curChar));
                    curToken = Token.IDENT;
                    curToken.setValue(name.toString());
                } else {
                    throw new ParseException("Illegal character " + (char) curChar, curPos);
                }
        }
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = inputStream.read();
        } catch (IOException ioe) {
            throw new ParseException(ioe.getMessage(), curPos);
        }
    }

    private boolean isLetter(int ch) {
        return Character.isLetter(ch);
    }

    private boolean isStar(int ch) {
        return ch == '*';
    }

    private boolean isBlank(int ch) {
        return ch == ' ' || ch == '\r' || ch == '\n' || ch == '\t';
    }
}

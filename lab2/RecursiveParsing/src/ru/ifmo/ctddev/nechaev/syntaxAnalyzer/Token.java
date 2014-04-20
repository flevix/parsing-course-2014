package ru.ifmo.ctddev.nechaev.syntaxAnalyzer;

/**
 * Created by Nechaev Mikhail
 * Since 16/04/14.
 */

/**
 * COMMA     -- запятая
 * SEMICOLON -- точка с запятой
 * POINTERS  -- указатели
 * IDENT     -- идентификатор тип/переменная
 */
public enum Token {
    COMMA, SEMICOLON, POINTERS, END, IDENT;

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return null == value ? 1 : value.length();
    }
}

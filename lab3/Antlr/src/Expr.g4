grammar Expr;

prog:   stat+ EOF;

stat:   ID '=' expr ';'             # FAssign
    |   expr ';'                    # Eval
    ;

expr:   expr op=(MUL|DIV) expr      # MulDiv
    |   expr op=(ADD|SUB) expr      # AddSub
    |       INT                     # Int
    |   SUB INT                     # SubInt
    |   ID '=' expr                 # Assign
    |       ID                      # Id
    |   SUB ID                      # SubId
    |   '(' expr ')'                # Parens
    ;

MUL :   '*' ;
DIV :   '/' ;
ADD :   '+' ;
SUB :   '-' ;
ID  :   [a-zA-Z]+ ;
INT :   [0-9]+ ;
WS  :   [ \r\n\t]+ -> skip ;
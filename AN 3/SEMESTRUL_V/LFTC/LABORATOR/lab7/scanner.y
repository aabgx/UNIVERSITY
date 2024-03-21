%{
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

extern void printTS();
extern void printFIP();
extern void printTabelaInterna();
extern int yylex();
extern int yyparse();
extern FILE* yyin;
extern int lineNumber;
void yyerror(char *s);
%}

%token ID
%token DECIMAL
%token REAL_NUMBER
%token HEXA
%token BINARY
%token OCTAL
%token REPETA
%token PANACAND
%token SFREPETA
%token MAIN
%token WHILE
%token USING
%token NAMESPACE
%token STD
%token CIN
%token COUT
%token FOR
%token IF
%token INCLUDE
%token INT
%token DOUBLE
%token IOSTREAM
%token ARROW
%token DECREMENTASSIGN
%token DECREMENT
%token DIVID
%token MOD
%token COUTARROWS
%token LT
%token CINARROWS
%token GT
%token EQUAL
%token NOTEQUAL
%token LE
%token GE
%token AND
%token BITAND
%token OR
%token BITOR
%token NOT
%token INCREMENT
%token INCREMENTASSIGN
%token MULASSIGN
%token DIVIDASSIGN
%token MODASSIGN
%token OPENBRACE
%token CLOSEBRACE
%token OPENPARAN
%token CLOSEPARAN
%token SEMICOLON
%token TWOPOINTS
%token HASHTAG
%token COMMA
%token MUL
%token PLUS
%token MINUS
%token ASSIGN
%token ELSE
%token RETURN
%token POINT
%token STRINGCONSTANT
%token CHAR
%token FLOAT
%token MATHH

%%
program: lista_importuri USING NAMESPACE STD SEMICOLON INT MAIN OPENPARAN CLOSEPARAN OPENBRACE lista_instructiuni RETURN SEMICOLON CLOSEBRACE;
lista_importuri: import | import lista_importuri;
import: HASHTAG INCLUDE LT librarie GT;
librarie: IOSTREAM | MATHH

lista_instructiuni: instructiune | instructiune lista_instructiuni;
instructiune: declarare | atribuire SEMICOLON | intrare | iesire | conditional | ciclare;

declarare: tip declarare_body SEMICOLON;
declarare_body: id_sau_atribuire | id_sau_atribuire COMMA declarare_body;
id_sau_atribuire: ID | atribuire;
tip: INT | FLOAT | CHAR;

atribuire: ID ASSIGN expresie;
expresie: atom | expresie op atom;
op: PLUS | MINUS | MUL | DIVID | MOD;
atom: ID | constant;
constant: DECIMAL | BINARY | OCTAL | HEXA | REAL_NUMBER | STRINGCONSTANT;

intrare: CIN CINARROWS ID SEMICOLON;
iesire: COUT COUTARROWS expresie SEMICOLON;

conditional: IF OPENPARAN conditie CLOSEPARAN OPENBRACE lista_instructiuni CLOSEBRACE altfel;
altfel: ELSE OPENBRACE lista_instructiuni CLOSEBRACE | /*empty*/;
conditie: expresie relatie expresie;
relatie: EQUAL | NOTEQUAL | LT | LE | GT | GE;

ciclare: for_loop | while_loop;
for_loop: FOR OPENPARAN atribuire SEMICOLON conditie SEMICOLON incrementare CLOSEPARAN OPENBRACE lista_instructiuni CLOSEBRACE;
incrementare: ID INCREMENT;
while_loop: WHILE OPENPARAN conditie CLOSEPARAN OPENBRACE lista_instructiuni CLOSEBRACE;
%%

int main(int argc, char* argv[]) {
    ++argv, --argc;

    // sets the input for flex file
    if (argc > 0)
        yyin = fopen(argv[0], "r");
    else
        yyin = stdin;

    //read each line from the input file and process it
    while (!feof(yyin)) {
        yyparse();
    }
    printTabelaInterna();
    printTS();
    printFIP();
    printf("The file is sintactically correct!\n");
    return 0;
}

void yyerror(char *s) {
    printTabelaInterna();
    printTS();
    printFIP();
    extern char* yytext;
    printf("Error for symbol %s at line: %d ! \n",yytext, lineNumber);
    exit(1);
}
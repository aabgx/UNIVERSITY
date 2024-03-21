%{
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int yyparse();
extern FILE* yyin;
void yyerror(char *s);
%}

%token ZEROO
%token UNUU
%token DOII
%token TREII
%token PATRUU
%token CINCII
%token SASEE
%token SAPTEE
%token OPTT
%token NOUAA
%token DOUA_PUNCTEE

%%
ora: UNUU ora UNUU | DOII ora DOII | TREII ora TREII | PATRUU ora PATRUU | CINCII ora CINCII | SASEE ora SASEE | SAPTEE ora SAPTEE | OPTT ora OPTT | NOUAA ora NOUAA | DOUA_PUNCTEE;
%%

int main(int argc, char **argv) {
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

    printf("Este palindrom!\n");
    return 0;
}

void yyerror(char *s) {
    extern char* yytext;
    printf("Nu este palindrom!");
    exit(1);
}
%{
#include "scanner.tab.h"

#include <string.h>
#include <stdio.h>

int lineNumber = 1;


typedef struct{
    char atom[100];
    int codAtom;
    int codTS;
}FIP;

typedef struct{
    char atom[100];
    int codAtomTS;
}TS;

struct SymbolTable {
    char symbol[20];
    int code;
};

struct SymbolTable symbolTable[] = {
    {"main", 2},
    {"while", 3},
    {"using", 4},
    {"namespace", 5},
    {"std", 6},
    {"cin", 7},
    {"cout", 8},
    {"for", 9},
    {"if", 10},
    {"include", 11},
    {"int", 12},
    {"double", 13},
    {"iostream", 14},
    {".",55},
    {"*", 15},
    {"+", 16},
    {"-", 17},
    {"/", 18},
    {"%", 19},
    {"<", 20},
    {">", 22},
    {"<=", 27},
    {">=", 28},
    {"==", 25},
    {"!=", 26},
    {"<<", 21},
    {">>", 23},
    {"=", 24},
    {"&", 29},
    {"|", 30},
    {"!", 31},
    {"++", 32},
    {"--", 33},
    {"+=", 34},
    {"-=", 35},
    {"*=", 36},
    {"/=", 37},
    {"%=", 38},
    {"{", 39},
    {"}", 40},
    {"(", 41},
    {")", 42},
    {";", 43},
    {":", 44},
    {"#", 45},
    {"repeta", 46},
    {"panacand", 47},
    {"sfrepeta", 48},
    {"->", 49},
    {",",50},
    {"&&",51},
    {"||",52},
    {"else",53},
    {"return 0",54},
    {".",55},
    {"char",56},
    {"float",57},
    {"math.h",58}
};

FIP fip[300];
TS tabela_simboluri[300];
int poz_TS = 0;
int poz_FIP = 0;

void addToFIP(char atom[], int codAtom) {
    int ok = 0;

    //verificăm dacă atomul există în tabela_simboluri
    for (int i = 0; i < poz_TS; ++i) {
        if (strcmp(tabela_simboluri[i].atom, atom) == 0) {
            strcpy(fip[poz_FIP].atom,atom);
            fip[poz_FIP].codAtom = codAtom;
            fip[poz_FIP].codTS = tabela_simboluri[i].codAtomTS;
            ok = 1;
            break;
        }
    }

    //dacă atomul nu există, adăugăm cu -1 în FIP
    if (ok == 0) {
        strcpy(fip[poz_FIP].atom,atom);
        fip[poz_FIP].codAtom = codAtom;
        fip[poz_FIP].codTS = -1;
    }
    
    //crestem numarul curent de elemente din FIP
    poz_FIP++;
}

void addToTS(char atom[]) {
    int gasit = 0;
    int index_inserare = 0;

    //verif. sa nu existe deja in TS - aflam indexul de inserare daca nu e de gasit
    for (int i = 0; i < poz_TS; ++i) {
        int comparatie = strcmp(tabela_simboluri[i].atom, atom);
	      
        //l-am gasit, nu ne mai batem capul
        if (comparatie == 0) {
            gasit = 1;
            index_inserare = i;
            break;
        } 
        //ne spune ca am ajuns la un atom > lexicografic decat atomul curent -> poz de inserare
        else if (comparatie > 0) {
            index_inserare = i;
            break;
        }
	if(i==poz_TS-1)
		index_inserare = poz_TS;

        //altfel inca suntem la atomi mai mici d=lexicografic decat atomul nostru
    }

    //daca nu l-am gasit, il adaugam pe pozitia corecta
    if (!gasit) {
        //mutam elems la dreapta pt a face loc noului atom
        for (int i = poz_TS; i > index_inserare; --i) {
            strcpy(tabela_simboluri[i].atom, tabela_simboluri[i - 1].atom);
            tabela_simboluri[i].codAtomTS = tabela_simboluri[i - 1].codAtomTS;
        }

        //adaugarea efectiva
        strcpy(tabela_simboluri[index_inserare].atom, atom);
        tabela_simboluri[index_inserare].codAtomTS = poz_TS;

        poz_TS++;
	}
}

void printTS() {
    printf("Tabela de Simboluri (TS):\n");
    printf("--------------------------------------\n");
    printf("| %-20s | %-10s |\n", "Atom", "Cod Atom TS");
    printf("--------------------------------------\n");

    for (int i = 0; i < poz_TS; i++) {
        printf("| %-20s | %-10d |\n", tabela_simboluri[i].atom, tabela_simboluri[i].codAtomTS);
    }

    printf("--------------------------------------\n");
}

// Funcția pentru a afișa FIP
void printFIP() {
    printf("FIP: \n");
    printf("-------------------------------------------------\n");
    printf("| %-20s | %-10s | %-11s |\n", "Atom", "Cod Atom", "Cod TS");
    printf("-------------------------------------------------\n");

    for (int i = 0; i < poz_FIP; i++) {
        printf("| %-20s | %-10d | %-11d |\n", fip[i].atom, fip[i].codAtom, fip[i].codTS);
    }

    printf("-------------------------------------------------\n");
}

// Funcția pentru a afișa Tabela Interna
void printTabelaInterna() {
    int size = sizeof(symbolTable) / sizeof(symbolTable[0]);

    printf("TABELA INTERNA:\n");
	printf("|----------------------|------|\n");
    printf("| %-20s | %-3s |\n", "Atom", "Code");
    printf("|----------------------|------|\n");

    for (int i = 0; i < size; ++i) {
        printf("| %-20s | %-4d |\n", symbolTable[i].symbol, symbolTable[i].code);
    }
    printf("|----------------------|------|\n");
}


%}

%option noyywrap

REAL_NUMBER  ([0-9]+(\.[0-9]*)?|\.[0-9]+)
IDENTIFIER   [a-zA-Z]+
STRING_CONST       \".*\"
DECIMAL      [1-9][0-9]*$
OCTAL        0[0-7]+
HEXA         0x[0-9a-fA-F]+
BINARY       0b[01]+

/*** Rule Section ***/
%%
{BINARY}             { addToTS(yytext); addToFIP(yytext, 1); return BINARY;}
"math.h"	     { addToFIP(yytext, 58); return MATHH;}
int                  { addToFIP(yytext, 12); return INT;}
char		     { addToFIP(yytext, 56); return CHAR;}
float		     { addToFIP(yytext, 57); return FLOAT;}
repeta		     { addToFIP(yytext, 46); return REPETA;}
panacand	     { addToFIP(yytext, 47); return PANACAND;}
sfrepeta	     { addToFIP(yytext, 48); return SFREPETA;}
main                 { addToFIP(yytext, 2); return MAIN;}
while                { addToFIP(yytext, 3); return WHILE;}
using                { addToFIP(yytext, 4); return USING;}
namespace            { addToFIP(yytext, 5); return NAMESPACE;}
std                  { addToFIP(yytext, 6); return STD;}
cin                  { addToFIP(yytext, 7); return CIN;}
cout                 { addToFIP(yytext, 8); return COUT;}
for                  { addToFIP(yytext, 9); return FOR;}
if                   { addToFIP(yytext, 10); return IF;}
else                 { addToFIP(yytext, 53); return ELSE;}
include              { addToFIP(yytext, 11); return INCLUDE;}
double               { addToFIP(yytext, 13); return DOUBLE;}
iostream             { addToFIP(yytext, 14); return IOSTREAM;}
"return 0"           { addToFIP(yytext, 54); return RETURN;}
"->"		     { addToFIP(yytext, 49); return ARROW;}
"-="                 { addToFIP(yytext, 35); return DECREMENTASSIGN;}
"--"                 { addToFIP(yytext, 33); return DECREMENT;}
"/"                  { addToFIP(yytext, 18); return DIVID;}
"%"                  { addToFIP(yytext, 19); return MOD;}
"<<"                 { addToFIP(yytext, 21); return COUTARROWS;}
"<"                  { addToFIP(yytext, 20); return LT;}
">>"                 { addToFIP(yytext, 23); return CINARROWS;}
">"                  { addToFIP(yytext, 22); return GT;}
"=="                 { addToFIP(yytext, 25); return EQUAL;}
"!="                 { addToFIP(yytext, 26); return NOTEQUAL;}
"<="                 { addToFIP(yytext, 27); return LE;}
">="                 { addToFIP(yytext, 28); return GE;}
"&&"                 { addToFIP(yytext, 51); return AND;}
"&"                  { addToFIP(yytext, 29); return BITAND;}
"||"                 { addToFIP(yytext, 52); return OR;}
"|"                  { addToFIP(yytext, 30); return BITOR;}
"!"                  { addToFIP(yytext, 31); return NOT;}
"++"                 { addToFIP(yytext, 32); return INCREMENT;}
"+="                 { addToFIP(yytext, 34); return INCREMENTASSIGN;}
"*="                 { addToFIP(yytext, 36); return MULASSIGN;}
"/="                 { addToFIP(yytext, 37); return DIVIDASSIGN;}
"%="                 { addToFIP(yytext, 38); return MODASSIGN;}
"{"                  { addToFIP(yytext, 39); return OPENBRACE;}
"}"                  { addToFIP(yytext, 40); return CLOSEBRACE;}
"("                  { addToFIP(yytext, 41); return OPENPARAN;}
")"                  { addToFIP(yytext, 42); return CLOSEPARAN;}
";"                  { addToFIP(yytext, 43); return SEMICOLON;}
":"                  { addToFIP(yytext, 44); return TWOPOINTS;}
"#"                  { addToFIP(yytext, 45); return HASHTAG;}
","                  { addToFIP(yytext, 50); return COMMA;}
"*"                  { addToFIP(yytext, 15); return MUL;}
"+"                  { addToFIP(yytext, 16); return PLUS;}
"-"                  { addToFIP(yytext, 17); return MINUS;}
"="                  { addToFIP(yytext, 24); return ASSIGN;}
"."		     { addToFIP(yytext, 55); return POINT;}
{OCTAL}              { addToTS(yytext); addToFIP(yytext, 1); return OCTAL;}
{IDENTIFIER}         { addToTS(yytext); addToFIP(yytext, 0); return ID;}
{REAL_NUMBER}        { addToTS(yytext); addToFIP(yytext, 1); return REAL_NUMBER;}
{DECIMAL}            { addToTS(yytext); addToFIP(yytext, 1); return DECIMAL;}
{HEXA}               { addToTS(yytext); addToFIP(yytext, 1); return HEXA;}
{STRING_CONST}       { addToTS(yytext); addToFIP(yytext, 1); return STRINGCONSTANT;}
[\n]		     { ++lineNumber; }
[ \t\n]+             { ; /* eat up whitespace */ }
%%
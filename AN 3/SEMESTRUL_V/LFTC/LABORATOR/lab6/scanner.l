/*** Definition Section ***/
%{
#include <string.h>
#include <stdio.h>


int errorFound = 0;
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
    {"->", 49}
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


REAL_NUMBER  [+-]?(0|[1-9][0-9]*)(\.[0-9]+)?
IDENTIFIER   [a-zA-Z]+
STRING_CONST       \".*\"
DECIMAL      ^[1-9][0-9]*$
OCTAL        0[0-7]+
HEXA         0x[0-9a-fA-F]+
BINARY       0b[01]+

/*** Rule Section ***/
%%
{BINARY}            { addToTS(yytext); addToFIP(yytext, 1); }
repeta		    { addToFIP(yytext, 46);}
panacand	    { addToFIP(yytext, 47);}
sfrepeta	    { addToFIP(yytext, 48);}
main                { addToFIP(yytext, 2); }
while               { addToFIP(yytext, 3); }
using               { addToFIP(yytext, 4); }
namespace           { addToFIP(yytext, 5); }
std                 { addToFIP(yytext, 6); }
cin                 { addToFIP(yytext, 7); }
cout                { addToFIP(yytext, 8); }
for                 { addToFIP(yytext, 9); }
if                  { addToFIP(yytext, 10); }
include             { addToFIP(yytext, 11); }
int                 { addToFIP(yytext, 12); }
double              { addToFIP(yytext, 13); }
iostream            { addToFIP(yytext, 14); }
\*                  { addToFIP(yytext, 15); }
\+                  { addToFIP(yytext, 16); }
\->		    { addToFIP(yytext, 49);}
\-                  { addToFIP(yytext, 17); }
\/                  { addToFIP(yytext, 18); }
\%                  { addToFIP(yytext, 19); }
\<<                 { addToFIP(yytext, 21); }
\<                  { addToFIP(yytext, 20); }
\>>                 { addToFIP(yytext, 23); }
\>                  { addToFIP(yytext, 22); }
\=                  { addToFIP(yytext, 24); }
\==                 { addToFIP(yytext, 25); }
\!=                 { addToFIP(yytext, 26); }
\<=                 { addToFIP(yytext, 27); }
\>=                 { addToFIP(yytext, 28); }
\&                  { addToFIP(yytext, 29); }
\|                  { addToFIP(yytext, 30); }
\!                  { addToFIP(yytext, 31); }
\++                 { addToFIP(yytext, 32); }
\--                 { addToFIP(yytext, 33); }
\+=                 { addToFIP(yytext, 34); }
\-=                 { addToFIP(yytext, 35); }
\*=                 { addToFIP(yytext, 36); }
\/=                 { addToFIP(yytext, 37); }
\%=                 { addToFIP(yytext, 38); }
\{                  { addToFIP(yytext, 39); }
\}                  { addToFIP(yytext, 40); }
\(                  { addToFIP(yytext, 41); }
\)                  { addToFIP(yytext, 42); }
\;                  { addToFIP(yytext, 43); }
\:                  { addToFIP(yytext, 44); }
\#                  { addToFIP(yytext, 45); }
{IDENTIFIER}        { addToTS(yytext); addToFIP(yytext, 0); }
{REAL_NUMBER}       { addToTS(yytext); addToFIP(yytext, 1); }
{DECIMAL}           { addToTS(yytext); addToFIP(yytext, 1); }
{OCTAL}             { addToTS(yytext); addToFIP(yytext, 1); }
{HEXA}              { addToTS(yytext); addToFIP(yytext, 1); }
{STRING_CONST}      { addToTS(yytext); addToFIP(yytext, 1); }
[\n]		    { ++lineNumber; }
[ \t\n]+            { ; /* eat up whitespace */ }
.                   { errorFound = 1; printf("Illegal token %s at line %d !", yytext, lineNumber); printf("\n"); exit(EXIT_FAILURE); }
%%

/* yywrap() - wraps the above rule section */
int yywrap() {}

int main(int argc, char** argv) {
	/* deschidere fisier intrare */
	FILE *fp;
	fp = fopen(argv[1], "r");
	if (fp == NULL) {
	    fprintf(stderr, "Error opening file\n");
	    return 1;
	}


	/* yyin - takes the file pointer which contains the input*/
	yyin = fp;



	/* yylex() - this is the main flex function which runs the Rule Section*/ 
	yylex();

 	int symbolTableSize = sizeof(symbolTable) / sizeof(symbolTable[0]);

	printf("\n\n\n");
    	printTabelaInterna(symbolTable, symbolTableSize);
	printf("\n\n\n");
	printFIP();
	printf("\n\n\n");
	printTS();

	return 0;
}
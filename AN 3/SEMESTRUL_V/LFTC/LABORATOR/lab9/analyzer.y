%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "attrib.h"
#include "codeASM.h"

int yylex(void); // Add this line
void yyerror();
extern int yylineno;
extern char* yytext;

char temps[10][10];

int tempnr = 1;
int nrVars = 0;

void newTempName(char* s){
  sprintf(s,"temp%d",tempnr);
  tempnr++;
}

void addToTemps(char* s){
	for(int i=0; i<nrVars; i++)
		if(strcmp(temps[i], s) == 0) return; 
	strcpy(temps[nrVars], s);
	nrVars++;
}

char tempbuffer[250];

int isNumeric(char *str) {
    char firstChar = *str;
    return (firstChar >= '0' && firstChar <= '9');
}

void addBracketsAroundString(char *input) {
    // Check if the input is not NULL and not an empty string
    if (input == NULL || *input == '\0') {
        return;  // Return for invalid input
    }

    // Calculate the length of the input string
    size_t inputLength = strlen(input);

    // Shift the content to make space for '[' at the beginning
    memmove(input + 1, input, inputLength + 1);

    // Add '[' at the beginning
    input[0] = '[';

    // Add ']' at the end
    input[inputLength + 1] = ']';
}

char instructions[20][250];
int nrI = 0;

void addInstruction(char s[]){
	strcpy(instructions[nrI++], s);
}

char *readMsg = "\n read_int_fmt DB \"%%i\", 0";
char *printMsg = "\n\n write_int_fmt DB \"%%d \", 0";

%}

%token<varname> ID 
%token SEMICOLON
%token<varname> POSITIVE
%token INCLUDE
%token IOSTREAM
%token STRING
%token USING
%token NAMESPACE
%token STD
%token LESS_SEP
%token GT_SEP
%token MAIN
%token OPEN_PAR
%token CLOSED_PAR
%token OPEN_BRACK
%token CLOSED_BRACK
%token RETURN
%token ZERO
%token INT
%token COMMA
%token CIN
%token COUT
%token CIN_OPERATOR
%token COUT_OPERATOR
%token ENDL
%token EQ 
%token<str> ARITHMETIC_OPERATOR

%start program 
%union {
 char varname[10];
 attributes attrib;
 char strCode[250];
 char str[10];
}

%type<attrib> expresie expresie_primara atribuire

%%

program: header_list using_directive program_root;

header_list: header | header header_list;	
header: INCLUDE LESS_SEP header_file GT_SEP;
header_file: IOSTREAM;

using_directive: USING NAMESPACE STD SEMICOLON;
	
program_root: INT MAIN OPEN_PAR CLOSED_PAR OPEN_BRACK program_content RETURN ZERO SEMICOLON CLOSED_BRACK;
program_content: lista_declarari lista_instructiuni;

lista_declarari: declarare SEMICOLON | declarare SEMICOLON lista_declarari;
declarare: tip_data lista_id;
tip_data: INT ;
lista_id: ID  { addToTemps($1); } | ID COMMA lista_id { addToTemps($1); };

lista_instructiuni:  instructiune SEMICOLON | instructiune SEMICOLON lista_instructiuni;
instructiune: citire | scriere | atribuire;

citire: CIN CIN_OPERATOR ID   { char instr[250];
				sprintf(instr, "\n mov EAX, %s\n push EAX\n push dword read_int_fmt\n call [scanf]\n add ESP, 8", $3);
				addInstruction(instr);
			      };

scriere: COUT COUT_OPERATOR ID  { char instr[250];
                		  sprintf(instr, "\n mov EAX, [%s]\n push EAX\n push dword write_int_fmt\n call [printf]\n add ESP, 8\n", $3); 
				  addInstruction(instr);
				}
	| COUT COUT_OPERATOR ENDL { char instr[250];
	               		    sprintf(instr, "\n push dword write_line_fmt\n call [printf]\n add ESP, 4\n"); 
				    addInstruction(instr);
	           		  };

expresie: expresie_primara ARITHMETIC_OPERATOR expresie { printf("expresie mare varn: %s, operator: %s, code: %s\n", $$.varn, $2, $3.code);
							  //imi creez o variabila temporara cu expresia
                                                          newTempName($$.varn);
							  addToTemps($$.varn);
							  printf("$$ varname: %s\n", $$.varn);
							  printf("$1 code: %s, $3 code: %s\n", $1.code, $3.code);

							  //acum am T2-ul si trebe sa il combin cu expresiile mai mici (T1..)
						          sprintf($$.code,"%s\n%s\n",$1.code,$3.code);

							  if(!isNumeric($1.varn))
							  addBracketsAroundString($1.varn);
							  if(!isNumeric($3.varn))
							  addBracketsAroundString($3.varn);
											
							  //construiesc efectiv foma assembly care imi trebe			  
							  if(strcmp($2, "+") == 0)
						          	sprintf(tempbuffer,ADD_ASM_FORMAT,$1.varn,$3.varn,$$.varn);
							  else if(strcmp($2, "-") == 0)
								sprintf(tempbuffer,SUB_ASM_FORMAT,$1.varn,$3.varn,$$.varn);
							  else if(strcmp($2, "*") == 0)
								sprintf(tempbuffer,MUL_ASM_FORMAT,$1.varn,$3.varn,$$.varn);
							  else if(strcmp($2, "/") == 0)
								sprintf(tempbuffer,DIV_ASM_FORMAT,$1.varn,$3.varn,$$.varn);
                                                          strcat($$.code,tempbuffer);
                                                        }
	| expresie_primara  { strcpy($$.code, $1.code); strcpy($$.varn, $1.varn); };
	
expresie_primara: ID       {  strcpy($$.code,""); strcpy($$.varn, $1);}
	| POSITIVE         { strcpy($$.code, ""); strcpy($$.varn, $1);}
	| OPEN_PAR expresie CLOSED_PAR {
			printf("expresie paranteze: %s %s \n", $2.varn, $2.code);
                        strcpy($$.code, $2.code);
                        strcpy($$.varn,$2.varn);
			printf("expresie paranteze: %s %s \n", $2.varn, $2.code);
                      };  

atribuire: ID EQ expresie { //printf("Atribuire: %s = %s\n", $1, $3.code);
			    addInstruction($3.code);
					
			    //in $3 o sa fie ceva variabila temporara probabil (expresie -> trb calculata)		
			    if(isNumeric($3.varn)){
				char instr[250];
				sprintf(instr, "mov eax, %s\n", $3.varn);
				addInstruction(instr);
			    }
			    else {
				char instr[250];
				sprintf(instr, "mov eax, [%s]\n", $3.varn);
				addInstruction(instr);
			    }
								
			    char instr[250];
			    sprintf(instr, "mov [%s], eax \n", $1);
			    addInstruction(instr);
			    //printf("mov [%s],ax\n",$1);
			  };
%%

int main() {
    	int r = yyparse();

    	if(r == 0){
		printf("No errors occured! \n");
    	}
    	else printf("Found errors! :( \n");
	
	printf("NASM Code: \n");
	printf("\n\n bits 32 \n global start \n extern exit \n import exit msvcrt.dll \n extern printf \n import printf msvcrt.dll \n extern scanf \n import scanf msvcrt.dll");
	printf("\n\n segment data use32 class=data");
	printf("\n\n write_int_fmt DB \"%%d \", 0");
    	printf("\n write_line_fmt DB 0xA, 0x0");
	printf("\n read_int_fmt DB \"%%i\", 0");
	printf("\n");

	for(int i=0; i<nrVars; i++){
		printf(" %s DD 0\n", temps[i]);
	}
	
	printf("\n\n segment code use32 class=code\n\n");
	printf("\n start:");
	
	for(int i=0; i<nrI; i++){
		printf(" %s\n", instructions[i]);
	}
	
	printf(" push dword 0\n");
	printf(" call [exit]");

    	return 0;
}


void yyerror() {
    printf("syntax error: %s at line:  %d\n", yytext, yylineno);
}

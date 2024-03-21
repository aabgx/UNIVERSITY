#ifndef CODEASM_H
#define CODEASM_H


#define ADD_ASM_FORMAT "\
 mov eax, %s \n\
 mov edx, %s \n\
 add eax, edx \n\
 mov [%s], eax \n\
" 

#define SUB_ASM_FORMAT "\
 mov eax, %s \n\
 mov edx, %s \n\
 sub eax, edx \n\
 mov [%s], eax \n\
" 

#define MUL_ASM_FORMAT "\
 mov eax, %s \n\
 mov edx, %s \n\
 imul edx \n\
 mov [%s], eax \n\
" 

#define DIV_ASM_FORMAT "\
 mov eax, %s \n\
 mov edx, %s \n\
 mov ebx, edx \n\
 CDQ \n\
 idiv ebx \n\
 mov [%s], eax \n\
"

#endif

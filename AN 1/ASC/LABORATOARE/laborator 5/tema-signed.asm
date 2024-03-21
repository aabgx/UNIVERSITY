bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; a,b,d-byte; c-doubleword; x-qword
    a db -2 
    b db -3
    c dd 120
    d db -4
    x dq 22
    ;->140 adica 8C in EAX si 0 in EDX
; our code starts here
segment code use32 class=code
    start:
        ; (8-a*b*100+c)/d+x;
        mov al,[a]
        imul byte[b] ; ax = a*b
        mov bx,100
        imul bx ; dx:ax = a*b*100
        
        ;8-a*b*100
        mov bl,8
        movsx bx,bl
        mov cx,0 ; cx:bx = 8
        
        sub bx,ax
        sbb cx,dx ; cx:bx = 8-a*b*100
        
        add bx,word[c+0] ;little endian
        adc cx,word[c+2] ; cx:bx = 8-a*b*100+c
        
        mov ax,bx
        mov dx,cx ; dx:ax = 8-a*b*100+c =1234 5680
        
        movsx bx,[d]
        idiv bx; ax - cat | dx - rest
        
        movsx eax,ax
        movsx edx,dx
        add eax,dword[x+0]
        adc edx,dword[x+4]; edx:eax = rezultatul
 
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

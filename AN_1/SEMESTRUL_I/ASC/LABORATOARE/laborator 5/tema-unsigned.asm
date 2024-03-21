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
    a db 0h ;ca sa nu dea un nr negativ la scadere
    b db 2h
    c dd 1234h
    d db 3h
    x dq 11223344h
    ;->00000000 11223958 in EDX:EAX
; our code starts here
segment code use32 class=code
    start:
        ; (8-a*b*100+c)/d+x;
        mov al,[a]
        mul byte[b] ; ax = a*b
        mov bx,100
        mul bx ; dx:ax = a*b*100
        
        ;8-a*b*100
        mov bl,8
        movzx bx,bl
        mov cx,0 ; cx:bx = 8
        
        sub bx,ax
        sbb cx,dx ; cx:bx = 8-a*b*100
        
        add bx,word[c+0] ;little endian
        adc cx,word[c+2] ; cx:bx = 8-a*b*100+c
        
        mov ax,bx
        mov dx,cx ; dx:ax = 8-a*b*100+c =1234 5680
        
        movzx bx,[d]
        div bx; ax - cat | dx - rest
        
        movzx eax,ax
        movzx edx,dx
        add eax,dword[x+0]
        adc edx,dword[x+4]; edx:eax = rezultatul

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

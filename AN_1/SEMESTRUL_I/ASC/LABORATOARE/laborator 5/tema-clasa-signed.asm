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
    a db -127
    b db -127
    c dd 0FFEEDDCCh ; daca pun 0FFEEDD nu mai merge
    d db 128
    x dq 8877665544332211h
    ;->
; our code starts here
segment code use32 class=code
    start:
        ; (8-a*b*100+c)/d+x;
        mov al,[a] ; 
        imul byte[b] ; ax = a*b ax=3F01
        ;CF=1  ZF=1  SF=0 OF=1
        mov bx,100 ;
        imul bx ; dx:ax = a*b*100 dx=0018 ax=9C64
        ;CF=1  ZF=1  SF=0 OF=1
        
        ;8-a*b*100
        mov bl,8
        movsx bx,bl
        mov cx,0 ; cx:bx = 8
        
        sub bx,ax ; bx=63A4
        ;CF=1  ZF=0  SF=0 OF=0
        sbb cx,dx ; cx:bx = 8-a*b*100 cx=FFE7
        ;CF=1  ZF=0  SF=1 OF=0
        
        add bx,word[c+0] ;little endian  bx=4170
        ;CF=1  ZF=0  SF=0 OF=0
        adc cx,word[c+2] ; cx:bx = 8-a*b*100+c  cx=FFD6
        ;CF=1  ZF=0  SF=1 OF=0
        
        mov ax,bx
        mov dx,cx ; dx:ax = 8-a*b*100+c =1234 5680
        
        movsx bx,[d]
        idiv bx; ax - cat | dx - rest  nu incape catul! cat=10056h=65622(10) rest=6C70h=27760(10)
        
        movsx eax,ax
        movsx edx,dx
        add eax,dword[x+0]
        adc edx,dword[x+4]; edx:eax = rezultatul
 
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

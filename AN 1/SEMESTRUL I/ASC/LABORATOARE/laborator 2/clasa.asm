bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
;   Write a program to compute the E expression with the following variables
;   a-word, b-byte, c-word, d-doubleword
;   0ABh - c + (a + 1011b - b) - d + 5
    a dw 12
    b dw 3
    c dw 1
    d dd 10
    x dq 7
    
    ;declarare constante (un nume alocat valorilor constante)
    ;directiva EQU
    consth EQU 0ABh
    constb EQU 1011b
    constd EQU 5
    
    
    
; our code starts here
segment code use32 class=code
    start:
        ; (a + 1011b - b)
    mov ax, [a]
    add ax, constb  ; ax=a+1011b (pot aduna const la un registru)
    
    ;ax= a + 1011b - 1word (16 biti)
    ; -b byte (8 biti)
    ; trebuie sa il convertim pe b la word
    movzx bx,[b] ;->se extinde b in bx (zx - move with 0 extended - registrul la stanga si ce vrem sa mutam la dreapta)
    sub ax,bx ; ax = (a + 1011b - b)
    
    ; 0ABh - c
    mov bx,consth
    sub bx,[c] ;bx = 0ABh - c
    
    ;0ABh - c + (a + 1011b - b)
    add bx,ax ; bx = 0ABh - c + (a + 1011b - b)
    
    ;convertim pe bx la ebx
    movzx EBX, BX ; ebx=0ABh - c + (a + 1011b - b)
    
    sub ebx,[d] ; ebx= 0ABh - c + (a + 1011b - b)-d
    add ebx, constd ; ebx=0ABh - c + (a + 1011b - b)-d+5
    
    
    ;++++++ suplimentar 0ABh - c + (a + 1011b - b)-d+5 - X(quadword)
    ; edx:eax
    ; ecx:ebx
    ;extindem pe ebx la combinatia de registrii ecx:ebx pt a efectua scaderea cu qw
    mov ecx,0
    ; ecx:ebx=0ABh - c + (a + 1011b - b)-d+5
    ;X quadword
    
    sub ebx, dword[x+0]
    sbb ecx, [x+4]
    ;rez final al expresiei este in ecx:ebx
    
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

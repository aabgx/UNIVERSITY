bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    x db 12h
    y db 10
    z dw 0ABCDh
    t dd 12345678h
    s dq 1122334455667788h

; our code starts here
segment code use32 class=code
    start:
        ; 10-x nu pot scrie sub 10,x pt ca 10 e constanta -> pun 10 in registru
        mov dl,10
        sub dl,[x]
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

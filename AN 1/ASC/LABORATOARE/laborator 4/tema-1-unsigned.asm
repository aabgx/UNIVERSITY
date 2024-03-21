bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; d/[(a+b)-(c+c)]   a,b,c - byte        d - word
    a db 3
    b db 4
    c db 2
    d dw 12
    ; -> 4
; our code starts here
segment code use32 class=code
    start:
        mov bl,[a]
        add bl,[b]
        sub bl,[c]
        sub bl,[c] ; al = (a+b)-(c+c)
        mov ax,[d]
        div bl ; ax = rezultat
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

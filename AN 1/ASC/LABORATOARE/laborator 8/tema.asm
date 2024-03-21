bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dw -22, 145, -48, 127
    lens equ ($-s)/2
    d times lens db -1
    saisprezece db 16
    copie dd 0
    copie1 dw 0
    zero db 0
    ; -> 3 3 5 7
; our code starts here
segment code use32 class=code
    start:
        mov ecx, lens ; ecx - lungimea sirului pt. loop
        mov edi, 0 ; edi - parcurgere s
        mov esi, 0 ; parcurgere d
        mov bl,0 ; aici salvez nr de biti care imi trebuie      
        
        repeta:
            mov al, [s+edi+0]
            mov ah, [s+edi+1] ; ->ax va fi cuvantul cu care lucram
            mov [copie1+0],al
            mov [copie1+1],ah
            mov [copie],ecx
            
            mov ecx,[zero]
            mov cl,[saisprezece]
            
            cifre1:
                shr ax,1     ;in CF va fi ultima cifra rotita la dreapta
                adc bl,[zero] ;numar cifrele de 1 ce apar in reprezentarea binara a numarului dat
            loop cifre1
            
            cmp word[copie1],0
            jge aici ; daca nr. e pozitiv sau 0, am rezolvat problema
            jl nega ; daca nr e negativ, cifrele de 0 vor fi 16 - nr. cifre de 1
            
            nega:
                mov bh,[saisprezece]
                sub bh,bl
                mov bl,bh
            
            aici:
            mov [d+esi],bl
            inc esi
            add edi,2
            mov ecx,[copie]
            mov bl,0
        loop repeta
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

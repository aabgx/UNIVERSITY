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
    c times lens dw -1
    saisprezece db 16
    copie dd 0
    copie2 dw 0
    zero db 0
    
; our code starts here
segment code use32 class=code
    start:
        
        mov esi, s 
        mov ebp, 0 ; parcurgem d cu el
        mov edi,c
        mov ecx, lens ; ecx - lungimea sirului pt. loop
        CLD
        
        repeta:
            lodsw ; cuvintele sunt incarcate pe rand in ax
            stosw ; salvam ax in c, parcurs cu edi
            mov [copie],ecx
            
            mov ecx,[zero]
            mov cl,[saisprezece] ; transformam ecx pt bucla mica, ce trebuie efectuata de 16 ori
            
            cifre1:
                shr ax,1     ;in CF va fi ultima cifra rotita la dreapta
                adc bl,[zero] ;numar cifrele de 1 ce apar in reprezentarea binara a numarului dat
            loop cifre1
            
            
            mov ax,0 ; ax oricum nu ne mai trebuie in forma asta, il folosim la comparare
            sub edi,2 ;ca sa comparam pozitia curenta cu 0, nu urmatoarea
            scasw ; aici e iar crescut edi,deci revein la pozitia buna
            jge nega ; daca nr e negativ, cifrele de 0 vor fi 16 - nr. cifre de 1
            jl aici ; daca nr. e pozitiv sau 0, am rezolvat problema
            
            nega:
                mov bh,[saisprezece]
                sub bh,bl
                mov bl,bh
            
            aici:
            
            mov [d+ebp],bl
            inc ebp
            mov ecx,[copie] ; revenim la valoarea lui ecx pt bucla mare
            mov bl,0
            
        loop repeta
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

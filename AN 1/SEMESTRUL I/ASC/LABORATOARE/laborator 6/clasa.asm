bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 12, -1, -2, 15, 10          ; ESI
    ; a in memorie:
    ;0c     FF    FE    0F    0A    FF  FE                0f   0a
 ;a   0     1      2     3     4  b 0  1   2    3   4   c 0    1   2   3  4
    lena equ $-a   ; 5
    b times lena db 0 ; nr negative din a    ; EDI
    c times lena db 0; pozitive div cu 5     ; EBP
    cinci db 5

; our code starts here
segment code use32 class=code
    start:
        mov esi, 0
        mov edi,0
        mov ebp,0
        mov ecx, lena
        repeta:
            mov bl, [a+esi]
            cmp bl, 0
            JL negativ
            JGE pozitiv
            
            negativ:
                mov [b+edi], bl
                inc edi ; add edi, 1
                inc esi 
                
            JMP finalrepeta
            
            
            pozitiv:
                mov al, bl
                mov ah, 0
                div byte[cinci] ; al - catul si ah restul
                cmp ah, 0
                JE divizibil
                JNE nedivizibil
                    divizibil:
                        mov [c+ebp], bl
                        inc ebp
                        inc esi
                jmp finalrepeta
                    nedivizibil:
                        inc esi
           finalrepeta:
        loop repeta
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

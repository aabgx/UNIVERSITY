bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 1,2,3,4
    s2 db 5,6,7,8
    ls equ $-s2;atat lungimea s1 si s2, cat si lungimea d
    doi db 2
    d times ls db 0

; our code starts here
segment code use32 class=code
    start:
        mov edi,0       ;cu asta vom parcurge toate 3 sirurile
        mov ecx,ls      ;vrem ca bucla sa mearga de nr. de elemente ori
        
        repeta:
            mov bl,[s1+edi]
            mov bh,[s2+edi]  ;elementele din fiecare sir, cu care lucram operatia curenta
            
            ;verificam daca suntem pe o pozitie para sau impara: edi%2
            mov ax,di
            div byte[doi]; ->   al = cat    si  ah = rest
            ;daca ah = 0 numarul e par, altfel impar
            cmp ah,0
            je par
            jne impar
            par:
                add bl,bh
                mov [d+edi],bl ;punem pe pozitia edi din rezultat valoarea calculata
                inc edi
                jmp final_repeta ;jump la final ca sa nu faca si calculul pentru eticheta impar
            impar:
                sub bl,bh
                mov [d+edi],bl ;punem pe pozitia edi din rezultat valoarea calculata
                inc edi
            final_repeta:
        loop repeta ;merge de ecx ori
                
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

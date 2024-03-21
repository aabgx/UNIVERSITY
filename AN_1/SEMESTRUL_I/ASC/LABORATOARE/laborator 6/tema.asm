bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 1001110101011011b
    b dw 1101001111001010b
    c dd 0b
    ;=>1001110101011011 0101011 001010 011(baza2) = 9D5B5651(baza16)
; our code starts here
segment code use32 class=code
    start:
        mov dx,[a]  ; =>ax primele 16 cifre din c vor fi ca si ale lui a
        ; => in dx am prima parte a numarului
        
        mov bx,[a]
        mov ax,0; pt ultimii 16 biti
        ;facem masca pt cifrele pe care vrem sa le folosim
        and bx, 0111000000000000b ; bx = 000000000000(a12)(a13)(a14)0
        ;mutam cifrele pe pozitiile care ne trebuie
        shr bx,12 ;bx = 0000000000000(a12)(a13)(a14)
        or ax,bx ; salvam in ax partea 2
        
        mov bx,[b]
        and bx,0000000000111111b
        shl bx,3
        or ax,bx ; am pus si urmatoarele 5 cifre
        
        mov bx,[a]
        and bx,0000001111111000b 
        shl bx,6 
        or ax,bx ; am pus si urmatoarele 7 cifre
        ; => ax partea a doua a nr
        ;=> NR E PE DX:AX
        
        ;mutarea rezultatului in c
        push dx
        push ax
        pop eax
  
        mov dword[c],eax 
        
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

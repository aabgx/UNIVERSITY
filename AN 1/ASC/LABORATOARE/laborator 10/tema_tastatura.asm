;problema 7
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 10
    b dw 3
    print_format db '%d mod %d = %d', 0
    
    
; our code starts here
segment code use32 class=code
    start:
        mov ax, word[a]
        mov dx, word[a+2]
        idiv word[b] ; ax->cat    dx->rest
        
        movzx edx,dx ;aici e rezultatul
        mov eax,[a]
        movzx ebx,word[b]
        
        push dword edx
        push dword ebx
        push dword eax
        push dword print_format
        call [printf]
        add esp, 4*4
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

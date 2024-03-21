bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ... (a+a+b*c*100)/(a+10)+a-e; a,b,c-byte; e-doubleword;  unsigned
    
    a db 10
    b db 3
    c db 5 
    e dd 7

; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;b*c
        mov al, [b]
        mul byte[c]  ; ax = b*c
        mov bx, 100
        mul bx   ; rez in dx:ax = b*c*100
        
        ; a de la byte sa fie extins la doubleword in comb de reg a-> cx:bx
        
        mov bl, [a]
        add bl, bl ; bl= a+a
        mov bh, 0
        mov cx, 0   ; cx:bx = a
        
        ; cx:bx +
        ; dx:ax
        ;---------
        
        add ax, bx  ; ax = ax+bx
        adc dx, cx   ; dx = dx+cx +cf (daca a fost trasnport in op anterioara)
        ; rez in dx:ax = (a+a+b*c*100)
        
        mov bl, 10
        add bl, [a] ; bl = a+10
        
        ; dx:ax                      / bl
        ; doubleword in comb de reg   / byte
        mov bh, 0 
        ; dx:ax                      / bx
        ; doubleword in comb de reg   /word
        
        div bx   ; se imparte dx:ax la bx si rez este in dx rest si in ax cat
        ; ax = (a+a+b*c*100)/(a+10)
        
        mov bl, [a]
        mov bh, 0
        
        add ax, bx ; ax = (a+a+b*c*100)/(a+10)+ a
        ; ax   - e
        ; word - doubleword
        movzx eax, ax 
        sub eax, [e] ; eax = (a+a+b*c*100)/(a+10)+a-e
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

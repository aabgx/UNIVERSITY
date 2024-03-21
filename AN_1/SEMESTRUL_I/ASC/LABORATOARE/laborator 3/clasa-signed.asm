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
     a db -11
    b db -3
    c db -5 
    e dd -7
    ;(a+a+b*c*100)/(a+10)+a-e; a,b,c-byte; e-doubleword;  signed
    aux1 dw 0; sau aux1 resw 1
    aux2 dw 0; sau aux2 resw 1

; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;b*c
        mov al, [b]
        imul byte [c]   ; AX = b*c
        mov bx, 100
        imul bx    ; dx:ax = bx*ax = b*c*100
        
        mov [aux1], ax
        mov [aux2], dx   ; aux2:aux1 = dx :ax = b*c*100
        
        mov bl, [a]
        add bl, [a] ; bl=a+a
        movsx AX, bl ; Ax = a+a
        CWD     ; ax -> DX:AX = a+a
        
        ;    dx : ax + 
         ; aux2 : aux1 
         
         add ax, [aux1]
         adc dx, [aux2]   ; dx:ax = a+a+b*c*100
         
         ;a+10
         mov bl, [a]
         add bl, 10
         movsx bx, bl
         
         idiv bx   ; dx:ax/ bx = ax cat si dx rest ; ax = (a+a+b*c*100)/(a+10)
         
         mov bl, [a]
         movsx bx, bl
         
         add ax, bx  ; ax = a+a+b*c*100)/(a+10)+a
         
         movsx eax, ax   ; eax = a+a+b*c*100)/(a+10)+a
         sub eax, [e]  ; eax = a+a+b*c*100)/(a+10)+a-e
         
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

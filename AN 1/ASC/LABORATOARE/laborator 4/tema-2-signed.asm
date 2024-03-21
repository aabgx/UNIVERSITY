bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; [(e+f-g)+(b+c)*3]/5   b,c - byte        e,f,g - word
    
    b db 15
    c db -2
    e dw -12
    f dw 7
    g dw -2
    
    ; -> 36/5 al=7  ah=1

; our code starts here
segment code use32 class=code
    start:
        mov al,[b]
        add al,[c]
        mov cl,3
        imul cl ; ax = (b+c)*3
        mov bx,[e]
        add bx,[f]
        sub bx,[g] ; bx = e+f-g
        add ax,bx ; ax = (e+f-g)+(b+c)*3
        
        mov bl,5
        
        idiv bl ; al = cat   ah = rest
    
    
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

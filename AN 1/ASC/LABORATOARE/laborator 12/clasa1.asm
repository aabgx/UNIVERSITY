bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    fisier db "decitit.txt", 0  ;fisierele cu care lucrez
    
    access_mode db "r", 0       ;mod acces fisier
    format_citire db "%h", 0
    descriptor dd -1

; our code starts here
segment code use32 class=code
    start:
        push dword access_mode  
        push dword fisier
        call [fopen]
        add esp, 4*2 
        
        mov [descriptor], eax  ;ca sa verificam daca s-a deschis bine (daca descriptorul e diferit de 0)
        cmp eax, 0
        je final_tot
        
        mov ecx, 10
        
        repeta:
        push dword a
            push dword format_citire
            push dword [descriptor]
            call [fscanf]
            add esp, 4*3
            
            
            
            
            
        loop repeta
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

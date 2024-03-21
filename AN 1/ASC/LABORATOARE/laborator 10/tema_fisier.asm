;problema 7
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
                          extern exit, fopen, fprintf, fclose, fscanf, printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fscanf msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    poz db 0
    max dd 0
    fisier db "decitit.txt", 0     ;fisierele cu care lucrez
    
                            ;mod acces fisier
    access_mode db "r", 0
    
    descriptor dd -1               ;descriptori de fisier
    
    format_citire db "%c", 0 
    printformat db 'Cel mai frecvent caracter este %c si apare de %d ori.', 0
    
    
    chr1 db 'a'
    chr2 db 'z'
    vector times 28 db 0
    a db -1
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
        
        ;daca ajunge aici e ok deschiderea si citim cu fscanf
        
        repeta:
            push dword a
            push dword format_citire
            push dword [descriptor]
            call [fscanf]
            add esp, 4*3
            cmp eax, -1 ; cand nu mai are ce sa citeasca, face eax -1, nu 0
            je final_repeta
            
        
            cmp byte[a], 'a'
            jb final_if
            cmp byte[a], 'z'
            jg final_if ;vad daca e litera mica, adica daca e intre a si z
            
            
            sub byte[a],'a'
            movzx ebx, byte[a] ;poz din vector
            add byte[vector+ebx],1
            mov edx,[max]
           
            cmp byte[vector+ebx],dl
            jg actualizare
            jmp final_if ;daca nu am de facut actualizare trec la urmatorrul caracter
            actualizare:
                movzx edx,byte[vector+ebx]
                mov [max], edx
                mov [poz], bl
                ; in max va fi nr de aparitii si la [vector+poz] litera cu cele mai multe aparitii
            final_if:
            jmp repeta
        final_repeta:
        movzx ebx, byte[poz]
        add ebx,'a' ; pt ca in vector lucram cu numere pana acum si verau sa lucrez cu caractere
        ;printf(printformat,ebx,max)
        push dword [max]            ; aici e numarul de aparitii al literei
        push ebx
        push dword printformat
        call [printf]
        add esp,4*3
        
        final_tot:
        
        push dword [descriptor]
        call [fclose]
        add esp, 4*1
       
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

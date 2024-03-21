bits 32
global start        

extern exit
import exit msvcrt.dll

segment data use32 class=data
extern functie

segment code use32 class=code
    start:
        call functie
        
        push    dword 0
        call    [exit]

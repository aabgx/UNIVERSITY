bits 32
global start        

extern printf
import printf msvcrt.dll

segment data use32 class=data

global functie
hello db 'Hello world',0
segment code use32 class=code
    functie:
        ;printf(hello)
        push dword hello
        call [printf]
        add esp,4
	ret
       

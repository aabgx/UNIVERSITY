 bits 32
 global start
 extern exit
 import exit msvcrt.dll
 extern printf
 import printf msvcrt.dll
 extern scanf
 import scanf msvcrt.dll

 segment data use32 class=data

 write_int_fmt DB "%d ", 0
 write_line_fmt DB 0xA, 0x0
 read_int_fmt DB "%i", 0
 c DD 0
 b DD 0
 a DD 0
 temp1 DD 0
 temp2 DD 0
 temp3 DD 0
 temp4 DD 0
 temp5 DD 0


 segment code use32 class=code


 start:
 mov EAX, a
 push EAX
 push dword read_int_fmt
 call [scanf]
 add ESP, 8

 mov EAX, b
 push EAX
 push dword read_int_fmt
 call [scanf]
 add ESP, 8


 mov eax, [a]
 mov edx, 2
 imul edx
 mov [temp1], eax

 mov eax, [temp1]

 mov [b], eax




 mov eax, [a]
 mov edx, 2
 mov ebx, edx
 CDQ
 idiv ebx
 mov [temp2], eax

 mov eax, [b]
 mov edx, [temp2]
 sub eax, edx
 mov [temp3], eax


 mov eax, [temp3]
 mov edx, 4
 add eax, edx
 mov [temp4], eax

 mov eax, [temp4]

 mov [a], eax



 mov eax, [b]
 mov edx, [a]
 mov ebx, edx
 CDQ
 idiv ebx
 mov [temp5], eax

 mov eax, [temp5]

 mov [c], eax


 mov EAX, [b]
 push EAX
 push dword write_int_fmt
 call [printf]
 add ESP, 8


 push dword write_line_fmt
 call [printf]
 add ESP, 4


 mov EAX, [a]
 push EAX
 push dword write_int_fmt
 call [printf]
 add ESP, 8


 push dword write_line_fmt
 call [printf]
 add ESP, 4


 mov EAX, [c]
 push EAX
 push dword write_int_fmt
 call [printf]
 add ESP, 8


 push dword write_line_fmt
 call [printf]
 add ESP, 4

 push dword 0
 call [exit]
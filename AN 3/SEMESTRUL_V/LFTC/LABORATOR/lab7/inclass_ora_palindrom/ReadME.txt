install bison in ubuntu
sudo apt install bison

comenzi de rulat
bison -d analyzer.y
flex analyzser.l
gcc analyzer.tab.c lex.yy.c
./a.out fisier.txt

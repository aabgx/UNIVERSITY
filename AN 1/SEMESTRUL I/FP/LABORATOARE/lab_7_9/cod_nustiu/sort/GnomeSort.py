import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import *

def cmp_grup(x,y):
    if int(x.getgrup()) > int(y.getgrup()):
        return 1
    elif int(x.getgrup()) < int(y.getgrup()):
        return -1
    else:
        return 0

def cmp_nume(x,y):
    if x.getnume() > y.getnume():
        return 1
    elif x.getnume() < y.getnume():
        return -1
    else:
        return 0

def GnomeSort( array, n, reverse = False, cmp=False):
    """
    Algoritm de implementare GnomeSort
    Daca elem. de pe poz curenta e <= elem. poz. urmatoare, e ok, crestem indexul
    Daca nu, facem un swap intre ele si mergem cu indexul un pas inapoii pt. a compara elementul cu toate cele de dinaintea lui.
    array: sirul de sortat
    n: lungimea sirului de sortat
    reverse: daca nu e specificat, e fals by default, daca e specificat True atunci lista va fi sortata descrescator
    cmp: daca nu e specificat, e fala by default, daca e specificat, lista e sortata dupa o anumita functie(definita in prealabil pt. utilizator)
        cu return 1 daca first>second, return 0 daca first=sewcond si return -1 daca first<second
    """
    index = 0
    if cmp == False:
        while index < n:
            if index == 0:
                index = index + 1
            if array[index] >= array[index - 1]:
                index = index + 1
            else:
                array[index], array[index-1] = array[index-1], array[index]
                index = index - 1
        
        if reverse ==True:
            array.reverse()

    else:
        while index < n:
            if index == 0:
                index = index + 1
            if cmp(array[index],array[index - 1]) == 1 or cmp(array[index],array[index - 1]) == 0:
                index = index + 1
            else:
                array[index], array[index-1] = array[index-1], array[index]
                index = index - 1
        
        if reverse ==True:
            array.reverse()

 
    return array

def test_GnomeSort():
    lista = [1,3,5,2,8,22,3,5,1,0,10,25,44,1,0]

    s1=student('aa','Sna','4')
    s2=student('bb','Bloom','5')
    s3=student('cc','Catalin','1')
    s4=student('dd','Wiana','3')
    s5=student('ee','Eminescu','2')
    lista1 = [s1,s2,s3,s4,s5]

    lista_numere = [0,0,1,1,1,2,3,3,5,5,8,10,22,25,44]
    lista_numere_reverse = [44, 25, 22, 10, 8, 5, 5, 3, 3, 2, 1, 1, 1, 0, 0]
    lista_nume = [s2,s3,s5,s1,s4]
    lista_nume_reverse = [s4,s1,s5,s3,s2]

    assert(GnomeSort(lista,len(lista)) == lista_numere)
    assert(GnomeSort(lista,len(lista),reverse=True) == lista_numere_reverse)

    assert(GnomeSort(lista1,len(lista1),cmp = cmp_nume) == lista_nume)
    assert(GnomeSort(lista1,len(lista1),reverse = True,cmp = cmp_nume) == lista_nume_reverse)

test_GnomeSort()

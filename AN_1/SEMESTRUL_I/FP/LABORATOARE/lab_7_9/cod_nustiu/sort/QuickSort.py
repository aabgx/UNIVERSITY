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


def partitie(array, start, end):
    pivot = array[start]
    low = start + 1
    high = end

    while True:
        while low <= high and array[high] >= pivot:
            high = high - 1

        while low <= high and array[low] <= pivot:
            low = low + 1

        if low <= high:
            array[low], array[high] = array[high], array[low]

        else:
            break

    array[start], array[high] = array[high], array[start]

    return high

def partitie1(array, start, end, cmp):
    pivot = array[start]
    low = start + 1
    high = end

    while True:
        while low <= high and (cmp(array[high], pivot)==1 or cmp(array[high], pivot)==0):
            high = high - 1

        while low <= high and (cmp(array[low], pivot)==-1 or cmp(array[high], pivot)==0):
            low = low + 1

        if low <= high:
            array[low], array[high] = array[high], array[low]
        else:
            break

    array[start], array[high] = array[high], array[start]

    return high

def partitie2(array, start, end,key):
    pivot = array[start]
    low = start + 1
    high = end

    while True:
        while low <= high and key(array[high]) >= key(pivot):
            high = high - 1

        while low <= high and key(array[low]) <= key(pivot):
            low = low + 1

        if low <= high:
            array[low], array[high] = array[high], array[low]

        else:
            break

    array[start], array[high] = array[high], array[start]

    return high

def QuickSort(array, start, end,reverse = False,cmp=False,key=False):
    """
    Metoda de soratre folsind QuickSort.
    array: lista de sortat
    start: index de inceput
    end: index de final
    reverse: daca nu e specificat, e fals by default, daca e specificat True atunci lista va fi sortata descrescator
    cmp: daca nu e specificat, e fala by default, daca e specificat, lista e sortata dupa o anumita functie(definita in prealabil pt. utilizator)
        cu return 1 daca first>second, return 0 daca first=sewcond si return -1 daca first<second
    """
    if start >= end:
        return
    if cmp == False and key==False:
        if reverse == False:
            p = partitie(array, start, end)
            QuickSort(array, start, p-1)
            QuickSort(array, p+1, end)
        elif reverse == True:
            p = partitie(array, start, end)
            QuickSort(array, start, p-1)
            QuickSort(array, p+1, end)
            array.reverse()

    elif cmp != False and key == False:
        if reverse == False:
            p = partitie1(array, start, end,cmp)
            QuickSort(array, start, p-1,cmp = cmp)
            QuickSort(array, p+1, end,cmp = cmp)
        elif reverse == True:
            p = partitie1(array, start, end,cmp)
            QuickSort(array, start, p-1,cmp = cmp)
            QuickSort(array, p+1, end,cmp = cmp)
            array.reverse()

    elif cmp == False and key != False:
        if reverse == False:
            p = partitie2(array, start, end,key)
            QuickSort(array, start, p-1,key = key)
            QuickSort(array, p+1, end,key = key)
        elif reverse == True:
            p = partitie2(array, start, end,key)
            QuickSort(array, start, p-1,key = key)
            QuickSort(array, p+1, end,key = key)
            array.reverse()


    return array


def test_QuickSort():
    lista = [1,3,5,2,8,22,3,5,1,0,10,25,44,1,0]

    s1=student('aa','Sna','4')
    s2=student('bb','Bloom','5')
    s3=student('cc','Catalin','1')
    s4=student('dd','Wiana','3')
    s5=student('ee','Eminescu','2')
    lista1 = [s1,s2,s3,s4,s5]

    lista_nume = [s2,s3,s5,s1,s4]
    lista_nume_reverse = [s4,s1,s5,s3,s2]
    lista_numere = [0,0,1,1,1,2,3,3,5,5,8,10,22,25,44]
    lista_numere_reverse = [44, 25, 22, 10, 8, 5, 5, 3, 3, 2, 1, 1, 1, 0, 0]
    
    assert(QuickSort(lista,0,len(lista)-1) == lista_numere)
    assert(QuickSort(lista,0,len(lista)-1,reverse=True) == lista_numere_reverse)

    assert(QuickSort(lista1,0,len(lista1)-1,cmp = cmp_nume) == lista_nume)
    assert(QuickSort(lista1,0,len(lista1)-1,reverse = True,cmp = cmp_nume) == lista_nume_reverse)

    assert(QuickSort(lista1,0,len(lista1)-1,key = lambda x:x.getnume()) == lista_nume)    


test_QuickSort()
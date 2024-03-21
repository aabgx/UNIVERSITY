import math
def read_list():
    string_list = input("Introduceti lista: ")
    number_list = string_list.split()
    number_list = [int(elem) for elem in number_list]
    return number_list

def smax(crt_list):
    """
    Gaseste cea mai lunga secventa de suma maxima
    :param crt_list: lista in care se cauta secventa
    :type crt_list: list
    :return: rez - secventa gasita
    :rtype: rez - list
    """
    s = 0
    rez = []
    ssmax = -math.inf
    left = 0
    lmax = 0
    right = 0
    rmax = 0
    for i in range (0,len(crt_list)):
        if s < 0:
            s = crt_list[i]
            left = i
        else:
            s = s + crt_list[i]
            right = i
        if ssmax < s:
            ssmax = s
            lmax = left
            rmax = right
        elif ssmax == s and rmax-lmax < right-left:
            rmax = right
            lmax = left
            
    
    for i in range(lmax,rmax+1):
        rez.append ( crt_list[i] )
    
    return rez

def sign(variabila1,variabila2):
    if variabila1 - variabila2 < 0:
        return 0
    else:
        return 1

def alternare(crt_list):
    """
    Gaseste cea mai lunga secventa de elemente cu proprietatea ca diferenta a doua consecutive are semn diferit de diferenta urmatoarelor doua
    :param crt_list: lista in care se cauta secventa
    :type crt_list: list
    :return: rez - secventa gasita
    :rtype: rez - list
    """
    if len(crt_list) <= 2:
        print("Sir prea scurt.")
        return None
    else:
        rez = []
        lmax = 0
        rmax = 1
        right = 0
        left = 0
        semn = sign(crt_list[1], crt_list[0])

        for i in range(2,len(crt_list)):
            if sign(crt_list[i], crt_list[i-1]) == semn:
                left = i-1
                right = i
            else:
                right = i
            if rmax - lmax < right - left:
                rmax = right
                lmax = left
            semn = sign(crt_list[i], crt_list[i-1])

        for i in range(lmax,rmax+1):
            rez.append ( crt_list[i] )
        
        return rez

def se_repeta(crt_list):
    """
    Gaseste cea mai lunga secventa de elemente cu proprietatea ca in oricare trei elemente consecutive exista o valoarea care se repeta
    :param crt_list: lista in care se cauta secventa
    :type crt_list: list
    :return: rez - secventa gasita
    :rtype: rez - list
    """
    if len(crt_list) <= 2:
        print("Sir prea scurt.")
        return None
    else:
        rez = []
        lmax = 0
        rmax = 1
        right = 0
        left = 0
        for i in range(2,len(crt_list)-1):
            if comparare(crt_list[i], crt_list[i+1], crt_list[i-1]) == 0:
                left = i
                right = i+1
            else:
                right = i+1
            if rmax - lmax < right - left:
                rmax = right
                lmax = left
            

        for i in range(lmax,rmax+1):
            rez.append ( crt_list[i] )
        
        return rez

def comparare(x,y,z):
    if x == y or y == z or z == x:
        return 1
    else:
        return 0

def lmax_0_10(crt_list):
    """
    Gaseste cea mai lunga secventa de elemente din intervalul [0,10]
    :param crt_list: lista in care se cauta secventa
    :type crt_list: list
    :return: rez - secventa gasita
    :rtype: rez - list
    """
    rez = []
    lmax = 0
    rmax = 0
    right = 0
    left = 0
    for i,elem in enumerate(crt_list):
        if elem < 0 or elem > 10:
            left = i+1
            right = i+1
        else:
            right = i
        if rmax - lmax < right - left:
            rmax = right
            lmax = left

    
    for i in range(lmax,rmax+1):
        rez.append ( crt_list[i] )
    
    return rez

def print_menu():
    print("1. Citeste de la tastatura o lista de numere intregi.")
    print("2. Gaseste secventa de lungime maxima, care are toate elementele din intervalul [0,10].")
    print("3. Gaseste cea mai lunga secventa de elemente cu proprietatea ca diferenta a doua numere consecutive are semn diferit de diferenta urmatoarelor doua.")
    print("4. Gaseste secventa de lungime maxima, care are suma maxima.")
    print("5. Gaseste secventa de lungime maxima, cu proprietatea ca in oricare 3 elem. consecutive, 2 se repeta.")
    print("6. Iesire din aplicatie.")

def start():
    MyList = []
    print_menu()
    while True:
        
        option = int(input("Dati optiunea dumneavoastra: "))
        if option == 1:
            MyList = read_list()
        if option == 2:
            list1 = lmax_0_10(MyList)
            print(list1)
        if option == 3:
            list1 = alternare(MyList)
            print(list1)
        if option == 4:
            list1 = smax(MyList)
            print(list1)
        if option == 5:
            list1 = se_repeta(MyList)
            print(list1)
        if option == 6:
            return

start()




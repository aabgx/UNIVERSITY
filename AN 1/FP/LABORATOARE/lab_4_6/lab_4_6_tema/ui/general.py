from domain.general import get_imag, get_real


def print_list(number_list):
    """
    Printeaza lista curenta.
    :param number_list: lista de numere
    :type number_list: list
    :return: -; lista de numere nemodificata
    :rtype: -;
    """
    print("Lista de numere este:")
    for elem in number_list:
        if get_imag(elem) >= 0:
            print("[", get_real(elem), "+", get_imag(elem), "*i]")
        else:
            print("[", get_real(elem), "-", -get_imag(elem), "*i]")
    print()


def read_new_number():
    """
    Citeste de la tastatura partea reala si partea imaginara a unui nou numar.
    :return: numarul citit
    :rtype: lista
    """
    try:
        parte_reala = int(input("Partea reala a numarului este: "))
        parte_imaginara = int(input("Partea imaginara a numarului este: "))
        number = [parte_reala, parte_imaginara]
        return number
    except ValueError:
        print('Trebuie sa introduceti numere.')
        return None

def get_lim():
    """
    Citeste de la tastatura un numar.
    :return: numarul daca e valid, None daca s-a introdus altceva decat un nr intreg
    """
    try:
        lim = int(input())
        return lim
    except ValueError:
        print('Trebuie sa introduceti numar.')
        return None
def read_poz():
    """
    Citeste de la tastatura un numar.
    :return: numarul daca e valid, None daca s-a introdus altceva decat un nr intreg
    """
    try:
        poz = int(input("Poziția dorită este: "))
        return poz
    except ValueError:
        print('Trebuie sa introduceti numar.')
        return None

def print_elem(elem):
    """
    Printeaza un element sub forma de numar complex x + y*i
    :param elem: elementul din lista ce contine partea reala si partea imaginara a numarului de afisat
    :return: -; se afiseaza numarul
    """
    if get_imag(elem) >= 0:
        print("[", get_real(elem), "+", get_imag(elem), "*i]")
    else:
        print("[", get_real(elem), "-", -get_imag(elem), "*i]")

def cautare(optiune):
    """
    Verifica daca "optiune" este in lista de optiuni disponibila.
    :param optiune: optiunea data
    :return: 1 daca este in lista de optiuni disponibila, 0 altfel
    """
    sir = ['1.1','1.2','2.1','2.1','2.3','3.1','3.2','3.3','4.1','4.2','4.3','5.1','5.2','6']
    ok = 0
    for elem in sir:
        if optiune == elem:
            ok = 1
    if ok == 1:
        return 1
    else:
        return 0

def validare_optiune(optiune):
    """
    Valideaza optiunea inprodusa.
    :param optiune: optiunea de verificat
    :return: -; raise ValueError daca nu este valida
    """
    if cautare(optiune) != 1:
        raise ValueError("Optiunea este invalida!")

def print_menu():
    print("1.Adaugă număr în listă. ")
    print("     1.1 Adaugă număr complex la sfârșitul listei ")
    print("     1.2 Inserare număr complex pe o poziție dată.")
    print("2.Modifică elemente din listă.")
    print("     2.1 Șterge element de pe o poziție dată.")
    print("     2.2 Șterge elementele de pe un interval de poziții.")
    print("     2.3 Înlocuiește toate aparițiile unui număr complex cu un alt număr complex.")
    print("3.Căutare numere.")
    print("     3.1 Tipărește partea imaginară pentru numerele din listă de pe anumite poziții (interval).")
    print("     3.2 Tipărește numerele complexe cu modulul mai mic decât 10.")
    print("     3.3 Tipărește toate numerele complexe cu modulul egal cu 10.")
    print("4. Operații cu numere din listă.")
    print("     4.1 Tipărește suma numerelor dintr-o subsecvență dată.")
    print("     4.1 Tipărește produsul numerelor dintr-o subsecvență dată.")
    print("     4.3 Tipărește lista sortată descrescător după partea imaginară.")
    print("5.Filtrare.")
    print("     5.1 Elimină din listă elementele a căror parte reală este un nr. prim.")
    print("     5.2 Elimină din listă numerele a căror modul este <, = sau > decât un număr dat.")
    print("7.Ieșire din aplicație.")
    print()

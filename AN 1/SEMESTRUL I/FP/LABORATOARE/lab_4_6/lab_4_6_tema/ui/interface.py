from domain.task1 import add_number_to_list_final, add_number_to_list_inter
from domain.task2 import inlocuieste_aparitii, out_from_list_one, out_from_list_sir
from domain.task3 import cautare_egal_mod_10, cautare_mic_modul_10, print_list_interval
from domain.task4 import suma_secventei, prod_secventei
from domain.task5 import filtrare_modul
from domain.task6 import undo_op
from ui.general import get_lim, print_elem, read_poz, read_new_number


def add_number_to_list_final_UI(number_list):
    """
    Adauga un numar la finalul listei date
    :param number_list: lista de numere
    :type number_list: list
    :return: -; lista de numere se modifica prin adaugarea numarului dat
    :rtype: -;
    """
    number = read_new_number()
    return add_number_to_list_final(number_list, number)

def add_number_to_list_inter_UI(number_list):
    """
    Adauga un numar in interiorul listei, pe o pozitie data.
    :param number_list: lista de numere
    :return: list
    """
    number = read_new_number()
    poz = read_poz()
    if number != None and poz!= None:
        return add_number_to_list_inter(number_list, number, poz)
    else:
        return number_list

def add_number_to_list_final_UI(number_list):
    """
    Adauga un numar la finalul listei date
    :param number_list: lista de numere
    :type number_list: list
    :return: -; lista de numere se modifica prin adaugarea numarului dat
    :rtype: -;
    """
    number = read_new_number()
    if number != None:
        return add_number_to_list_final(number_list, number)
    else:
        return number_list

def out_from_list_sir_UI(number_list):
    """
    Elimina elemente din lista intre doua pozitii date.
    :param number_list: lista de numere
    :type number_list: list
    :return: lista de numere obtinuta prin eliminarea elementelor de pe pozitiile cerute
    :rtype: list
    """
    print("Dati limita inferioara: ")
    inferior = get_lim()
    print("Dati limita superioara: ")
    superior = get_lim()
    if inferior != None and superior != None:
        return out_from_list_sir(number_list, inferior, superior)
    else:
        return number_list

def out_from_list_one_UI(number_list):
    """
    Elimina elementul din lista de pe o pozitie data.
    :param number_list: lista de numere
    :type number_list: list
    :return: lista de numere obtinuta prin eliminarea elementului de pe pozitia ceruta
    :rtype: list

    """
    poz = read_poz()
    if poz != None:
        return out_from_list_one(number_list, poz)
    else:
        return number_list

def inlocuieste_aparitii_UI(number_list):
    """
    Inlocuieste toate aparitiile unei valori date cu o alta valoare data.
    :param number_list: lista de numere
    :type number_list: list
    :return: lista de numere obtinuta prin inlocuirea elementelor cu valoarea ceruta
    :rtype: list
    """
    print("Dati detaliile numărului DE ÎNLOCUIT")
    out_sir = read_new_number()
    print("Dati detaliile numărului ÎNLOCUITOR")
    in_sir = read_new_number()
    if out_sir != None and in_sir != None:
        return inlocuieste_aparitii(number_list, out_sir, in_sir)
    else:
        return number_list

def afis_imag_interval_UI(number_list):
    """
    Afiseaza partea imaginara a numerelor din lista, dintr-un interval dat
    :param number_list: lista de numere
    :return: -;
    """
    print("Dati limita inferioara: ")
    inferior = get_lim()
    print("Dati limita superioara: ")
    superior = get_lim()
    print("Partea imaginara a numerelor din intervalul selectat este: ")
    print_list_interval(number_list,inferior,superior)

def egal_modul_10_UI(number_list):
    """
    Afiseaza toate numerele complexe din lista cu modulul = 10
    :param number_list: lista de numere
    :return: -;
    """
    print("Partea imaginară a numerelor cu modulul egal cu 10 este: ")
    cautare_egal_mod_10(number_list)

def mic_modul_10_UI(number_list):
    """
    Afiseaza toate numerele complexe din lista cu modulul < 10
    :param number_list: lista de numere
    :return: -;
    """
    print("Partea imaginară a numerelor cu modulul mai mic decât 10 este: ")
    cautare_mic_modul_10(number_list)

def filtrare_modul_UI(number_list):
    """
    Elimina din lista elemenele care au modulul <, > sau = cu un numar dat.
    :param number_list: lista de numere
    :return: lista modificata prin eliminarea elementelor cerute
    """
    nr = int(input("Introduceti numarul de comparatie: "))
    print("Doriti elminarea numerelor cu modulul <, = sau > decat", nr, " ?")
    semn = input("Introduceti semnul corespunzator: ")
    new_list = filtrare_modul(number_list, semn, nr)
    return new_list

def suma_secventei_UI(number_list):
    """
    Calculeaza suma elementelor dintr-o secventa data (inclusiv a elementelor de inceput si de final)
    :param number_list: lista de numere
    :return: -; printeaza suma elementelor din secventa ceruta
    """
    print("Dati limita inferioara")
    inceput = int(get_lim())
    print("Dati limita superioara")
    final = int(get_lim())
    print_elem(suma_secventei(number_list, inceput, final))

def prod_secventei_UI(number_list):
    """
    Calculeaza produsul elementelor dintr-o secventa data (inclusiv a elementelor de inceput si de final)
    :param number_list: lista de numere
    :return: -; printeaza produsul elementelor din secventa ceruta
    """
    print("Dati limita inferioara")
    inceput = int(get_lim())
    print("Dati limita inferioara")
    final = int(get_lim())
    print_elem(prod_secventei(number_list, inceput, final))

def undo_op_UI(crt_undo):
    """
    Anuleaza ultima operatie efectuata, lista de numere revenind la starea de dinainte de aceasta operatie.
    :param crt_undo: o lista contor
    :return: lista de numere la starea de dinaintea ultimei operatii
    """
    if crt_undo == []:
        print("Nu se mai pot face operatii de undo, s-a ajuns la starea initiala")
        print()
        return []
    else:
        undo_op(crt_undo)
        if crt_undo == []:
            print("Lista este goala.")
            print()
            return []
        else:
            return crt_undo[-1]


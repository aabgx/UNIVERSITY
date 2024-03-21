from domain.general import get_imag
from utils.general_utils import egal_modul_10, mic_modul_10


def cautare_egal_mod_10(number_list):
    """
    Cauta in lista toate elementele  care au modulul egal cu 10 si le afiseaza doar parteas imaginara
    :param number_list: lista de numere
    :return: ;nu se modifica nimic, se afiseaza doar partea imaginara a elementelor cerute
    """
    for i, number in enumerate(number_list):
        if egal_modul_10(number) == 1:
            print(get_imag(number),"*i")

def cautare_mic_modul_10(number_list):
    """
    Cauta in lista toate elementele  care au modulul egal cu 10 si le afiseaza doar parteas imaginara
    :param number_list: lista de numere
    :return:
    """
    for i, number in enumerate(number_list):
        if mic_modul_10(number) == 1:
            print(get_imag(number))



def print_list_interval(number_list, inferior,superior):
    """
        Printeaza partea imag a elementelor dintr-un dinterval din lista curenta.
        :param number_list: lista de numere
        :type number_list: list
        :param inceput: pozitia de incepere
        :type inceput: int
        :param final: pozitia ultimului elem
        :type final: int
        :return: -; lista de numere nemodificata
        :rtype: -;
    """
    print("Partea imaginara a numerelor din intervalul dar este:")
    for i in range(inferior, superior + 1):
        print(number_list[i][1])
    print()



#functiile lucreaza doarla afisare, de aceea nu sunt teste
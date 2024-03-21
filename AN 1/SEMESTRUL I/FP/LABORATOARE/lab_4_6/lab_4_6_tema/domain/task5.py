from domain.general import get_real, get_imag
from utils.general_utils import prim, modul


def generate_number_list_2():
    return [[1, 0], [3, 1], [5, 2], [4, 3], [6, 4], [15, 5], [11, 6], [21, 7]]


def generate_number_list_3():
    return [[1, 0], [6, 8], [33, 2], [4, 3], [-36, 4], [8, 6], [11, 6], [2, 7]]

def filtrare_prim(number_list):
    """
    Elimină din listă numerele complexe la care partea reala este prim
    :param number_list: lista de numere
    :type number_list: list
    :return: lista de numere obtinuta prin eliminarea elementelor cu proprietatea ceruta
    :rtype: list
    """

    new_list = []
    for i, elem in enumerate(number_list):
        if (prim(get_real(elem)) == 0):
            new_list.append(elem)
    return new_list


def filtrare_modul(number_list, semn, nr):
    """
    Elimină din listă numerele complexe la care modulul este <,> sau = cu un nr dat
    :param number_list: lista de numere
    :type number_list: list
    :param semn: semnul cu care se lucreaza
    :type semn: string
    :param nr: numarul de comparatie dat
    :type nr:int
    :return: lista de numere obtinuta prin eliminarea elementelor cu proprietatea ceruta
    :rtype: list
    """
    new_list = []
    for i, elem in enumerate(number_list):
        if semn == '<':
            if (modul(elem) >= nr * nr):
                new_list.append(elem)
        if semn == '>':
            if (modul(elem) <= nr * nr):
                new_list.append(elem)
        if semn == '=':
            if (modul(elem) != nr * nr):
                new_list.append(elem)
    return new_list

#teste
def test_filtrare_prim():
    test_list = generate_number_list_2()
    test_list = filtrare_prim(test_list)
    assert (len(test_list) == 5)
    assert (get_real(test_list[4]) == 21)
    assert (get_imag(test_list[4]) == 7)


def test_filtrare_modul():
    test_list = generate_number_list_3()

    test_list = filtrare_modul(test_list, '=', 10)
    assert (len(test_list) == 6)
    assert (get_real(test_list[1]) == 33)
    assert (get_imag(test_list[1]) == 2)

    test_list = filtrare_modul(test_list, '<', 10)
    assert (len(test_list) == 3)
    assert (get_real(test_list[0]) == 33)
    assert (get_imag(test_list[0]) == 2)

    test_list = filtrare_modul(test_list, '>', 10)
    assert (len(test_list) == 0)

test_filtrare_prim()
test_filtrare_modul()
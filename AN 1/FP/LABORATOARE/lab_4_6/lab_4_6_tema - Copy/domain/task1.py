from domain.general import get_real, get_imag
from domain.task6 import crt_undo_list


def generate_number_list():
    return [[1, 0], [1, 1], [1, 2], [1, 3], [1, 4], [1, 5], [1, 6], [1, 7], [1, 8], [1, 9], [1, 10]]

def add_number_to_list_final(number_list, number):
    """
    Adauga un numar la finalul listei date.
    :param number_list: lista de numere
    :param number: numarul ce trebuie adaugat
    :return: lista cu numarul adaugat la final
    :rtype: list
    """
    number_list.append(number)

    return number_list

def add_number_to_list_inter(number_list, number, poz):
    """
    Adauga un numar in interiorul listei date, pe o pozitie ceruta.
    :param number_list: lista de numere
    :type number_list: list
    :param number: numarul care trebuie adaugat
    :type number: list
    :return: lista cu numarul adaugat pe pozitia ceruta
    :rtype: list
    """
    new_list = []
    if poz > 0:
        for i in range(poz):
            new_list.append(number_list[i])
    new_list.append(number)
    for i in range(poz, len(number_list)):
        new_list.append(number_list[i])
    return new_list

#Teste

def test_add_number_to_list_final():

    test_list = []
    number = [1, 2]
    add_number_to_list_final(test_list, number)
    assert (len(test_list) == 1)
    assert (get_real(test_list[0]) == 1)
    assert (get_imag(test_list[0]) == 2)

    number = [9, -1]
    add_number_to_list_final(test_list, number)
    assert (len(test_list) == 2)
    assert (get_real(test_list[1]) == 9)
    assert (get_imag(test_list[1]) == -1)

def test_add_number_to_list_inter():
    test_list = generate_number_list()
    number = [9, 11]
    poz = 3
    test_list = add_number_to_list_inter(test_list, number, poz)
    assert (len(test_list) == 12)
    assert (get_real(test_list[3]) == 9)
    assert (get_imag(test_list[3]) == 11)

    number = [1, -20]
    poz = 5
    test_list = add_number_to_list_inter(test_list, number, poz)
    assert (len(test_list) == 13)
    assert (get_real(test_list[5]) == 1)
    assert (get_imag(test_list[5]) == -20)


test_add_number_to_list_inter()
test_add_number_to_list_final()
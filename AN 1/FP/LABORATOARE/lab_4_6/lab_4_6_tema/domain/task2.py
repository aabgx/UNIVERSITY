from domain.general import get_real, get_imag


def generate_number_list():
    return [[1, 0], [1, 1], [1, 2], [1, 3], [1, 4], [1, 5], [1, 6], [1, 7], [1, 8], [1, 9], [1, 10]]

def out_from_list_sir(number_list, inferior, superior):
    """
    Elimina din lista elementele ce se afla intre doua pozitii date (inclusiv acele pozitii).
    :param number_list: lista de numere
    :param inferior: pozitia de inceput
    :param superior: pozitia de final
    :return: lista modificata prin eliminarea elementelor de pe pozitiile cerute
    """
    new_list = [elem for i, elem in enumerate(number_list) if i < inferior or i > superior]
    return new_list

def out_from_list_one(number_list, poz):
    """
    Elimina din sir un element de pe o pozitie ceruta.
    :param number_list: lista de numere
    :param poz: pozitia elementului ce trebuie eliminat
    :return: lista modificata prin eliminarea elementului de pe pozitia ceruta
    """
    new_list = [elem for i, elem in enumerate(number_list) if i != poz]
    return new_list

def inlocuieste_aparitii(number_list, out_sir, in_sir):
    """
    Inlocuieste toate aparitiile unui element dat cu alt element dat
    :param number_list: lista de numere
    :param out_sir: elementul ce trebuie inlocuit
    :param in_sir: elementul cu care se inlocuieste
    :return: lista modificata prin inlocuirea elementului cerut
    """
    new_list = []
    for elem in number_list:
        if elem == out_sir:
            new_list.append(in_sir)
        else:
            new_list.append(elem)
    return new_list

#teste

def test_out_from_list_one():
    test_list = generate_number_list()
    poz = 3
    test_list = out_from_list_one(test_list, poz)

    assert (get_real(test_list[3]) == 1)
    assert (get_imag(test_list[3]) == 4)
    assert (get_real(test_list[2]) == 1)
    assert (get_imag(test_list[2]) == 2)


def test_out_from_list_sir():
    test_list = generate_number_list()
    inferior = 2
    superior = 6
    test_list = out_from_list_sir(test_list, inferior, superior)
    assert (len(test_list) == 6)
    assert (get_real(test_list[2]) == 1)
    assert (get_imag(test_list[2]) == 7)
    assert (get_real(test_list[1]) == 1)
    assert (get_imag(test_list[1]) == 1)
    assert (get_real(test_list[3]) == 1)
    assert (get_imag(test_list[3]) == 8)


def test_inlocuieste_aparitii():
    test_list = generate_number_list()
    in_sir = [9, 9]
    out_sir = [1, 9]
    test_list = inlocuieste_aparitii(test_list, out_sir, in_sir)
    assert (len(test_list) == 11)
    assert (get_real(test_list[9]) == 9)
    assert (get_imag(test_list[9]) == 9)

test_out_from_list_one()
test_out_from_list_sir()
test_inlocuieste_aparitii()
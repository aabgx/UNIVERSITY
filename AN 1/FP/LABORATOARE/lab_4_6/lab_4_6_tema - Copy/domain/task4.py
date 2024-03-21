from domain.general import get_imag, get_real


def generate_number_list():
    return [[1, 0], [1, 1], [1, 2], [1, 3], [1, 4], [1, 5], [1, 6], [1, 7], [1, 8], [1, 9], [1, 10]]


def generate_number_list_2():
    return [[1, 0], [3, 1], [5, 2], [4, 3], [6, 4], [15, 5], [11, 6], [21, 7]]

def sortare(number_list):
    """
    Sorteaza lista descrescator dupa partea imaginara
    :param number_list: lista de numere
    :type number_list: list
    :return: lista de numere sortata
    :rtype: list
    """
    new_list = []
    new_list_1 = []

    for elem in number_list:
        new_list.append(get_imag(elem))

    new_list.sort()

    lenght = len(new_list) - 1

    for i in range(lenght, -1, -1):
        new_list_1.append(new_list[i])

    new_list = []

    for i, elem in enumerate(new_list_1):
        for j, eelem in enumerate(number_list):
            if i == 0 and get_imag(eelem) == elem:
                new_list.append(eelem)
            elif get_imag(eelem) == elem and i > 0 and elem != new_list_1[i - 1]:
                new_list.append(eelem)
    return new_list

def suma_secventei(number_list, inceput, final):
    """
    Calculeaza suma elementelor dintr-o secventa data
    :param number_list: lista de numere
    :type number_list: list
    :param inceput: pozitia primului element din suma
    :type inceput: int
    :param final: pozitia ultimului element din suma
    :type final: int
    :return: suma
    :rtype: int
    """
    sum_r = 0
    sum_i = 0
    for i, elem in enumerate(number_list):
        if i >= inceput and i <= final:
            sum_r = sum_r + get_real(number_list[i])
            sum_i = sum_i + get_imag(number_list[i])
    return [sum_r, sum_i]

def prod_secventei(number_list, inceput, final):
    """
    Calculeaza produsul elementelor dintr-o secventa data
    :param number_list: lista de numere
    :type number_list: list
    :param inceput: pozitia primului element din produs
    :type inceput: int
    :param final: pozitia ultimului element din produs
    :type final: int
    :return: produsul
    :rtype: int
    """
    prod_r = 1
    prod_i = 0
    for i, elem in enumerate(number_list):
        if i >= inceput and i <= final:
            aux = prod_r
            prod_r = prod_r * get_real(number_list[i]) - prod_i * get_imag(number_list[i])
            prod_i = prod_i * get_real(number_list[i]) + aux * get_imag(number_list[i])
    return [prod_r, prod_i]

#teste
def test_suma_secventei():
    test_list = generate_number_list_2()
    assert (get_real(suma_secventei(test_list, 1, 2)) == 8)
    assert (get_imag(suma_secventei(test_list, 1, 2)) == 3)
    assert (get_real(suma_secventei(test_list, 1, 3)) == 12)
    assert (get_imag(suma_secventei(test_list, 1, 3)) == 6)


def test_prod_secventei():
    test_list = [[2, 3], [1, 4]]
    assert (get_real(prod_secventei(test_list, 0, 1)) == -10)
    assert (get_imag(prod_secventei(test_list, 0, 1)) == 11)


def test_sortare():
    test_list = generate_number_list()

    test_list = sortare(test_list)
    assert (len(test_list) == 11)
    assert (get_real(test_list[0]) == 1)
    assert (get_imag(test_list[0]) == 10)
    assert (get_real(test_list[10]) == 1)
    assert (get_imag(test_list[10]) == 0)

test_suma_secventei()
test_prod_secventei()
test_sortare()
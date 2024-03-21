from domain.general import get_real, get_imag


def generate_number_list():
    return [[1, 0], [1, 1], [1, 2], [1, 3], [1, 4], [1, 5], [1, 6], [1, 7], [1, 8], [1, 9], [1, 10]]


def crt_undo_list(crt_undo, number_list):
    """
    Adauga lista curenta de numere la lista de undo
    :param number_list: lista de numere
    :type number_list: list
    :param crt_undo: lista de undo
    :return: - (face doar adaugarea)
    :rtype: -

    """
    copy_list = []

    for elem in number_list:
        copy_list.append([get_real(elem), get_imag(elem)])

    crt_undo.append(copy_list)


def undo_op(crt_undo):
    """
    Sterge ultimul element din lista de undo.
    :param crt_undo: lista de undo
    :type crt_undo: list
    :return: lista fara ultimul element
    :rtype: list
    """

    crt_undo.pop(-1)

    return crt_undo

def test_undo_op():
    lista = generate_number_list()
    lista.append([2,3])
    assert(len(lista) == 12)
    assert(lista[11] == [2,3])
    undo_op(lista)
    assert(len(lista) == 11)
    assert (lista[10] == [1,10])

test_undo_op()
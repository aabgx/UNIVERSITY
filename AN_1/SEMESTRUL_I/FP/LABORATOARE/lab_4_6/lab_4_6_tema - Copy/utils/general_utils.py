from domain.general import get_real, get_imag


def modul(number):
    """
    Calculeaza patratul modulului unui numar (modul*modul)
    :param number: numarul pentru care se calculeaza valoarea
    :return: valoarea ceruta
    """
    return get_real(number) * get_real(number) + get_imag(number) * get_imag(number)

def egal_modul_10(number):
    """
    Verifica daca modulul unui nr. complex este egal cu 10
    :param number: numarul pentru care se verifica egalitatea
    :return: 1 daca se respecta egalitatea, 0 altfel
    """

    if modul(number) == 100:
        return 1
    else:
        return 0

def mic_modul_10(number):
    """
    Verifica daca modulul unui nr. complex este < 10
    :param number: numarul pentru care se verifica inegalitatea
    :return: 1 daca se respecta inegalitatea, 0 altfel
    """
    if modul(number) < 100:
        return 1
    else:
        return 0

def prim(number):
    """
    Verifica daca un numar este prim
    :param number: numarul verificat
    :return: 1 daca nr. este prim, 0 altfel
    """
    ok = 1
    if number <= 1:
        return 0
    for i in range(2, number - 1):
        if number % i == 0:
            ok = 0
            break
    if ok == 0:
        return 0
    else:
        return 1

#teste

def test_egal_modul_10():
    number = [6, 8]
    assert (egal_modul_10(number) == 1)
    number = [-1, 4]
    assert (egal_modul_10(number) == 0)


def test_mic_modul_10():
    number = [8, 8]
    assert (mic_modul_10(number) == 0)
    number = [-1, 4]
    assert (mic_modul_10(number) == 1)

test_egal_modul_10()
test_mic_modul_10()
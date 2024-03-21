def validator1(value,poz):
    """
    Valideaza o valoare ce trebuie sa fie parametru al unui obiect de tip student.
    ::
    ::
    ::
    ::
    :: -; raise ValueError daca datele nu sunt valide
    """
    errors = []
    if poz != '0' and poz != '1' and poz != '2':
        errors.append('Ati ales un parametru invalid. Acesta trebuia sa fie 1, 2 sau 3.')
    elif poz == '0' and (value.isdigit() != True or int(value) < 0):
        errors.append('ID student trebuie sa fie un numar pozitiv')
    elif poz == '1' and (value.isdigit() == True or len(value) < 2):
        errors.append('Numele studentului trebuie sa aiba minim 2 caractere si sa nu fie numar')
    elif poz == '2' and (value.isdigit() != True or int(value) < 0):
        errors.append('Grupa trebuie sa fie un numar pozitiv')
    if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

def validator3(value,poz):
    """
    Valideaza o valoare ce trebuie sa fie parametru al unui obiect de tip laborator.
    ::
    ::
    ::
    ::
    :: -; raise ValueError daca datele nu sunt valide
    """
    errors = []
    if poz != '0' and poz != '1' and poz != '2':
        errors.append('Ati ales un parametru invalid. Acesta trebuia sa fie 1, 2 sau 3.')
    elif poz == '0':
        sir1 = value.split('_')
        if len(sir1) < 2:
            errors.append('Numărul laboratorului si al problemei trebuie scris sub forma: lab_prob.')
        elif sir1[0].isdigit()!=True or int(sir1[0]) < 1 or int(sir1[0]) > 100:
            errors.append('Laboratorul trebuie sa fie reprezentat printr-un numar intre 1 si 100.')
        elif sir1[1].isdigit()!=True or int(sir1[1]) < 1 or int(sir1[1]) > 100:
            errors.append('Problema trebuie sa fie reprezentata printr-un numar intre 1 si 100.')

    elif poz == '1' and (value.isdigit() == True or len(value) < 2):
        errors.append('Descrierea trebuie sa fie un text de minim 2 caractere')

    elif poz == '2':
        sir2 = value.split('.')
        if len(sir2) < 3:
            errors.append('Data deadline-ului trebuie sa fie de forma: zz.ll.aaaa .')
        elif sir2[0].isdigit()!=True or int(sir2[0]) < 1 or int(sir2[0]) > 31:
            errors.append('Ziua trebuie sa fie un numar intre 1 si 31.')
        elif sir2[1].isdigit()!=True or int(sir2[1]) < 1 or int(sir2[1]) > 12:
            errors.append('Luna trebuie sa fie un numar intre 1 si 12.')
        elif sir2[2].isdigit()!=True or int(sir2[2]) < 2020 or int(sir2[2]) > 2025:
            errors.append('Anul trebuie sa fie un numar intre 2020 si 2030.')

    if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

def validator2(value):
    errors = []
    if value.isdigit() == False or (value != '1' and value != '2'):
        errors.append('Numarul introdus trebuia sa fie 1 sau 2 in functie de optiunea aleasa.')
    if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)



def test_validator1():
    try:
        validator1('Ofelia Bugnar','1')
        assert True
    except ValueError:
        assert False 

    try:
        validator1('-45','0')
        assert False
    except ValueError:
        assert True  

    try:
        validator1('r','0')
        assert False
    except ValueError:
        assert True

def test_validator3():
    try:
        validator3('3_9','0')
        assert True
    except ValueError:
        assert False

    try:
        validator3('3_r','0')
        assert False
    except ValueError:
        assert True
    
    try:
        validator3('er.12.2020','2')
        assert False
    except ValueError:
        assert True

def test_validator2():
    try:
        validator2('1')
        assert True
    except ValueError:
        assert False
    try:
        validator2('3')
        assert False
    except ValueError:
        assert True

test_validator2()
test_validator3()
test_validator1()       
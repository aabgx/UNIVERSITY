import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import student, laborator, notare

class StudentValidator:
    def validate(self, student):
        errors = []
        if student.getstudentID().isdigit() != True or int(student.getstudentID()) < 0:
            errors.append('ID student trebuie sa fie un numar mai mare decat 0.')

        if len(student.getnume()) < 2:
            errors.append('Numele studentului trebuie să conțină cel puțin 2 caractere.')

        if student.getgrup().isdigit() != True or int(student.getgrup()) < 0:
            errors.append('Numarul grupei trebuie sa fie mai mare decat 0.')
        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

class LaboratorValidator:
    def validate(self, laborator):
        errors = []
        sir1 = []
        sir1 = laborator.getlab_prob().split('_')
        if len(sir1) < 2:
            errors.append('Numărul laboratorului si al problemei trebuie scris sub forma: lab_prob.')
        elif sir1[0].isdigit()!=True or int(sir1[0]) < 1 or int(sir1[0]) > 100:
            errors.append('Laboratorul trebuie sa fie reprezentat printr-un numar intre 1 si 100.')
        elif sir1[1].isdigit()!=True or int(sir1[1]) < 1 or int(sir1[1]) > 100:
            errors.append('Problema trebuie sa fie reprezentata printr-un numar intre 1 si 100.')

        if len(laborator.getdescriere()) < 2:
            errors.append('Descrierea trebuie sa fie un text de minim 2 caractere.')

        sir2 = laborator.getdeadline().split('.')
        if len(sir2) < 3:
            errors.append('Data deadline-ului trebuie sa fie de forma: zz.ll.aaaa .')
        elif sir2[0].isdigit()!=True or int(sir2[0]) < 1 or int(sir2[0]) > 31:
            errors.append('Ziua trebuie sa fie un numar intre 1 si 31.')
        elif sir2[1].isdigit()!=True or int(sir2[1]) < 1 or int(sir2[1]) > 12:
            errors.append('Luna trebuie sa fie un numar intre 1 si 12.')
        elif sir2[2].isdigit()!=True or int(sir2[2]) < 2020 or int(sir2[2]) > 2030:
            errors.append('Anul trebuie sa fie un numar intre 2020 si 2025.')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

class NotareValidator:
    def validate(self, nota):
        """
        :param nota: obiect de tip notare
        """
        errors = []
        if nota.getnota() < 0 or nota.getnota() > 5:
            errors.append('Nota trebuie sa fie intre 1 si 10.')

        if len(errors) > 0:
            raise ValueError(errors)


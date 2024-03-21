import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import student, laborator, notare
from termcolor import colored

class note_repository:
    def __init__(self):
        self.__note = []

    def cautare(self, n):
        """
        Cauta n
        :param n: notarea de cautat
        :type n: notare
        :return: notarea cautata daca exista in lista, None altfel
        :rtype: notare
        """
        for notare in self.__note:
            if n == notare:
                return notare
        return None

    def cautare_student(self,stud):
        """
        Cauta id-ul studentului stud in lista de asignari.
        :param stud: studentul de cautat
        :type stud: student
        :return: studentul cautat daca exista in lista, lista goala altfel
        :rtype: list
        """
        return  [el for el in self.__note if el.getstudent() == stud]

    def cautare_stud_lab(self,stud,lab):
        """
        Cauta lab_prob al laboratorului lab si id-ul studentului stud in lista de asignari.
        :param lab: laboratorul de cautat
        :type lab: student
        :return: notatrea daca exista in lista, None altfel
        :rtype: list
        """
        for notare in self.__note:
            if notare.getstudent() == stud and notare.getlaborator() == lab:
                return notare
        return None

    def cautare_nota(self,nota):
        """
        Cauta nota nota in lista de asignari
        :param nota: nota de cautat
        :type nota: float
        :return: nota cautata daca exista in lista, lista goala altfel
        :rtype: list
        """
        return  [el for el in self.__note if el.getnota() == nota]

    def add(self, notare):
        """
        Adauga un element la lista de asignari
        :param nota: asignarea de adaugat
        :type nota: notare
        :return: -; se adauga nota la lista de note
        :raises: ValueError daca nota a fost deja acordata
        """
        
        n = self.cautare_stud_lab(notare.getstudent(),notare.getlaborator())
        errors = []
        if n is not None:
            errors.append('Nota fost deja acordata.')
        else:
            self.__note.append(notare)
        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

    def get_all(self):
        """
        Returneaza o lista cu toate notarile facute.
        :rtype: lista de obiecte de tip notare
        """
        return self.__note

    def show_all(self):
        """
        Afiseaza lista de notari
        :rtype: -; afiseaza lista
        """
        print(colored('Lista de note este: ','cyan'))
        for el in self.__note:
            print(el)
    def delete_all(self):
        self.__note=[]
    def save(self,note):
        self.__note=note

class note_repository_file(note_repository):
    def __init__(self,filename):
        note_repository.__init__(self)
        self.__filename = filename
        self.__load_from_file()

    def __load_from_file(self):
        f = open(self.__filename, 'r')
        lines = f.readlines()

        for i in range(0,len(lines)-1,3):
            linie_noua = lines[i:i+3]
            student1,laborator1,nota = linie_noua
            student1 = student1.replace('\n','')
            laborator1 = laborator1.replace('\n','')
            nota = nota.replace('\n','')
            nota = notare(student1,laborator1,float(nota))
            note_repository.add(self, nota)
        f.close()

    def __save_to_file(self):
        lista = note_repository.get_all(self)
        with open(self.__filename, 'w') as f:
            for el in lista:
                
                f.write(el.getstudent() + '\n')
                f.write(el.getlaborator() + '\n')
                f.write(str(el.getnota()) + '\n')
    def save(self,note):
        note_repository.save(self,note)
    def add(self, notare):
        """
        Adauga un element la lista de asignari
        :param nota: asignarea de adaugat
        :type nota: notare
        :return: -; se adauga nota la lista de note
        :raises: ValueError daca nota a fost deja acordata
        """
        note_repository.add(self, notare)
        self.__save_to_file()

    def cautare(self, n):
        """
        Cauta n
        :param n: notarea de cautat
        :type n: notare
        :return: notarea cautata daca exista in lista, None altfel
        :rtype: notare
        """
        return note_repository.cautare(self,n)

    def cautare_student(self,stud):
        """
        Cauta studentul stud in lista de asignari.
        :param stud: studentul de cautat
        :type stud: student
        :return: studentul cautat daca exista in lista, lista goala altfel
        :rtype: list
        """
        return  note_repository.cautare_student(self,stud)

    def cautare_stud_lab(self,stud,lab):
        """
        Cauta laboratorul lab in lista de asignari.
        :param lab: laboratorul de cautat
        :type lab: student
        :return: laboratorul cautat daca exista in lista, lista goala altfel
        :rtype: list
        """
        return note_repository.cautare_stud_lab(self,stud,lab)

    def cautare_nota(self,nota):
        """
        Cauta nota nota in lista de asignari
        :param nota: nota de cautat
        :type nota: float
        :return: nota cautata daca exista in lista, lista goala altfel
        :rtype: list
        """
        return note_repository.cautare_nota(self,nota)

    def get_all(self):
        """
        Returneaza o lista cu toate notarile facute.
        :rtype: lista de obiecte de tip notare
        """
        return note_repository.get_all(self)

    def show_all(self):
        """
        Afiseaza lista de notari
        :rtype: -; afiseaza lista
        """
        note_repository.show_all(self)

    def delete_all(self):
        note_repository.delete_all(self)
    
    


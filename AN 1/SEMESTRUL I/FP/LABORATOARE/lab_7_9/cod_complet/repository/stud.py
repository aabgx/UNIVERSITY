import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import student
from termcolor import colored
import random

class stud_repository:
    """
    Clasa creata cu responsabilitatea de a gestiona multimea de studenti
    """
    def __init__(self):
        """
        Este o listă care conține toți studenții introduși, goala initial.
        """
        self.__students = []

    def cautare_by_ID(self,ID,index):
        """
        Cauta studentul cu un ID dat
        :param id: ID 
        :type id: str
        :return: studentul cu ID dat, None daca nu exista studentul cu ID dat
        :rtype: student
        """
        lista = self.__students()
        if index == len(lista):
            return None
        elif lista[index].getstudentID() == ID:
            return lista[index]
        else:
            return self.cautare_by_ID(ID,index+1)

            
    def add(self, student):
        """
        Adauga un student in lista
        :param student: studentul care se adauga
        :type student: student
        :return: -; lista de studenti se modifica prin adaugarea studentului cerut
        :rtype:
        """
        self.__students.append(student)
    
    def dell(self,value,poz):
        """
        Sterge un student din lista, dupa criteriul ales de utilizator.
        :param value: valoarea criteriului ales de utilizator
        :type value: int sau str
        :param poz: pozitia in entitatea student a valorii alese (0=ID, 1=nume, 2=grup)
        :type poz: int
        """
        if poz == '0':
            self.__students = [el for el in self.__students if el.getsudentID() != value]
        if poz == '1':
            self.__students = [el for el in self.__students if el.getnume() != value]
        if poz == '2':
            self.__students = [el for el in self.__students if el.getgrup() != value]
    
    def modificare(self,ID,value,poz):
        """
        Modifica valoarea parametrului poz (0, 1 sau 2) din studentul cu id-ul ID cu valoarea value data.
        :param ID: ID-ul studentului pe care vrem sa il modificam
        :type ID: str (un numar)
        :param value: data pe care vrem sa o modificam in interiorul studentului ales
        :type value: str
        :param poz: pozitia in obiectul student (0-ID, 1-nume, 2-grupa)
        :type poz:str (un numar)
        :: -; valoarea se modifica dupa ceritele date
        """
        for elem in self.__students:
            if elem.getstudentID() == ID:
                if poz == '0':
                    elem.setstudentID(value)
                elif poz == '1':
                    elem.setnume(value)
                elif poz == '2':
                    elem.setgrup(value)
    def cautare_ID(self, ID):
        """
        Cauta studentul cu un ID dat
        :param id: ID 
        :type id: str
        :return: studentul cu ID dat, None daca nu exista studentul cu ID dat
        :rtype: student
        """
        for el in self.__students:
            if ID == el.getstudentID():
                return el
        return None


    def cautare(self,value,poz):
        """
        Cauta elemente in sirul de studenti dupa criteriul poz si valoarea value. Returneaza o lista cu valorile gasite.
        :param value: valoarea care se cauta in lista de studenti
        :type value: str
        :param poz: tipul valorii value (0-ID, 1-nume, 2-grupa)
        :type poz: str
        :return: lista cu studentii gasiti
        :rtype: list
        """
        if poz == '0':
            return  [el for el in self.__students if el.getstudentID() == value]
        elif poz == '1':
            return [el for el in self.__students if el.getnume() == value]
        elif poz == '2':
            return  [el for el in self.__students if el.getgrup() == value]
    
    def validator(self, value,poz,cerinta):
        """"
        Pt cerinta add: verifica daca un student se afla in lista, daca da, arunca exceptie.
        Pt cerinta dell: verifica daca un student se afla in lista, daca nu, arunca exceptie
        """
        errors = []
        if self.cautare(value,poz) != [] and cerinta == 'add':
            errors.append('Studentul cu acest ID este deja in lista')
        elif self.cautare(value,poz) == [] and cerinta == 'dell':
                errors.append('Nu exista in lista un student cu aceste date')    
        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

    def get_all_students(self):
        """
        Returneaza o lista cu toți studenții
        :rtype: lista de obiecte de tip student
        """
        return [student(el.getstudentID(),el.getnume(),el.getgrup()) for el in self.__students]

    def show_all_students(self):
        """
        Afiseaza lista de studenti
        :rtype: -; afiseaza lista
        """
        print(colored('Lista de studenti este: ','cyan'))
        for el in self.__students:
            print(el)

    def add_random(self,value):
        for i in range(value):
            ID = str(random.randint(1,1000))
            nume = ''
            nume_list = []
            cate_litere = random.randint(2,20)
            for i in range(cate_litere):
                nume_list.append(chr(ord('a')+random.randint(0,22)))
            for x in nume_list: 
                nume += x
            grupa = str(random.randint(1,100))
            
            s = student(ID,nume,grupa)
            self.__students.append(s)
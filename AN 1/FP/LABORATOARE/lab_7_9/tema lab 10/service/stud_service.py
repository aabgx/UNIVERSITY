import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import student
from domain.validators import StudentValidator
from repository.stud import stud_repository
from termcolor import colored
from repository.validators import validator1

class StudentService:
    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiect de tip repo care ne ajuta sa gestionam multimea de studenti
        :type repo: stud_repository
        :param validator: validator pentru verificarea datelor obiectului de tip student
        :type validator: StudentValidator
        """
        self.__repo = repo
        self.__validator = validator

    def add_student(self, studentID, nume, grup):
        """
        Adauga student
        :param studentID: ID-ul studentului
        :type studentID: str
        :param nume: numele studentului
        :type nume: str
        :param grup: grupa din care face parte studentul
        :type grup: str
        :return: obiectul de tip student creat
        :rtype:-; serialul s-a adaugat in lista
        :raises: ValueError daca serialul are date invalide
        """
        s = student(studentID, nume, grup)
        
        self.__validator.validate(s) 
        self.__repo.validator(studentID,'0','add')
        self.__repo.add(s)

    def add_random(self,value):
        """
        Adauga value obiecte random la lista de studenti.
        :param value: numarul de obiecte de adaugat.
        :type value: int
        """
        
        self.__repo.add_random(value)
        
        
    def dell_student(self,value,poz):
        """
        Sterge student
        :param value: data dupa care se va identifica studentul de sters
        :type value: srt 
        :param poz: pozitia in entitate (0-ID, 1-nume, 2-grupa)
        :type poz: str
        :return: - ;se sterge studentul dorit la lista
        :raises: ValueError daca au fost introduse date invalide
        """
        validator1(value,poz)
        self.__repo.validator(value,poz,'dell')
        self.__repo.dell(value,poz)

    def modificare_student(self,ID,value,poz):
        """
        Modifica student
        :param ID: studentul la care se va produce modificarea este identificat dupa acest parametru
        :param ID: str (un numar sub forma de sir)
        :param value: data cu care se va schimba una din cadrul studentului
        :type value: srt 
        :param poz: pozitia in entitate (0-ID, 1-nume, 2-grupa)
        :type poz: str
        :return: - ;se modifica studentul dorit din lista
        :raises: ValueError daca au fost introduse date invalide
        """
        validator1(value,poz)
        validator1(ID,'0')
        if poz == '0':
            self.__repo.validator(ID,'0','add')
        self.__repo.validator(ID,'0','dell')
        self.__repo.modificare(ID,value,poz)
    
    def cautare_student(self,value,poz):
        """
        Cauta elemente in sirul de studenti dupa criteriul poz si valoarea value. Returneaza o lista cu valorile gasite.
        :param value: valoarea care se cauta in lista de studenti
        :type value: str
        :param poz: tipul valorii value (0-ID, 1-nume, 2-grupa)
        :type poz: str
        :return: lista cu studentii gasiti
        :rtype: list
        """
        validator1(value,poz)
        return self.__repo.cautare(value,poz)
    
    def afis_cautare(self,value,poz):
        
        for el in self.__repo.cautare(value,poz):
            print(el)
    
    def get_all(self):
        """
        Returneaza o lista cu toti studentii introdusi.
        """
        return self.__repo.get_all_students()
    
    def show_all(self):
        """
        Afiseaza o lista cu toti studentii introdusi.
        """
        self.__repo.show_all_students()
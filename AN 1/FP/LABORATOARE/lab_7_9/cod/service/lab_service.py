import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import laborator
from domain.validators import LaboratorValidator
from repository.lab import lab_repository
from termcolor import colored
from repository.validators import validator3

class LabService:
    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiect de tip repo care ne ajuta sa gestionam multimea de laboratoare
        :type repo: lab_repository
        :param validator: validator pentru verificarea datelor obiectului de tip laborator
        :type validator: LaboratorValidator
        """
        self.__repo = repo
        self.__validator = validator

    def add_lab(self, lab_prob, descriere, deadline):
        """
        Adauga laborator
        :param lab_prob: numarul laboratorului si numarul problemei
        :type lab_prob: srt (nr.lab_nr.prob)
        :param descriere: descrierea problemei
        :type nume: str
        :param deadline: data limita de predare a laboratorului
        :type deadline: srt
        :return: - ;se adauga laboratorul dorit la lista
        :raises: ValueError daca au fost introduse date invalide
        """
        l = laborator(lab_prob, descriere, deadline)

        
        self.__validator.validate(l)
        self.__repo.validator(lab_prob,'0','add') 
        self.__repo.add(l)

    def add_random(self,value):
        """
        Adauga value obiecte random la lista de laboratoare.
        :param value: numarul de obiecte de adaugat.
        :type value: int
        """
        self.__repo.add_random(value)


    def dell_lab(self,value,poz):
        """
        Sterge laborator
        :param value: data dupa care se va identifica laboratorul de sters
        :type value: srt 
        :param poz: pozitia in entitate (0-lab_rob, 1-descriere, 2-deadline)
        :type poz: str
        :return: - ;se sterge laboratorul dorit la lista
        :raises: ValueError daca au fost introduse date invalide
        """
        validator3(value,poz)
        self.__repo.validator(value,poz,'dell') 
        self.__repo.dell(value,poz)

    def modificare_lab(self,lab_prob,value,poz):
        """
        Modifica laborator
        :param lab_prob: laboratorul la care se va produce modificarea este identificat dupa acest parametru
        :type lab_prob: str
        :param value: data cu care se va schimba una din cadrul laboratorului
        :type value: srt 
        :param poz: pozitia in entitate (0-lab_rob, 1-descriere, 2-deadline)
        :type poz: str
        :return: - ;se modifica laboratorul dorit din lista
        :raises: ValueError daca au fost introduse date invalide
        """
        validator3(value,poz)
        validator3(lab_prob,'0')
        self.__repo.validator(lab_prob,'0','dell') 
        self.__repo.modificare(lab_prob,value,poz)

    def cautare_lab(self,value,poz):
        """
        Cauta elemente in sirul de laboratoare dupa criteriul poz si valoarea value. Returneaza o lista cu valorile gasite.
        :param value: valoarea care se cauta in lista de laboratoare
        :type value: str
        :param poz: tipul valorii value (0-lab_prob, 1-descriere, 2-deadline)
        :type poz: str
        :return: lista cu laboratoarele gasite
        :rtype: list
        """
        validator3(value,poz)  
        return self.__repo.cautare(value,poz)
        
    def afis_cautare(self,value,poz):
        
        for el in self.__repo.cautare(value,poz):
            print(el)
    
    def get_all(self):
        """
        Returneaza o lista cu toate laboratoarele introduse.
        """
        return self.__repo.get_all_labs()
    
    def show_all(self):
        """
        Afiseaza o lista cu toate laboratoarele introduse.
        """
        self.__repo.show_all_labs()



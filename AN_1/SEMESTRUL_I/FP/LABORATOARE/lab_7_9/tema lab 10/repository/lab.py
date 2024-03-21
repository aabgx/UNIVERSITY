import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import laborator
from termcolor import colored
import random

class lab_repository:
    """
        Clasa creata cu responsabilitatea de a gestiona multimea de laboratoare
    """
    def __init__(self):
        """
        Este o listă care conține toate laboratoarele introduse, goala initial.
        """
        self.__laboratoare = []


    def add(self, laborator):
        """
        Adauga un laborator in lista
        :param laborator: laboratorul care se adauga
        :type laborator: laborator
        :return: -; lista de laboratoare se modifica prin adaugarea laboratorului cerut
        :rtype:
        """
        self.__laboratoare.append(laborator)
    
    def add_random(self,value):
        for i in range(value):
            lab_prob = str(random.randint(1,100)) + '_' + str(random.randint(1,100))
            descriere = ''
            descriere_list = []
            cate_litere = random.randint(2,20)
            for i in range(cate_litere):
                descriere_list.append(chr(ord('a')+random.randint(0,22)))
            for x in descriere_list: 
                descriere += x
            deadline = str(random.randint(1,30)) + '.' + str(random.randint(1,12)) + '.' + str(random.randint(2020,2025))
            
            l = laborator(lab_prob,descriere,deadline)
            self.add(l)


    def dell(self,value,poz):
        """
        Sterge un student din lista, dupa criteriul ales de utilizator.
        :param value: valoarea criteriului ales de utilizator
        :type value: int sau str
        :param poz: pozitia in entitatea laborator a valorii alese (0=prob_lab, 1=descriere, 2=deadline)
        :type poz: int
        """
        if poz == '0':
            self.__laboratoare = [el for el in self.__laboratoare if el.getprob_lab() != value]
        if poz == '1':
            self.__laboratoare = [el for el in self.__laboratoare if el.getdescriere() != value]
        if poz == '2':
            self.__laboratoare = [el for el in self.__laboratoare if el.getdeadline() != value]
    
    def modificare(self,lab_prob,value,poz):
        """
        Modifica valoarea parametrului poz (0, 1 sau 2) din cu numarul si problema prob_lab cu valoarea value data.
        :param lab_prob: nr. lab si nr. problema de la laboratorul pe care vrem sa il modificam
        :type lab_prob: str
        :param value: data pe care vrem sa o modificam in interiorul laboratorului ales
        :type value: str
        :param poz: pozitia in obiectul laborator (0-lab_prob, 1-descriere, 2-deadline)
        :type poz:str
        :: -; valoarea se modifica dupa ceritele date
        """
        for elem in self.__laboratoare:
            if elem.getlab_prob() == lab_prob:
                if poz == '0':
                    elem.setlab_prob(value)
                elif poz == '1':
                    elem.setdescriere(value)
                elif poz == '2':
                    elem.setdeadline(value)

    def cautare_lab_prob(self, lab_prob):
        """
        Cauta laboratorul cu lab_prob dat
        :param lab_prob: lab_prob
        :type lab_prob: str
        :return: laboratorul cu lab_prob dat, None daca nu exista in lista
        :rtype: laborator
        """
        for el in self.__laboratoare:
            if lab_prob == el.getlab_prob():
                return el
        return None

    def cautare(self,value,poz):
        """
        Cauta elemente in sirul de laboratoare dupa criteriul poz si valoarea value. Returneaza o lista cu valorile gasite.
        :param value: valoarea care se cauta in lista de laboratoare
        :type value: str
        :param poz: tipul valorii value (0-lab_prob, 1-descriere, 2-deadline)
        :type poz: str
        :return: lista cu laboratoarele gasite
        :rtype: list
        """
        if poz == '0':
            return  [el for el in self.__laboratoare if el.getlab_prob() == value]
        elif poz == '1':
            return [el for el in self.__laboratoare if el.getdescriere() == value]
        elif poz == '2':
            return  [el for el in self.__laboratoare if el.getdeadline() == value]

    def validator(self, value,poz,cerinta):
        """"
        Pt cerinta add: verifica daca un laborator se afla in lista, daca da, arunca exceptie.
        Pt cerinta dell: verifica daca un laborator se afla in lista, daca nu, arunca exceptie
        """
        errors = []
        if self.cautare(value,poz) != [] and cerinta == 'add':
            errors.append('Laboratorul cu acest numar si problema este deja in lista')
        elif self.cautare(value,poz) == [] and cerinta == 'dell':
                errors.append('Nu exista in lista un laborator cu aceste date')    
        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)
        
    def get_all_labs(self):
        """
        Returneaza o lista cu toate laboratoarele
        :rtype: lista de obiecte de tip laborator
        """
        return [laborator(el.getlab_prob(),el.getdescriere(),el.getdeadline()) for el in self.__laboratoare]

    def show_all_labs(self):
        """
        Afiseaza lista de laboratoare
        :rtype: -; afiseaza lista
        """
        print(colored('Lista de laboratoare este: ','cyan'))
        for el in self.__laboratoare:
            print(el)
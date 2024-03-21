import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import laborator
from termcolor import colored
import random

file = './data/lab.txt'

def generare_labs():
    lrf = lab_repository_file(file)
    #0
    lab1 = laborator('1_3','aa','1.2.2021')
    lrf.add(lab1)
    #1
    lab2 = laborator('2_3','bb','2.2.2021')
    lrf.add(lab2)
    #2
    lab3 = laborator('3_3','cc','3.2.2021')
    lrf.add(lab3)
    #3
    lab4 = laborator('4_3','dd','4.2.2021')
    lrf.add(lab4)
    #4
    lab5 = laborator('5_3','ee','5.2.2021')
    lrf.add(lab5)
    #5
    lab6 = laborator('6_3','ff','6.2.2021')
    lrf.add(lab6)
    #6
    lab7 = laborator('7_3','gg','7.2.2021')
    lrf.add(lab7)
    #7
    lab8 = laborator('8_3','aa','8.2.2021')
    lrf.add(lab8)

    return lrf.get_all_labs()

class lab_repository_file:
    """
        Clasa creata cu responsabilitatea de a gestiona multimea de laboratoare
    """
    def __init__(self,filename):
       
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca elementele din fisier.
        :return: lista cu obiecte de tip laborator
        :rtype: list
        """
        lista_labs = []
        with open(self.__filename, 'r') as f: #f e descriptorul
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    lab_prob,descriere,deadline = line.split('/')
                    lab = laborator(lab_prob,descriere,deadline)
                    lista_labs.append(lab)
        return lista_labs

    def save_to_file(self,all_labs):
        """
        Salveaza lista all_labs in fisier.
        :param all_students: lista ce trebuie salvata
        :all_students type: list
        """
        with open(self.__filename, 'w') as f:
            for lab in all_labs:
                string = lab.getlab_prob() + '/' + lab.getdescriere() + '/' + lab.getdeadline() + '\n'
                f.write(string)

    def add(self, laborator):
        """
        Adauga un laborator in lista
        :param laborator: laboratorul care se adauga
        :type laborator: laborator
        :return: -; lista de laboratoare se modifica prin adaugarea laboratorului cerut
        :rtype:
        """
        lista = self.__load_from_file()
        lista.append(laborator)
        self.save_to_file(lista)
    
    def add_random(self,value):
        lista = self.__load_from_file()
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
            lista.append(l)

        self.save_to_file(lista)

    def dell(self,value,poz):
        """
        Sterge un student din lista, dupa criteriul ales de utilizator.
        :param value: valoarea criteriului ales de utilizator
        :type value: int sau str
        :param poz: pozitia in entitatea laborator a valorii alese (0=prob_lab, 1=descriere, 2=deadline)
        :type poz: int
        """
        lista = self.__load_from_file()
        if poz == '0':
            lista = [el for el in lista if el.getlab_prob() != value]
        if poz == '1':
            lista = [el for el in lista if el.getdescriere() != value]
        if poz == '2':
            lista = [el for el in lista if el.getdeadline() != value]

        self.save_to_file(lista)
    
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
        lista = self.__load_from_file()
        for elem in lista:
            if elem.getlab_prob() == lab_prob:
                if poz == '0':
                    elem.setlab_prob(value)
                elif poz == '1':
                    elem.setdescriere(value)
                elif poz == '2':
                    elem.setdeadline(value)
        self.save_to_file(lista)
    
    def cautare_lab_prob(self, lab_prob,index):
        """
        Cauta laboratorul cu lab_prob dat
        :param lab_prob: lab_prob
        :type lab_prob: str
        :return: laboratorul cu lab_prob dat, None daca nu exista in lista
        :rtype: laborator
        """
        lista = self.__load_from_file()
        if index == len(lista):
            return None
        elif lista[index].getlab_prob() == lab_prob:
            return lista[index]
        else:
            return self.cautare_lab_prob(lab_prob,index+1)


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
        lista = self.__load_from_file()
        if poz == '0':
            return  [el for el in lista if el.getlab_prob() == value]
        elif poz == '1':
            return [el for el in lista if el.getdescriere() == value]
        elif poz == '2':
            return  [el for el in lista if el.getdeadline() == value]

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

    
    def generare(self):
        self.save_to_file(generare_labs())

    def delete_all(self):
        self.save_to_file([])

    def get_all_labs(self):
        """
        Returneaza o lista cu toate laboratoarele existente in fisier.
        """
        return self.__load_from_file()

    def show_all_labs(self):
        """
        Afiseaza lista de studenti din fisier
        :rtype: -; afiseaza lista
        """
        lista = self.__load_from_file()
        print(colored('Lista de laboratoare este: ','cyan'))
        for el in lista:
            print(el)
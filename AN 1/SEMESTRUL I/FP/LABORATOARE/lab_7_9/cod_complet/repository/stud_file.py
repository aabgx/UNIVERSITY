import os
import sys
sys.path.append(os.getcwd() + "\\")
from repository.stud import *
from domain.entities import student

file = './data/stud.txt'

def generare_studenti():
    srf = stud_repository_file(file)
    #0
    student1 = student('1','aa','1')
    srf.add(student1)
    #1
    student2 = student('2','bb','2')
    srf.add(student2)
    #2
    student3 = student('3','cc','3')
    srf.add(student3)
    #3
    student4 = student('4','dd','4')
    srf.add(student4)
    #4
    student5 = student('5','ee','5')
    srf.add(student5)
    #5
    student6 = student('6','ff','6')
    srf.add(student6)
    #6
    student7 = student('7','gg','7')
    srf.add(student7)

    return srf.get_all_students()

class stud_repository_file:
    def __init__(self,filename):
        self.__filename = filename

    def cautare_by_ID(self,ID,index):
        """
        Cauta studentul cu un ID dat
        :param id: ID 
        :type id: str
        :return: studentul cu ID dat, None daca nu exista studentul cu ID dat
        :rtype: student
        """
        lista = self.__load_from_file()
        if index == len(lista):
            return None
        elif lista[index].getstudentID() == ID:
            return lista[index]
        else:
            return self.cautare_by_ID(ID,index+1)

    def __load_from_file(self):
        """
        Incarca elementele din fisier.
        :return: lista cu obiecte de tip student
        :rtype: list
        """
        lista_studenti = []
        with open(self.__filename, 'r') as f: #f e descriptorul
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    ID,nume,grupa = line.split('/')
                    student1 = student(ID,nume,grupa)
                    lista_studenti.append(student1)
        return lista_studenti

    def save_to_file(self,all_students):
        """
        Salveaza lista all_students in fisier.
        :param all_students: lista ce trebuie salvata
        :all_students type: list
        """
        with open(self.__filename, 'w') as f:
            for student in all_students:
                string = student.getstudentID() + '/' + student.getnume() + '/' + student.getgrup() + '\n'
                f.write(string)

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

    def add(self, student):
        """
        Adauga un student in fisier
        :param student: studentul care se adauga
        :type student: student
        """
        lista = self.__load_from_file()
        lista.append(student)
        self.save_to_file(lista)

    def dell(self,value,poz):
        """
        Sterge un student din fisier, dupa criteriul ales de utilizator.
        :param value: valoarea criteriului ales de utilizator
        :type value: int sau str
        :param poz: pozitia in entitatea student a valorii alese (0=ID, 1=nume, 2=grup)
        :type poz: int
        """
        lista = self.__load_from_file()
        if poz == '0':
            lista = [el for el in lista if el.getsudentID() != value]
        if poz == '1':
            lista = [el for el in lista if el.getnume() != value]
        if poz == '2':
            lista = [el for el in lista if el.getgrup() != value]

        self.save_to_file(lista)
    def cautare_ID(self, ID):
        """
        Cauta studentul cu un ID dat
        :param id: ID 
        :type id: str
        :return: studentul cu ID dat, None daca nu exista studentul cu ID dat
        :rtype: student
        """
        lista = self.__load_from_file()
        for el in lista:
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
        lista = self.__load_from_file()
        if poz == '0':
            return [el for el in lista if el.getstudentID() == value]
        elif poz == '1':
            return [el for el in lista if el.getnume() == value]
        elif poz == '2':
            return [el for el in lista if el.getgrup() == value]


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
        lista = self.__load_from_file()
        for elem in lista:
            if elem.getstudentID() == ID:
                if poz == '0' and lista.cautareID(value) == None:
                    elem.setstudentID(value)
                elif poz == '1':
                    elem.setnume(value)
                elif poz == '2':
                    elem.setgrup(value)
        self.save_to_file(lista)

    def generare(self):
        self.save_to_file(generare_studenti())

    def delete_all(self):
        self.save_to_file([])

    def get_all_students(self):
        """
        Returneaza o lista cu toti studentii existenti in fisier.
        """
        return self.__load_from_file()

    def show_all_students(self):
        """
        Afiseaza lista de studenti din fisier
        :rtype: -; afiseaza lista
        """
        lista = self.__load_from_file()
        print(colored('Lista de studenti este: ','cyan'))
        for el in lista:
            print(el)

    def add_random(self,value):
        lista = self.__load_from_file()
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
            lista.append(s)
        self.save_to_file(lista)


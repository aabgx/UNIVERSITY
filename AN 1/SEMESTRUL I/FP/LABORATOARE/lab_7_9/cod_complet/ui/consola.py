import os
import sys
sys.path.append(os.getcwd() + "\\")
from termcolor import colored
from service.stud_service import StudentService
from service.lab_service import LabService
from repository.validators import validator2


class Console:
    def __init__(self, srv_stud, srv_lab, srv_nota):
        """
        Initializeaza consola
        :type srv_stud: StudentService
        :type srv_lab: LabService
        :type srv_nota: NotaService
        """
        self.__srv_stud = srv_stud
        self.__srv_lab = srv_lab
        self.__srv_nota = srv_nota

    def __print_all(self):
        """
        Afiseaza cele 3 liste disponibile: studenti, laboratoare, notari.
        """
        student_list = self.__srv_stud.get_all()
        if len(student_list) == 0:
            print(colored('Nu exista studenti in lista.','cyan'))
        else:
            self.__srv_stud.show_all()

        print()

        lab_list = self.__srv_lab.get_all()
        if len(lab_list) == 0:
            print(colored('Nu exista laboratoare in lista.','cyan'))
        else:
            self.__srv_lab.show_all()
        print()

        lista_notare = self.__srv_nota.get_all()
        if len(lista_notare) == 0:
            print(colored('Nu s-a acordat nicio nota inca.','cyan'))
        else:
            print(colored('Lista de note este:','cyan'))
            
            for nota in lista_notare:
                print(colored('Student: ','cyan'), str(nota.getstudent()), '; ',
                      colored('Laborator si problema: ','cyan'), str(nota.getlaborator()), ';', colored('Nota: ','cyan'), str(
                        nota.getnota()))
        
    
    

    def __modificare(self,op):
        """
        Modifica un student sau laborator in functie de o data citita de la tastatura
        :param op: aca op = 1 se efectueaza modificarea unui student, daca op = 2 se efectueaza modificarea unui laborator.
        :type op: str
        """
        if(op == '1'):
            ID = input('Dati ID-ul studentlui la care vreti sa efectuati modificarea: ')
            print("Ce vreti sa modificati la acest student?")
            print("0.ID  1.Nume  2.Grupa")
            poz = input("Optiunea dumneavoastra este: ")
            value = input("Dati detaliile datei alese: ")
            
            try:
                self.__srv_stud.modificare_student(ID,value,poz)
                print(colored('Studentul a fost modificat cu succes','green'))
            except ValueError as ve:
                print(colored(str(ve),'red'))

        if(op == '2'):
            lab_prob = input('Dati nr. laboratorului si nr. problemei de modificat sub forma lab_prob: ')
            print("Ce vreti sa modificati la acest laborator?")
            print("0.Nr. laborator si/sau nr. problema  1.Descrierea  2.Deadline-ul")
            poz = input("Optiunea dumneavoastra este: ")
            value = input("Dati detaliile datei alese: ")
            
            try:
                self.__srv_lab.modificare_lab(lab_prob,value,poz)
                print(colored('Laboratorul a fost modificat cu succes','green'))
            except ValueError as ve:
                print(colored(str(ve),'red'))


    def __dell(self,op):
        """
        Sterge un student sau lab in functie de o data citita de la tastatura
        :param op: aca op = 1 se efectueaza stergerea unui student, daca op = 2 se efectueaza stergerea unui laborator.
        :type op: str
        """
        if(op == '1'):
            print("Dupa ce criteriu vreti sa stergeti un student?")
            print("0.ID  1.Nume  2.Grupa")
            poz = input("Optiunea dumneavoastra este: ")
            value = input("Dati detaliile datei alese: ")
            
            try:
                self.__srv_stud.dell_student(value,poz)
                print(colored('Studentul a fost sters cu succes','green'))
            except ValueError as ve:
                print(colored(str(ve),'red'))

        elif(op == '2'):
            print("Dupa ce criteriu vreti sa stergeti un laborator?")
            print("0. Nr. laborator si nr. problema  1.Descriere  2.Deadline")
            poz = input('Optiunea dumneavoastra este: ')
            value = input("Dati detaliile datei alese: ")
            
            try:
                self.__srv_lab.dell_lab(value,poz)
                print(colored('Laboratorul a fost sters cu succes','green'))
            except ValueError as ve:
                print(colored(str(ve),'red'))

    def __add(self,op):
        """
        Adauga un student sau lab cu datele citite de la tastatura.
        :param op: aca op = 1 se efectueaza adaugarea unui student, daca op = 2 se efectueaza adaugarea unui laborator.
        :type op: str
        """
        if(op == '1'):
            ID = input("ID student: ")
            nume = input("Nume student: ")
            grup = input("Grupa de apartenenta: ")
            
            try:
                self.__srv_stud.add_student(ID,nume,grup)
                print(colored('Studentul a fost adaugat cu succes','green'))
            except ValueError as ve:
                print(colored(str(ve),'red'))
        elif(op == '2'):
            lab_prob = input("Nr. lab si nr. problema sub forma laborator_problema: ")
            descriere = input("Descrierea problemei: ")
            deadline = input("Data limita de predare: ")
            try:
                self.__srv_lab.add_lab(lab_prob,descriere,deadline)
                print(colored('Laboratorul a fost adauga cu succes','green'))
            except ValueError as ve:
                print(colored(str(ve),'red'))

    def __add_random(self,op):
        """
        Se adauga random un numar val de studenti sau laboratoare in lista.
        :param op: aca op = 1 se efectueaza adaugarea de studenti, daca op = 2 se efectueaza adaugarea de laboratoare.
        :type op: str
        """
        if op == '1':

            try:
                val = int(input('Nr. de studenti pe care vreti sa ii adaugati: '))
                self.__srv_stud.add_random(val)
                print(colored('Studentii au fost adaugati cu succes','green'))
            except ValueError:
                print(colored('Introduceti UN NUMAR INTREG de studenti pe care vreti sa il adaugati','red'))
        
        elif op == '2':
            try:
                val = int(input('Nr. de laboratoare pe care vreti sa ii adaugati: '))
                self.__srv_lab.add_random(val)
                print(colored('Laboratoarele au fost adaugati cu succes','green'))
            except ValueError:
                print(colored('Introduceti UN NUMAR INTREG de laboratoare pe care vreti sa il adaugati','red'))

    def __cautare(self,op):
        """
        Se cauta in lista studenti sau laboratoare dupa un criteriu dat: 0 -> ID/lab_prob, 1-> nume/descriere, 2 -> grupa/deadline.
        :param op: aca op = 1 se efectueaza cautarea unui student, daca op = 2 se efectueaza cautarea unui laborator.
        :type op: str 
        """
        if op == '1':
            print("Dupa ce criteriu vreti sa cautati un student?")
            print("0.ID  1.Nume  2.Grupa")
            poz = input("Optiunea dumneavoastra este: ")
            value = input("Dati detaliile datei alese: ")
            
            try:
                self.__srv_stud.cautare_student(value,poz)
                if self.__srv_stud.cautare_student(value,poz) == []:
                    print(colored('Nu exista un student cu aceste date in lista','cyan'))
                else:
                    print(colored('Studentii gasiti sunt:','cyan'))
                    self.__srv_stud.afis_cautare(value,poz)
                
            except ValueError as ve:
                print(colored(str(ve),'red'))
        elif op == '2':
            print("Dupa ce criteriu vreti sa cautati un laborator?")
            print("0.nr.lab si nr.prob  1.descriere  2.deadline")
            poz = input("Optiunea dumneavoastra este: ")
            value = input("Dati detaliile datei alese: ")
            
            try:
                self.__srv_lab.cautare_lab(value,poz)
                if self.__srv_lab.cautare_lab (value,poz) == []:
                    print(colored('Nu exista un laborator cu aceste date in lista','cyan'))
                else:
                    print(colored('Laboratoarele gasite sunt:','cyan'))
                    self.__srv_lab.afis_cautare(value,poz)
            except ValueError as ve:
                print(colored(str(ve),'red'))
    
    def __notare(self):
        """
        Se atribuie un laborator unui student si acesta este notat.
        """
        ID = input('ID student: ')
        lab_prob = input('Nr. laboratorului si al problemei: ')
        try:
            nota = float(input('Nota acordata: '))
            try:
                self.__srv_nota.asignare(ID, lab_prob, nota)
                print(colored('Nota a fost acordata cu succes.','green'))
            except ValueError as ve:
                print(colored(str(ve),'red'))
        except ValueError:
            print(colored('Nota trebuie sa fie un numar.', 'red'))

        

    def __ordonare(self,op):
        """
        Se ordoneaza lista de notari dupa un criteriu dat: op = 1 -> alfabetic dupa nume, op = 2 -> crescator dupa nota.
        """
        if op == '1':
            lista_notare = self.__srv_nota.ordonare_nota()
            if len(lista_notare) == 0:
                print(colored('Nu s-a acordat nicio nota inca.','cyan'))
            else:
                print(colored('Lista de note ordonata crescator dupa note este:','cyan'))
        elif op == '2':
            lista_notare = self.__srv_nota.ordonare_alfabetica()
            if len(lista_notare) == 0:
                print(colored('Nu s-a acordat nicio nota inca.','cyan'))
            else:
                print(colored('Lista de note ordonata alfabetic dupa nume este:','cyan'))
        
        if op == '1' or op == '2':
            for nota in lista_notare:
                print(colored('Student: ','cyan'), str(nota.getstudent()), '; ',
                colored('Laborator si problema: ','cyan'), str(nota.getlaborator()), ';', colored('Nota: ','cyan'), str(
                nota.getnota()))

    def __media_sub_5(self):
        """
        Se creeaza o statistica ce contine studentii cu media tuturor laboratoarelor sub 5 si se afiseaza o lista a acestora pe ecran.
        """
        lista = self.__srv_nota.media_sub_5()
        if len(lista) == 0:
                print(colored('Niciun student nu are media laboratoarelor sub 5.','cyan'))
        else:
            print(colored('Studentii care au media tuturor laboratoarelor sub 5 sunt:','cyan'))
            for el in lista:
                print(colored('Nume student: ','cyan'), el[0],';  ',colored('Media: ','cyan'),el[1])
    
    def __primele_3(self):
        lista = self.__srv_nota.primele_3()
        if len(lista) == 0:
                print(colored('Nu a fost notat niciun laborator inca.','cyan'))
        else:
            for el in lista:
                print(colored('Nr. laborator si problema: ','cyan'),el[0],colored(' Numar laboratoare notate: ','cyan'),el[1])
        
    def __ordonare_studenti_combinat(self):
        lista = self.__srv_nota.ordonare_studenti_combinat()
        print('Lista de studenti ordonata dupa nume si dupa grupa este:')
        for el in lista:
            print(el)
            
                

    def start(self):

        while True:
            
            print(colored('Comenzi disponibile: add, add_random, dell, change, search, notare, ordonare, raport, raport_primele_3, show_all, ordonare_studenti_combinat exit','magenta'))
            cmd = input('Comanda este: ')
            print()
            cmd = cmd.lower().strip()

            if cmd == 'add':
                print('Adaugati un student sau un laborator?')
                print('1. student 2. laborator')
                op = input('Optiunea dumneavoastra este: ')
                try:
                    validator2(op)
                    self.__add(op)
                except ValueError as ve:
                    print(colored(str(ve),'red'))

            elif cmd == 'ordonare_studenti_combinat':
                self.__ordonare_studenti_combinat()

            elif cmd == 'raport':
                self.__media_sub_5()
            elif cmd == 'raport_primele_3':
                self.__primele_3()

            elif cmd == 'add_random':
                print('Doriti sa adaugati studenti sau laboratoare? ')
                print('1. student 2. laborator')
                op = input('Optiunea dumneavoastra este: ')
                self.__add_random(op)
            
            elif cmd == 'dell':
                print('Stergeti un student sau un laborator??')
                print('1. student 2. laborator')
                op = input('Optiunea dumneavoastra este: ')
                try:
                    validator2(op)
                    self.__dell(op)
                except ValueError as ve:
                    print(colored(str(ve),'red'))
            
            elif cmd == 'change':
                print('Schimbati un element al unui student sau al unui laborator?')
                print('1. student 2. laborator')
                op = input('Optiunea dumneavoastra este: ')
                try:
                    validator2(op)
                    self.__modificare(op)
                except ValueError as ve:
                    print(colored(str(ve),'red'))
            
            elif cmd == 'search':
                print('Cautati studenti sau laboratoare?')
                print('1.studenti 2.laboratoare')
                op = input('Optiunea dumneavoastra este: ')
                try:
                    validator2(op)

                    self.__cautare(op)
                except ValueError as ve:
                    print(colored(str(ve),'red'))

            elif cmd == 'notare':
                self.__notare()

            elif cmd == 'show_all':
                self.__print_all()

            elif cmd == 'ordonare':
                print('Doriti sa ordonati dupa nota sau alfabetic dupa nume?')
                print('1. dupa nota  2. alfabetic dupa nume')
                op = input('Introduceti optiunea dumneavoastra: ')
                self.__ordonare(op)

            elif cmd == 'exit':
                return

            elif cmd == 'sort':
                lista = [[1,2],[3,1],[6,4],[1,3]]
                print(lista)
                lista.sort(key=lambda x: x[1])
                print(lista)



            else:
                print(colored('Comanda invalida.', 'red'))
            print()
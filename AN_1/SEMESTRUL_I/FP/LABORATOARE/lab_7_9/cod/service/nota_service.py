import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import notare,student,laborator
from domain.validators import NotareValidator

from repository.stud import stud_repository
from repository.lab import lab_repository
from repository.nota import note_repository


class NotaService:
    def __init__(self, repo_nota, repo_lab, repo_stud):
        self.__repo_nota = repo_nota
        self.__repo_lab = repo_lab
        self.__repo_stud = repo_stud

    def asignare(self, ID, lab_prob, nota):
        """
        Atribuie un laborator unui student si il noteaza.
        :param ID: identificator student
        :param lab_prob: identificator laborator
        :param nota: nota acordata
        :raises: ValueError daca studentul sau laboratorul nu exista.
        """
        errors = []
        stud = self.__repo_stud.cautare_ID(ID)
        
        if stud is None:
            errors.append('Studentul nu exista in lista.')

        lab = self.__repo_lab.cautare_lab_prob(lab_prob)
        
        if lab is None:
            errors.append('Laboratorul nu exista in lista.')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

        notaa = notare(ID, lab_prob, nota)
        
        self.__repo_nota.add(notaa)
        return notaa

    def ordonare_alfabetica(self):
        """
        Ordoneaza alfabetic dupa nume lista de studenti care au fost notati pentru un anumit laborator.
        :return: lista ordonata
        :rtype: list
        """
        lista_nume = []
        lista_sortata= []
        
        for el in self.__repo_nota.get_all():
            studentul = self.__repo_stud.cautare_ID(el.getstudent())
            lista_nume.append(studentul.getnume())

        lista_sortata_nume = sorted(lista_nume, key=str.lower)

        for i,el in enumerate (lista_sortata_nume):
            if i == 0 or (i != 0 and lista_sortata_nume[i-1] != lista_sortata_nume[i]):
                for el1 in self.__repo_stud.cautare(el,'1'):
                    for el2 in self.__repo_nota.cautare_student(el1.getstudentID()):
                        lista_sortata.append(el2)

        
        return lista_sortata

    def ordonare_nota(self):
        """
        Ordoneaza crescator dupa nota lista de studenti care au fost notati pentru un anumit laborator.
        :return: lista ordonata
        :rtype: list
        """
        lista_note = []
        lista_sortata= []
        
        for el in self.__repo_nota.get_all():
            lista_note.append(el.getnota())

        lista_sortata_note = sorted(lista_note)

        for i,el in enumerate (lista_sortata_note):
            if i == 0 or (i != 0 and lista_sortata_note[i-1] != lista_sortata_note[i]):
                for el1 in self.__repo_nota.cautare_nota(el):
                        lista_sortata.append(el1)
        
        return lista_sortata

    def primele_3(self):
        """
        Creaza un raport cu privirea la primele 3 laboratoare cu cele mai multe note.
        (daca nu sunt 3, afiseaza cate sunt sau un mesaj daca nu s-a notat niciun laborator)
        Daca gaseste cu acelasi nr de laboratoare intre cele 3, le ia pe toate pe poz diferite, in ordinea in care le gaseste 
        (daca sunt 4 laburi cu cate 5 note, acesta fiind maximul, le ia doar pe primele 3 pe care le gaseste)
        :return: o lista cu elemente a cate 3 pozitii fiecare: prima nr. laborator si nr. problema, a doua descrierea si a treia nr. note
        :rtype: list
        """
        lista_labs = []
        lista_primele = []
        for el in self.__repo_nota.get_all():
            ok = 1
            cnt_nr = 0
            lab = el.getlaborator()

            for el1 in self.__repo_nota.get_all():
                if el1.getlaborator() == lab:
                    cnt_nr = cnt_nr + 1

            elem = [lab,cnt_nr]

            for el in lista_labs:
                if el[0] == lab:
                    ok = 0
            if ok == 1:
                lista_labs.append(elem)
            
        lista_labs.sort(key=lambda x: x[1])

        if len(lista_labs) < 3:
            lista_primele = lista_labs
        else:
            lista_primele.append(lista_labs[-1])
            lista_primele.append(lista_labs[-2])
            lista_primele.append(lista_labs[-3])
           
     

        return lista_primele

    def media_sub_5(self):
        """
        Creaza un raport cu privire la studentii care au media tuturor laboratoarelor sub 5.
        :return: o lista cu elemente a cate 2 pozitii fiecare: prima numele studentului, a doua media (sub 5)
        :rtype: list
        """
        lista_medii = []
        for el in self.__repo_nota.get_all():
            media = 0
            lista_note = []
            stud = el.getstudent()

            for el1 in self.__repo_nota.get_all():
                if el1.getstudent() == stud:
                    lista_note.append(el1.getnota())

            for elem in lista_note:
                media += elem
            media = media / len(lista_note)

            elem = [stud,media]

            ok = 1
            for el in lista_medii:
                if el[0] == stud:
                    ok = 0

            if ok == 1 and media < 5:
                lista_medii.append(elem)
    
        for el in lista_medii:
            el[0] = self.__repo_stud.cautare_ID(el[0]).getnume()

        return lista_medii

    def get_all(self):
        return self.__repo_nota.get_all()
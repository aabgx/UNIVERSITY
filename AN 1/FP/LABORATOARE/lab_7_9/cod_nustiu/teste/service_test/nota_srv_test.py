import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest

from service.nota_service import *

class TestCaseNotaSRV(unittest.TestCase):
    def test_asignare(self):
    
        repo_nota = note_repository()
        repo_lab = lab_repository()
        repo_stud = stud_repository()
        nota_srv = NotaService(repo_nota, repo_lab, repo_stud)

        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        repo_stud.add(student1)
        repo_lab.add(laborator1)

        ID = '1'
        lab = '1_1'
        nota = 5

        self.assertTrue (nota_srv.asignare(ID,lab,nota) == notare(ID,lab,nota))
        self.assertTrue(len(nota_srv.get_all()) == 1)

        ID = '2'
        lab = '1_1'
        nota = 5

        try:
            nota_srv.asignare(ID,lab,nota)
            self.assertTrue (False)
        except ValueError:
            self.assertTrue (True)

    def test_ordonare_alfabetica(self):
        repo_nota = note_repository()
        repo_lab = lab_repository()
        repo_stud = stud_repository()
        nota_srv = NotaService(repo_nota, repo_lab, repo_stud)

        student1 = student('1','Alina','211')
        student2 = student('2','Bianca','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        laborator2 = laborator('1_2','aialalta','1.1.2021')
    
        repo_stud.add(student1)
        repo_stud.add(student2)
        repo_lab.add(laborator1)
        repo_lab.add(laborator2)

        notare1 = nota_srv.asignare('2','1_1',7.8)
        notare2 = nota_srv.asignare('1','1_1',6.7)

        self.assertTrue(nota_srv.ordonare_alfabetica()[0] == notare2)
        self.assertTrue(nota_srv.ordonare_alfabetica()[1] == notare1)


    def test_media_sub_5(self):
        repo_nota = note_repository()
        repo_lab = lab_repository()
        repo_stud = stud_repository()
        nota_srv = NotaService(repo_nota, repo_lab, repo_stud)

        student1 = student('1','Alina','211')
        student2 = student('2','Bianca','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        laborator2 = laborator('1_2','aialalta','1.1.2021')
    
        repo_stud.add(student1)
        repo_stud.add(student2)
        repo_lab.add(laborator1)
        repo_lab.add(laborator2)

        for el in repo_lab.get_all_labs():
            print(el)

        notare1 = nota_srv.asignare('2','1_1',7.8)
        notare2 = nota_srv.asignare('1','1_1',6.7)
        notare3 = nota_srv.asignare('1','1_2',2.2)

        self.assertTrue(nota_srv.media_sub_5()[0][1] == 4.45)
        self.assertTrue(len(nota_srv.media_sub_5()) == 1)

        print(nota_srv.media_sub_5())



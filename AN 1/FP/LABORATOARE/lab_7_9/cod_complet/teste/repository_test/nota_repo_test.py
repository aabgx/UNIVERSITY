import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest

from repository.nota import *

class TestCaseLabRepo(unittest.TestCase):
    def test_add12(self):
        repo_test = note_repository()
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota = 6
        notare1 = notare(student1,laborator1,nota)
        self.assertTrue(repo_test.get_all() == [])
        repo_test.add(notare1)
        self.assertTrue(len(repo_test.get_all()) == 1)

        nota = 7
        notare2 = notare(student1,laborator1,nota)
        try:
            repo_test.add(notare2)
            self.assertTrue (False)
        except ValueError:
            self.assertTrue (True)

    def test_cautare12(self):
        repo_test = note_repository()
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota = 6
        notare1 = notare(student1,laborator1,nota)
        repo_test.add(notare1)
        self.assertTrue(repo_test.cautare(notare1) == notare1)

        student2 = student('2','Alina','211')
        laborator2 = laborator('1_2','asta','1.1.2021')
        nota = 6
        notare2 = notare(student2,laborator2,nota)
        self.assertTrue(repo_test.cautare(notare2) == None)

    def test_cautare_student12(self):
        repo_test = note_repository()
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota = 6
        notare1 = notare(student1,laborator1,nota)
        repo_test.add(notare1)

        self.assertTrue(len(repo_test.cautare_student(student1)) == 1)

        student2 = student('2','Alina','211')
        laborator2 = laborator('1_2','asta','1.1.2021')
        nota = 7
        notare2 = notare(student2,laborator2,nota)
        self.assertTrue(repo_test.cautare_student(student2) == [])

    def test_cautare_nota1(self):
        repo_test = note_repository()
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota1 = 6
        notare1 = notare(student1,laborator1,nota1)
        repo_test.add(notare1)
        self.assertTrue(len(repo_test.cautare_nota(nota1)) == 1)

        student2 = student('2','Alina','211')
        laborator2 = laborator('1_2','asta','1.1.2021')
        nota2 = 7
        notare2 = notare(student2,laborator2,nota2)
        self.assertTrue(repo_test.cautare_nota(nota2) == [])

    def test_cautare_stud_lab1(self):
        repo_test = note_repository()
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota1 = 6
        notare1 = notare(student1,laborator1,nota1)
        repo_test.add(notare1)
        self.assertTrue(repo_test.cautare_stud_lab(student1,laborator1) == notare1)

        student2 = student('2','Alina','211')
        laborator2 = laborator('1_2','asta','1.1.2021')
        nota2 = 7
        notare2 = notare(student2,laborator2,nota2)
        self.assertTrue(repo_test.cautare_stud_lab(student2,laborator2) == None)

    def test_add(self):
        repo_test = note_repository_file('./data/note.txt')
        lista = repo_test.get_all()
        repo_test.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        notare1 = notare('1','1_1',6)
        self.assertTrue(repo_test.get_all() == [])
        repo_test.add(notare1)
        self.assertTrue(len(repo_test.get_all()) == 1)
        notare2 = notare('1','1_1',7)
        try:
            repo_test.add(notare2)
            self.assertTrue (False)
        except ValueError:
            self.assertTrue (True)

        repo_test.save(lista)
        
    def test_cautare(self):
        repo_test = note_repository_file('./data/note.txt')
        lista = repo_test.get_all()
        repo_test.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota = 6
        notare1 = notare('1','1_1',nota)
        repo_test.add(notare1)
        self.assertTrue(repo_test.cautare(notare1) == notare1)

        student2 = student('2','Alina','211')
        laborator2 = laborator('1_2','asta','1.1.2021')
        nota = 6
        notare2 = notare('2','1_2',nota)
        self.assertTrue(repo_test.cautare(notare2) == None)
        repo_test.save(lista)

    def test_cautare_student(self):
        repo_test = note_repository_file('./data/note.txt')
        lista = repo_test.get_all()
        repo_test.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota = 6
        notare1 = notare('1','1_1',6)
        repo_test.add(notare1)
        self.assertTrue(len(repo_test.cautare_student('1')) == 1)
        student2 = student('2','Alina','211')
        laborator2 = laborator('1_2','asta','1.1.2021')
        nota = 7
        notare2 = notare('2','1_2',nota)
        self.assertTrue(repo_test.cautare_student('2') == [])
        repo_test.save(lista)

    def test_cautare_nota(self):
        repo_test = note_repository_file('./data/note.txt')
        lista = repo_test.get_all()
        repo_test.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        student1 = student('1','Alina','211')
        laborator1 = laborator('1_1','asta','1.1.2021')
        nota1 = 6
        notare1 = notare('1','1_1',nota1)
        repo_test.add(notare1)
        self.assertTrue(len(repo_test.cautare_nota(nota1)) == 1)
        student2 = student('2','Alina','211')
        laborator2 = laborator('1_2','asta','1.1.2021')
        nota2 = 7
        notare2 = notare('2','1_2',nota2)
        self.assertTrue(repo_test.cautare_nota(nota2) == [])
        repo_test.save(lista)

    def test_cautare_stud_lab(self):
        repo_test = note_repository_file('./data/note.txt')
        lista = repo_test.get_all()
        repo_test.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
    
        nota1 = 6
        notare1 = notare('1','1_1',nota1)
        repo_test.add(notare1)
        self.assertTrue(repo_test.cautare_stud_lab('1','1_1') == notare1)

        
        nota2 = 7
        notare2 = notare('2','1_2',nota2)
        self.assertTrue(repo_test.cautare_stud_lab('2','1_2') == None)
        repo_test.save(lista)
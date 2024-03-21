import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest

from repository.stud_file import *

class TestCaseStudentRepo(unittest.TestCase):
    def test_add1(self):
        srf = stud_repository_file(file)
        lista = srf.get_all_students()
        srf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        srf.generare()

        srf.add(student('6','ff','6'))
        self.assertTrue(srf.get_all_students()[-1] == student('6','ff','6'))

        srf.save_to_file(lista)

    def test_add_random1(self):
        srf = stud_repository_file(file)
        lista = srf.get_all_students()
        srf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        srf.generare()
        lin = len(srf.get_all_students())

        srf.add_random(5)
        self.assertTrue(len(srf.get_all_students()) == lin + 5)

        srf.save_to_file(lista)

    def test_dell1(self):
        srf = stud_repository_file(file)
        lista = srf.get_all_students()
        srf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        srf.generare()

        lin = len(srf.get_all_students())
        srf.dell('aa','1')

        self.assertTrue(len(srf.get_all_students()) == lin-1)

        srf.save_to_file(lista)

    def test_cautare_ID1(self):
        srf = stud_repository_file(file)
        lista = srf.get_all_students()
        srf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        srf.generare()
        self.assertTrue(srf.cautare_ID('4') == student('4','dd','4'))

        srf.save_to_file(lista)
        
    def test_cautare1(self):
        srf = stud_repository_file(file)
        lista = srf.get_all_students()
        srf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        srf.generare()

        self.assertTrue(srf.cautare('4','0') == [student('4','dd','4')])
        self.assertTrue(srf.cautare('1000','1') == [])

        srf.save_to_file(lista)
        
    def test_modificare1(self):
        srf = stud_repository_file(file)
        lista = srf.get_all_students()
        srf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        srf.generare()

        srf.modificare('4','hh','1')
        self.assertTrue(srf.cautare_ID('4') == student('4','hh','4'))

        srf.save_to_file(lista)
    def test_modificare(self):
        students = stud_repository()
        student1 = student('4563','Ana Manole','6')
        student2 = student('657','Ofelia Maxim','16')
        students.add(student1)
        students.add(student2)

        students.modificare('4563','Alina Maria','1')
        self.assertTrue(students.get_all_students()[0].getnume() == 'Alina Maria')

    def test_add(self):
        students = stud_repository()

        student1 = student('4563','Ana Manole','6')
        students.add(student1)
        self.assertTrue(students.get_all_students()[0] == student1)

        student2 = student('657','Ofelia Maxim','16')
        students.add(student2)
        self.assertTrue(students.get_all_students()[1] == student2)
        

    def test_dell(self):
        students = stud_repository()

        student1 = student('4563','Ana Manole','6')
        student2 = student('657','Ofelia Maxim','16')
        student3 = student('3321','Bugnar Diana','90')
        students.add(student1)
        students.add(student2)
        students.add(student3)

        students.dell('Ofelia Maxim','1')
        self.assertTrue(students.get_all_students()[1] == student3)

        students.dell('6','2')
        self.assertTrue(students.get_all_students()[0] == student3)

    def test_cautare(self):
        students = stud_repository()

        student1 = student('4563','Ana Manole','6')
        student4 = student('112','Ofelia Maxim','10')
        student2 = student('657','Ofelia Maxim','16')
        student3 = student('3321','Bugnar Diana','90')
        students.add(student1)
        students.add(student2)
        students.add(student3)
        students.add(student4)
        
        self.assertTrue(len(students.cautare('Ofelia Maxim','1')) == 2)
        self.assertTrue(students.cautare('Ofelia Maxim','1')[1] == student4)
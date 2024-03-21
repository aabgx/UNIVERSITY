import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest

from repository.lab_file import *
from repository.lab import *

class TestCaseLabRepo(unittest.TestCase):
    def test_add1(self):
        laboratoare = lab_repository()

        lab1 = laborator('5_8','asta e','31.5.2020')
        laboratoare.add(lab1)
        self.assertTrue(laboratoare.get_all_labs()[0] == lab1)

        lab2 = laborator('6_7','aia e','12.11.2021')
        laboratoare.add(lab2)
        self.assertTrue(laboratoare.get_all_labs()[1] == lab2)
    

    def test_dell1(self):
        laboratoare = lab_repository()

        lab1 = laborator('1_1','asta ai de facut','1.1.2020')
        lab2 = laborator('1_2','aia ai de facut','2.1.2020')
        lab3 = laborator('1_3','astalalta ai de facut','3.1.2020')
        laboratoare.add(lab1)
        laboratoare.add(lab2)
        laboratoare.add(lab3)

        laboratoare.dell('aia ai de facut','1')
        self.assertTrue(laboratoare.get_all_labs()[1] == lab3)

        laboratoare.dell('1.1.2020','2')
        self.assertTrue(laboratoare.get_all_labs()[0] == lab3)


    def test_modificare1(self):
        laboratoare = lab_repository()
        lab1 = laborator('3_9','asta e','12.3.2020')
        lab2 = laborator('4_10','aia e','12.4.2021')
        laboratoare.add(lab1)
        laboratoare.add(lab2)

        laboratoare.modificare('4_10','nu mai face asta','1')
        self.assertTrue(laboratoare.get_all_labs()[1].getdescriere() == 'nu mai face asta')

    def test_cautare1(self):
        labs = lab_repository()

        lab1 = laborator('3_6','fa asta','22.4.2022')
        lab2 = laborator('3_7','nu face asa','22.5.2022')
        lab3 = laborator('3_8','asta face','22.6.2022')
        lab4 = laborator('3_9','fa asta','22.7.2022')
        labs.add(lab1)
        labs.add(lab2)
        labs.add(lab3)
        labs.add(lab4)
        
        self.assertTrue(len(labs.cautare('fa asta','1')) == 2)
        self.assertTrue(labs.cautare('fa asta','1')[1] == lab4)

    def test_add(self):
        lrf = lab_repository_file(file)
        lista = lrf.get_all_labs()
        lrf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        lrf.generare()

        lab1 = laborator('5_8','asta e','31.5.2020')
        lrf.add(lab1)
        self.assertTrue(lrf.get_all_labs()[-1] == lab1)

        lab2 = laborator('6_7','aia e','12.11.2021')
        lrf.add(lab2)
        self.assertTrue(lrf.get_all_labs()[-1] == lab2)

        lrf.save_to_file(lista)

    def test_add_random(self):
        lrf = lab_repository_file(file)
        lista = lrf.get_all_labs()
        lrf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        lrf.generare()
        lin = len(lrf.get_all_labs())

        lrf.add_random(5)
        self.assertTrue(len(lrf.get_all_labs()) == lin + 5)

        lrf.save_to_file(lista)
        

    def test_dell(self):
        lrf = lab_repository_file(file)
        lista = lrf.get_all_labs()
        lrf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        lrf.generare()

        lrf.dell('aa','1')
        self.assertTrue(lrf.get_all_labs()[0] == laborator('2_3','bb','2.2.2021'))

        lin = len(lrf.get_all_labs())
        lrf.dell('100_1','0')
        self.assertTrue(len(lrf.get_all_labs()) == lin)

        lrf.save_to_file(lista)

    def test_modificare(self):
        lrf = lab_repository_file(file)
        lista = lrf.get_all_labs()
        lrf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        lrf.generare()

        lrf.modificare('2_3','nu mai face asta','1')
        self.assertTrue(lrf.get_all_labs()[1].getdescriere() == 'nu mai face asta')

        lrf.save_to_file(lista)

    def test_cautare(self):
        lrf = lab_repository_file(file)
        lista = lrf.get_all_labs()

        lrf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        lrf.generare()

        self.assertTrue(len(lrf.cautare('aa','1')) == 2)
        self.assertTrue(lrf.cautare('aa','1')[0] == laborator('1_3','aa','1.2.2021'))

        lrf.save_to_file(lista)

    def test_cautare_lab_prob(self):
        lrf = lab_repository_file(file)
        lista = lrf.get_all_labs()

        lrf.delete_all() #fac fisierul sa fie gol inainte sa incerc testarea
        lrf.generare()

        self.assertTrue(lrf.cautare_lab_prob('10_9',0) == None)
        self.assertTrue(lrf.cautare_lab_prob('1_3',0) == laborator('1_3','aa','1.2.2021'))
        self.assertTrue(lrf.cautare_lab_prob('8_3',0) == laborator('8_3','aa','8.2.2021'))
        self.assertTrue(lrf.cautare_lab_prob('2_3',0) == laborator('2_3','bb','2.2.2021'))

        lrf.save_to_file(lista)
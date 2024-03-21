import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest
from domain.entities import student

class TestCaseStudent(unittest.TestCase):
    def test_create_student(self):
        student1 = student(12,'Bugnar Catalin',211)
        self.assertTrue(student1.getstudentID() == 12)
        self.assertTrue(student1.getnume() == 'Bugnar Catalin')
        self.assertTrue(student1.getgrup() == 211)

        student1.setstudentID(335)
        student1.setnume('Bugnar Diana')
        student1.setgrup(212)
        
        self.assertTrue(student1.getstudentID() == 335)
        self.assertTrue(student1.getnume() == 'Bugnar Diana')
        self.assertTrue(student1.getgrup() == 212)


    def test_equals_student(self):
        student1 = student(1232,'Bugnar Catalin',211)
        student2 = student(1232,'Bugnar Catalin',211)

        self.assertTrue(student1 == student2)

        student3 = student(1232, 'Alina Bidian', 44)
        self.assertTrue(student1 == student3)

        student4 = student(1244, 'Andrei Contis', 55)
        self.assertTrue(student1 != student4)

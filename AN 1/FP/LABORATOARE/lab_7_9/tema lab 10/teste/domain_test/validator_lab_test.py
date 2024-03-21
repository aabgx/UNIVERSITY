import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest
from domain.entities import laborator
from domain.validators import LaboratorValidator

class TestCaseLaborator(unittest.TestCase):
    def test_validare(self):
        stud_val = LaboratorValidator()
        laborator1 = laborator('1_1','fa asta','1.1.2020')
        try:
            stud_val.validate(laborator1)
            self.assertTrue(True)
        except ValueError:
            self.assFalse(False)

        laborator5 = laborator('1.1','fa asta','1.1.2020')
        try:
            stud_val.validate(laborator5)
            self.assFalse(False)
        except ValueError:
            self.assertTrue(True)

        laborator6 = laborator('1_200','fa asta','1.1.2020')
        try:
            stud_val.validate(laborator6)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)

        laborator9 = laborator('500_2','fa asta','1.1.2020')
        try:
            stud_val.validate(laborator9)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)

        laborator2 = laborator('1.1','','1.1.2020')
        try:
            stud_val.validate(laborator2)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)

        laborator3 = laborator('1_2','fa asta','2.3.40')
        try:
            stud_val.validate(laborator3)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)

        laborator4 = laborator('12','Bugnar Catalin','-3')
        try:
            stud_val.validate(laborator4)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)
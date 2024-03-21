import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest
from domain.entities import student
from domain.validators import StudentValidator

class TestCaseStudent(unittest.TestCase):
    def test_validare(self):
        stud_val = StudentValidator()
        student1 = student('12','Bugnar Catalin','211')
        try:
            stud_val.validate(student1)
            self.assertTrue(True)
        except ValueError:
            self.assertTrue(False)

        student2 = student('-2','Bugnar Catalin','211')
        try:
            stud_val.validate(student2)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)

        student3 = student('12','','211')
        try:
            stud_val.validate(student3)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)

        student4 = student('12','Bugnar Catalin','-3')
        try:
            stud_val.validate(student4)
            self.assertTrue(False)
        except ValueError:
            self.assertTrue(True)
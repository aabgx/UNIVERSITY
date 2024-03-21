import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest

from service.stud_service import *

class TestCaseStudentSRV(unittest.TestCase):
    def test_modificare_student(self):
        repo = stud_repository()
        validator = StudentValidator()
        test_srv = StudentService(repo, validator)

        test_srv.add_student('345','Ana Mia','6')
        test_srv.add_student('123','Diana','8')
        test_srv.add_student('124','Andreea','6')
        test_srv.add_student('876','Catalin','10')

        try:
            test_srv.modificare_student('123','ALINA','1')
            self.assertTrue (True)
        except ValueError:
            self.assertTrue (False)

        try:
            test_srv.modificare_student('rtt','ALINA','1')
            self.assertTrue (False)
        except ValueError:
            self.assertTrue (True)
    

    def test_dell_student(self):
        repo = stud_repository()
        validator = StudentValidator()
        test_srv = StudentService(repo, validator)

        test_srv.add_student('345','Ana Mia','6')
        test_srv.add_student('123','Diana','8')
        test_srv.add_student('124','Andreea','6')
        test_srv.add_student('876','Catalin','10')
        
        try:
            test_srv.dell_student('Ana Mia','1')
            self.assertTrue (True)
        except ValueError:
            self.assertTrue (False)

        try:
            test_srv.dell_student('8','1')
            self.assertTrue (False)
        except ValueError:
            self.assertTrue (True)



    def test_add_student(self):
        repo = stud_repository()
        validator = StudentValidator()
        test_srv = StudentService(repo, validator)

        try:
            test_srv.add_student('345','Ana Mia','6')
            self.assertTrue (True)
        except ValueError:
            self.assertTrue (False)

        try:
            test_srv.add_student('-45','','6')
            self.assertTrue (False)
        except ValueError:
            self.assertTrue (True)
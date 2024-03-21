import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest

from service.lab_service import *

class TestCaseLabSRV(unittest.TestCase):
    def test_modificare_student(self):
        repo = lab_repository()
        validator = LaboratorValidator()
        test_srv = LabService(repo, validator)

        test_srv.add_lab('1_1','asta','1.1.2020')
        test_srv.add_lab('1_2','aia','2.1.2020')
        test_srv.add_lab('1_3','aialalta','3.1.2020')
        test_srv.add_lab('1_4','cealalta','4.1.2020')

        try:
            test_srv.modificare_lab('1_2','nu mai face aia','1')
            self.assertTrue( True)
        except ValueError:
            self.assertTrue( False)

        try:
            test_srv.modificare_lab('rtt','fa asta','1')
            self.assertTrue( False)
        except ValueError:
            self.assertTrue( True)

    def test_dell_student(self):
        repo = lab_repository()
        validator = LaboratorValidator()
        test_srv = LabService(repo, validator)

        test_srv.add_lab('1_1','asta','1.1.2020')
        test_srv.add_lab('1_2','aia','2.1.2020')
        test_srv.add_lab('1_3','aialalta','3.1.2020')
        test_srv.add_lab('1_4','cealalta','4.1.2020')
        
        try:
            test_srv.dell_lab('aialalta','1')
            self.assertTrue( True)
        except ValueError:
            self.assertTrue( False)

        try:
            test_srv.dell_lab('8','1')
            self.assertTrue( False)
        except ValueError:
            self.assertTrue( True)
        


    def test_add_student(self):
        repo = lab_repository()
        validator = LaboratorValidator()
        test_srv = LabService(repo, validator)

        try:
            test_srv.add_lab('3_7','asta e','22.3.2022')
            self.assertTrue( True)
        except ValueError:
            self.assertTrue( False)

        try:
            test_srv.add_lab('3_r','aia e','23.14.2022')
            self.assertTrue( False)
        except ValueError:
            self.assertTrue( True)
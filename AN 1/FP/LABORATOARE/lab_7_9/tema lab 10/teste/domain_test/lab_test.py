import os
import sys
sys.path.append(os.getcwd() + "\\")
import unittest
from domain.entities import laborator

class TestCaseLaborator(unittest.TestCase):
    def test_create_laborator(self):
        laborator1 = laborator('3_34','aceasta problema','22.08.2021')
        self.assertTrue (laborator1.getlab_prob() == '3_34')
        self.assertTrue (laborator1.getdescriere() == 'aceasta problema')
        self.assertTrue (laborator1.getdeadline() == '22.08.2021')

        laborator1.setlab_prob('3_35')
        laborator1.setdescriere('cealalta problema')
        laborator1.setdeadline('23.08.2021')
        
        self.assertTrue (laborator1.getlab_prob() == '3_35')
        self.assertTrue (laborator1.getdescriere() == 'cealalta problema')
        self.assertTrue (laborator1.getdeadline() == '23.08.2021')

    def test_equals_laborator(self):
        laborator1 = laborator('3_34','aceasta problema','22.08.2021')
        laborator2 = laborator('3_34','aceasta problema','22.08.2021')

        self.assertTrue (laborator1 == laborator2)

        laborator3 = laborator('3_34','cealalta problema','13.08.2021')
        self.assertTrue (laborator1 == laborator3)

        laborator4 = laborator('3_35','cealalta problema','13.08.2021')
        self.assertTrue (laborator1 != laborator4)
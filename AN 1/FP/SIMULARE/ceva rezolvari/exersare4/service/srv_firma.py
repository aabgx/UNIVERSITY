import os
import sys
sys.path.append(os.getcwd() + "\\")
from repo.repo_firma import *

class srv_firma:
    def __init__(self,repo_firma):
        self.__repo_firma = repo_firma

    def cautare(self,x):
        lista = []
        lista = self.__repo_firma.cautare(x)
        return lista

    def impozitare(self,cui,procent):
        firma = self.__repo_firma.cautare_cui(cui)
        rez_sold = int(firma.get_sold()) - procent/100*int(firma.get_sold())
        rez = Impozitare(firma,procent,rez_sold)
        return rez

def test_impozitare():
    repo_test = repo_firma('./data/firme_test.txt')
    srv_test = srv_firma(repo_test)

    assert(srv_test.impozitare('6859662',50).get_sold_after_taxes() == 2500.0)
    assert(srv_test.impozitare('6859662',50).get_firma().get_nume() == 'Termoline')

test_impozitare()

def test_cautare():
    repo_test = repo_firma('./data/firme.txt')
    srv_test = srv_firma(repo_test)

    assert(len(srv_test.cautare('Cleaning')) == 1)

    assert(srv_test.cautare('Cleaning')[0].get_cui() == '3465636')

test_cautare()
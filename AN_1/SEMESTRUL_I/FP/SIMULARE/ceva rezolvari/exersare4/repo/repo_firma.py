import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.entities import *

class repo_firma:
    def __init__(self,filename):
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca din fisier firmele disponibile.
        :return: lista de firme
        :rtype: list
        """

        lista_firme = []
        with open(self.__filename,'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    cui,nume_firma,sold = line.split(',')
                    firma = Firma(cui,nume_firma,sold)
                    lista_firme.append(firma)
        return lista_firme

    def cautare(self,x):
        """
        Cauta firme pe baza numelui.
        :param x: numele de cautat
        :type x: str
        """

        lista = self.__load_from_file()
        lista_return = []
        for firma in lista:
            cuvinte = []
            cuvinte = firma.get_nume().split(' ')
            for el in cuvinte:
                if el == x:
                    lista_return.append(firma)
        return lista_return
    
    def cautare_cui(self,x):
        """
        Cauta firma cu un id dat.
        :param x: id-ul de cautat
        :type x: int
        """
        lista = self.__load_from_file()
        for firma in lista:
            if firma.get_cui() == x:
                return firma
        return None

def test_cautare_cui():
    repo_test = repo_firma('./data/firme_test.txt')

    assert(repo_test.cautare_cui('6859662').get_nume() == 'Termoline')
    assert(repo_test.cautare_cui('3465636').get_sold() == '47000') 

test_cautare_cui()

def test_cautare():
    repo_test = repo_firma('./data/firme_test.txt')

    assert(len(repo_test.cautare('Cleaning')) == 1)

    assert(repo_test.cautare('Cleaning')[0].get_cui() == '3465636')

test_cautare()
import os
import sys
sys.path.append(os.getcwd() + '\\')
from domain.entities import *

def sort_nume(loc_parcare):
    return loc_parcare.get_nume()

def sort_strada(loc_parcare):
    return loc_parcare.get_strada()

class RepoParcare:
    def __init__(self,filename):
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca lista de parcari din fisier
        :return: lista cu obiecte de tip loc_parcare
        :rtype: list
        """
        lista_parcari = []
        with open(self.__filename, 'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    id,nume,strada,nr_utilizari = line.split(',')
                    parcare = loc_parcare(id,nume,strada,nr_utilizari)
                    lista_parcari.append(parcare)
        return lista_parcari
    
    def cautare(self,x):
        """
        Cauta parcari de pe o anumita strada in lista de parcari si le ordoneaza alfabetic dupa nume.
        :param x: strada de cautat
        :type x: str
        """
        lista_return = []
        lista_nume = []
        lista = self.__load_from_file()

        for parcare in lista:
            if parcare.get_strada() == x:
                lista_return.append(parcare)
        lista_return = sorted(lista_return, key = sort_nume)

        return lista_return

    def raport(self):
        lista = self.__load_from_file()
        lista = sorted(lista, key = sort_strada)

        lista_maxime = []
        maxim = 0
        
        for i,el in enumerate(lista):
            if i==0:
                if maxim < int(el.get_nr_utilizari()):
                    maxim = int(el.get_nr_utilizari())
                    parcare = el

            elif el.get_strada() == lista[i-1].get_strada():
                if maxim < int(el.get_nr_utilizari()):
                    maxim = int(el.get_nr_utilizari())
                    parcare = el

            elif el.get_strada() != lista[i-1].get_strada():
                lista_maxime.append(parcare)
                maxim = int(el.get_nr_utilizari())
                parcare = el
                
        lista_maxime.append(parcare)

        return lista_maxime

def test_cautare():
    repo_test = RepoParcare('./data/parcari_test.txt')

    assert(len(repo_test.cautare('Dorobantilor')) == 2)
    assert(repo_test.cautare('Dorobantilor')[0].get_nume() == 'W903')

test_cautare()

def test_raport():
    repo_test = RepoParcare('./data/parcari_test.txt')

    assert(repo_test.raport()[0].get_id() == '7')
    assert(repo_test.raport()[1].get_id() == '23')

test_raport()
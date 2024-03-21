import os
import sys
sys.path.append(os.getcwd() + '\\')
from domain.entities import *

class repo_show:
    def __init__(self,filename):
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca din fisier show-urile disponibile.
        :return: lista de show-uri
        :type return: list
        """
        lista_show = []
        with open(self.__filename, 'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    id,titlu,gen,nr_eps = line.split(',')
                    show = TvShow(id,titlu,gen,nr_eps)
                    lista_show.append(show)
        return lista_show

    def cautare(self,x):
        """
        Cauta un show pe baza unuia dintre genuri.
        :param x: genul de cautat
        :type x: str
        """
        lista = self.__load_from_file()
        lista_return = []
        for show in lista:
            genuri = []
            genuri = show.get_gen().split(' ')
            for el in genuri:
                if el == x:
                    lista_return.append(show)

        return lista_return

    def cautare_id(self,id):
        """
        Cuta un show pe baza de id
        :param id: id-ul de cautat
        :type id: str
        """
        lista = self.__load_from_file()
        for show in lista:
            if show.get_id() == id:
                return show
        return None


def test_cautare_id():
    repo_test = repo_show('./data/show_test.txt')

    assert(repo_test.cautare_id('CD32').get_titlu() == 'The Marvelous Mrs. Maisel')
    assert(repo_test.cautare_id('nu este') == None)

test_cautare_id()

def test_cautare():
    repo_test = repo_show('./data/show_test.txt')

    assert(len(repo_test.cautare('fantasy')) == 2)
    assert(repo_test.cautare('fantasy')[0].get_titlu() == 'The Good Place')


test_cautare()
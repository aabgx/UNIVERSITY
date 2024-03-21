import os
import sys
sys.path.append(os.getcwd() + '\\')

from domain.entities import *

class repo_playlist:
    def __init__(self,filename):
        self.__filename = filename

    def load_from_file(self):
        """
        Incarca din fisier playlist-urile disponibile
        :return: lista de playlist-uri
        :rtype: list
        """
        lista_playlist = []
        with open(self.__filename, 'r') as f:
            lines = f.readlines()
            for line in lines:
                if line != '':
                    line = line.strip()
                    playlist_id,titlu,data_creare,durata_totala = line.split(',')
                    playlist = Playlist(playlist_id,titlu,data_creare,durata_totala)
                    lista_playlist.append(playlist)

        return lista_playlist

    def cautare(self,x):
        """
        Cauta un playlist pe baza unuia dintre cuvintele din titlu.
        :param x: cuvantul de cautat
        :type x: str
        :return: lista de obiecte Playlist ceruta
        :rtype: list
        """
        lista = self.load_from_file()
        lista_return = []
        for playlist in lista:
            cuvinte = []
            cuvinte = playlist.get_titlu().split(' ')
            for el in cuvinte:
                if el == x:
                    lista_return.append(playlist)

        return lista_return

    def cautare_id(self,x):
        """
        Cauta un playlist pe baza id-ului.
        :param x: id de cautat
        :type x: str
        :return: obiectul gasit, daca exista, None altfel
        :rtype: obiect de tip Playlist, daca exista, None altfel
        """   
        lista = self.load_from_file()
        for playlist in lista:
            if playlist.get_playlist_id() == x:
                return playlist

        return None

def test_cautare_id():
    repo_test = repo_playlist('./data/playlist_test.txt')

    assert(repo_test.cautare_id('CLS95').get_titlu() == 'Chill Classical Musicto Study')

    assert(repo_test.cautare_id('nu') == None)

test_cautare_id()

def test_cautare():
    repo_test = repo_playlist('./data/playlist_test.txt')

    assert(len(repo_test.cautare('Classical')) == 1)
    assert(repo_test.cautare('Classical')[0].get_titlu() == 'Chill Classical Musicto Study')

    assert(len(repo_test.cautare('nu')) == 0)

test_cautare()

def test_load_from_file():
    repo_test = repo_playlist('./data/playlist_test.txt')
    assert(len(repo_test.load_from_file()) == 3)
    assert(repo_test.load_from_file()[0].get_titlu() == 'Top 40 Hits')

test_load_from_file()

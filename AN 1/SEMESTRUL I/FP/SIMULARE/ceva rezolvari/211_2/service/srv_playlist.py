import os
import sys
sys.path.append(os.getcwd() + '\\')

from repo.repo_playlist import *
from datetime import timedelta,datetime

class srv_playlist:
    def __init__(self,repo_playlist):
        self.__repo_playlist = repo_playlist

    def cautare(self,x):
        """
        Cauta un playlist pe baza unuia dintre cuvintele din titlu.
        :param x: cuvantul de cautat
        :type x: str
        :return: lista de obiecte Playlist ceruta
        :rtype: list
        """
        lista = []
        lista = self.__repo_playlist.cautare(x)
        return lista

    def evaluare(self,id,start,end):
        """
        Evalueaza posibilitatea ascultarii in intregime a unui playlist pe baza orei de inceput si de final.
        :param id: id-ul playlist-ului de evaluat
        :type id: str
        :param start: inceputul timpului disponibil
        :type start: str
        :param end: sfarsitul timpului disponibil
        :type end: str
        :return: obiect de tip Listen, daca exista obiectul de tip Playlist cautat in lista, None altfel
        :rtype: Listen/None
        """
        playlist = self.__repo_playlist.cautare_id(id)
        if(playlist != None):
            durata_playlist = int(playlist.get_durata_totala())
            ora_in = datetime.strptime(start,'%H:%M')
            ora_out = datetime.strptime(end,'%H:%M')

            time_diff = ora_out - ora_in
            total_seconds=time_diff.total_seconds()

            if(durata_playlist > total_seconds):
                evaluare = Listen(playlist,ora_in,ora_out,'Sorry, too little time!')
            else:
                evaluare = Listen(playlist,ora_in,ora_out,'Good to go, enjoy!')
            
            return evaluare

        else:
            return None

def test_cautare():
    repo_test = repo_playlist('./data/playlist_test.txt')
    srv_test = srv_playlist(repo_test)

    assert(len(srv_test.cautare('Classical')) == 1)
    assert(srv_test.cautare('Classical')[0].get_titlu() == 'Chill Classical Musicto Study')

    assert(len(srv_test.cautare('nu')) == 0)

test_cautare()

def test_evaluare():
    repo_test = repo_playlist('./data/playlist_test.txt')
    srv_test = srv_playlist(repo_test)

    assert(srv_test.evaluare('RCK01','14:34','14:35').get_is_playable() == 'Sorry, too little time!')
    assert(srv_test.evaluare('RCK01','14:34','14:39').get_is_playable() == 'Good to go, enjoy!')

test_evaluare()
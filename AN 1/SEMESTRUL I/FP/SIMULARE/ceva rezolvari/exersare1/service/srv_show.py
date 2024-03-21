import os
import sys
sys.path.append(os.getcwd() + '\\')

from repo.repo_show import *

class srv_show:
    def __init__(self,repo_show):
        self.__repo_show = repo_show

    def cautare(self,x):
        """
        Cauta dupa gen un show.
        :param x: genul de cautat
        :type x: str
        :return: o lista cu toate show-urile apartinand genului respectiv
        """
        lista = []
        lista = self.__repo_show.cautare(x)
        return lista
    
    def preferinta(self,id,nr_eps):
        """
        Calculeaza preferinta utilizatorului pt un serial, pe baza nr de eps vazute
        :param id: id serial
        :param nr_eps: nr de episoade vizionate
        """
        show = self.__repo_show.cautare_id(id)
        nr_total_eps = show.get_nr_eps()

        if int(nr_eps) >= float((2/3) * int(nr_total_eps)):
            string = 'favorit'
        elif int(nr_eps) >= float((1/3) * int(nr_total_eps)):
            string = 'if_bored'
        else:
            string = 'disliked'

        preferinta = ShowEvaluation(show,nr_eps,string)

        return preferinta,show.get_gen()


def test_preferinta():
    repo_test = repo_show('./data/show_test.txt')
    srv_test = srv_show(repo_test)

    preferinta,gen = srv_test.preferinta('COM1',3)
    assert(preferinta.get_get_preference() == 'disliked')
    preferinta,gen = srv_test.preferinta('CD32',25)
    assert(preferinta.get_get_preference() == 'favorit')
    preferinta,gen = srv_test.preferinta('FAN4',4)
    assert(preferinta.get_get_preference() == 'if_bored')

test_preferinta()

def test_cautare():
    repo_test = repo_show('./data/show_test.txt')
    srv_test = srv_show(repo_test)

    assert(len(srv_test.cautare('fantasy')) == 2)
    assert(srv_test.cautare('fantasy')[0].get_titlu() == 'The Good Place')

test_cautare()


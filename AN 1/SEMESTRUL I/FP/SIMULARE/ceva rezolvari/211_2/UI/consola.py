import os
import sys
sys.path.append(os.getcwd() + '\\')
from service.srv_playlist import *
from termcolor import *

class consola:
    def __init__(self,srv_playlist):
        self.__srv_playlist = srv_playlist

    def cautare(self):
        """
        Cauta in lista de playlist-uri pe cele care contin in titlu un anumit cuvant (cuvant).
        """
        cuvant = input('Introduceti cuvantul de cautat: ')
        lista = self.__srv_playlist.cautare(cuvant)
        if len(lista) == 0:
            print(colored('Nu exista asemenea playlist-uri in lista.','red'))
        else:
            for el in lista:
                print(colored(el,'cyan'))

    def evaluare(self):
        """
        Evalueaza daca un anumit playlist poate sa fie ascultat in intregime sau nu, pe baza unei ore de intrare si a unei ore de iesire.
        """
        id = input('Introduceti id-ul playlist-ului: ')
        start = input('Introduceti ora de start: ')
        end = input('Introduceti ora de sfarsit: ')
        if self.__srv_playlist.evaluare(id,start,end) != None:
            print(colored(self.__srv_playlist.evaluare(id,start,end).get_is_playable(),'cyan'))
        else:
            print(colored('Nu s-a gasit in lista playlist-ul in lista.','red'))

    def start(self):
        while True:
            print()
            print('Optiunile disponibile sunt: cautare, evaluare, exit.')
            cmd = input('Optiunea dvs este: ')
            if cmd == 'cautare':
                self.cautare()
            elif cmd == 'evaluare':
                self.evaluare()
            else:
                return
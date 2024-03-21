import os
import sys
sys.path.append(os.getcwd() + '\\')

class consola:
    def __init__(self,srv_show):
        self.__srv_show = srv_show

    def cautare(self):
        gen = input('Dati genul de cautat: ')
        lista = self.__srv_show.cautare(gen)
        for el in lista:
            print(el)
    
    def preferinta(self):
        id = input('Introduceti id-ul show-ului: ')
        nr_eps = int(input('Introduceti nr eps vizionate: '))
        preferinta,gen = self.__srv_show.preferinta(id,nr_eps)
        print('Titlu' + str(preferinta.get_TvShow().get_titlu()) + ', Gen: ' + str(gen) + ', Preferinta: ' + preferinta.get_get_preference())


    def start(self):
        while True:
            print()
            print('Comenzile disponibile sunt: cautare, preferinta.')
            cmd = input('Comanda dvs este: ')

            if cmd == 'cautare':
                self.cautare()

            elif cmd == 'preferinta':
                self.preferinta()
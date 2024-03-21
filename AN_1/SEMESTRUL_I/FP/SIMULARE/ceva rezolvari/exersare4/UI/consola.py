class consola:
    def __init__(self,srv_firma):
        self.__srv_firma = srv_firma

    def __cautare(self):
        cuvant = input('Introduceti un sir de cautat: ')
        lista = self.__srv_firma.cautare(cuvant)
        for el in lista:
            print(el)
        if len(lista) == 0:
            print('Nicio firma nu contine acest string.')

    def __venituri_dupa_impozitare(self):
        cui = input('Introduceti codul de identificare al firmei: ')
        procent = int(input('Introduceti procentul de impozitare: '))
        rez = self.__srv_firma.impozitare(cui,procent)
        print(rez)

    def start(self):
        while True:
            print()
            print('Comenzile disponibile sunt: cautare, venituri_dupa_impozitare.')
            cmd = input('Comanda dvs este: ')
            if cmd == 'cautare':
                self.__cautare()
            elif cmd == 'venituri_dupa_impozitare':
                self.__venituri_dupa_impozitare()
            else:
                print('Comanda invalida.')
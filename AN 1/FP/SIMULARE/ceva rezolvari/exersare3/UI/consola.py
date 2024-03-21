class consola:
    def __init__(self,srv_parcare):
        self.__srv_parcare = srv_parcare

    def __cautare(self):
        strada = input('Dati strada de cautat: ')
        lista = self.__srv_parcare.cautare(strada)
        if len(lista) == 0:
            print('Nu exista masini pe aceasta strada.')
        else:
            for el in lista:
                print(el)
    def __raport(self):
        lista = self.__srv_parcare.raport()
        for el in lista:
            print(el)

    def start(self):
        while True:
            print()
            print('Comenzile disponibile sunt: cautare, raport.')
            cmd = input('Comanda dvs este: ')
            if cmd == 'cautare':
                self.__cautare()
            elif cmd == 'raport':
                self.__raport()
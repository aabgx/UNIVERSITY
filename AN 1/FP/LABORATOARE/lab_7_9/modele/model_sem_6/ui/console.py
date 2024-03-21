from termcolor import colored


class Console:
    def __init__(self, srv):
        """
        Initializeaza consola
        :type srv: ShowService
        """
        self.__srv = srv

    def __print_all_shows(self):
        """
        Afiseaza toate serialele disponibile

        """
        show_list = self.__srv.get_all_shows()
        if len(show_list) == 0:
            print('Nu exista seriale in lista.')
        else:
            print('Lista de seriale este:')
            for show in show_list:
                # print(show)
                print(
                    'Titlu serial: ', colored(show.getTitle(), 'cyan'), ' - An aparitie: ',
                    colored(str(show.getAnAparitie()),
                            'cyan'), ' - Nr. episoade: ', colored(str(
                        show.getEpisoade()), 'cyan'))

    def __add_show(self):
        """
        Adauga un serial cu datele citite de la tastatura
        """
        titlu = input("Titlul serialului:")
        try:
            an_aparitie = int(input("Anul aparitiei:"))
            eps = int(input("Numar de episoade difuzate:"))
        except ValueError:
            print(colored('Anul aparitiei si nr. episoade trebuie sa fie un numar.', 'red'))
            return

        try:
            added_show = self.__srv.add_show(titlu, an_aparitie, eps)
            print('Serialul ' + added_show.getTitle() + ' (' + str(
                added_show.getAnAparitie()) + ') a fost adaugat cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_shows(self):
        pass

    def show_ui(self):
        # command-driven menu (just to practice something different)
        # Lab7-9 oricare varianta (print-menu + optiuni/comenzi) este ok
        # dar si la comenzi sa existe un user guide, ce comenzi sunt dispobibile, cum le folosim
        while True:
            print('Comenzi disponibile: add, delete, show_all, undo, exit')
            cmd = input('Comanda este:')
            cmd = cmd.lower().strip()
            if cmd == 'add':
                self.__add_show()
            elif cmd == 'show_all':
                self.__print_all_shows()
            elif cmd == 'exit':
                return
            else:
                print(colored('Comanda invalida.', 'red'))

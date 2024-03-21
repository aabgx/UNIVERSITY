class Playlist:
    def __init__(self,playlist_id,titlu,data_creare,durata_totala):
        """
        Creaza un obiect de tip Playlist cu atributele playlist_id,titlu,data_creare,durata_totala.
        :param playlist_id: id-ul unic al obiectului
        :type playloist_id: str
        :param titlu: titlul playlist-ului
        :type titlu: str
        :param data_creare: data de creare a playlist-ului (zz.ll.aaaa)
        :type data_creare: str
        :param durata_totala: durata playlist-ului (in secunde)
        :type durata_totala: int
        """
        self.__playlist_id = playlist_id
        self.__titlu = titlu
        self.__data_creare = data_creare
        self.__durata_totala = durata_totala

    def get_playlist_id(self):
        return self.__playlist_id

    def get_titlu(self):
        return self.__titlu

    def get_data_creare(self):
        return self.__data_creare

    def get_durata_totala(self):
        return self.__durata_totala

    def __str__(self):
        return 'ID: '+self.__playlist_id+', Tilu:'+self.__titlu+', Data creare: '+self.__data_creare+', Durata: '+self.__durata_totala

class Listen:
    def __init__(self,playlist,start_interval,stop_interval,is_playable):
        """
        Creaza un obiect de tip Listen cu playlist,start_interval,stop_interval,is_playable.
        :param playlist: un obiect de tip Playlist caracterizat prin atributele noului obiect
        :type playlist: Playlist
        :param start_interval: ora de inceput disponibila
        :type start_interval: str
        :param stop_interval: ora de final disponibila
        :type stop_interval: str
        :param is_playable: concluzia (daca playlist-ul poate fi redat in intregime sau nu)
        :type is_playable: str
        
        """
        self.__playlist = playlist
        self.__start_interval = start_interval
        self.__stop_interval = stop_interval
        self.__is_playable = is_playable

    def get_playlist(self):
        return self.__playlist

    def get_start_interval(self):
        return self.__start_interval

    def get_stop_interval(self):
        return self.__stop_interval

    def get_is_playable(self):
        return self.__is_playable

    def __str__(self):
        return 'Playlist: '+ self.__playlist.get_playlist_id() + ', Start: '+str(self.__start_interval)+', Stop: '+str(self.__stop_interval)+', Concluzie: '+self.__is_playable

def test_creare_playlist():
    playlist = Playlist('RCK01','Epic ballads','18/08/2019',120)
    assert(playlist.get_playlist_id() == 'RCK01')
    assert(playlist.get_titlu() == 'Epic ballads')
    assert(playlist.get_data_creare() == '18/08/2019')
    assert(playlist.get_durata_totala() == 120)

test_creare_playlist()

def test_creare_listen():
    playlist = Playlist('RCK01','Epic ballads','18/08/2019',120)
    listen = Listen(playlist,'14:34','14:56','Sorry, too little time!')
    assert(listen.get_playlist().get_titlu() == 'RCK01')
    assert(listen.get_start_interval() == '14:34')
    assert(listen.get_stop_interval() == '14:56')
    assert(listen.get_is_playable() == 'Sorry, too little time!')

test_creare_playlist()
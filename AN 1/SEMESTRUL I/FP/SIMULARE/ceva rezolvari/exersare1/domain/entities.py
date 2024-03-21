class TvShow:
    def __init__(self,id,titlu,gen,nr_eps):
        self.__id = id
        self.__titlu = titlu
        self.__gen = gen
        self.__nr_eps = nr_eps

    def get_id(self):
        return self.__id

    def get_titlu(self):
        return self.__titlu

    def get_gen(self):
        return self.__gen

    def get_nr_eps(self):
        return self.__nr_eps

    def __str__(self):
        return 'ID: ' + self.__id + ', ' + 'Titlu: ' + self.__titlu + ', ' + 'Gen: ' + self.__gen + ', ' + 'Nr. episoade: ' + self.__nr_eps

class ShowEvaluation:
    def __init__(self, TvShow, nr_ore, get_preference):
        self.__TvShow = TvShow
        self.__nr_ore = nr_ore
        self.__get_preference = get_preference
    
    def get_TvShow(self):
        return self.__TvShow

    def get_nr_ore(self):
        return self.__nr_ore
    
    def get_get_preference(self):
        return self.__get_preference

    def __str__(self):
        return 'Show: ' + self.__TvShow.get_titlu() + ', ' + 'Preferinta: ' + self.__get_preference

def test_creare_TvShow():
    show = TvShow('COM1','TheGoodPlace','fantasy comedy',53)
    assert(show.get_id() == 'COM1')
    assert(show.get_titlu() == 'TheGoodPlace')
    assert(show.get_gen() == 'fantasy comedy')
    assert(show.get_nr_eps() == 53)

test_creare_TvShow()

def test_creare_evaluare():
    show = TvShow('COM1','TheGoodPlace','fantasy comedy',53)
    evaluare = ShowEvaluation(show,47,'favorit')
    assert(evaluare.get_TvShow().get_id() == 'COM1')
    assert(evaluare.get_nr_ore() == 47)
    assert(evaluare.get_get_preference() == 'favorit')

test_creare_evaluare()

class loc_parcare:
    def __init__(self,id,nume,strada,nr_utilizari):
        """
        Creeaza un obiect de tip loc_parcare cu id, nume, strada si nr_utilizari
        :param id: id unic pt fiecare loc de parcare
        :type id: str
        :param nume: numele locului de parcare
        :type nume: str
        :param strada: numele strazii pe care se afla parcarea
        :type strada: str
        :param nr_utilizari: nr. de utilizari ale locului de parcare
        :type nr_utilizari: int
        """

        self.__id = id
        self.__nume = nume
        self.__strada = strada
        self.__nr_utilizari = nr_utilizari

    def get_id(self):
        return self.__id

    def get_nume(self):
        return self.__nume

    def get_strada(self):
        return self.__strada

    def get_nr_utilizari(self):
        return self.__nr_utilizari
    
    def __str__(self):
        return 'id: ' + self.__id + ',' + 'nume: ' + self.__nume + ',' + 'strada: ' + self.__strada + ',' + 'nr_utilizari: ' + str(self.__nr_utilizari)

def test_creare_entitate():

    parcare = loc_parcare('97','W903','Dorobantilor',9)
    assert(parcare.get_id() == '97')
    assert(parcare.get_nume() == 'W903')
    assert(parcare.get_strada() == 'Dorobantilor')
    assert(parcare.get_nr_utilizari() == 9)

test_creare_entitate()
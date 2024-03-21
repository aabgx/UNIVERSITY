class Firma:
    def __init__(self, cui, nume, sold):
        """
        Creaza un obiect de tip Firma cu cui, nume, sold.
        :param cui: codul unic de identificare al firmei
        :type cui: int
        :param nume: numele firmei
        :type nume: str
        :param sold: sold-ul curent al firmei
        :type sold: int
        """
        self.__cui = cui
        self.__nume = nume
        self.__sold = sold

    def get_cui(self):
        return self.__cui

    def get_nume(self):
        return self.__nume

    def get_sold(self):
        return self.__sold

    def __str__(self):
        return 'CUI: ' + str(self.__cui) + ', ' + 'nume: ' + self.__nume + ', ' + 'sold: ' + str(self.__sold)

class Impozitare:
    def __init__(self, firma, procent_impozitare, get_sold_after_taxes):
        """
        Creaza un obiect de tip Impozitare cu firma, procent_impozitare, get_sold_after_taxes.
        :param firma: obiect de tip Firma
        :type firma: Firma
        :param procent_impozitare: procent de impozitarele al firmei
        :type procent_impozitare: int
        :param get_sold_after_taxes: metoda de calcul a sold-ului ramas 
        :type get_sold_after_taxes: float
        """
        self.__firma = firma
        self.__procent_impozitare = procent_impozitare
        self.__get_sold_after_taxes = get_sold_after_taxes

    def get_firma(self):
        return self.__firma

    def get_procent_impozitare(self):
        return self.__procent_impozitare

    def get_sold_after_taxes(self):
        return self.__get_sold_after_taxes

    def __str__(self):
        return 'firma: ' + str(self.__firma) + ', ' + 'get_sold_after_taxes: ' + str(self.__get_sold_after_taxes)

def test_creare_firma():
    firma1 = Firma(6859662, 'Termoline', 5000)
    assert(firma1.get_cui() == 6859662)
    assert(firma1.get_nume() == 'Termoline')
    assert(firma1.get_sold() == 5000)

test_creare_firma()

def test_creare_impozit():
    firma1 = Firma(6859662, 'Termoline', 5000)
    impozit1 = Impozitare(firma1, 50, 2500)
    assert(impozit1.get_firma() == firma1)
    assert(impozit1.get_procent_impozitare() == 50)
    assert(impozit1.get_sold_after_taxes() == 2500)

test_creare_impozit()
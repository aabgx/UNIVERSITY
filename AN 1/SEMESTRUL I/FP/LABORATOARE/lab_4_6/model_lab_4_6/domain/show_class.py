class Serial:
    def __init__(self, titlu, an_aparitie, eps):
        self.__title = titlu
        self.__an_ap = an_aparitie
        self.__eps = eps

    def getTitle(self):
        return self.__title


show1 = Serial('See', 2019, 12)
show1.getTitle()
show2 = Serial('Squid Game', 2021, 9)

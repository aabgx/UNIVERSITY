#AF:
#stari_initiale: list<str>
#alfabet: list<str>
#tranzitii: list<tuple<str,str,str>>
#stari_finale: list<str>
#determinist: boolean
class AF:
    def __init__(self, stari_initiale, alfabet, tranzitii, stari_finale):
        self.stari_initiale = stari_initiale
        self.alfabet = alfabet
        self.tranzitii = tranzitii
        self.stari_finale = stari_finale
        self.determinist = self.verificare_automat_finit_determinist()

    def verificare_automat_finit_determinist(self):
        #verific daca exista 2 in lista de tranzitii care au acelesi pe primele 2 pozitii
        for tranzitie1 in self.tranzitii:
            for tranzitie2 in self.tranzitii:
                if tranzitie1[0] == tranzitie2[0] and tranzitie1[1] == tranzitie2[1] and tranzitie1[2] != tranzitie2[2]:
                    return False
        return True

    def afisare(self):
        print("Stări inițiale:", self.stari_initiale)
        print("Alfabet:", self.alfabet)
        print("Tranziții:", self.tranzitii)
        print("Stări finale:", self.stari_finale)

    def este_acceptata_seq(self,seq):
        stare_curenta = self.stari_initiale[0]
        for litera in seq:
            ok=0
            for tranzitie in self.tranzitii:
                if tranzitie[0] == stare_curenta and tranzitie[1] == litera:
                    stare_curenta=tranzitie[2]
                    ok=1 
                    break
            if ok==0:
                return False
        if stare_curenta in self.stari_finale:
            return True
        else:
            return False
            
    def ce_mai_lunga_subseq(self,cuvant):
        sq=""
        cea_mai_lunga_seq = ""
        if (self.determinist):
            for litera in cuvant:
                sq += litera
                if (self.este_acceptata_seq(sq)):
                    cea_mai_lunga_seq = sq
                    
            if cea_mai_lunga_seq == cuvant:
                return "\033[92mCuvant acceptat!\033[0m"
            else:
                return "\033[91mCuvant respins!\nCel mai lung prefix acceptat: "+cea_mai_lunga_seq+"\033[0m"
        else:
            return "\033[91mAutomatul nu este determinist!\033[0m"

    
    def afisare_stari_initiale(self):
        print("Stări inițiale:", self.stari_initiale)

    def afisare_alfabet(self):
        print("Alfabet:", self.alfabet)
    
    def afisare_stari_finale(self):
        print("Stări finale:", self.stari_finale)

    def afisare_tranzitii(self):
        print("Tranziții:", self.tranzitii)

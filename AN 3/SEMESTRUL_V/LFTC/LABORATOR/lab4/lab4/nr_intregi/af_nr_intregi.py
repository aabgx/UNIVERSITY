#AfNrIntregi:
#stari_initiale: list<str>
#alfabet: list<str>
#tranzitii: list<tuple<str,str,str>>
#stari_finale: list<str>
#determinist: boolean
class AfNrIntregi:
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


def main():
    #citire fisier intrare
    tranzitii=[]

    print("\033[96mCUM DORIȚI SĂ CITIȚI AUTOMATUL?")
    print("1. TASTATURĂ")
    print("2. FIȘIER\033[0m\n")
    op=input("Introduceți opțiunea: ")

    if op=="2":
        with open("nr_intregi/af_nr_intregi.txt", "r") as file:
        # with open("nr_intregi/af_lab.txt", "r") as file:
            index_line=0
            for line in file:
                line = line.rstrip("\n")
                if index_line==0:
                    stari_initiale=line.split(",")
                elif index_line==1:
                    alfabet=line.split(",")
                elif index_line==2:
                    stari_finale=line.split(",")
                else:
                    tranzitii.append(line.split(" "))
                index_line+=1
    elif op=="1":
        print("\033[96m\nCITIRE STĂRI INITIALE\033[0m")
        stari_initiale = input("Introduceți stările inițiale separate prin virgulă: ").split(",")

        print("\033[96m\nCITIRE ALFABET\033[0m")
        alfabet = input("Introduceți alfabetul automatului: ").split(",")

        print("\033[96m\nCITIRE STĂRI FINALE\033[0m")
        stari_finale = input("Introduceți stările finale separate prin virgulă: ").split(",")

        print("\033[96m\nCITIRE TRANZIȚII\033[0m")
        print("Introduceți tranzitii de forma: stare1 componenta_alfabet stare2")
        print("Introduceți 'stop' pentru a opri citirea tranzitiilor")
        while (1):
            tranzitie = input("Introduceți tranzitie: ")
            if tranzitie == "stop":
                break
            tranzitii.append(tranzitie.split(" "))
    else:
        print("\033[91mOpțiune invalidă!\033[0m")
        exit()

    #creare automat
    automat = AfNrIntregi(stari_initiale, alfabet, tranzitii, stari_finale)

    while (1):
        print("\033[96m\nMENIU:\n")
        print("1. AFISARE STARI INITIALE")
        print("2. AFISARE ALFABET")
        print("3. AFISARE STARI FINALE")
        print("4. AFISARE TRANZITII")
        print("5. VERIFICARE CUVANT\033[0m\n\n")

        optiune = input("Introduceti optiunea: ")

        if optiune == "1":
            automat.afisare_stari_initiale()
        elif optiune == "2":
            automat.afisare_alfabet()
        elif optiune == "3":
            automat.afisare_stari_finale()
        elif optiune == "4":
            automat.afisare_tranzitii()
        elif optiune == "5":
            cuvant = input("Introduceti cuvantul: ")
            rezultat = automat.ce_mai_lunga_subseq(cuvant)
            print(rezultat)
        else:
            print("\033[91mOptiune invalida!\033[0m")

main()

        

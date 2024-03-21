import copy
from colorama import Fore, Style
from tabulate import tabulate
from Grammar import Grammar

def init_grammar(filename):
    gr = Grammar()
    gr.parse_grammar(filename)
    return gr

class LL1:
    def __init__(self,file_grammar):
        self.__grammar = init_grammar(file_grammar)
        self.__can_accept_sequence = True
        self.__first = self.calculate_first_sets()
        self.__ll1_table = self.create_table_ll1()

    def calculate_first_for_one_terminal(self, neterminal):
        #calculated ca sa nu fac de mai multe ori pt acelasi simbol din dreapta (ex apare E->T si E->TS)
        calculated = set()
        #in stiva punem neterminalul pentru care calculam first
        stack = [neterminal]
        first_set = set()

        while stack:
            current_symbol = stack.pop()

            #daca e deja calculat, sarim la urmatorul pas din while
            if current_symbol in calculated:
                continue
            calculated.add(current_symbol)

            #luam productiile neterminalului curent
            productions = self.__grammar.get_productions_dict().get(current_symbol, [])

            for production in productions:
                production = production.split()

                #dacă primul simbol este neterminal il adaugam in stiva (inseamna ca mai avem de adaugat la first)
                if production[0][0].isupper():
                    stack.append(production[0])
                #altfel adaugam primul simbol din productie in first
                else:
                        first_set.add(production[0])

        return first_set

    #calculez first pentru toate neterminalele
    def calculate_first_sets(self):
        first_sets = {}
        for neterminal in self.__grammar.get_non_terminals():
            first_sets[neterminal] = list(self.calculate_first_for_one_terminal(neterminal))
        return first_sets
    
    def calculate_follow_sets(self):
        follow_sets = {neterminal: set() for neterminal in self.__grammar.get_non_terminals()}
        follow_sets[self.__grammar.get_start()].add('$')  #adăugăm $ la follow pentru simbolul de start

        #regula 1: daca dupa neterminal urmeaza un terminal, il adaugam la follow de acel neterminal
        #regula 2: daca dupa neterminal1 urmeaza un neterminal2, adaugam first de acel neterminal2 la follow de neterminal1 (fara eps)
        #regula 3: daca dupa neterminal1 urmeaza neterminal2 si first de neterminal2 contine epsilon, atunci adaugam follow de neterminal1 la follow de neterminal2
        #regula 4: daca neterminal este ultimul din dreapta, adaugam follow de neterminal la follow de neterminalul din stanga
        
        #pt ca eu am de adaugat follow la unele neterminale care poate nu e gata de calculat
        change = True
        while change:
            change = False
            #regula 1
            for neterminal in self.__grammar.get_non_terminals():
                for production in self.__grammar.get_productions_dict()[neterminal]:
                    production  = production.split()
                    for i in range(len(production) - 1):
                        if production[i][0].isupper() and not production[i + 1][0].isupper():                       
                            aux = set(copy.deepcopy(follow_sets[production[i]]))
                            follow_sets[production[i]].add(production[i + 1])

                            if aux != follow_sets[production[i]]:
                                change = True

            #regula 2
            for neterminal in self.__grammar.get_non_terminals():
                for production in self.__grammar.get_productions_dict()[neterminal]:
                    production  = production.split()
                    for i in range(len(production) - 1):
                        if production[i][0].isupper() and production[i + 1][0].isupper():
                            aux = set(copy.deepcopy(follow_sets[production[i]]))

                            follow_sets[production[i]].update(self.__first[production[i + 1]])

                            #sa nu adaug eps in follow
                            if "eps" in follow_sets[production[i]]:
                                follow_sets[production[i]].remove("eps")

                            if aux != follow_sets[production[i]]:
                                change = True
            #regula 3
            for neterminal in self.__grammar.get_non_terminals():
                for production in self.__grammar.get_productions_dict()[neterminal]:
                    production  = production.split()
                    for i in range(len(production) - 1):
                        if production[i][0].isupper() and production[i + 1][0].isupper():
                            if "eps" in self.__first[production[i + 1]]:
                                aux = set(copy.deepcopy(follow_sets[production[i]]))

                                follow_sets[production[i]].update(follow_sets[neterminal])

                                if aux != follow_sets[production[i]]:
                                    change = True
            #regula 4
            for neterminal in self.__grammar.get_non_terminals():
                for production in self.__grammar.get_productions_dict()[neterminal]:
                    production  = production.split()
                    if production[-1][0].isupper():
                        aux = set(copy.deepcopy(follow_sets[production[-1]]))

                        follow_sets[production[-1]].update(follow_sets[neterminal])

                        if aux != follow_sets[production[-1]]:
                            change = True
        return follow_sets
    
    #afisare first si follow frumos cu tabel
    def display_first_and_follow_sets(self):
        first_sets = self.__first
        follow_sets = self.calculate_follow_sets()

        col_width = 20

        def print_separator():
            print('-' * (3 * col_width + 4))

        def print_table_header():
            header = f"{'Neterminal':{col_width}}|{'First':{col_width}}|{'Follow':{col_width}}"
            print(header)

        def format_cell(value):
            return value[:col_width].ljust(col_width)

        # Afișare manuală a tabelului
        print_separator()
        print_table_header()
        print_separator()

        for neterminal in first_sets:
            first_str = ', '.join(map(str, first_sets[neterminal]))
            follow_str = ', '.join(map(str, follow_sets[neterminal]))

            neterminal = format_cell(neterminal)
            first_str = format_cell(first_str)
            follow_str = format_cell(follow_str)

            print(f"{neterminal}|{first_str}|{follow_str}")

        print_separator()


    #pun productiile plus a cata productie din lista de productii este
    #L -> S pe linia lui L si coloana lui first(S) unde S e primul simbol din productie (first(term)=term)
    #L -> eps pe linia lui L si coloana lui follow(L)
    def create_table_ll1(self):
        matrice = []

        #definim headere pentru linii si coloane
        coloane = list(self.__grammar.get_terminals()) + ['$']
        linii = list(self.__grammar.get_non_terminals()) + list(self.__grammar.get_terminals()) + ['$']
        coloane.remove("eps")
        linii.remove("eps")

        #initializam matricea cu None
        for _ in range(len(linii)):
            matrice.append([None] * len(coloane))

        #luam pe rand productiile
        nr_regula = 1
        for productie in self.__grammar.get_productions():
            left,right = productie.split("->")
            right = right.strip()
            left = left.strip()

            regula = [right,nr_regula]

            #productie L -> eps
            if right == "eps":
                follow = self.calculate_follow_sets()[left]
                line = linii.index(left)
                columns = [coloane.index(f) for f in follow if f != "eps"] #nu avem eps in coloane

                for column in columns:
                    if matrice[line][column] is not None:
                        matrice[line][column].append(regula)
                        print(f'{Fore.CYAN}line: {left},column: {column},   celula: {matrice[line][column]}{Style.RESET_ALL}')
                        self.__can_accept_sequence = False
                    else:
                        matrice[line][column] = [regula]

            #productie L -> S
            else:
                right = right.split()
                
                if right[0][0].isupper():
                    first = self.__first[right[0]]
                else:
                    first = [right[0]]
                    
                line = linii.index(left)
                columns = [coloane.index(f) for f in first if f != "eps"] #nu avem eps in coloane

                for column in columns:
                    if matrice[line][column] is not None:
                        matrice[line][column].append(regula)
                        print(f'{Fore.CYAN}line: {left},column: {column},   celula: {matrice[line][column]}{Style.RESET_ALL}')
                        self.__can_accept_sequence = False
                    else:
                        matrice[line][column] = [regula]

            nr_regula += 1
        
        #ramane sa adaugam pop la perechile de terminale egale si accept la perechea ($,$)
        for i in range(len(linii)):
            for j in range(len(coloane)):
                if linii[i]==coloane[j] and not linii[i].isupper() and linii[i]!='$':
                    matrice[i][j] = "pop"
                if linii[i]==coloane[j] and linii[i]=='$':
                    matrice[i][j] = "accept"

        return matrice
    
    def display_table_ll1(self,matrix):
        coloane = list(self.__grammar.get_terminals()) + ['$']
        linii = list(self.__grammar.get_non_terminals()) + list(self.__grammar.get_terminals()) + ['$']
        if "eps" in coloane:
            coloane.remove("eps")
        if "eps" in linii:
            linii.remove("eps")

        # Transformăm elementele în șiruri separate prin virgulă
        formatted_matrix = [[', '.join(map(str, val)) if isinstance(val, list) else val for val in row] for row in matrix]

        table = {'': linii, **dict(zip(coloane, zip(*formatted_matrix)))}
        print(tabulate(table, headers='keys', tablefmt='fancy_grid'))

    def verify_sequence(self,sequence):
        if not self.__can_accept_sequence:
            print(f'{Fore.RED}CONFLICT! CAN NOT VERIFY SEQUENCE!{Style.RESET_ALL}')
            return False
        print(f'{Fore.CYAN}STEPS FOR VERYFING {sequence}:{Style.RESET_ALL}')

        coloane = list(self.__grammar.get_terminals()) + ['$']
        linii = list(self.__grammar.get_non_terminals()) + list(self.__grammar.get_terminals()) + ['$']
        if "eps" in coloane:
            coloane.remove("eps")
        if "eps" in linii:
            linii.remove("eps")

        sequence = sequence.split()
        sequence.append("$")
        stack = ["$", self.__grammar.get_start()]

        while stack:
            #asta ma ajuta sa afisez pasii de lucru
            sequence_print = str(sequence)
            stack_print = str(stack[::-1])
            regula_print = ""

            current_symbol = stack.pop()

            #daca avem in capul stivei terminalul din secventa, il scoatem din secventa
            if current_symbol == sequence[0] and current_symbol != "$":
                sequence = sequence[1:]
                regula_print = "pop"

            #daca avem in capul stivei un terminal diferit de cel din secventa -> nu e acc secventa
            elif current_symbol[0].islower():
                print(f'     ({sequence_print}, {stack_print})   {Fore.RED}NOT ACCEPTED!{Style.RESET_ALL}')
                return False
            
            #daca avem in capul stivei un neterminal cautam in matricea ll1 si facem push in stiva
            elif current_symbol[0].isupper():
                try:
                    line = linii.index(current_symbol)
                    column = coloane.index(sequence[0])
                except ValueError:
                    print(f'     ({sequence_print}, {stack_print})   {Fore.RED}NOT ACCEPTED! SYMBOL UNKNOWN!{Style.RESET_ALL}')
                    return False

                #daca nu avem regula in matricea ll1 -> nu e acc secventa
                if self.__ll1_table[line][column] is None:
                    print(f'     ({sequence_print}, {stack_print})   {Fore.RED}NOT ACCEPTED!{Style.RESET_ALL}')
                    return False
                
                #daca avem regula in matricea ll1 o punem in stiva in ordine inversa
                elif self.__ll1_table[line][column][0][0] != "eps":
                    regula = self.__ll1_table[line][column][0][0]
                    regula = regula.split()

                    for simbol in reversed(regula):
                        stack.append(simbol)

                #daca avem eps in matrice doar trebuia sa scoatem un elem din stiva, ceea ce am facut deja

            #afisare pasi de lucru
            if regula_print == "pop":
                print("     (" + sequence_print + ", " + stack_print + ") --"+regula_print+"-->")
            else:
                regula_print = "push" + str(self.__ll1_table[line][column][0][1])
                if stack_print == '[\'$\']' and sequence_print != '[\'$\']':
                    print(f'     ({sequence_print}, {stack_print})   {Fore.RED}NOT ACCEPTED!{Style.RESET_ALL}')
                    return False
                if sequence_print == '[\'$\']' and stack_print == '[\'$\']':
                    print(f'     ({sequence_print}, {stack_print})   {Fore.GREEN}ACCEPTED!{Style.RESET_ALL}')
                else:
                    print("     (" + sequence_print + ", " + stack_print + ") --"+regula_print+"-->")

        return True

    
def transform_fip_in_text(filename):
    text_final = ""
    with open(filename, 'r') as file:
        fip = file.read()
        fip = fip.split('\n')
        fip = [line.split() for line in fip]
        for line in fip:
            if "return" in line:
                atom = "return 0"
                pozitie_ts = line[3]
            else:
                atom = line[0]
                pozitie_ts = line[2]

            if pozitie_ts != '-1':
                if '0x' in atom or '0b' in atom:
                    atom = atom[0] + atom[1]+ " " + " ".join(list(atom[2:]))
                else:
                    atom = " ".join(list(atom))
                #print(f'{Fore.CYAN}atom: {atom}{Style.RESET_ALL}')
            text_final += atom + " "

    #print(f'{Fore.MAGENTA}text final: {text_final}{Style.RESET_ALL}')
    return text_final

        

if __name__ == "__main__":
    # ll1=LL1('verify_fip/grammars/rules_simple.txt')
    # ll1.display_first_and_follow_sets()
    # ll1.display_table_ll1(ll1.create_table_ll1())
    # ll1.verify_sequence("a + a")
    # ll1.verify_sequence("a + a + +")
    
    # ll1=LL1('verify_fip/grammars/rules_part.txt')
    # ll1.display_first_and_follow_sets()
    # #ll1.display_table_ll1(ll1.create_table_ll1())
    # ll1.verify_sequence("# include < iostream > # include < math.h > using namespace std ; int main ( ) { int a b c d ; for ( i =  0 7 6 ; i < 9 ; i ++ ) { while ( a < 0b 1 0 1 0 ) { a = a - 9 ; } a = 0x 7 5 f ; } return 0 ; }")

    # ll1=LL1('verify_fip/grammars/binary.txt')
    # ll1.display_first_and_follow_sets()
    # #ll1.display_table_ll1(ll1.create_table_ll1())
    # ll1.verify_sequence("0b 1 0 1 0")

    code = transform_fip_in_text('verify_fip/fip/fip.txt')
    ll1=LL1('verify_fip/grammars/rules.txt')
    #ll1.display_first_and_follow_sets()
    #ll1.display_table_ll1(ll1.create_table_ll1())
    ll1.verify_sequence(code)

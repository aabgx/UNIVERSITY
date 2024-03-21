from colorama import Fore, Style

class Grammar:
    def __init__(self):
        self.__nonTerminals = set()
        self.__terminals = set()
        self.__productions = set()
        self.__startSymbol = None

    def set_start(self, start):
        self.__startSymbol = start

    def get_start(self):
        return self.__startSymbol

    def get_nonTerminals(self):
        return self.__nonTerminals

    def get_terminals(self):
        return self.__terminals

    def get_productions(self):
        return self.__productions

    def set_non_terminals(self, nonT):
        self.__nonTerminals = nonT

    def set_terminals(self, t):
        self.__terminals = t

    def set_productions(self, prod):
        self.__productions = prod

    def parse_grammar(self,file_name):
        with open(file_name, 'r') as file:
            for line in file:
                line = line.strip()
                if not line:
                    continue

                self.__productions.add(line.strip())

                parts = line.split('->')
                stanga, dreapta = parts[0].strip(), parts[1].strip()

                if(self.__startSymbol is None):
                    self.__startSymbol = stanga[0]

                for caracter in stanga:
                    if caracter.islower():
                        self.__terminals.add(caracter)
                    elif caracter.isupper():
                        self.__nonTerminals.add(caracter)

                for caracter in dreapta:
                    if caracter.islower():
                        self.__terminals.add(caracter)
                    elif caracter.isupper():
                        self.__nonTerminals.add(caracter)
    
    def get_right_recursive(self):
        prods = []
        for p in self.__productions:
            ps = p.split('->')
            left_part = ps[0].strip()
            right_part = ps[1].strip()
            if left_part[0] == right_part[-1]:
                prods.append(p)
        return prods


if __name__ == "__main__":
    gr = Grammar()
    fileName = 'grammar.txt'
    gr.parse_grammar(fileName)
    print(f'{Fore.CYAN}Terminals:{Style.RESET_ALL} {gr.get_terminals()}')
    print(f'{Fore.CYAN}Nonterminals:{Style.RESET_ALL} {gr.get_nonTerminals()}')
    print(f'{Fore.CYAN}Start:{Style.RESET_ALL} {gr.get_start()}')
    print(f'{Fore.CYAN}Productions:{Style.RESET_ALL} {gr.get_productions()}')
    print(f'{Fore.CYAN}Productions right recursive:{Style.RESET_ALL} {gr.get_right_recursive()}')


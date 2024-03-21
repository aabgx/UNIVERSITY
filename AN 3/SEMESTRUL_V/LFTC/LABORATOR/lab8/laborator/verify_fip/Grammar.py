from colorama import Fore, Style

class Grammar:
    def __init__(self):
        self.__non_terminals = set()
        self.__terminals = set()
        self.__productions = []
        self.__start_symbol = None
        self.__productions_dict = {}

    def set_start(self, start):
        self.__start_symbol = start

    def get_start(self):
        return self.__start_symbol

    def get_non_terminals(self):
        return self.__non_terminals

    def get_terminals(self):
        return self.__terminals

    def get_productions(self):
        return self.__productions

    def set_non_terminals(self, nonT):
        self.__non_terminals = nonT

    def set_terminals(self, t):
        self.__terminals = t

    def set_productions(self, prod):
        self.__productions = prod

    def get_productions_dict(self):
        return self.__productions_dict

    #dictionar cu neterminale (cheie) si productiile acestora (valoare)
    def productions_dict(self):
        for production in self.__productions:
            parts = production.split('->')
            left, right = parts[0].strip(), parts[1].strip()
            if left not in self.__productions_dict:
                self.__productions_dict[left] = []
            self.__productions_dict[left].append(right)

    def parse_grammar(self, file_name):
        with open(file_name, 'r') as file:
            for line in file:
                line = line.strip()
                if not line:
                    continue

                self.__productions.append(line.strip())

                parts = line.split('->')
                left, right = parts[0].strip(), parts[1].strip()

                if self.__start_symbol is None:
                    self.__start_symbol = left

                # Adăugăm toate cuvintele ca simboluri, atât pentru neterminale, cât și pentru terminale
                for word in left.split():
                    self.__non_terminals.add(word)

                for word in right.split():
                    if word[0].isupper():
                        self.__non_terminals.add(word)
                    else:
                        self.__terminals.add(word)
        self.productions_dict()
                    


if __name__ == "__main__":
    gr = Grammar()
    fileName = 'verify_fip/grammars/rules_simple.txt'
    gr.parse_grammar(fileName)
    print(f'{Fore.CYAN}Terminals:{Style.RESET_ALL} {gr.get_terminals()}')
    print(f'{Fore.CYAN}Nonterminals:{Style.RESET_ALL} {gr.get_non_terminals()}')
    print(f'{Fore.CYAN}Start:{Style.RESET_ALL} {gr.get_start()}')
    print(f'{Fore.CYAN}Productions:{Style.RESET_ALL} {gr.get_productions()}')
    print(f'{Fore.CYAN}Productions dict:{Style.RESET_ALL} {gr.get_productions_dict()}')
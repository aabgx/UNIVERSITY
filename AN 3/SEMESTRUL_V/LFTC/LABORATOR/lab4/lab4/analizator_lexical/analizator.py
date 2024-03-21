import re
from prettytable import PrettyTable 

#definim cum ar trebui să arate operatorii, separatorii și keyword-urile
keywords = ["main", "while","using","namespace","std","cin","cout","for","if","include","int","double","iostream"]
operators = ["*", "+", "-", "/", "%","<",">","=","==","!=","<=",">=","&&","||","!","<<",">>","++","--","+=","-=","*=","/=","%=","&","|"]
separators = ["{","}" ,"(", ";", ",", ")", ":", "#",'\n']

tip_atom = ["identificator", "constanta","main", "while","using","namespace","std","cin","cout","for","if","include","int","double","iostream", "*", "+", "-", "/", "%","<",">","=","==","!=","<=",">=","&&","||","!","<<",">>","++","--","+=","-=","*=","/=","%=","&","|","{","}" ,"(", ";", ",", ")", ":", "#"]
cod = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44,45,46,47,48]
dict_tip_cod = dict(zip(tip_atom, cod))

#lista de tupluri pentru tabela de simboluri
tabela_simboluri = []

#FIP - tot o lista de tupluri
fip = []

#regex sa faca match pe identificatori de maxim 8 chr ce contine doar litere
variable_regex= r"[a-z]{1,8}$"
binar_regex=r"0b[01]+$"
octal_regex=r"0[0-7]+$"
hexa_regex=r"0x[0-9A-E]+$"

def add_to_FIP(token):
    atom_type = classify_token(token, keywords, operators, separators)

    #verific pe ce pozitie e tokenul in tip_atom
    if atom_type == "identifiers":
        index_tip_atom = 0
    elif atom_type == "constants":
        index_tip_atom = 1
    else:
        index_tip_atom = 0
        for elem in tip_atom:
            if elem == token:
                break
            else:
                index_tip_atom += 1

    #caut in tabela de simboluri tokenul si pun perechea lui
    ok=0
    for elem in tabela_simboluri:
        if elem[0] == token:
            fip.append((index_tip_atom, elem[1]))
            ok=1
            break
    if ok==0:
        fip.append((index_tip_atom, -1))

def classify_token(token, keywords, operators, separators):
    if token in keywords:
        return "keywords"
    elif token in operators:
        return "operators"
    elif token in separators:
        return "separators"
    elif is_constant(token) or re.match(binar_regex,token) or re.match(octal_regex,token) or re.match (hexa_regex,token):
        return "constants"
    elif re.match(variable_regex,token):
        return "identifiers"
    else:
        return "error"
    
#verificare daca un token este constanta
def is_constant(token):
    try:
        #incercam sa convertim in nr intreg sau cu virgula
        float(token)
        return True
    except ValueError:
        #daca a esuat, incercam sa convertin in string cu " la inceput si sfarsit
        if token[0] == '"' and token[-1] == '"':
            return True
        return False

def main():
    index_tabela_simboluri=0

    #setăm un dictionar gol, cu cheile de care avem nevoie
    atom_dict = {
        "keywords": set(),
        "operators": set(),
        "separators": set(),
        "identifiers": set(),
        "constants": set()
    }

    #ca să pot ridica excepție dacă am ceva ce nu convine regulilor
    try:
        with open("code.txt", "r") as file:
            index_line = 0
            for line in file:
                token = ""
                i=0 #index curent
                index_line += 1
                while i<len(line):
                    char = line[i]

                    #verificăm dacă am ajuns la un final de cuvânt
                    if char.isspace() or (char == ("\n")) or char in separators or char in operators:
                        #vedem în ce categorie de atom se clasifică cuvântul găsit și adăugăm la dicționar
                        if token:
                            atom_type = classify_token(token, keywords, operators, separators)
                            if(atom_type != "error"):
                                atom_dict[atom_type].add(token)

                                #daca e identificator sau constanta, il adaugam in tabela de simboluri - lexicografic
                                if atom_type in ["identifiers", "constants"]:
                                    gasit = any(token == item[0] for item in tabela_simboluri)

                                    if not gasit:
                                        index_inserare = 0
                                        while index_inserare < len(tabela_simboluri) and token > tabela_simboluri[index_inserare][0]:
                                            index_inserare += 1

                                        tabela_simboluri.insert(index_inserare, (token, index_tabela_simboluri))
                                        index_tabela_simboluri += 1

                                #adaugam in FIP
                                add_to_FIP(token)

                            else:
                                raise Exception("ERROR AT LINE " + str(index_line) + " : " + token + " is not a valid token!")
                        token = ""
    
                        #vedem dacă separatorul tocmai găsit nu e de fapt ceva din lista de atomi (operator sau separator)
                        have_atom_type = classify_token(char,keywords,operators,separators)
                        if(have_atom_type and char!=" " and char!="\n"):
                            #includem cazul operatorilor de 2 caractere
                            if i+1<len(line) and char+line[i+1] in operators:
                                atom_dict["operators"].add(char+line[i+1])
                                add_to_FIP(char+line[i+1])
                                i+=1
                            else:
                                atom_dict[have_atom_type].add(char)
                                add_to_FIP(char)

                        i+=1
    
                    #dacă nu, continuăm formarea cuvântului
                    else:
                        token += char
                        i+=1
    except Exception as e:
        print("\033[91m", e, "\033[0m")


#AFISARI IN FISIERE
    #SCRIERE IMPARTIRE ATOMI
    with open("atoms.txt", "w") as f:
        for category, atoms in atom_dict.items():
            f.write(f"{category}: ")
            for atom in atoms:
                f.write(f"{atom}    ")
            f.write("\n")

    #SCRIERE TABELA DE SIMBOLURI
    table = PrettyTable()
    table.field_names = ["Simbol", "Index"]
    for simbol, index in tabela_simboluri:
        table.add_row([simbol, index])
    with open("tabela_simboluri.txt", "w") as f:
        f.write(str(table))

    
    #SCRIERE FIP
    table = PrettyTable()
    table.field_names = ["COD ATOM", "POZITIE TS"]
    for cod_atom,pozitie_ts in fip:
        table.add_row([cod_atom, pozitie_ts])
    with open("fip.txt", "w") as f:
        f.write(str(table))


    #SCRIERE TABELA DE CODIFICARE
    table = PrettyTable()
    table.field_names = ["ATOM", "COD"]
    for atom, cod in dict_tip_cod.items():
        table.add_row([atom, cod])
    with open("tabela_interna.txt", "w") as f:
        f.write(str(table))

main()

import re 

#regex sa faca match pe identificatori de maxim 8 chr ce contine doar litere
variable_regex= r"[a-z]{1,8}$"
binar_regex=r"0b[01]+$"
octal_regex=r"0[0-7]+$"
hexa_regex=r"0x[0-9A-E]+$"

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
        return "operands"
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
    #definim cum ar trebui să arate operatorii, separatorii și keyword-urile
    keywords = ["main", "while","using","namespace","std","cin","cout","for","if","include","int","double","iostream"]
    operators = ["*", "+", "-", "/", "%","<",">","=","==","!=","<=",">=","&&","||","!","<<",">>","++","--","+=","-=","*=","/=","%=","&","|"]
    separators = ["{","}" ,"(", ";", ",", ")", ":", "#",'\n']

    #setăm un dictionar gol, cu cheile de care avem nevoie
    atom_dict = {
        "keywords": set(),
        "operators": set(),
        "separators": set(),
        "operands": set(),
        "constants": set()
    }

    #ca să pot ridica excepție dacă am ceva ce nu convine regulilor
    try:
        with open("code.txt", "r") as file:
            for line in file:
                token = ""
                i=0 #index curent
                while i<len(line):
                    char = line[i]

                    #verificăm dacă am ajuns la un final de cuvânt
                    if char.isspace() or (char == ("\n")) or char in separators or char in operators:
                        #vedem în ce categorie de atom se clasifică cuvântul găsit și adăugăm la dicționar
                        if token:
                            atom_type = classify_token(token, keywords, operators, separators)
                            if(atom_type != "error"):
                                atom_dict[atom_type].add(token)
                            else:
                                print(token)
                                raise Exception("An error occurred.")
                        token = ""
    
                        #vedem dacă separatorul tocmai găsit nu e de fapt ceva din lista de atomi (operator sau separator)
                        have_atom_type = classify_token(char,keywords,operators,separators)
                        if(have_atom_type and char!=" " and char!="\n"):
                            #includem cazul operatorilor de 2 caractere
                            if i+1<len(line) and char+line[i+1] in operators:
                                atom_dict["operators"].add(char+line[i+1])
                                i+=1
                            else:
                                atom_dict[have_atom_type].add(char)

                        i+=1
    
                    #dacă nu, continuăm formarea cuvântului
                    else:
                        token += char
                        i+=1
    except Exception as e:
        print("ERROR!")

    #scriere rezultat în fișier
    with open("atoms.txt", "w") as f:
        for category, atoms in atom_dict.items():
            f.write(f"{category}: ")
            for atom in atoms:
                f.write(f"{atom}    ")
            f.write("\n")

main()

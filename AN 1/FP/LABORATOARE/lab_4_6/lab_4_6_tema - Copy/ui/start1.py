import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.task1 import add_number_to_list_final
from ui.general import print_list
from domain.task6 import crt_undo_list
from ui.interface import undo_op_UI
from domain.task2 import out_from_list_one

def start1():

    print("Operatiile disponibile sunt: add, show, del, undo")
    cmd = input("Introduceti optiunile: ")
    comenzi = cmd.split(',')
    nr_elem = len(comenzi)
    

    number_list = []
    crt_undo = []

    for el in range(nr_elem):
        date_comanda = comenzi[el].split(' ')
        if date_comanda[0] == 'add':
            if len(date_comanda) == 3 and date_comanda[1].isdigit() == True and date_comanda[2].isdigit() == True:
                real = int(date_comanda[1])
                imag = int(date_comanda[2])
                number = [real,imag]
                number_list = add_number_to_list_final(number_list, number)
                if (len(crt_undo)>0 and crt_undo[-1] != number_list) or (crt_undo == [] and number_list != []):
                    crt_undo_list(crt_undo, number_list)
            else:
                pass
        
        elif date_comanda[0] == 'del':
            
            poz = int(date_comanda[1])
            number_list = out_from_list_one(number_list,poz)
            if (len(crt_undo)>0 and crt_undo[-1] != number_list) or (crt_undo == [] and number_list != []):
                    crt_undo_list(crt_undo, number_list)
            

        elif date_comanda[0] == 'show':
            if len(date_comanda) == 1:
                print_list(number_list)
        
        elif date_comanda[0] == 'undo':
            if len(date_comanda) == 1:
                number_list = undo_op_UI(crt_undo)
            

start1()        
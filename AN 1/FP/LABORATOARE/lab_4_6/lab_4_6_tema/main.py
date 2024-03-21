from domain.task4 import sortare
from domain.task5 import filtrare_prim
from domain.task6 import crt_undo_list
from ui.general import print_menu, print_list, validare_optiune
from ui.interface import add_number_to_list_final_UI, add_number_to_list_inter_UI, out_from_list_one_UI, \
    out_from_list_sir_UI, inlocuieste_aparitii_UI, afis_imag_interval_UI, mic_modul_10_UI, egal_modul_10_UI, \
    suma_secventei_UI, prod_secventei_UI, filtrare_modul_UI, undo_op_UI


def start():
    number_list = []
    finished = False
    crt_undo = []

    print_menu()
    while not finished:
        option = input("Optiunea dumneavoastra este: ")
        try:
            validare_optiune(option)
        except ValueError:
            print("Optiunea nu este valida")

        if option == '1.1':

            number_list = add_number_to_list_final_UI(number_list)

            if (len(crt_undo)>0 and crt_undo[-1] != number_list) or (crt_undo == [] and number_list != []):
                crt_undo_list(crt_undo, number_list)
                print_list(number_list)
                print("Numarul a fost adaugat cu succes.")
                print()

        elif option == '1.2':
            number_list = add_number_to_list_inter_UI(number_list)
            if (len(crt_undo) > 0 and crt_undo[-1] != number_list) or (crt_undo == [] and number_list != []):
                print_list(number_list)
                crt_undo_list(crt_undo, number_list)
                print("Numarul a fost adaugat cu succes.")
                print()

        elif option == '2.1':
            number_list = out_from_list_one_UI(number_list)
            if (len(crt_undo) > 0 and crt_undo[-1] != number_list) or (crt_undo == [] and number_list != []):
                print_list(number_list)
                crt_undo_list(crt_undo, number_list)
                print("Numarul a fost eliminat cu succes.")
                print()

        elif option == '2.2':
            number_list = out_from_list_sir_UI(number_list)
            if (len(crt_undo) > 0 and crt_undo[-1] != number_list) or (crt_undo == [] and number_list != []):
                print_list(number_list)
                crt_undo_list(crt_undo, number_list)
                print("Numerele au fost eliminate cu succes.")
                print()


        elif option == '2.3':
            number_list = inlocuieste_aparitii_UI(number_list)
            if (len(crt_undo) > 0 and crt_undo[-1] != number_list) or (crt_undo == [] and number_list != []):
                print_list(number_list)
                crt_undo_list(crt_undo, number_list)
                print("Numarul a fost inlocuit cu succes.")
                print()

        elif option == '3.1':
            afis_imag_interval_UI(number_list)

        elif option == '3.2':
            mic_modul_10_UI(number_list)

        elif option == '3.3':
            egal_modul_10_UI(number_list)

        elif option == '4.1':
            suma_secventei_UI(number_list)

        elif option == '4.2':
            prod_secventei_UI(number_list)

        elif option == '4.3':
            number_list = sortare(number_list)

            print_list(number_list)
            crt_undo_list(crt_undo, number_list)
            print("Numerele au fost sortate cu succes.")
            print()

        elif option == '5.1':
            print_list(filtrare_prim(number_list))
            print("Numerele au fost eliminate cu succes.")
            print()

        elif option == '5.2':
            print_list(filtrare_modul_UI(number_list))
            print("Numerele au fost eliminate cu succes.")
            print()

        elif option == '6':
            number_list = undo_op_UI(crt_undo)
            if number_list != []:
                print_list(number_list)
                print("S-a revenit la lista precedenta ultimei operatii.")
                print()
        elif option == '7':
            finished = True

start()






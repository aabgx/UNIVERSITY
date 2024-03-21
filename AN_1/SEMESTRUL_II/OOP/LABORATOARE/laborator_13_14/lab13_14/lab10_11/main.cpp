#include "lab10_11.h"
#include <QtWidgets/QApplication>
#pragma once
#include <iostream>
#include "activitate.h"
#include "repo.h"
#include "service.h"
#include "UI.h"
#include "interfata.h"

void test_all() {

	test_activitate();

	test_creaza();
	test_adauga();
	test_get_all();
	test_sterge();
	test_cauta();
	test_str();

	test_creaza_srv();
	test_adauga_srv();
	test_sterge_srv();
	test_cauta_srv();
	test_str_srv();
	test_modifica_srv();
	test_filtrare();
	test_sortare();

	teste_program();
	test_stergere_noua();
	test_undo();
	test_salveaza_program();

}

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    lab10_11 w;
    w.show();

	test_all();
	Service srv;

	ConsoleGUI gui(srv);
	gui.show();

	/*ConsoleGUIProgram gui_2(srv);
	gui_2.show();*/

    return a.exec();
}

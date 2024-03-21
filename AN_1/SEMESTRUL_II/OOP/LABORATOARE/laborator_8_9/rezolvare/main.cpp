#pragma once
#include <iostream>
#include "activitate.h"
#include "repo.h"
#include "service.h"
#include "UI.h"

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


int main() {

	test_all();
	Service srv;
	Consola ui(srv);
	ui.run();
	return 0;
}
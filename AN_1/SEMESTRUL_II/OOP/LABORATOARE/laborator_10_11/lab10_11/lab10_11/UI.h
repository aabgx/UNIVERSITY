#pragma once
#include "service.h"
#include <iostream>
#include "repo.h"
#include "activitate.h"

class Consola {

private:
	Service srv;


public:
	Consola(const Service& srv);

	void print_menu();
	void adauga_ui();
	void sterge_ui();
	void modificare_ui();
	void str_ui();
	void cauta_ui();
	void filtrare_ui();
	string de_afisat(vector<Activitate> lista);
	void sortare_ui();
	void run();

	void adauga_program_ui();
	void print_program();
	void adauga_random_ui();
	void goleste_program_ui();

	void sterge_noua_ui();
	void undo();
	void salveaza_program();
};

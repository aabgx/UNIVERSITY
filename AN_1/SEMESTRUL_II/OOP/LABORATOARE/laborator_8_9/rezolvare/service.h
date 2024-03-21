#pragma once
#include "repo.h"
//#include "vector.h"
#include <vector>
#include <string>
#include "activitate.h"
#include "repo.h"
#include <functional>
#include <algorithm>
#include "validator.h"
#include "program.h"
#include "undo.h"

class Service {
private:
	Repository repo;
	Validator val;
	Program program_lista;
	vector<ActiuneUndo*>actiuniUndo;


public:
	Service();
	//nu putem face copie
	Service(const Service& srv) = delete;

	void undo();

	/*const vector<Activitate>& get_all_srv() {
		return this->repo.get_all();
	}*/

	///PENTRU PROGRAM
	//Adauga o activitate cu titlu titlu si descrierea descriere in program
	void adauga_program_srv(string titlu);
	
	//Adauga un numar random de activitati in program
	size_t adauga_random_srv(int n);

	//Elimina toate activitatile din program
	void goleste_program_srv();

	//Returneaza un vector cu toate activitatile din program
	const vector<Activitate>& get_all_program_srv();
	string str_program();



	///PENTRU OPERATIILE ORIGINALE
	bool adauga_srv(const string& titlu, const string& descriere, const string& tip, int durata);
	bool sterge_srv(const string& titlu);
	bool modifica_srv(const string& titlu,const string& descriere_noua,const string& tip_nou,int durata_noua);
	
	Activitate cauta_srv(const string& titlu);
	const string str_srv();
	size_t get_dim_srv();
	
	//vector<Activitate> filtru(function<bool(const Activitate&)> fct);
	vector<Activitate> filtrare_descriere(const string& descriere);
	vector<Activitate> filtrare_tip(const string& tip);

	vector<Activitate> sortare_generala(bool(*fct_mic)(Activitate&,Activitate&));
	vector<Activitate> sortare_titlu();
	vector<Activitate> sortare_descriere();
	vector<Activitate> sortare_tip_durata();

	vector<Activitate> stergere_dupa_tip(string tip);
	void salveaza_program(string fisier);

};

void test_creaza_srv();
void test_adauga_srv();
void test_sterge_srv();
void test_cauta_srv();
void test_str_srv();
void test_modifica_srv();
void test_filtrare();
void test_sortare();
void teste_program();
void test_stergere_noua();
void test_undo();
void test_salveaza_program();
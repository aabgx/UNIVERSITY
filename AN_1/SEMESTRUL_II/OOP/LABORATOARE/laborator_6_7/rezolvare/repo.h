#pragma once
#include <string>
#include "vector.h"
//#include <vector>
#include "activitate.h"
using namespace std;

class Repository {

private:
	//aici am reprezentarea interva
	VectorDinamic<Activitate> lista_activitati;

public:
	Repository();

	//nu vrem sa se poata efectua copie
	Repository(const Repository& repo) = delete;

	//returneaza lista de activitati
	VectorDinamic<Activitate> get_all();

	//adauga o activitate
	void adauga(const Activitate& a);

	//sterge o activitate
	void sterge(Activitate a);

	//cauta o activitate dupa titlu
	Activitate cauta(const string& titlu);

	//returneaza dimensiunea listei
	int get_dim();

	//ajuta la afisare
	const string str();


};
///TESTE
void test_creaza();
void test_adauga();
void test_get_all();
void test_sterge();
void test_cauta();
void test_str();
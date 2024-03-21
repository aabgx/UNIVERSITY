#pragma once
#include "repo.h"
#include "vector.h"
//#include <vector>
#include <string>
#include "activitate.h"
#include "repo.h"
#include <functional>
#include <algorithm>

class Service {

private:
	Repository repo;

public:
	Service();
	//nu putem face copie
	Service(const Service& srv) = delete;

	bool adauga_srv(const string& titlu, const string& descriere, const string& tip, int durata);
	bool sterge_srv(const string& titlu);
	bool modifica_srv(const string& titlu,const string& descriere_noua,const string& tip_nou,int durata_noua);
	
	Activitate cauta_srv(const string& titlu);
	const string str_srv();
	int get_dim_srv();
	
	//vector<Activitate> filtru(function<bool(const Activitate&)> fct);
	VectorDinamic<Activitate> filtrare_descriere(const string& descriere);
	VectorDinamic<Activitate> filtrare_tip(const string& tip);

	VectorDinamic<Activitate> sortare_generala(bool(*fct_mic)(Activitate&,Activitate&));
	VectorDinamic<Activitate> sortare_titlu();
	VectorDinamic<Activitate> sortare_descriere();
	VectorDinamic<Activitate> sortare_tip_durata();

};

void test_creaza_srv();
void test_adauga_srv();
void test_sterge_srv();
void test_cauta_srv();
void test_str_srv();
void test_modifica_srv();
void test_filtrare();
void test_sortare();
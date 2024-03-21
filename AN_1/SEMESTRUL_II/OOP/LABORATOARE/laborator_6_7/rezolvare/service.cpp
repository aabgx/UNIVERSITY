#pragma once
#include "repo.h"
#include "vector.h"
//#include <vector>
#include <string>
#include "activitate.h"
#include "repo.h"
#include "service.h"
#include <assert.h>
#include <iostream>
#include <exception>

Service::Service() {
	//Repository repo;
	this->repo = repo;
}

bool Service::adauga_srv(const string& titlu, const string& descriere, const string& tip, int durata) {
	Activitate a(titlu,descriere,tip,durata);

	Activitate aux = this->repo.cauta(titlu);
	
	if (aux.get_titlu() != "null")
	{
		throw exception("Titlul deja exista!");
	}

	this->repo.adauga(a);
	return true;
}

bool Service::sterge_srv(const string& titlu) {

	Activitate aux = this->repo.cauta(titlu);

	if (aux.get_titlu() == "null")
	{
		throw exception("Activitatea nu exista!");

	}

	this->repo.sterge(aux);
	return true;
}

Activitate Service::cauta_srv(const string& titlu) {

	return this->repo.cauta(titlu);
}

const string Service::str_srv() {
	return this->repo.str();
}

int Service::get_dim_srv() {
	return this->repo.get_dim();
}

bool Service::modifica_srv(const string& titlu, const string& descriere_noua, const string& tip_nou, int durata_noua) {

	Activitate aux = this->repo.cauta(titlu);

	if (aux.get_titlu() != "null")
	{
		this->repo.sterge(aux);
		Activitate a(titlu, descriere_noua, tip_nou, durata_noua);
		this->repo.adauga(a);
		return true;

	}

	throw exception("Activitatea nu exista!");
}

VectorDinamic<Activitate> Service::filtrare_descriere(const string& descriere) {
	VectorDinamic<Activitate> rezultat;
	VectorDinamic<Activitate> lst = this->repo.get_all();
	for (int i = 0; i < lst.size(); i++)
	{
		if (lst[i].get_descriere()==descriere)
			rezultat.push_back(lst[i]);
	}
	return rezultat;
}

VectorDinamic<Activitate> Service::filtrare_tip(const string& tip) {
	VectorDinamic<Activitate> rezultat;
	VectorDinamic<Activitate> lst = this->repo.get_all();
	for (int i = 0; i < lst.size(); i++)
	{
		if (lst[i].get_tip() == tip)
			rezultat.push_back(lst[i]);
	}
	return rezultat;
}

VectorDinamic<Activitate> Service::sortare_generala(bool(*fct_mic)(Activitate&,Activitate&)) {
	VectorDinamic<Activitate> v{ this->repo.get_all() };
	for (int i = 0; i < v.size(); i++) {
		for (int j = i + 1; j < v.size(); j++) {
			if (!fct_mic(v[i], v[j])) {
				Activitate aux = v[i];
				v[i] = v[j];
				v[j] = aux;
			}
		}
	}
	return v;
}

VectorDinamic<Activitate> Service::sortare_titlu() {
	return sortare_generala(cmp_titlu);
}

VectorDinamic<Activitate> Service::sortare_descriere() {
	return sortare_generala(cmp_descriere);
}

VectorDinamic<Activitate> Service::sortare_tip_durata() {
	return sortare_generala(cmp_tip_durata);
}



//TESTE
void test_sortare() {
	Service srv;
	assert(srv.get_dim_srv() == 0);
	Activitate a1("titlu1", "descriere1", "tip1", 10);
	Activitate a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu3", "descriere3", "tip3", 30);
	Activitate a4("titlu4", "descriere4", "tip4", 40);
	Activitate a5("titlu5", "descriere5", "tip5", 50);

	srv.adauga_srv(a3.get_titlu(), a3.get_descriere(), a3.get_tip(), a3.get_durata());
	srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata());
	srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata());
	srv.adauga_srv(a5.get_titlu(), a5.get_descriere(), a5.get_tip(), a5.get_durata());
	srv.adauga_srv(a4.get_titlu(), a4.get_descriere(), a4.get_tip(), a4.get_durata());

	VectorDinamic<Activitate> lista1 = srv.sortare_titlu();
	assert(lista1[0].get_titlu() == "titlu1");
	assert(lista1[1].get_titlu() == "titlu2");
	assert(lista1[2].get_titlu() == "titlu3");
	assert(lista1[3].get_titlu() == "titlu4");
	assert(lista1[4].get_titlu() == "titlu5");

	VectorDinamic<Activitate> lista2 = srv.sortare_descriere();
	assert(lista2[0].get_titlu() == "titlu1");
	assert(lista2[1].get_titlu() == "titlu2");
	assert(lista2[2].get_titlu() == "titlu3");
	assert(lista2[3].get_titlu() == "titlu4");
	assert(lista2[4].get_titlu() == "titlu5");

	Activitate a6("titlu6", "descriere5", "tip5", 20);
	srv.adauga_srv(a6.get_titlu(), a6.get_descriere(), a6.get_tip(), a6.get_durata());

	VectorDinamic<Activitate> lista3 = srv.sortare_tip_durata();
	assert(lista3[0].get_titlu() == "titlu1");
	assert(lista3[1].get_titlu() == "titlu2");
	assert(lista3[2].get_titlu() == "titlu3");
	assert(lista3[3].get_titlu() == "titlu4");
	assert(lista3[4].get_titlu() == "titlu6");
	assert(lista3[5].get_titlu() == "titlu5");

	VectorDinamic<int> my_vector;
	my_vector.push_back(1);
	my_vector.push_back(2);

	my_vector.clear();

}

void test_filtrare() {

	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu3", "descriere1", "tip2", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);
	assert(srv.adauga_srv(a3.get_titlu(), a3.get_descriere(), a3.get_tip(), a3.get_durata()) == true);

	VectorDinamic<Activitate> lista1 = srv.filtrare_descriere("descriere1");
	assert(lista1[0].get_titlu() == "titlu1");
	assert(lista1[1].get_titlu() == "titlu3");

	VectorDinamic<Activitate> lista2 = srv.filtrare_tip("tip2");
	assert(lista2[0].get_titlu() == "titlu2");
	assert(lista2[1].get_titlu() == "titlu3");

}
void test_modifica_srv() {

	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu1", "descriere3", "tip3", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);

	assert(srv.get_dim_srv() == 2);

	assert(srv.get_dim_srv() == 2);

	assert(srv.cauta_srv("titlu1").get_descriere() == "descriere1");

	assert(srv.modifica_srv("titlu1", "alta_descriere", "alt_tip", 100) == true);
	try {
		srv.modifica_srv("titlu10", "alta_descriere", "alt_tip", 100);
		assert(false);
	}
	catch (exception&) { assert(true); }

	assert(srv.cauta_srv("titlu1").get_descriere() == "alta_descriere");
}


void test_creaza_srv() {

	Service srv;
	assert(srv.get_dim_srv() == 0);
}

void test_adauga_srv() {

	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu1", "descriere3", "tip3", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);

	assert(srv.get_dim_srv() == 2);

	try {
		srv.adauga_srv(a3.get_titlu(), a3.get_descriere(), a3.get_tip(), a3.get_durata());
		assert(false);
	}
	catch(exception&)
	{
		assert(true);
	}
	assert(srv.get_dim_srv() == 2);
																										

}

void test_sterge_srv() {

	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu1", "descriere3", "tip3", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);

	assert(srv.get_dim_srv() == 2);

	assert(srv.sterge_srv("titlu1") == true);
	assert(srv.get_dim_srv() == 1);

	try {
		srv.sterge_srv("titlu3");
		assert(false);
	}
	catch (exception&) { assert(true); }
	assert(srv.get_dim_srv() == 1);

}


void test_cauta_srv() {

	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu1", "descriere3", "tip3", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);

	assert(srv.get_dim_srv() == 2);

	assert(srv.cauta_srv("titlu1").get_titlu() == "titlu1");
	assert(srv.cauta_srv("titlu3").get_titlu() == "null");

}

void test_str_srv() {

	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu1", "descriere3", "tip3", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);

	assert(srv.get_dim_srv() == 2);

	assert(srv.str_srv() == a1.str() + "\n" + a2.str() + "\n");

}

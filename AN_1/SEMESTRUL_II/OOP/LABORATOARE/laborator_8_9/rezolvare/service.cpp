#pragma once
#include "repo.h"
//#include "vector.h"
#include <vector>
#include <string>
#include "activitate.h"
#include "repo.h"
#include "service.h"
#include <assert.h>
#include <iostream>
#include <exception>
#include <fstream>
using namespace std;

Service::Service() {
	//Repository repo;
	this->repo = repo;
	this->val = val;
}
///PENTRU PROGRAM
void Service::adauga_program_srv(string titlu) {
	Activitate aux = repo.cauta(titlu);
	if (aux.get_titlu() == "null") throw exception();
	program_lista.adauga_program(aux);
}
size_t Service::adauga_random_srv(int n) {
	program_lista.adauga_random(this->repo.get_all(), n);
	return program_lista.get_all_program().size();
}

void Service::goleste_program_srv() {
	program_lista.goleste_program();
}
const vector<Activitate>& Service::get_all_program_srv() {
	return program_lista.get_all_program();
}

void Service::salveaza_program(string fisier) {
	ofstream fout(fisier);
	vector<Activitate>lista = program_lista.program;
	for (auto& el : lista) {
		fout<< el.str() <<"\n";
	}
	fout.close();

}

///PENTRU ADAUGARE SIMPLA
bool Service::adauga_srv(const string& titlu, const string& descriere, const string& tip, int durata) {
	Activitate a(titlu,descriere,tip,durata);

	Activitate aux = this->repo.cauta(titlu);
	if (aux.get_titlu() != "null") throw exception();
	val.valideaza(a);
	this->repo.adauga(a);
	actiuniUndo.push_back(new UndoAdauga(repo, a));
	return true;
}

string Service::str_program() {
	string lista_de_afisat = "";
	vector<Activitate>lista = this->program_lista.program;

	for (int i = 0; i < lista.size(); ++i)
	{
		lista_de_afisat = lista_de_afisat + lista[i].str() + "\n";
	}
	return lista_de_afisat;
}

bool Service::sterge_srv(const string& titlu) {

	Activitate aux = this->repo.cauta(titlu);

	val.valideaza(aux);

	this->repo.sterge(aux);
	actiuniUndo.push_back(new UndoSterge(repo, aux));
	return true;
}

/*vector<Activitate> Service::stergere_dupa_tip(string tip) {
	vector<Activitate> activities = this->repo.get_all();
	vector<Activitate> rez;
	rez=remove_if(activities.begin(), activities.end(), [&](Activitate& activitate) {return activitate.get_tip() == tip;});
	return rez;
}*/

vector<Activitate> Service::stergere_dupa_tip(const string tip) {
	vector<Activitate> activities = this->repo.get_all();
	auto functie = [tip](Activitate a) mutable {return a.get_tip() == tip;};
	auto it = remove_if(activities.begin(), activities.end(), functie);

	activities.erase(it, activities.end());
	return activities;

}
void test_stergere_noua() {
	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu3", "descriere3", "tip1", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);

	vector<Activitate> activities = srv.stergere_dupa_tip("tip1");
	assert(activities.size() == 1);

}

Activitate Service::cauta_srv(const string& titlu) {

	return this->repo.cauta(titlu);
}

const string Service::str_srv() {
	return this->repo.str();
}

size_t Service::get_dim_srv() {
	return this->repo.get_dim();
}

bool Service::modifica_srv(const string& titlu, const string& descriere_noua, const string& tip_nou, int durata_noua) {

	Activitate aux = this->repo.cauta(titlu);


	val.valideaza(aux);

	this->repo.sterge(aux);
	Activitate a(titlu, descriere_noua, tip_nou, durata_noua);
	this->repo.adauga(a);

	actiuniUndo.push_back(new UndoModifica(repo, aux, a));
	return true;
}

vector<Activitate> Service::filtrare_descriere(const string& descriere) {
	vector<Activitate> rezultat;
	vector<Activitate> lst = this->repo.get_all();
	/*for (int i = 0; i < lst.size(); i++)
	{
		if (lst[i].get_descriere()==descriere)
			rezultat.push_back(lst[i]);
	}*/
	std::copy_if(lst.begin(), lst.end(), back_inserter(rezultat),
		[descriere](Activitate& a) {
			return a.get_descriere() == descriere;
		});
	return rezultat;
}

vector<Activitate> Service::filtrare_tip(const string& tip) {
	vector<Activitate> rezultat;
	vector<Activitate> lst = this->repo.get_all();
	/*for (int i = 0; i < lst.size(); i++)
	{
		if (lst[i].get_tip() == tip)
			rezultat.push_back(lst[i]);
	}*/
	std::copy_if(lst.begin(), lst.end(), back_inserter(rezultat),
		[tip](Activitate& a) {
			return a.get_tip() == tip;
		});
	return rezultat;
}

/*vector<Activitate> Service::sortare_generala(bool(*fct_mic)(Activitate&, Activitate&)) {
	vector<Activitate> v{ this->repo.get_all() };
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
}*/

vector<Activitate> Service::sortare_titlu() {
	//return sortare_generala(cmp_titlu);
	vector<Activitate> lst = this->repo.get_all();
	sort(lst.begin(), lst.end(), cmp_titlu);
	return lst;
}

vector<Activitate> Service::sortare_descriere() {
	//return sortare_generala(cmp_descriere);
	vector<Activitate> lst = this->repo.get_all();
	sort(lst.begin(), lst.end(), cmp_descriere);
	return lst;
}

vector<Activitate> Service::sortare_tip_durata() {
	//return sortare_generala(cmp_tip_durata);
	//return sortare_generala(cmp_descriere);
	vector<Activitate> lst = this->repo.get_all();
	sort(lst.begin(), lst.end(), cmp_tip_durata);
	return lst;
}

void Service::undo() {
	if (actiuniUndo.size() == 0)
		throw (exception("Nu se poate face undo!"));

	actiuniUndo.back()->doUndo(); // Facem undo
	delete actiuniUndo.back(); // Stergem elementul din memorie
	actiuniUndo.pop_back(); // Stergem elementul din vector efectiv

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

	vector<Activitate> lista1 = srv.sortare_titlu();
	assert(lista1[0].get_titlu() == "titlu1");
	assert(lista1[1].get_titlu() == "titlu2");
	assert(lista1[2].get_titlu() == "titlu3");
	assert(lista1[3].get_titlu() == "titlu4");
	assert(lista1[4].get_titlu() == "titlu5");

	vector<Activitate> lista2 = srv.sortare_descriere();
	assert(lista2[0].get_titlu() == "titlu1");
	assert(lista2[1].get_titlu() == "titlu2");
	assert(lista2[2].get_titlu() == "titlu3");
	assert(lista2[3].get_titlu() == "titlu4");
	assert(lista2[4].get_titlu() == "titlu5");

	Activitate a6("titlu6", "descriere5", "tip5", 20);
	srv.adauga_srv(a6.get_titlu(), a6.get_descriere(), a6.get_tip(), a6.get_durata());

	vector<Activitate> lista3 = srv.sortare_tip_durata();
	assert(lista3[0].get_titlu() == "titlu1");
	assert(lista3[1].get_titlu() == "titlu2");
	assert(lista3[2].get_titlu() == "titlu3");
	assert(lista3[3].get_titlu() == "titlu4");
	assert(lista3[4].get_titlu() == "titlu6");
	assert(lista3[5].get_titlu() == "titlu5");

	vector<int> my_vector;
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

	vector<Activitate> lista1 = srv.filtrare_descriere("descriere1");
	assert(lista1[0].get_titlu() == "titlu1");
	assert(lista1[1].get_titlu() == "titlu3");

	vector<Activitate> lista2 = srv.filtrare_tip("tip2");
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
	catch (ExceptiiValidare&) { assert(true); }

	assert(srv.cauta_srv("titlu1").get_descriere() == "alta_descriere");
}

void teste_program() {
	Service srv;
	assert(srv.get_dim_srv() == 0);

	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu1", "descriere3", "tip3", 30);

	assert(srv.adauga_srv(a1.get_titlu(), a1.get_descriere(), a1.get_tip(), a1.get_durata()) == true);
	assert(srv.adauga_srv(a2.get_titlu(), a2.get_descriere(), a2.get_tip(), a2.get_durata()) == true);

	srv.adauga_program_srv("titlu1");
	try
	{
		srv.adauga_program_srv("nu_exista");
	}
	catch (exception&) { assert(true); }
	srv.str_program();
	srv.adauga_random_srv(2);
	srv.goleste_program_srv();
	srv.get_all_program_srv();


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
	catch(ExceptiiValidare&)
	{
		assert(true);
	}
	catch (exception&) {
		assert(true);
	}
	assert(srv.get_dim_srv() == 2);

	srv.undo();
																										

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
	catch (ExceptiiValidare&) { assert(true); }
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
void test_undo() {

	Repository repo;
	vector<ActiuneUndo*>undo_list;
	Activitate a1("titlu1", "descriere1", "tip1", 10);
	Activitate a2("titlu2", "descriere2", "tip2", 20);
	Activitate a3("titlu3", "descriere3", "tip3", 30);
	Activitate a5("titlu5", "descriere5", "tip5", 50);

	repo.adauga(a3);
	undo_list.push_back(new UndoAdauga(repo, a3));
	repo.adauga(a2);
	undo_list.push_back(new UndoAdauga(repo, a2));
	repo.adauga(a1);
	undo_list.push_back(new UndoAdauga(repo, a1));

	repo.cauta(a1.get_titlu()) = a5;
	undo_list.push_back(new UndoModifica(repo, a1, a5));

	repo.sterge(a2);
	undo_list.push_back(new UndoSterge(repo, a2));

	assert(repo.get_dim() == 2);

	undo_list[4]->doUndo();
	assert(repo.get_dim() == 3);

	undo_list[3]->doUndo();
	assert(repo.get_dim() == 3);

	undo_list[2]->doUndo();
	assert(repo.get_dim() == 2);

	for (auto& el : undo_list) {
		delete el;
	}
}
void test_salveaza_program() {
	Service srv;
	srv.adauga_srv("titlu1", "descriere1", "tip1", 1);
	srv.adauga_srv("titlu2", "descriere2", "tip2", 2);
	srv.adauga_srv("titlu3", "descriere3", "tip3", 3);

	srv.adauga_program_srv("titlu1");
	srv.adauga_program_srv("titlu2");
	srv.adauga_program_srv("titlu3");

	srv.salveaza_program("test.html");

	ifstream fin("test.html");
	Activitate a1("titlu1", "descriere1", "tip1", 1);
	Activitate a2("titlu2", "descriere2", "tip2", 2);
	Activitate a3("titlu3", "descriere3", "tip3", 3);

	string test;
	getline(fin, test);
	assert(test == a1.str());
	getline(fin, test);
	assert(test == a2.str());
	getline(fin, test);
	assert(test == a3.str());

	fin.close();

}
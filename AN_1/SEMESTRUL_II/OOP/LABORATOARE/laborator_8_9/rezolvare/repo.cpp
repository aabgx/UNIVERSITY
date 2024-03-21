#pragma once
#include "repo.h"
#include <assert.h>
using namespace std;

//creeaza o lista goala de activitati
Repository::Repository() {
	this->lista_activitati.clear();
}

vector<Activitate> Repository::get_all() {

	return this->lista_activitati;

}

void Repository::adauga(const Activitate& a) {

	this->lista_activitati.push_back(a);

}

void Repository::sterge(Activitate a) {
	vector<Activitate> lista = this->lista_activitati;
	for (int i = 0;i < lista.size();i++)
	{
		if (a.eq(lista[i]) == true) {

			this->lista_activitati.erase(this->lista_activitati.begin() + i);
			break;

		}
	}

}

Activitate Repository::cauta(const string& titlu) {

	vector<Activitate> lista = this->lista_activitati;
	for (int i = 0;i < lista.size();i++)
	{
		if (titlu == lista[i].get_titlu()) 
				return this->lista_activitati[i];
	}
	Activitate activitate_nula("null","null","null",-1);
	return activitate_nula;
}

size_t Repository::get_dim() {
	return this->lista_activitati.size();
}

const string Repository::str() {
	string lista_de_afisat = "";
	vector<Activitate>lista = this->lista_activitati;

	for (int i = 0; i < lista.size(); ++i)
	{
		lista_de_afisat = lista_de_afisat + lista[i].str() + "\n";
	}
	return lista_de_afisat;
}

///TESTE
void test_creaza() {
	Repository repo;
	assert(repo.get_dim() == 0);
}

void test_adauga() {

	Repository repo;
	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	repo.adauga(a1);
	repo.adauga(a2);

	assert(repo.get_dim() == 2);
	vector<Activitate> lista=repo.get_all();
	assert(lista.size() == 2);
	assert(a1.eq(lista[0]) == true);
	assert(a2.eq(lista[1]) == true);
}

void test_get_all() {

	Repository repo;
	vector<Activitate> lista = repo.get_all();
	assert(lista.size() == 0);
}

void test_sterge() {

	Repository repo;
	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	repo.adauga(a1);
	repo.adauga(a2);

	assert(repo.get_dim() == 2);
	vector<Activitate> lista = repo.get_all();
	assert(lista.size() == 2);
	assert(a1.eq(lista[0]) == true);
	assert(a2.eq(lista[1]) == true);

	repo.sterge(a2);
	assert(repo.get_dim() == 1);
	lista = repo.get_all();
	assert(a1.eq(lista[0]) == true);

	repo.sterge(a1);
	assert(repo.get_dim() == 0);
}

void test_cauta() {

	Repository repo;
	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	repo.adauga(a1);
	repo.adauga(a2);

	assert(repo.get_dim() == 2);
	vector<Activitate> lista = repo.get_all();
	assert(lista.size() == 2);
	assert(a1.eq(lista[0]) == true);
	assert(a2.eq(lista[1]) == true);

	assert(a1.eq(repo.cauta("titlu1")) == true);
	assert(a2.eq(repo.cauta("titlu2")) == true);
	Activitate a = repo.cauta("inexistent");
	assert(a.get_titlu() == "null");

}

void test_str() {

	Repository repo;
	Activitate a1("titlu1", "descriere1", "tip1", 10), a2("titlu2", "descriere2", "tip2", 20);
	repo.adauga(a1);

	assert(repo.str() == a1.str() + "\n");

	repo.adauga(a2);

	assert(repo.str() == a1.str() + "\n" + a2.str() + "\n");

	assert(repo.get_dim() == 2);
	vector<Activitate> lista = repo.get_all();
	assert(lista.size() == 2);
	assert(a1.eq(lista[0]) == true);
	assert(a2.eq(lista[1]) == true);

}
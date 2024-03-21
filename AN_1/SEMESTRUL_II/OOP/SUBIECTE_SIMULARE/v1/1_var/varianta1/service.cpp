#pragma once
#include "service.h"
#include "algorithm"
#include <assert.h>
#include <iterator>

//cauta un jucator pe baza numelui
Jucator Service::cauta(string nume) {
	return repo.cauta(nume);
}

//sorteaza pe baza punctelor jucatorilor
bool cmp_puncte(Jucator& j1, Jucator& j2) {
	return j1.get_puncte() > j2.get_puncte();
}
vector<Jucator> Service::sortare_puncte() {
	vector<Jucator> lst = this->repo.get_repo();
	sort(lst.begin(), lst.end(), cmp_puncte);
	return lst;
}

//elimina ultimii n jucatori din lista
vector<Jucator> Service::elimina(int n) {
	vector<Jucator> lst = repo.get_repo();
	lst.erase(lst.end() - n,lst.end());
	return lst;
}

void test_service() {
	Repo repo;
	Service srv(repo);

	//test cauta
	Jucator a("Ana", "Romania", 130, 3);
	assert(srv.cauta("Ana").get_nume() == a.get_nume());
	assert(srv.cauta("Ana").get_tara() == a.get_tara());

	//test sorteaza
	vector<Jucator> lst = srv.sortare_puncte();
	assert(lst[0].get_nume() == "Maria");
	assert(lst[1].get_nume() == "Iulia");
	assert(lst[2].get_nume() == "Ana");

	//test elimina
	assert(lst.size() == 3);
	lst = srv.elimina(1);
	assert(lst.size() == 2);
}
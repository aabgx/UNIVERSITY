#pragma once
#include "service.h"
#include "algorithm"
#include <assert.h>
#include <iterator>

//adauga un jucator in lista pe baza nume,tara,puncte,rank
bool Service::adauga_srv(const string& nume, const string& tara,int puncte,int rank) {
	Jucator j(nume, tara, puncte,rank);

	this->repo.adauga(j);
	return true;
}

//calculeaza rank-ul pe baza unui nr. de puncte
int Service::calculeaza_rank(int puncte)
{
	int rank = 1;
	vector<Jucator> lst=repo.get_repo();
	for (auto& el : lst) {
		if (el.get_puncte() > puncte) rank++;
	}
	return rank;
}

//sorteaza jucatorii dupa puncte
bool cmp_puncte(Jucator& j1, Jucator& j2) {
	return j1.get_puncte() > j2.get_puncte();
}
vector<Jucator> Service::sortare_puncte() {
	vector<Jucator> lst = this->repo.get_repo();
	sort(lst.begin(), lst.end(), cmp_puncte);
	return lst;
}

//face update la rank-uri pe baza nr de puncte ale jucatorilor
vector<Jucator> Service::update_ranks(vector<Jucator> lst) {
	int nr = 1;
	for (auto& el : lst) {
		el.set_rank(nr);
		nr++;
	}
	return lst;

}

//returneaza o lista doar cu jucatorii apartinatori de tara data ca argument
vector<Jucator> Service::filtrare_tara(string tara) {
	vector<Jucator> filter;
	copy_if(repo.get_repo().begin(), repo.get_repo().end(), back_inserter(filter), [tara](const Jucator& j) {
		return j.get_tara() == tara;
		});
	return filter;
}

void test_service() {
	Repo repo;
	Service srv(repo);
	Jucator j("nume", "tara", 1, 100);

	//test sorteaza
	vector<Jucator> lst = srv.sortare_puncte();
	assert(lst[0].get_nume() == "Maria");
	assert(lst[1].get_nume() == "Iulia");
	assert(lst[2].get_nume() == "Ana");

	//test adauga
	assert(srv.get_all_srv().size() == 3);
	srv.adauga_srv(j.get_nume(), j.get_tara(), j.get_puncte(), j.get_rank());
	assert(srv.get_all_srv().size() == 4);
	assert(srv.get_all_srv()[3].get_nume() == "nume");

	//test calculeaza rank
	int rank = srv.calculeaza_rank(500);
	assert(rank == 1);

	//test update ranks
	lst = srv.get_all_srv();
	lst = srv.update_ranks(lst);
	assert(lst[3].get_rank() == 4);

	//test filtrare tara
	vector<Jucator> lista = srv.filtrare_tara("Italia");
	assert(lista.size() == 1);
	assert(lista[0].get_nume() == "Iulia");

}
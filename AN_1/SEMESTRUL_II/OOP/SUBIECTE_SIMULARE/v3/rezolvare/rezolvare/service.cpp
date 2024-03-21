#pragma once
#include "service.h"
#include "algorithm"
#include <assert.h>
#include <iterator>
//adauga un jucator in lista pe baza nume,tara,puncte,rank
bool Service::adauga(const string& nume, const string& tara, int puncte, int rank) {
	Jucator j(nume, tara, puncte, rank);
	this->repo.adauga(j);
	return true;
}

//sorteaza jucatorii dupa rank
bool cmp_rank(Jucator& j1, Jucator& j2) {
	return j1.get_rank() < j2.get_rank();
}
vector<Jucator> Service::sortare_rank() {
	vector<Jucator> lst = this->repo.get_repo();
	sort(lst.begin(), lst.end(), cmp_rank);
	return lst;
}

void test_service() {
	Repo repo;
	Service srv(repo);
	Jucator j("nume", "tara", 1, 100);

	//test sorteaza
	vector<Jucator> lst = srv.sortare_rank();
	assert(lst[0].get_nume() == "Maria");
	assert(lst[1].get_nume() == "Iulia");
	assert(lst[2].get_nume() == "Ana");

	//test adauga
	assert(srv.get_all_srv().size() == 3);
	srv.adauga(j.get_nume(), j.get_tara(), j.get_puncte(), j.get_rank());
	assert(srv.get_all_srv().size() == 4);
	assert(srv.get_all_srv()[3].get_nume() == "nume");


}
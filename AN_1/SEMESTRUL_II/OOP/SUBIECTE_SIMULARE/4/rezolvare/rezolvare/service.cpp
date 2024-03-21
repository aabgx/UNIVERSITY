#pragma once
#include "service.h"
#include "algorithm"
#include <assert.h>
#include <iterator>

//sorteaza pe baza ppretului
bool cmp_pret(Joc& j1, Joc& j2) {
	return j1.get_pret() > j2.get_pret();
}
vector<Joc> Service::sortare_pret() {
	vector<Joc> lst = this->repo.get_repo();
	sort(lst.begin(), lst.end(), cmp_pret);
	return lst;
}

vector<Joc> Service::filtru_varsta(const int& varsta) {
	vector<Joc> filter;
	copy_if(repo.get_repo().begin(), repo.get_repo().end(), back_inserter(filter), [varsta](Joc& d) {
		return d.get_age() <= varsta;
		});
	return filter;
}

void test_service() {
	Repo repo;
	Service srv(repo);

	//test get_all
	assert(srv.get_all_srv().size() == 10);
	assert(srv.get_all_srv()[0].get_titlu() == "God Of War");

	//test cauta
	Joc a("God Of War", 90, "PlayStation", 18);
	assert(srv.cauta("God Of War").get_titlu() == a.get_titlu());
	assert(srv.cauta("God Of War").get_pret() == a.get_pret());

	//test filtrare
	vector<Joc> lst = srv.filtru_varsta(12);
	assert(lst[0].get_titlu() == "Mario Kart Deluxe 8");
	assert(lst.size() == 5);

	//test sortare
	lst = srv.sortare_pret();
	assert(lst[0].get_titlu() == "The Legend Of Zelda");
	assert(lst.size() == 10);
}
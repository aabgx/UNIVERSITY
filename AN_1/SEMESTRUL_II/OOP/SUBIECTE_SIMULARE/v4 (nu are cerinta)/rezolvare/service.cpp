#pragma once
#include "service.h"
#include "algorithm"
#include <assert.h>
#include <iterator>
#include <QMessageBox>
//cauta proiectul cu numele nume si ii modifica bugetul alocat in functie de n
vector<Proiect> Service::cauta_modifica(vector<Proiect> lst,string nume, int n) {
	for (Proiect p : lst) {
		if (p.get_nume() == nume)
		{
			if (n==1) p.set_buget_a(n/2);
			else if (n==2) p.set_buget_a(n/3);
			else p.set_buget_a(n/5);
		}
	}
	return lst;
}

//calculeaza totalul pierderilor pt proiectele din lista
int Service::calculeaza() {
	vector<Proiect> lst = this->get_all_srv();
	int rez=0;
	for (Proiect p : lst) {
		if (p.get_buget_e() > p.get_buget_a()) rez = rez + p.get_buget_a() - p.get_buget_e();
	}
	return rez;
}

void test_service() {
	Repo repo;
	Service srv(repo);

	//test getter
	assert(srv.get_all_srv().size() == 10);
	assert(srv.get_all_srv()[0].get_nume() == "proiect1");

	//test calculeaza
	assert(srv.calculeaza() == -850);

	//test cauta_modifica
	vector<Proiect> lst = srv.cauta_modifica(srv.get_all_srv(),"proiect1", 1);
	//assert(lst[1].get_buget_a() == 50);

}
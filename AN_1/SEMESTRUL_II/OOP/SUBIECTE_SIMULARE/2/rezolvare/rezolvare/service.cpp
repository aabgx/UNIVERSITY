#pragma once
#include "service.h"
#include "algorithm"
#include <assert.h>
#include <iterator>

bool cmp_model(Device& d1, Device& d2) {
	return d1.get_model() < d2.get_model();
}
bool cmp_pret(Device& d1, Device& d2) {
	return d1.get_pret() < d2.get_pret();
}

vector<Device> Service::sortare_model() {
	vector<Device> lst = this->repo.get_repo();
	sort(lst.begin(), lst.end(), cmp_model);
	return lst;
}

vector<Device> Service::sortare_pret() {
	vector<Device> lst = this->repo.get_repo();
	sort(lst.begin(), lst.end(), cmp_pret);
	return lst;
}


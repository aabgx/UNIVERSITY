#pragma once
#include "service.h"
#include "algorithm"
#include <assert.h>
#include <iterator>

vector<Doctor> Service::filtrare_sectie(const string& sectie) {
    vector<Doctor> filter;
    copy_if(repo.get_repo().begin(), repo.get_repo().end(), back_inserter(filter), [sectie](const Doctor& d) {
        return d.get_sectie() == sectie;
        });
    return filter;
}

vector<Doctor> Service::filtrare_nume(const string& nume) {
    vector<Doctor> filter;
    copy_if(repo.get_repo().begin(), repo.get_repo().end(), back_inserter(filter), [nume](const Doctor& d) {
        return d.get_nume() == nume;
        });
    return filter;
}

vector<Doctor> Service::filtrare_tot(const string& nume, const string& sectie) {
    vector<Doctor> filter;
    for (auto& d : this->get_all_srv()) {
        if (d.get_nume() == nume && d.get_sectie() == sectie) filter.push_back(d);
    }
    return filter;
}

void test_service() {
    Repo repo;
    Service srv(repo);
    assert(srv.get_all_srv().size() == 10);
    assert(srv.filtrare_nume("Dan").size() == 9);
    assert(srv.filtrare_sectie("Onco").size() == 9);
}


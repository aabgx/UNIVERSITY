#pragma once
#include "repo.h"
#include "vector"
#include "string"

using namespace std;

class Service {
private:
    Repo& repo;

public:
    Service(Repo& repo) :repo(repo) {};
    vector<Doctor> get_all_srv() { return this->repo.get_repo(); };
    vector<Doctor> filtrare_sectie(const string& sectie);
    vector<Doctor> filtrare_nume(const string& nume);
    vector<Doctor> filtrare_tot(const string& nume, const string& sectie);
    int cauta_srv(string nume, string prenume) { return repo.cauta(nume, prenume); };
};
void test_service();

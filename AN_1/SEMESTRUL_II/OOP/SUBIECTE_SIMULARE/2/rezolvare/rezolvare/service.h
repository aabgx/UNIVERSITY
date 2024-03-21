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
    vector<Device> get_all_srv() { return this->repo.get_repo(); };
    vector<Device> sortare_pret();
    vector<Device> sortare_model();
    vector<Device> nesortat();
    Device cauta_srv(string model, int pret) { return repo.cauta(model, pret); };

};
void test_service();

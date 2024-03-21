#pragma once
#include "repo.h"
#include "vector"
#include "string"

using namespace std;

class Service {
    friend class GUI;
private:
    Repo& repo;

public:
    Service(Repo& repo) :repo(repo) {};

    //getter pt lista cu care lucram
    vector<Joc>& get_all_srv() { return this->repo.get_repo(); };

    //cauta un jov pe baza titlului
    Joc cauta(string titlu) { return repo.cauta(titlu); };

    vector<Joc> sortare_pret();

    vector<Joc> filtru_varsta(const int& varsta);
};
void test_service();
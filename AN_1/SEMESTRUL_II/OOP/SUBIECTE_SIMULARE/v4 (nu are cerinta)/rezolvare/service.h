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
    vector<Proiect>& get_all_srv() { return this->repo.get_repo(); };

    //cauta proiectul cu numele nume si ii modifica bugetul alocat in functie de n
    vector<Proiect> cauta_modifica(vector<Proiect> v,string nume, int n);

    //calculeaza totalul pierderilor pt proiectele din lista
    int calculeaza();

};
void test_service();

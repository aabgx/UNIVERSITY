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
    vector<Jucator>& get_all_srv() { return this->repo.get_repo(); };

    //cauta un jucator pe baza numelui
    Jucator cauta(string nume);

    //sorteaza pe baza punctelor jucatorilor
    vector<Jucator> sortare_puncte();

    //elimina ultimii n jucatori din lista
    vector<Jucator> elimina(int n);
};
void test_service();
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

    //adauga un jucator in lista pe baza nume,tara,puncte,rank
    bool adauga(const string& nume, const string& tara, int puncte, int rank);

    //sorteaza jucatorii dupa rank
    vector<Jucator> sortare_rank();

};
void test_service();

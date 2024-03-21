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
    bool adauga_srv(const string& nume, const string& tara, int puncte,int rank);
    
    //calculeaza rank-ul pe baza unui nr. de puncte
    int calculeaza_rank(int puncte);

    //sorteaza jucatorii dupa puncte
    vector<Jucator> sortare_puncte();

    //face update la rank-uri pe baza nr de puncte ale jucatorilor
    vector<Jucator> update_ranks(vector<Jucator> lst);

    //returneaza o lista doar cu jucatorii apartinatori de tara data ca argument
    vector<Jucator> filtrare_tara(string tara);
};
void test_service();

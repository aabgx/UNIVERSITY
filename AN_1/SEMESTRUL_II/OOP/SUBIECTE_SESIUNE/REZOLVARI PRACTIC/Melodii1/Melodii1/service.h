#pragma once
#include "repo.h"

using namespace std;

class ServiceException :public exception {

};


class Service {
private:
    Repo& repo;

public:
    explicit Service(Repo& repo) :repo(repo) {};

    vector<Melodie>sort_lista(vector<Melodie> vect);
    vector<Melodie> get_all_srv() { return sort_lista(repo.get_all()); };
    void modifica_srv(int id, string titlu, int rank);
    vector<int> rank_fr();
    void remove_srv(int id);

};
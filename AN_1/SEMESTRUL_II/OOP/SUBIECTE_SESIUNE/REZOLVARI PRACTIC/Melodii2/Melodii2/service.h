#pragma once
#include "repo.h"
#include "map"

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
    map<string,int> artisti_fr();
    map<string, int> genuri_fr();

    void add(string titlu, string artist, string gen);
    void remove(int id);

};
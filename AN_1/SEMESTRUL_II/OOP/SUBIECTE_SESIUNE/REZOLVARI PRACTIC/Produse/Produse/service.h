#pragma once
#include <set>
#include "repo.h"
#include "observer.h"

using namespace std;

class ServiceException :public exception {

};
class Service :public Observable {
private:
    Repo& repo;

public:
    explicit Service(Repo& repo) :repo{ repo } {}

    vector<Produs> sort_lista(vector<Produs> vect);
    vector<Produs> get_all_srv() {
        return sort_lista(repo.get_all());
    }

    void valideaza(int id,string& nume,string& tip, double pret);
    void adauga(int id, string nume, string tip, double pret);
    vector<Produs> filtrare(double pret, vector<Produs> vect);
    int nr_tipuri(string tip);
    map<string, int> get_tipuri();
};
#pragma once
#include <map>
#include "vector"
#include "service.h"
#include <algorithm>
#include <iterator>  

vector<Produs> Service::sort_lista(vector<Produs> vect) {
    sort(vect.begin(), vect.end(), [](Produs& p1,Produs& p2) {
        return p1.get_pret() < p2.get_pret();
        });
    return vect;
}

void Service::valideaza(int id,string& nume,string& tip, double pret) {
    for (auto& p : get_all_srv()) {
        if (p.get_id() == id)
            throw ServiceException();
    }
    if (nume.empty() || tip.empty() || pret < 1.0 || pret>100.0)
        throw ServiceException();

}

void Service::adauga(int id, string nume, string tip, double pret) {
    valideaza(id, nume, tip, pret);
    repo.adauga(Produs(id, nume, tip, pret));
    notify();
}

vector<Produs> Service::filtrare(double pret, vector<Produs> vect) {
    vector<Produs> rez;
    copy_if(vect.begin(), vect.end(), back_inserter(rez), [pret](Produs& p1) {
        return p1.get_pret() < pret;
        });
    return rez;
}

int Service::nr_tipuri(string tip) {
    int k = 0;
    for (auto& p : get_all_srv()) {
        if (tip == p.get_tip())
            k++;
    }
    return k;
}

map<string, int> Service::get_tipuri() {
    map<string, int> rez;
    for (auto& p : get_all_srv()) {
        rez[p.get_tip()]++;
    }
    return rez;
}
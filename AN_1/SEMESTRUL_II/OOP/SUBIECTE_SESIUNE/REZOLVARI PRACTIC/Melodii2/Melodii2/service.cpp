#pragma once
#include "Service.h"
#include "algorithm"
#include "map"

vector<Melodie> Service::sort_lista(vector<Melodie> vect) {
    vector<Melodie> rez;
    sort(vect.begin(), vect.end(), [](Melodie& m1, Melodie& m2) {
        return m1.get_artist() < m2.get_artist();
        });

    return vect;
}

map<string, int> Service::genuri_fr() {
    map<string, int> rez;
    for (auto& m : get_all_srv()) {
        rez[m.get_gen()]++;
    }
    return rez;
}

map<string, int> Service::artisti_fr() {
    map<string, int> rez;
    for (auto& m : get_all_srv()) {
        rez[m.get_artist()]++;
    }
    return rez;
}

void Service::add(string titlu, string artist, string gen) {
    if (titlu != "" and artist != "" and (gen == "pop" || gen == "rock" || gen == "disco" || gen == "folk"))
        repo.adauga(Melodie(repo.get_all().size() + 1, titlu, artist, gen));
}

void Service::remove(int id) {
    repo.sterge(id);
}
#pragma once
#include "repo.h"
#include "fstream"
#include "string"
#include "sstream"
#include "cassert"

using namespace std;

//functie care preia datele din fisier si le adauga in lista cu care lucram
void Repo::load_from_file() {
    ifstream f("input.txt");
    string line;
    while (getline(f, line)) {
        string titlu, platforma;
        int pret, age;
        stringstream linestream(line);
        string curent;
        int nr = 0;
        while (getline(linestream, curent, ',')) {
            if (nr == 0)
                titlu = curent;
            if (nr == 1)
                pret = stoi(curent);
            if (nr == 2)
                platforma = curent;
            if (nr == 3)
                age = stoi(curent);
            nr++;
        }
        Joc j(titlu, pret, platforma, age);
        repo.push_back(j);
    }
}

//cauta in lista un joc pe baza titlului
Joc Repo::cauta(string titlu) {
    for (const auto& j : get_repo()) {
        if (j.get_titlu() == titlu)
            return j;
    }
    return Joc("null", -1, "null", -1);
}


void test_repo() {
    Repo repo;

    //test load_from_file
    assert(repo.get_repo().size() == 10);
    assert(repo.get_repo()[0].get_titlu() == "God Of War");

    //test cauta
    assert(repo.cauta("Forza 7").get_pret() == 190);
    assert(repo.cauta("NU_EXISTA").get_pret() == -1);
}

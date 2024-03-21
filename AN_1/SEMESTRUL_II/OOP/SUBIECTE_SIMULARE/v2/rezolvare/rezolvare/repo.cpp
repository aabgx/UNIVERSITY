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
        string nume,tara;
        int puncte,rank;
        stringstream linestream(line);
        string curent;
        int nr = 0;
        while (getline(linestream, curent, ',')) {
            if (nr == 0)
                nume = curent;
            if (nr == 1)
                tara = curent;
            if (nr == 2)
                puncte = stoi(curent);
            if (nr == 3)
                rank = stoi(curent);
            nr++;
        }
        Jucator j(nume, tara, puncte, rank);
        repo.push_back(j);
    }
}

//cauta un jucator pe baza numelui
Jucator Repo::cauta(string nume) {
    for (const auto& j : get_repo()) {
        if (j.get_nume() == nume)
            return j;
    }
    return Jucator("null","null",-1,-1);
}

//adauga un jucator in lista
void Repo::adauga(const Jucator& j) {
    repo.push_back(j);
}

void test_repo() {
    Repo repo;

    //test load_from_file
    assert(repo.get_repo().size() == 3);
    assert(repo.get_repo()[0].get_nume() == "Ana");

    //test cauta
    Jucator a("Ana", "Romania", 130, 3);
    assert(repo.cauta("Ana").get_nume() == a.get_nume());
    assert(repo.cauta("Ana").get_tara() == a.get_tara());

    assert(repo.cauta("nu_exista").get_nume() == "null");
    assert(repo.cauta("nu_exista").get_rank() == -1);

    //test adauga
    Jucator j("nume", "tara", 1, 100);
    repo.adauga(j);
    assert(repo.get_repo().size() == 4);
    assert(repo.get_repo()[3].get_nume() == "nume");
}

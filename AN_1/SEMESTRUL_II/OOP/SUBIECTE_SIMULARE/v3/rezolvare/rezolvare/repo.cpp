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
        string nume, tara;
        int puncte, rank;
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

//adauga un jucator in lista
void Repo::adauga(const Jucator& j) {
    repo.push_back(j);
}

void test_repo() {
    Repo repo;
    Jucator j("nume", "tara", 1, 100);
    assert(repo.get_repo().size() == 3);
    assert(repo.get_repo()[0].get_nume() == "Ana");

    repo.adauga(j);
    assert(repo.get_repo().size() == 4);
    assert(repo.get_repo()[3].get_nume() == "nume");
}

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
        string nume, tip;
        int buget_a, buget_e;
        stringstream linestream(line);
        string curent;
        int nr = 0;
        while (getline(linestream, curent, ',')) {
            if (nr == 0)
                nume = curent;
            if (nr == 1)
                tip = curent;
            if (nr == 2)
                buget_a = stoi(curent);
            if (nr == 3)
                buget_e = stoi(curent);
            nr++;
        }
        Proiect j(nume, tip, buget_a, buget_e);
        repo.push_back(j);
    }
}


void test_repo() {
    Repo repo;
    assert(repo.get_repo().size() == 10);
    assert(repo.get_repo()[0].get_nume() == "proiect1");

}

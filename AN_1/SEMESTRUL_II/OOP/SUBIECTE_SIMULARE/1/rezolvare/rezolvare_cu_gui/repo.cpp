#pragma once
#include "repo.h"
#include "fstream"
#include "string"
#include "sstream"
#include "cassert"

using namespace std;

void Repo::load_from_file() {
    ifstream f("input.txt");
    string line;
    while (getline(f, line)) {
        string nume, prenume, sectie;
        bool concediu;
        int cnp;
        stringstream linestream(line);
        string curent;
        int nr = 0;
        while (getline(linestream, curent, ',')) {
            if (nr == 0)
                cnp = stoi(curent);
            if (nr == 1)
                nume = curent;
            if (nr == 2)
                prenume = curent;
            if (nr == 3)
                sectie = curent;
            if (nr == 4)
                concediu = stoi(curent);
            nr++;
        }
        Doctor d(cnp, nume, prenume, sectie, concediu);
        repo.push_back(d);
    }

}

int Repo::cauta(string nume, string prenume) {
    for (const auto& d : get_repo()) {
        if (d.get_nume() == nume && d.get_prenume() == prenume)
            return d.get_cnp();
    }
    return -1;
}

void test_repo() {
    Repo repo;
    //printf("%d", repo.get_repo().size());
    assert(repo.get_repo().size() == 10);
    assert(repo.get_repo()[0].get_cnp() == 1);
}

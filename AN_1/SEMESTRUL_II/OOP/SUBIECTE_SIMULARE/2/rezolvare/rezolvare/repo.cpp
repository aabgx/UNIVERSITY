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
        string tip, model, culoare;
        int an, pret;
        stringstream linestream(line);
        string curent;
        int nr = 0;
        while (getline(linestream, curent, ',')) {
            if (nr == 0)
                tip = curent;
            if (nr == 1)
                model = curent;
            if (nr == 2)
                an = stoi(curent);
            if (nr == 3)
                culoare = curent;
            if (nr == 4)
                pret = stoi(curent);
            nr++;
        }
        Device d(tip, model, an, culoare, pret);
        repo.push_back(d);
    }
}
Device Repo::cauta(string model, int pret) {
        for (const auto& d : get_repo()) {
            if (d.get_model() == model && d.get_pret() == pret)
                return d;
        }
        return Device("null","null",-1,"null",-1);
    }

void test_repo() {
    Repo repo;
    assert(repo.get_repo().size() == 7);
    assert(repo.get_repo()[0].get_tip() == "tip1");
}

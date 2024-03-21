#pragma once
#include "repo.h"
#include "domain.h"
#include "fstream"
#include "sstream"

void Repo::load_from_file() {
    list.clear();
    ifstream f("input.txt");
    string line;
    while (getline(f, line)) {
        int nr = 0;
        string curent;
        stringstream linestream(line);
        int id;
        string titlu, tip;
        double pret;
        while (getline(linestream, curent, ',')) {
            if (nr == 0)
                id = stoi(curent);
            if (nr == 1)
                titlu = curent;
            if (nr == 2)
                tip = curent;
            if (nr == 3)
                pret = stod(curent);
            nr++;
        }
        list.push_back(Produs(id, titlu, tip, pret));
    }
    f.close();
}

void Repo::save_to_file() {
    ofstream g("input.txt");
    for (auto& p : list) {
        g << p.get_id() << "," << p.get_nume() << "," << p.get_tip() << "," << p.get_pret() << endl;
    }
    g.close();
}
#pragma once
#include <sstream>
#include "repo.h"
#include "fstream"
#include "algorithm"

void Repo::load_from_file() {
    v.clear();
    ifstream f("input.txt");
    string line;
    while (getline(f, line)) {
        string titlu, artist, gen;
        int id;
        stringstream linestream(line);
        string curent;
        int nr = 0;
        while (getline(linestream, curent, ',')) {
            if (nr == 0)
                id = stoi(curent);
            if (nr == 1)
                titlu = curent;
            if (nr == 2)
                artist = curent;
            if (nr == 3)
                gen = curent;
            nr++;
        }
        v.push_back(Melodie(id, titlu, artist, gen));
    }
    f.close();
}

void Repo::save_to_file() {
    ofstream g("input.txt");
    for (auto& m : v) {
        g << m.get_id() << "," << m.get_titlu() << "," << m.get_artist() << "," << m.get_gen() << endl;
    }
    g.close();
}

void Repo::sterge(int& id) {
    int i = 0;
    for (auto& m : v) {
        if (m.get_id() == id) {
            v.erase(v.begin() + i);
            save_to_file();
            return;
        }
        i++;
    }
}
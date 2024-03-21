#pragma once
#include <sstream>
#include "Repo.h"
#include "fstream"
#include "algorithm"
#include <cassert> 

void Repo::load_from_file() {
    v.clear();
    ifstream f("input.txt");
    string line;
    while (getline(f, line)) {
        string titlu, artist;
        int id, rank;
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
                rank = stoi(curent);
            nr++;
        }
        v.push_back(Melodie(id, titlu, artist, rank));
    }
    f.close();
}

void Repo::save_to_file(vector<Melodie> lst) {
    ofstream g("input.txt");
    for (auto& m : lst) {
        g << m.get_id() << "," << m.get_titlu() << "," << m.get_artist() << "," << m.get_rank() << endl;
    }
    g.close();
}

void Repo::remove(int id) {
    int i = 0;
    for (auto& m : v) {
        if (m.get_id() == id) {
            v.erase(v.begin() + i);
        }
        i++;
    }
    save_to_file(get_all());

}

void Repo::modifica(int id, string titlu, int rank) {
    for (auto& m : v) {
        if (m.get_id() == id) {
            m.set_titlu(titlu);
            m.set_rank(rank);
        }
        save_to_file(get_all());
        load_from_file();
    }

}

void test_get_all() {
    Repo repo;
    auto lst = repo.get_all(), aux = repo.get_all();
    assert(lst.size() == 9);
}

void test_remove() {
    Repo repo;
    vector<Melodie> aux = repo.get_all();
    assert(repo.get_all().size() == 9);
    repo.remove(1);
    assert(repo.get_all().size() == 8);
    for (auto& el : repo.get_all())
        if (el.get_id() == 1) assert(false);
    repo.save_to_file(aux);
}

void test_modifica() {
    Repo repo;
    vector<Melodie> aux = repo.get_all();
    assert(repo.get_all().size() == 9);
    repo.modifica(1,"pune asta",4);
    assert(repo.get_all().size() == 9);
    for (auto& el : repo.get_all())
        if (el.get_id() == 1)
        {
            assert(el.get_titlu() == "pune asta");
            assert(el.get_rank() == 4);
        }
    repo.save_to_file(aux);
}
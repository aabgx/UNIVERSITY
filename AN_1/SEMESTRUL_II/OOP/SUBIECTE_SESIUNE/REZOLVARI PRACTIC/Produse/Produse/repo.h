#pragma once
#include "vector"
#include "domain.h"

using namespace std;

class Repo {
private:
    vector<Produs> list;

    void load_from_file();
    void save_to_file();

public:
    Repo() {
        load_from_file();
    }

    vector<Produs> get_all() { return list; }
    void adauga(const Produs& p) { list.push_back(p); save_to_file(); }

};
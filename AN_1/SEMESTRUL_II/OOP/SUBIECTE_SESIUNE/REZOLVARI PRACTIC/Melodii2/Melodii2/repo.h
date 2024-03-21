#pragma once
#include "vector"
#include "domain.h"
class RepoException :public exception {

};
class Repo {
private:
    vector<Melodie> v;
    void load_from_file();
    void save_to_file();
public:
    Repo(const Repo& ot) = delete;
    Repo() {
        load_from_file();
    };
    vector<Melodie> get_all() { return v; };

    void adauga(const Melodie& m) {
        v.push_back(m);
        save_to_file();
    };
    void sterge(int& id);
};
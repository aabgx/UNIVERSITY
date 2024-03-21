#pragma once
#include "vector"
#include "domain.h"
class RepoException :public exception {

};
class Repo {
private:
    vector<Melodie> v;
    void load_from_file();
public:
    void save_to_file(vector<Melodie> lst);
    Repo(const Repo& ot) = delete;
    Repo() {
        load_from_file();
    };
    vector<Melodie>& get_all() { return v; };
    void remove(int id);
    void modifica(int id, string titlu, int rank);

};

void test_get_all();
void test_remove();
void test_modifica();
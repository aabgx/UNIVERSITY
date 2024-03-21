#pragma once 
#include "domain.h"
#include "vector"

using namespace std;

class Repo {
private:
    vector<Doctor> repo;

    void load_from_file();
public:
    Repo() {
        load_from_file();
    };
    Repo(const Repo& ot) = delete;
    const vector<Doctor>& get_repo() { return this->repo; };
    int cauta(string nume, string prenume);

};
void test_repo();
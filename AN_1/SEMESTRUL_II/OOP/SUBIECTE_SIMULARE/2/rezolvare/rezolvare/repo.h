#pragma once 
#include "domain.h"
#include "vector"

using namespace std;

class Repo {
private:
    vector<Device> repo;

    void load_from_file();
public:
    Repo() {
        load_from_file();
    };
    Repo(const Repo& ot) = delete;
    const vector<Device>& get_repo() { return this->repo; };
    Device cauta(string model, int pret);

};
void test_repo();
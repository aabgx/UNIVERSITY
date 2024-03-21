#pragma once 
#include "domain.h"
#include "vector"

using namespace std;

class Repo {
    friend class Service;
    friend class GUI;
private:
    vector<Jucator> repo;

    //functie care preia datele din fisier si le adauga in lista cu care lucram
    void load_from_file();

public:
    Repo() {
        load_from_file();
    };

    //destructor, nu vrem sa avem copii pt repo-ul nostru
    Repo(const Repo& ot) = delete;

    //getter pt lista cu care se lucreaza
    vector<Jucator>& get_repo() { return this->repo; };

    //cauta un jucator pe baza numelui
    Jucator cauta(string nume);

    //adauga un jucator in lista
    void adauga(const Jucator& j);
};
void test_repo();
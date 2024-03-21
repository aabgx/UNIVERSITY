#pragma once 
#include "domain.h"
#include "vector"

using namespace std;

class Repo {
    friend class Service;
    friend class GUI;
private:
    vector<Joc> repo;

    //functie care preia datele din fisier si le adauga in lista cu care lucram
    void load_from_file();

public:
    Repo() {
        load_from_file();
    };

    //destructor, nu vrem sa avem copii pt repo-ul nostru
    Repo(const Repo& ot) = delete;

    //getter pt lista cu care se lucreaza
    vector<Joc>& get_repo() { return this->repo; };

    //cauta in lista un joc pe baza titlului
    Joc cauta(string titlu);

};
void test_repo();
//PROBLEMA 4 filtreaza, sorteaza, TABEL/LIST selectare, colorare list
#pragma once
#include "string"
using namespace std;
class Joc {
private:
    int pret, age;
    string titlu, platforma;
public:
    //constructor,destructor
    Joc() = delete;
    Joc(string titlu, int pret, string platforma,int age) :titlu{ titlu }, pret{ pret }, platforma{ platforma }, age{ age }{};

    //gettere pentru fiecare camp
    string get_titlu() const { return this->titlu; };
    int get_pret()const { return this->pret; };
    string get_platforma()const { return this->platforma; };
    int get_age() { return this->age; };

    //suprascrie functia de transformare in string pt a se mula pe ce vrem noi sa afisam
    string str();
};
void test_domain();

#pragma once
#include "string"

using namespace std;
class Doctor {
private:
    int cnp;
    string nume, prenume, sectie;
    bool concediu;
public:
    Doctor() = delete;
    Doctor(int cnp, string nume, string prenume, string sectie, bool concediu) :cnp{ cnp }, nume{ nume }, prenume{ prenume }, sectie{ sectie }, concediu{ concediu }{};
    int get_cnp() const { return this->cnp; };
    string get_nume()const { return this->nume; };
    string get_prenume()const { return this->prenume; };
    string get_sectie()const { return this->sectie; };
    bool get_concediu() const { return this->concediu; };
    string str();
};
void test_domain();

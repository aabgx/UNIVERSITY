#pragma once
#include "string"
using namespace std;
class Proiect {
private:
    int buget_a, buget_e;
    string nume, tip;
public:
    //constructor,destructor
    Proiect() = delete;
    Proiect(string nume, string tip, int buget_a, int buget_e) :nume{ nume }, tip{ tip }, buget_a{ buget_a }, buget_e{ buget_e }{};

    //gettere pentru fiecare camp
    string get_nume() const { return this->nume; };
    string get_tip()const { return this->tip; };
    int get_buget_a()const { return this->buget_a; };
    int get_buget_e()const { return this->buget_e; };

    void set_buget_a(int n);

    //suprascrie functia de transformare in string pt a se mula pe ce vrem noi sa afisam
    string str();
};
void test_domain();

#pragma once
#include "string"
using namespace std;

class Produs {
    int id;
    string nume, tip;
    double pret;

public:
    Produs(int id, string nume, string tip, double pret) :id{ id }, nume{ nume }, tip(tip), pret{ pret }{}
    int get_id(){ return id; }
    string get_nume(){ return nume; };
    string get_tip(){ return tip; }
    double get_pret(){ return pret; }

    int nr_vocale();
};
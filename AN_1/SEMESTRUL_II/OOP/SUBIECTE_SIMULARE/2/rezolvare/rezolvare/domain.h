#pragma once
#include "string"
using namespace std;
class Device {
private:
    int an, pret;
    string model, tip, culoare;
public:
    Device() = delete;
    Device(string tip, string model, int an, string culoare, int pret) :tip{ tip }, model{model}, an{ an }, culoare{culoare}, pret{pret}{};
    string get_tip() const { return this->tip; };
    string get_model()const { return this->model; };
    int get_an()const { return this->an; };
    string get_culoare()const { return this->culoare; };
    int get_pret() const { return this->pret; };
    string str();
};
void test_domain();

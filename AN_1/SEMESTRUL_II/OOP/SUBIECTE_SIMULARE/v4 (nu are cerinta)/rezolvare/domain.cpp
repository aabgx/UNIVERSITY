#pragma once
#include "domain.h"
#include "assert.h"

//suprascrie functia de transformare in string pt a se mula pe ce vrem noi sa afisam
string Proiect::str() {
    return this->nume + " " + this->tip + " " + to_string(this->buget_a) + " " + to_string(this->buget_e);
}

void Proiect::set_buget_a(int n) {
    this->buget_a = n;
}

void test_domain() {
    Proiect a("nume", "tip", 200, 300);
    //test gettere
    assert(a.get_nume() == "nume");
    assert(a.get_tip() == "tip");
    assert(a.get_buget_a() == 200);
    assert(a.get_buget_e() == 300);

    //test settere
    a.set_buget_a(4);
    assert(a.get_buget_a() == 4);

    //test str
    string msg = a.get_nume() + " " + a.get_tip() + " " + to_string(a.get_buget_a()) + " " + to_string(a.get_buget_e());
    assert(msg == a.str());
}
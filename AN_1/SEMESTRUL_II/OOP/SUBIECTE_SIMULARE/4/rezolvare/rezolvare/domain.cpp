#pragma once
#include "domain.h"
#include "assert.h"

//suprascrie functia de transformare in string pt a se mula pe ce vrem noi sa afisam
string Joc::str() {
    return this->titlu + " " + to_string(this->pret) + " " + this->platforma + " " + to_string(this->age);
}

void test_domain() {
    Joc a("titlu", 1, "platforma", 3);
    //test gettere
    assert(a.get_titlu() == "titlu");
    assert(a.get_pret() == 1);
    assert(a.get_platforma() == "platforma");
    assert(a.get_age() == 3);

    //test str
    string msg = a.get_titlu() + " " + to_string(a.get_pret()) + " " + a.get_platforma() + " " + to_string(a.get_age());
    assert(msg == a.str());

}
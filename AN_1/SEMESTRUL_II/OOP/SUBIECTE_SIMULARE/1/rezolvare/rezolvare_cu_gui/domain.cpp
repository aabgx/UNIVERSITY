#pragma once
#include "domain.h"
#include "assert.h"

string Doctor::str() {
    return this->nume + " " + this->prenume + " - " + this->sectie;
}

void test_domain() {
    Doctor d(1, "Dan", "Balan", "Onco", true);
    assert(d.get_cnp() == 1);
    assert(d.get_nume() == "Dan");
    assert(d.get_prenume() == "Balan");
    assert(d.get_sectie() == "Onco");
    assert(d.get_concediu() == true);
}
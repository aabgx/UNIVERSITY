#pragma once
#include "domain.h"
#include "assert.h"

//settere pentru campurile de puncte si rank
void Jucator::set_puncte(int puncte) {
    this->puncte = puncte;
}
void Jucator::set_rank(int rank) {
    this->rank = rank;
}

//suprascrie functia de transformare in string pt a se mula pe ce vrem noi sa afisam
string Jucator::str() {
    return this->nume + " " + this->tara + " " + to_string(this->puncte) +" " + to_string(this->rank);
}

void test_domain() {
    Jucator a("Ana", "Romania", 200, 3);
    //test gettere
    assert(a.get_nume() == "Ana");
    assert(a.get_tara() == "Romania");
    assert(a.get_puncte() == 200);
    assert(a.get_rank() == 3);

    //test settere
    a.set_rank(4);
    assert(a.get_rank() == 4);
    a.set_puncte(400);
    assert(a.get_puncte() == 400);

    //test str
    string msg = a.get_nume() + " " + a.get_tara() + " " + to_string(a.get_puncte()) + " " + to_string(a.get_rank());
    assert(msg == a.str());

}
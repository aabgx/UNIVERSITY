#pragma once
#include "string"
using namespace std;
class Jucator {
private:
    int rank, puncte;
    string nume, tara;
public:
    //constructor,destructor
    Jucator() = delete;
    Jucator(string nume, string tara, int puncte, int rank) :nume{ nume }, tara{ tara }, puncte{ puncte }, rank{ rank }{};

    //gettere pentru fiecare camp
    string get_nume() const { return this->nume; };
    string get_tara()const { return this->tara; };
    int get_puncte()const { return this->puncte; };
    int get_rank()const { return this->rank; };

    //setter pentru rank
    void set_rank(int rank);

    //suprascrie functia de transformare in string pt a se mula pe ce vrem noi sa afisam
    string str();
};
void test_domain();

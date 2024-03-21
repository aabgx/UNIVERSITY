#include "IteratorDictionar.h"
#include "Dictionar.h"
#include <exception>

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d) {
	/*
	Complexitate in cazul favorabil = Complexitate in cazul defavorabil = Complexitate in cazul mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/
	current = dict.prim;
}


void IteratorDictionar::prim() {
	/*
	Complexitate in cazul favorabil = Complexitate in cazul defavorabil = Complexitate in cazul mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/
	current = dict.prim;
}


void IteratorDictionar::urmator() {
	/*
	Complexitate in cazul favorabil = Complexitate in cazul defavorabil = Complexitate in cazul mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/
	if (current == -1) throw exception("Nu este valid!");
	current = dict.urm[current];
}


TElem IteratorDictionar::element() const {
	/*
	Complexitate in caz favorabil = Theta(1)
	Complexitate in caz defavorabil = Theta(1)
	Complexitate in caz mediu = Theta(1)
	Complexitate generala: O(1)
	*/
	if (current == -1) throw exception("Nu este valid!");
	return dict.e[current];
}


bool IteratorDictionar::valid() const {
	/*
	Complexitate in cazul favorabil = Complexitate in cazul defavorabil = Complexitate in cazul mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/

	if (current != -1)return true;
	return false;
}


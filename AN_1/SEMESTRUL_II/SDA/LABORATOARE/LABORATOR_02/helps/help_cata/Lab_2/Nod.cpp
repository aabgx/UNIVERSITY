#pragma once
#include "Nod.h"

Nod::Nod(TElem valoare, int i, int j, PNod urm) {
	/*
	Caz favorabil = Caz defavorabil = Caz mediu = Theta(1)
	Complexitate generala: Theta(1)
	*/
	Triplet tr;
	tr.val = valoare;
	tr.i = i;
	tr.j = j;
	this->util = tr;
	this->urm = urm;
}

void Nod::set_valoare(TElem valoare) {
	/*
	Caz favorabil = Caz defavorabil = Caz mediu = Theta(1)
	Complexitate generala: Theta(1)
	*/
	this->util.val = valoare;
}

PNod Nod::urmator() {
	/*
	Caz favorabil = Caz defavorabil = Caz mediu = Theta(1)
	Complexitate generala: Theta(1)
	*/
	return this->urm;
}
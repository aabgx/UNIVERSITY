#include "Matrice.h"

#include <exception>
#include <iostream>

using namespace std;


Matrice::Matrice(int m, int n) {
	/*
	Caz favorabil = Caz defavorabil = Caz mediu = Theta(1)
	Complexitate generala: Theta(1)
	*/
	n_linii = m;
	n_coloane = n;
	prim = nullptr;
}



int Matrice::nrLinii() const {
	/*
	Caz favorabil = Caz defavorabil = Caz mediu = Theta(1)
	Complexitate generala: Theta(1)
	*/
	return n_linii;
}


int Matrice::nrColoane() const {
	/*
	Caz favorabil = Caz defavorabil = Caz mediu = Theta(1)
	Complexitate generala: Theta(1)
	*/
	return n_coloane;
}


TElem Matrice::element(int i, int j) const {
	/* 
	Caz favorabil = Theta(1)
	Caz defavorabil = Theta(n*m)
	Caz mediu = Theta(n*m)
	Complexitate generala: O(n*m)
	*/
	if (i<0 || i>n_linii || j<0 || j>n_coloane) {
		throw std::exception("Nu e element valid");
	}
	PNod current = prim;
	while (current != nullptr)
	{
		int idx_i = current->util.i, idx_j = current->util.j;
		if (idx_i == i && idx_j == j) {
			return current->util.val;
		}
		current = current->urmator();
	}
	return NULL_TELEMENT;
}



TElem Matrice::modifica(int i, int j, TElem e) {
	/*
	Caz favorabil = Theta(1)
	Caz defavorabil = Theta(n*m)
	Caz mediu = Theta(n*m)
	Complexitate generala: O(n*m)
	*/
	if (i<0 || i>n_linii || j<0 || j>n_coloane) {
		throw std::exception("Nu e element valid");
	}

	PNod current = prim;
	PNod prev = nullptr;

	while (current != nullptr) {
		int idx_i = current->util.i, idx_j = current->util.j;
		if (idx_i == i && idx_j == j) {
			TElem vechi = current->util.val;
			current->set_valoare(e);
			return vechi;
		}
		if (idx_i >= i && idx_j > j || idx_i > i) {
			PNod ptr = new Nod(e, i, j, current->urm);
			current->urm = ptr;
			return NULL_TELEMENT;
		}
		prev = current;
		current = current->urmator();
	}
	if (prev != nullptr) {
		PNod ptr = new Nod(e, i, j,nullptr);
		prev->urm = ptr;
		return NULL_TELEMENT;
	}
	else {
		PNod ptr = new Nod(e, i, j, nullptr);
		this->prim = ptr;
		return NULL_TELEMENT;
	}
}



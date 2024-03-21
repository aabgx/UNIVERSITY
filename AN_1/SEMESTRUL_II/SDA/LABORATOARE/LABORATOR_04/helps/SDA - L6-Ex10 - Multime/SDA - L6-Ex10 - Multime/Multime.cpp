#include "Multime.h"
#include "IteratorMultime.h"

#include <iostream>
#include <math.h>

int hashCode(TElem e) {
	return abs(e);
}

//functia de dispersie
int Multime::d(TElem e) const {
	return hashCode(e) % this->m;
}

Multime::Multime() {
	this->m = MAX;
	this->dimensiune = 0;
	for(int i = 0; i < this->m; i++) {
		this->e[i] = INIT;
		this->urm[i] = INIT;
	}
	this->primLiber = 0;
}

void Multime::actPrimLiber() {
	this->primLiber++;
	while (this->primLiber < m && this->e[this->primLiber] != INIT)
		this->primLiber++;
}


bool Multime::adauga(TElem elem) {
	int i = d(elem);
	if (this->e[i] == INIT) { // daca pozitia e libera se adauga elem
		this->e[i] = elem;
		this->dimensiune++;
		if (i == this->primLiber)
			actPrimLiber();
		return true;
	}
	int j = -1; // j e pozitia precedenta lui i
	while (i != INIT && this->e[i] != elem) {
		j = i;
		i = this->urm[i];
	}
	if (this->e[i] == elem)
		return false;
	if (this->primLiber >= this->m) {
		return false;
		throw std::exception("Nu mai exista spatiu!");
	}

	//std::cout << this->primLiber << " ";
	this->e[this->primLiber] = elem;
	this->urm[j] = this->primLiber;
	this->dimensiune++;
	actPrimLiber();
}


bool Multime::sterge(TElem elem) {
	int i = d(elem);
	int j = INIT, k = 0;
	while (k < this->m && j == INIT) {
		if (this->urm[k] == i)
			j = k;
		else k++;
		//if (k == i)
		//	j = k;
		//else k++;
	}
	while (i != INIT && this->e[i] != elem) {
		j = i;
		i = this->urm[i];
	}
	if (i == INIT) {
		//this->e[j] = NULL_TELEM;
		//this->m = MAX;
		return false;
	}
	else {
		bool gata = false;
		do {
			int p = this->urm[i];
			int pp = i;  //precedentul lui p
			while (p != INIT && this->e[p] != i) {
				pp = p;
				p = this->urm[p];
			}
			if (p == INIT)
				gata = true;
			else {
				this->e[i] = this->e[p];
				i = p;
				j = pp;
			}
		} while (gata == false);
		if (j != INIT) // daca nu exista anterior
			this->urm[j] = this->urm[i];
		this->e[i] = NULL_TELEM;
		this->urm[i] = INIT;
		if (i < this->primLiber)
			this->primLiber = i;
		this->dimensiune--;
		return true;
	}
}


bool Multime::cauta(TElem elem) const {
	/* O(nrElem) */
	int i = d(elem);
	int j = -1, k = 0;
	while (k < this->m && j == -1) {
		//if (this->urm[k] == i)
		//	j = k;
		//else k++;
		if (k == i)
			j = k;
		else k++;
	}
	while (j != INIT && this->e[i] != elem) {
		j = i;
		i = this->urm[i];
	}
	if (this->e[i] == elem)
		return true;
	else return false;
}


int Multime::dim() const {
	/* Theta(1) */
	return this->dimensiune;
}

bool Multime::vida() const {
	/* Theta(1) */
	if (this->dimensiune)
		return false;
	return true;
}


Multime::~Multime() {
	/* de adaugat */
}



IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}


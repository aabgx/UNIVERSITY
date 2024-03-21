#pragma once

#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>
#include <cmath>

using namespace std;

Multime::Multime() {
	this->max = 0;
	this->idx_of_0 = 0;
	this->elems = new bool[1];
	this->elems[0] = 0;
}

void Multime::redim(int maxim) {
	bool* noul_ptr = new bool[2 * maxim + 1];
	int noul_idx_of_0 = maxim ,i;
	for (i = 0; i <= 2 * maxim; ++i) {
		noul_ptr[i] = 0;
	}
	for (i = 0; i <= 2*this->max; ++i) {
		if (this->elems[i] == 1) {
			int elm = i - this->idx_of_0;
			noul_ptr[noul_idx_of_0 + elm] = 1;
		}
	}
	delete this->elems;
	this->elems = noul_ptr;
	this->idx_of_0 = noul_idx_of_0;
	this->max = maxim;
}


bool Multime::adauga(TElem elem) {
	/* de adaugat */
	if (abs(elem) > this->max) {
		redim(abs(elem));
	}
	bool ok = false;
	if (this->elems[this->idx_of_0 + elem] == 0)ok = true;
	this->elems[this->idx_of_0 + elem] = 1;
	return ok;
}


bool Multime::sterge(TElem elem) {
	/* de adaugat */
	if (abs(elem) > this->max)
		return false;
	if(this->elems[this->idx_of_0+elem]==0)
		return false;
	this->elems[this->idx_of_0 + elem] = 0;
	return true;

}


bool Multime::cauta(TElem elem) const {
	/* de adaugat */
	if (abs(elem) > this->max)return false;
	if (this->elems[this->idx_of_0 + elem] == 0)
		return false;
	return true;
}


int Multime::dim() const {
	/* de adaugat */
	int i,cnt = 0;
	for (i = 0; i <= 2 * this->max; ++i)
		if (this->elems[i] == 1)++cnt;
	return cnt;
}

bool Multime::vida() const {
	/* de adaugat */
	int i;
	for (i = 0; i <= 2 * this->max; ++i)
		if (this->elems[i] == 1)return false;
	return true;
}


Multime::~Multime() {
	/* de adaugat */
	delete this->elems;
}



IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}


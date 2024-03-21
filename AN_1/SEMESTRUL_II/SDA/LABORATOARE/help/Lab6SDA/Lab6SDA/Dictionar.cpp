#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

//Theta(1)
int Dictionar::d(TCheie c) const
{
	return abs(c) % m;
}

//Theta(m)
Dictionar::Dictionar() {
	m = MAX;
	for (int i = 0; i < m; i++)
		l[i] = NULL;
}

//Theta(m+n), unde n=numarul de elemente din dictionar 
Dictionar::~Dictionar() {
	for (int i = 0; i < m; i++) {
		while (l[i] != NULL) {
			Nod* n = l[i];
			l[i] = l[i]->urm;
			delete(n);
		}
	}
}

//O(n), unde n=numarul de elemente din dictionar ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
TValoare Dictionar::adauga(TCheie c, TValoare v) {
	TValoare k = cauta(c);
	if (k == NULL_TVALOARE) {
		Nod* x = new Nod();
		x->e = TElem(c, v);
		x->urm = NULL;
		x->urm = l[d(c)];
		l[d(c)] = x;
	}
	else {
		Nod* n = l[d(c)];
		while (n != NULL && n->e.first != c)
			n = n->urm;
		n->e = TElem(c, v);
	}
	return k;
}

//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
//O(n), unde n=numarul de elemente din dictionar ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
TValoare Dictionar::cauta(TCheie c) const {
	Nod* n = l[d(c)];
	while (n != NULL && n->e.first != c)
		n = n->urm;
	if (n == NULL)
		return NULL_TVALOARE;
	else
		return n->e.second;
}

//O(n), unde n=numarul de elemente din dictionar ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
TValoare Dictionar::sterge(TCheie c) {
	TValoare k = cauta(c);
	if(k==NULL_TVALOARE)
		return NULL_TVALOARE;
	else {
		Nod* n = l[d(c)];
		Nod* prec = NULL;
		while (n != NULL && n->e.first != c) {
			prec = n;
			n = n->urm;
		}
		if (prec == NULL)
			l[d(c)] = n->urm;
		else
			prec->urm = n->urm;
		return k;
	}
}

//Theta(n), unde n=numarul de elemente din dictionar 
int Dictionar::dim() const {
	IteratorDictionar it = this->iterator();
	it.prim();
	int k = 0;
	while (it.valid()) {
		k++;
		it.urmator();
	}
	return k;
}

//Theta(1)
bool Dictionar::vid() const {
	return dim()==0;
}

//Theta(1)
IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}


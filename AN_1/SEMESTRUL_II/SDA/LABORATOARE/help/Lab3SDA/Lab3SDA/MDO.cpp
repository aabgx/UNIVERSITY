#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

//Theta(1)
MDO::MDO(Relatie r) {
	lista = new LSIchei();
	lista->prim = NULL;
	rel = r;
}

//O(n+m), n=nr de chei, m=nr de valori ale unei chei ( caz favorabil: Theta(1), caz defavorabil: Theta(n+m) )
void MDO::adauga(TCheie c, TValoare v) {
	NodLSIchei* cheie = lista->prim;
	NodLSIchei* prev = new NodLSIchei();
	prev = NULL;
	if (cheie == NULL) {
		NodLSIchei* nod = new NodLSIchei();
		nod->e = c;
		NodLSIval* val = new NodLSIval();
		val->e = v;
		val->urm = NULL;
		LSIval* lval = new LSIval();
		lval->prim = val;
		nod->valori = lval;
		lista->prim = nod;
		return;
	}
	while (cheie->urm != NULL && rel(cheie->e, c) && cheie->e != c) {
		prev = cheie;
		cheie = cheie->urm;
	}
	if (cheie->e == c) {
		NodLSIval* nou = new NodLSIval();
		nou->e = v;
		nou->urm = NULL;
		if (cheie->valori->prim == NULL)
			cheie->valori->prim = nou;
		else {
			NodLSIval* val = cheie->valori->prim;
			while (val->urm != NULL)
				val = val->urm;
			val->urm = nou;
		}
	}
	else {
		NodLSIchei* cnoua = new NodLSIchei();
		cnoua->e = c;
		LSIval* lv = new LSIval();
		NodLSIval* nv = new NodLSIval();
		nv->e = v;
		nv->urm = NULL;
		lv->prim = nv;
		cnoua->valori = lv;
		if (cheie == lista->prim && !rel(cheie->e, c)) {
			lista->prim = cnoua;
			cnoua->urm = cheie;
			return;
		}
		if (cheie->urm == NULL && rel(cheie->e, c)) {
			cnoua->urm = NULL;
			cheie->urm = cnoua;
			return;
		}
		cnoua->urm = cheie;
		prev->urm = cnoua;
	}
}

//O(n*m) ( caz favorabil: Theta(m), caz defavorabil: Theta(n*m) )
vector<TValoare> MDO::cauta(TCheie c) const {
	NodLSIval* v;
	vector<TValoare> vect;
	NodLSIchei* cheie = lista->prim;
	while (cheie != NULL) {
		if (cheie->e == c) {
			v = cheie->valori->prim;
			while (v != NULL) {
				vect.push_back(v->e);
				v = v->urm;
			}
			return vect;
		}
		cheie = cheie->urm;
	}
	return vect;
}


//O(m) ( caz favorabil: Theta(1), caz defavorabil: Theta(m) )
bool MDO::stergeVal(LSIval* val, TValoare v) {
	NodLSIval* nv = val->prim;
	NodLSIval* prev = new NodLSIval();
	prev = NULL;
	while (nv != NULL) {
		if (nv->e == v) {
			if (nv == val->prim)
				val->prim = nv->urm;
			if (prev != NULL)
				prev->urm = nv->urm;
			return true;
		}
		prev = nv;
		nv = nv->urm;
	}
	return false;
}

//O(n) ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
void MDO::stergeCheie(TCheie c) {
	NodLSIchei* cheie = lista->prim;
	NodLSIchei* prev = new NodLSIchei();
	prev = NULL;
	while (cheie->urm != NULL && cheie->e != c) {
		prev = cheie;
		cheie = cheie->urm;
	}
	if (cheie != NULL) {
		if (cheie == lista->prim)
			lista->prim = cheie->urm;
		if (prev != NULL)
			prev->urm = cheie->urm;
	}
}

//O(n*m) ( caz favorabil: Theta(1), caz defavorabil: Theta(n*m) )
bool MDO::sterge(TCheie c, TValoare v) {
	NodLSIchei* cheie = lista->prim;
	while (cheie != NULL) {
		if (cheie->e == c) {
			bool sters = stergeVal(cheie->valori, v);
			if (sters) {
				if (cheie->valori->prim == NULL) {
					stergeCheie(cheie->e);
				}
			}
			return sters;
		}
		cheie = cheie->urm;
	}
	return false;
}

//Theta(n*m)
int MDO::dim() const {
	NodLSIchei* cheie = lista->prim;
	if (cheie == NULL)
		return 0;
	int d = 0;
	while (cheie != NULL) {
		NodLSIval* v = cheie->valori->prim;
		while (v != NULL) {
			d++;
			v = v->urm;
		}
		cheie = cheie->urm;
	}
	return d;
}

//Theta(1)
bool MDO::vid() const {
	return lista->prim == NULL;
}

//Theta(1)
IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

//Theta(n)
int MDO::nrchei() const
{
	int k = 0;
	NodLSIchei* cheie = lista->prim;
	while (cheie != NULL) {
		k++;
		cheie = cheie->urm;
	}
	return k;
}

//Theta(n*m)
MDO::~MDO() {
	NodLSIchei* cheie = lista->prim;
	while (cheie != NULL) {
		NodLSIval* v = cheie->valori->prim;
		while (v != NULL) {
			NodLSIval* aux = v->urm;
			delete(v);
			v = aux;
		}
		delete(v);
		NodLSIchei* aux2 = cheie->urm;
		delete(cheie->valori);
		delete(cheie);
		cheie = aux2;
	}
	delete(cheie);
	delete(lista);
}
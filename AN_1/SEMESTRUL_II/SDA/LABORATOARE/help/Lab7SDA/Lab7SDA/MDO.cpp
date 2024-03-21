#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

//O(h), unde h=inaltimea arborelui ( caz favorabil: Theta(1), caz defavorabil: Theta(h) )
Nod* MDO::adauga_rec(Nod* r, Nod* prec, TCheie c, TValoare v)
{
	if (r == NULL) {
		r = new Nod();
		r->e = c;
		r->st = NULL;
		r->dr = NULL;
		r->lg = 1;
		r->cp = 10;
		r->v = new TValoare[r->cp];
		r->v[0] = v;
		if (prec == NULL) {
			rad = r;
			r->parinte = NULL;
		}
		else {
			if (rel(r->e, prec->e))
				prec->st = r;
			else
				prec->dr = r;
			r->parinte = prec;
		}
	}
	else {
		if (r->e == c) {
			if (r->lg == r->cp)
				redimens(r);
			r->v[r->lg] = v;
			r->lg++;
		}
		else {
			if (rel(c, r->e))
				adauga_rec(r->st, r, c, v);
			else
				adauga_rec(r->dr, r, c, v);
		}
	}
	return r;
}

//Theta(n), unde n=nr de chei
void MDO::distruge_rec(Nod* r)
{
	if (r != NULL) {
		distruge_rec(r->st);
		distruge_rec(r->dr);
		delete(r->v);
		delete(r);
	}
}

//Theta(m), unde m=nr de valori ale cheii
void MDO::redimens(Nod* r)
{
	TValoare* x = new TValoare[r->cp * 2];
	for (int i = 0; i < r->lg; i++)
		x[i] = r->v[i];
	r->cp *= 2;
	delete(r->v);
	r->v = x;
}

//O(h), unde h=inaltimea arborelui ( caz favorabil: Theta(1), caz defavorabil: Theta(h) )
Nod* MDO::cauta_rec(Nod* r, TCheie c) const
{
	if (r == NULL)
		return NULL;
	else
		if (r->e == c)
			return r;
		else
			if (rel(c, r->e))
				cauta_rec(r->st, c);
			else
				cauta_rec(r->dr, c);
}

//O(h+m), unde h=inaltimea arborelui si m=nr de valori ale cheii c 
//(caz favorabil: Theta(1), caz defavorabil: Theta(h+m) )
bool MDO::sterge_rec(Nod* r, Nod* prec, TCheie c, TValoare v)
{
	if (r == NULL) {
		return false;
	}
	if (r->e == c) {
		if (r->lg == 1 && r->v[0] == v) {
			delete(r->v);
			r->lg = 0;
			if (prec == NULL) {
				if (rad->st == NULL) {
					rad = rad->dr;
				}
				else {
					prec = rad;
					rad = rad->st;
					Nod* k = rad;
					while (k->dr != NULL)
						k = k->dr;
					k->dr = prec->dr;
				}
				if(rad!=NULL)
					rad->parinte = NULL;
				return true;
			}
			if (prec->st == r) {
				prec->st = r->st;
				r->st->parinte = prec;
				prec->st->dr = r->dr;
				r->dr->parinte = prec->st;
			}
			else
				if (prec->dr == r) {
					prec->dr = r->dr;
					r->dr->parinte = prec;
					prec->dr->st = r->st;
					r->st->parinte = prec->dr;
				}
			return true;
		}
		bool sters = false;
		for (int i = 0; i < r->lg; i++)
			if (r->v[i] == v) {
				sters = true;
				for (int j = i; j < r->lg - 1; j++)
					r->v[j] = r->v[j + 1];
				r->lg--;
				break;
			}
		return sters;
	}
	if (rel(c, r->e))
		return sterge_rec(r->st, r, c, v);
	else
		return sterge_rec(r->dr, r, c, v);
}

//Theta(n), unde n=nr de chei
int MDO::dim_rec(Nod* r) const
{
	if (r == NULL)
		return 0;
	if (r->st == NULL&&r->dr==NULL)
		return r->lg;
	int a = dim_rec(r->st);
	int b = dim_rec(r->dr);
	return a + b + r->lg;
}

//Theta(1)
MDO::MDO(Relatie r) {
	rel = r;
	rad = NULL;
}

//Theta(1)
void MDO::adauga(TCheie c, TValoare v) {
	rad = adauga_rec(rad, NULL, c, v);
}

//O(m), unde m=nr de valori ale cheii c ( caz favorabil: Theta(1), caz defavorabil: Theta(m) )
vector<TValoare> MDO::cauta(TCheie c) const {
	Nod* v = cauta_rec(rad, c);
	vector<TValoare> vect;
	if (v == NULL)
		return vect;
	for (int i = 0; i < v->lg; i++)
		vect.push_back(v->v[i]);
	return vect;
}

//Theta(1)
bool MDO::sterge(TCheie c, TValoare v) {
	return sterge_rec(rad, NULL, c, v);
}

//Theta(1)
int MDO::dim() const {
	return dim_rec(rad);
}

//Theta(1)
bool MDO::vid() const {
	return dim() == 0;
}

//Theta(1)
IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

//Theta(h), unde h=inaltimea arborelui
int MDO::diferentaCheieMaxMin() const
{
	if (vid())
		return -1;
	Nod* min = rad;
	while (min->st != NULL)
		min = min->st;
	Nod* max = rad;
	while (max->dr != NULL)
		max = max->dr;
	return max->e - min->e;
}

//Theta(1)
MDO::~MDO() {
	distruge_rec(rad);
}
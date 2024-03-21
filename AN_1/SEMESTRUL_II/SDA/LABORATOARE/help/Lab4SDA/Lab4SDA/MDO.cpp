#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

//Theta(n*n), unde n=cp
MDO::MDO(Relatie r) {
	this->r = r;
	l.cp = 10;
	l.prim = -1;
	l.ultim = -1;
	l.primLiber = 0;
	l.prec = new int[l.cp];
	l.urm = new int[l.cp];
	l.e = new TCheie[l.cp];
	l.val = new LDIvalori[l.cp];
	for (int i = 0; i < l.cp; i++) {
		l.val[i].cp = 10;
		l.val[i].prim = -1;
		l.val[i].ultim = -1;
		l.val[i].primLiber = 0;
		l.val[i].prec = new int[l.val[i].cp];
		l.val[i].urm = new int[l.val[i].cp];
		l.val[i].e = new TValoare[l.val[i].cp];
		for (int j = 0; j < l.cp - 1; j++)
			l.val[i].urm[j] = j + 1;
		l.val[i].urm[l.cp - 1] = -1;
		for (int j = 1; j < l.cp; j++)
			l.val[i].prec[j] = j - 1;
		l.val[i].prec[0] = -1;
	}
	for (int i = 0; i < l.cp - 1; i++)
		l.urm[i] = i + 1;
	l.urm[l.cp - 1] = -1;
	for (int i = 1; i < l.cp; i++)
		l.prec[i] = i - 1;
	l.prec[0] = -1;
}

//O(n), unde n= nr de chei ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
void MDO::adauga(TCheie c, TValoare v) {
	if (l.prim == -1) {
		aloca();
		l.prim = 0;
		l.e[0] = c;
		l.urm[0] = -1;
		l.prec[0] = -1;
		l.ultim = 0;
		alocaVal(l.val[0]);
		l.val[0].e[0] = v;
		l.val[0].prec[0] = -1;
		l.val[0].urm[0] = -1;
		l.val[0].prim = 0;
		l.val[0].ultim = 0;
		return;
	}
	int k = l.prim;
	while (k != -1 && l.e[k] != c && r(l.e[k], c))
		k = l.urm[k];
	if (l.e[k] == c) {
		if (l.val[k].primLiber == -1)
			redimensVal(l.val[k]);
		int nou = l.val[k].primLiber;
		alocaVal(l.val[k]);
		l.val[k].e[nou] = v;
		l.val[k].prec[nou] = l.val[k].ultim;
		l.val[k].urm[nou] = -1;
		l.val[k].urm[l.val[k].ultim] = nou;
		l.val[k].ultim = nou;
		return;
	}
	if (l.primLiber == -1)
		redimens();
	int nou = l.primLiber;
	aloca();
	l.e[nou] = c;
	alocaVal(l.val[nou]);
	l.val[nou].e[0] = v;
	l.val[nou].prec[0] = -1;
	l.val[nou].urm[0] = -1;
	l.val[nou].prim = 0;
	l.val[nou].ultim = 0;
	if (k == l.prim && !r(l.e[k], c)) {
		l.urm[nou] = l.prim;
		l.prec[nou] = -1;
		l.prec[l.prim] = nou;
		l.prim = nou;
		return;
	}
	if (k == -1) {
		l.urm[nou] = -1;
		l.prec[nou] = l.ultim;
		l.urm[l.ultim] = nou;
		l.ultim = nou;
		return;
	}
	l.urm[nou] = k;
	l.prec[nou] = l.prec[k];
	l.urm[l.prec[k]] = nou;
	l.prec[k] = nou;
}

//O(n+m), unde n= nr de chei si m=nr de valori ale unei chei (caz favorabil: Theta(1), caz defavorabil: Theta(n+m))
vector<TValoare> MDO::cauta(TCheie c) const {
	vector<TValoare> v(0);
	int k = l.prim;
	while (k != -1 && l.e[k] != c && r(l.e[k],c)) {
		k = l.urm[k];
	}
	if (k == -1 || !r(l.e[k],c)) {
		return v;
	}
	LDIvalori val = l.val[k];
	while (val.prim != -1) {
		v.push_back(val.e[val.prim]);
		val.prim = val.urm[val.prim];
	}
	return v;
}

//O(n+m), unde n= nr de chei si m=nr de valori ale unei chei (caz favorabil: Theta(1), caz defavorabil: Theta(n+m))
bool MDO::sterge(TCheie c, TValoare v) {
	int k = l.prim;
	while (k != -1 && l.e[k] != c && r(l.e[k], c))
		k = l.urm[k];
	if (k == -1 || !r(l.e[k], c))
		return false;
	int x = l.val[k].prim;
	while (x != -1 && l.val[k].e[x] != v)
		x = l.val[k].urm[x];
	if (x == -1)
		return false;
	dealocaVal(l.val[k], x);
	if (l.val[k].prim == -1)
		dealoca(k);
	return true;
}

//O(n*m), unde n= nr de chei si m=nr de valori ale unei chei (caz favorabil: Theta(1), caz defavorabil: Theta(n*m))
int MDO::dim() const {
	int d = 0;
	int k = l.prim;
	while (k != -1) {
		int v = l.val[k].prim;
		while (v != -1) {
			d++;
			v = l.val[k].urm[v];
		}
		k = l.urm[k];
	}
	return d;
}

//Theta(1)
bool MDO::vid() const {
	return l.prim==-1;
}

//Theta(1)
IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

//Theta(1)
void MDO::aloca()
{
	int nou = l.primLiber;
	if (nou != -1) {
		l.primLiber = l.urm[l.primLiber];
		l.prec[l.primLiber] = -1;
		l.urm[nou] = -1;
		l.prec[nou] = -1;
	}
}

//Theta(1)
void MDO::dealoca(int i)
{
	if (i == l.ultim)
		l.ultim = l.prec[i];
	if (i == l.prim)
		l.prim = l.urm[i];
	l.prec[i] = -1;
	l.urm[i] = l.primLiber;
	l.prec[l.primLiber] = i;
	l.primLiber = i;
}

//Theta(n*m), unde n=l.cp, m=val[i].cp
void MDO::redimens()
{
	int prim = l.prim;
	TCheie* e = new TCheie[l.cp*2];
	int* urm = new int[l.cp*2];
	int* prec = new int[l.cp*2];
	LDIvalori* val = new LDIvalori[l.cp*2];
	for (int i = 0; i < l.cp; i++) {
		urm[i] = l.urm[i];
		e[i] = l.e[i];
		val[i] = l.val[i];
		prec[i] = l.prec[i];
	}
	for (int i = l.cp; i < l.cp*2; i++) {
		val[i].cp = 10;
		val[i].prim = -1;
		val[i].ultim = -1;
		val[i].primLiber = 0;
		val[i].prec = new int[val[i].cp];
		val[i].urm = new int[val[i].cp];
		val[i].e = new TValoare[val[i].cp];
		for (int k = 0; k < val[i].cp - 1; k++)
			val[i].urm[k] = k + 1;
		val[i].urm[val[i].cp - 1] = -1;
		for (int j = 1; j < val[i].cp; j++)
			val[i].prec[j] = j - 1;
		val[i].prec[0] = -1;
	}
	for (int i = l.cp; i < l.cp*2 - 1; i++)
		urm[i] = i + 1;
	urm[l.cp*2 - 1] = -1;
	for (int i = l.cp+1; i < l.cp*2; i++)
		prec[i] = i - 1;
	prec[l.cp] = -1;
	l.primLiber=l.cp;
	l.cp *= 2;
	l.e = e;
	l.urm = urm;
	l.prec = prec;
	l.val = val;
	l.prim = prim;
}

//Theta(1)
void MDO::alocaVal(LDIvalori& v)
{
	int nou = v.primLiber;
	if (nou != -1) {
		v.primLiber = v.urm[v.primLiber];
		v.prec[v.primLiber] = -1;
		v.urm[nou] = -1;
		v.prec[nou] = -1;
	}
}

//Theta(1)
void MDO::dealocaVal(LDIvalori& v, int i)
{
	if (i == v.ultim)
		v.ultim = v.prec[i];
	if (i == v.prim)
		v.prim = v.urm[i];
	v.prec[i] = -1;
	v.urm[i] = l.primLiber;
	v.prec[v.primLiber] = i;
	v.primLiber = i;
}

//Theta(n), unde n=v.cp
void MDO::redimensVal(LDIvalori& v)
{
	TValoare* e = new TValoare[v.cp*2];
	int* prec = new int[v.cp*2];
	int* urm = new int[v.cp*2];
	for (int i = 0; i < v.cp; i++) {
		urm[i] = v.urm[i];
		e[i] = v.e[i];
		prec[i] = v.prec[i];
	}
	for (int i = v.cp; i < v.cp*2 - 1; i++)
		urm[i] = i + 1;
	urm[v.cp*2 - 1] = -1;
	for (int i = v.cp + 1; i < v.cp*2; i++)
		prec[i] = i - 1;
	prec[v.cp] = -1;
	v.e = e;
	v.prec = prec;
	v.urm = urm;
	v.primLiber = v.cp;
	v.cp *= 2;
}

//Theta(1)
int MDO::diferentaCheieMaxMin() const
{
	if (dim() == 0)
		return -1;
	else
		return l.e[l.ultim] - l.e[l.prim];
}

//O(n), unde n=nr de chei (caz favorabil: Theta(1), caz defavorabil: Theta(n))
MDO::~MDO() {
	while (l.prim != -1) {
		delete(l.val[l.prim].e);
		delete(l.val[l.prim].prec);
		delete(l.val[l.prim].urm);
		l.prim = l.urm[l.prim];
	}
	delete(l.e);
	delete(l.urm);
	delete(l.prec);
}
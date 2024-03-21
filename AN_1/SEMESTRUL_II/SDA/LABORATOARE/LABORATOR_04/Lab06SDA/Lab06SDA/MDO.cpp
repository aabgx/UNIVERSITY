#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;
/*
subalg val_max:
	init v[]=0
	daca vid() atunci
	       returneaza -1
	max<-0
	pentru i<-0, m, +1 executa
		daca e[i].valoare!=NIl at
			[v.e[i].valoare]++
		daca [v.e[i].valoare] > max at
			max <- [v.e[i].valoare]
			val<-e[i].valoare
	return max
sf subalg
			

*/
//Theta(m)
TValoare MDO::val_max()
{
	int v[400] = { 0 },val;
	if (vid()) {
		return -1;
	}
	int max = 0;
	for (int i = 0; i < m; i++) {
		if(e[i].second!=INIT) v[e[i].second]++;
		if (v[e[i].second] > max) { max = v[e[i].second]; val = e[i].second; }
	}
	return val;
}

//functia de dispersie
int MDO::d(TCheie c) const {
	if (c < 0) c = -c;
	return c % m;
}
//complexitate O(m), unde m dimensiunea tabelei de dispersie
MDO::MDO(Relatie r) {
	/* de adaugat */
	relatie = r;
	m = MAX;
	n = 0;
	for (int i = 0;i < m;i++)
	{
		e[i].first = INIT;
		e[i].second = INIT;
		urm[i] = INIT;
	}
	primLiber = 0;
}

//
// actualizare primLiber
void MDO::actPrimLiber()
{
	primLiber++;
	while (primLiber < m && e[primLiber].first != INIT)
		primLiber++;
}


//worst case: avem aceeasi cheie peste tot si parcurgem toata TD=> theta(m)
//best case: o gasim la locatie si e unica=> theta(1)
//medie=> O(m)
vector<TValoare> MDO::cauta(TCheie c) const {
	/* de adaugat */
	int poz = d(c);

	vector<TValoare> v;
	while (poz != -1 && e[poz].first != c)
	{

		poz = urm[poz];
	}

	//daca poz=-1 cheia nu a fost gasita
	while (poz != -1 && e[poz].first == c)
	{
		v.push_back(e[poz].second);
		//printf("al %d-lea pe pozitia %d \n", v.size(), poz);
		poz = urm[poz];
		//printf("%d element: %d\n", poz,e[poz].second);
	}
	return v;
}


bool MDO::sterge(TCheie c, TValoare v) {
	/* de adaugat */
	TElem elem(c, v);
	int i = d(c), j = INIT, k = 0, p, pp;
	bool gata;

	while (k < m && j == INIT)
	{
		if (urm[k] == i)	j = k;
		else k++;
	}

	while (i != INIT && e[i] != elem)
	{
		j = i;
		i = urm[i];
	}
	//printf("%d\n", i);
	if (i == INIT) //cheia cu val asociata nu exista
	{
		//printf("FALS\n");
		return false;
	}

	else
	{
		//printf("ADEVARAT\n");
		gata = false;
		do {

			p = urm[i];
			pp = i;
			while (p != INIT && d(e[p].first) != i)
			{
				pp = p;
				p = urm[p];
			}
			if (p == INIT) gata = true;
			else {
				e[i] = e[p];
				j = pp;
				i = p;
			}

		} while (!gata);
		if (j != INIT) urm[j] = urm[i];
		e[i].first = INIT;
		urm[i] = INIT;
		if (primLiber > i) primLiber = i;
		n--;
		return true;
	}
}


void MDO::adauga(TCheie c, TValoare v) {
	//ADAUGA BUN
	int i = d(c), j, ok = 1, aux;
	if (e[i].first == INIT)
	{
		e[i].first = c;
		e[i].second = v;
		if (i == primLiber) {
			actPrimLiber();
		}
		n++;
	}
	else
	{
		while (i != INIT)
		{
			if ((Relatie)(c, e[i].first) && d(e[i].first) == i)
			{
				int aux1 = c;
				int aux2 = v;
				c = e[i].first;
				v = e[i].second;
				e[i].first = aux1;
				e[i].second = aux2;
			}
			j = i;
			i = urm[i];
		}
		e[primLiber].first = c;
		e[primLiber].second = v;
		urm[j] = primLiber;
		actPrimLiber();
		n++;
	}
}
int MDO::dim() const {
	/* de adaugat */
	return n;
}

bool MDO::vid() const {
	/* de adaugat */
	return n == 0;
}

IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

MDO::~MDO() {
	/* de adaugat */
}


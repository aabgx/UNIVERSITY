#include "IteratorDictionar.h"
#include "Dictionar.h"
#include <exception>
#include <iostream>

using namespace std;

//Theta(1)
IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d) {
	poz = 0;
	c = 0;
	deplasare();
}

//Theta(m)
void IteratorDictionar::deplasare()
{
	while (poz < dict.m && dict.l[poz] == NULL)
		poz++;
	if (poz < dict.m) {
		curent = dict.l[poz];
		c++;
	}
}

//Theta(1)
void IteratorDictionar::prim() {
	poz = 0;
	c = 0;
	deplasare();
}

//Theta(1)
void IteratorDictionar::urmator() {
	curent = curent->urm;
	if (curent == NULL) {
		poz++;
		deplasare();
	}
}

//Theta(1)
TElem IteratorDictionar::element() const {
	return pair <TCheie, TValoare>(curent->e.first, curent->e.second);
}

//Theta(n), unde n=min(c,k)
bool IteratorDictionar::validk(int k)
{
	int x = 1;
	IteratorDictionar it = dict.iterator();
	it.prim();
	while (x < c && x < k) {
		x++;
		it.urmator();
	}
	if (x == k)
		return true;
	else
		return false;
}

//O(c-k) ( caz favorabil: Theta(1), caz defavorabil: Theta(c-k) )
void IteratorDictionar::revinoKPasi(int k)
{
	if (!validk(k))
		throw exception();
	IteratorDictionar it = dict.iterator();
	it.prim();
	int x = 1;
	while (x < c - k) {
		x++;
		it.urmator();
	}
	curent = it.curent;
	c -= k;
}

//Theta(1)
bool IteratorDictionar::valid() const {
	if (poz < dict.m && curent != NULL)
		return true;
	else
		return false;
}
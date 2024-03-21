#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"
#define INIT_CAPACITY 100

Dictionar::Dictionar() {
	/*
	Complexitate in caz favorabil = Complexitate in caz defavorabil = Complexitate in caz mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/
	e = new TElem[INIT_CAPACITY];
	urm = new int[INIT_CAPACITY];
	prim = -1;
	primLiber = 0;
	int i;
	for (i = 0; i < INIT_CAPACITY; i++)
		urm[i] = i + 1;
	urm[INIT_CAPACITY - 1] = -1;
	n = 0;
	cp = INIT_CAPACITY;

}

Dictionar::~Dictionar() {
	/* 
	Complexitate in caz favorabil = Complexitate in caz defavorabil = Complexitate in caz mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/
	delete[] e;
	delete[] urm;
	prim = -1;
	primLiber = -1;
}

void Dictionar::resize() {
	/*
	Complexitate in caz favorabil = Complexitate in caz defavorabil = Complexitate in caz mediu = Theta(n)
	Complexitate generala = Theta(n)
	*/
	TElem* new_e = new TElem[cp * 2];
	int* new_urm = new int[cp * 2];
	int i;
	for (i = 0; i < cp; i++)
	{
		new_e[i] = e[i];
		new_urm[i] = urm[i];
	}
	//primLiber = cp;
	for (i = cp; i < cp * 2; i++)
		new_urm[i] = i + 1;
	new_urm[2 * cp - 1] = -1;
	primLiber = cp;
	cp = cp * 2;
	delete[] urm;
	delete[] e;
	urm = new_urm;
	e = new_e;
}

TValoare Dictionar::adauga(TCheie c, TValoare v) {
	/* 
	Complexitate in caz favorabil = Complexitate in caz defavorabil = Complexitate in caz mediu = Theta(n)
	Complexitate generala = Theta(n)
	*/
	int current = prim;
	while (current != -1) {
		TElem& element = e[current];
		if (element.first == c) {
			TValoare vechi = element.second;
			element.second = v;
			return vechi;
		}
		current = urm[current];
	}
	if (primLiber == -1) {
		resize();
	}
	n++;
	e[primLiber] = { c,v };
	int next_liber = urm[primLiber];
	urm[primLiber] = prim;
	prim = primLiber;
	primLiber = next_liber;
	return NULL_TVALOARE;
}



//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare Dictionar::cauta(TCheie c) const {
	/*
	Complexitate in caz favorabil = Theta(1)
	Complexitate in caz defavorabil = Theta(n)
	Complexitate in caz mediu = Theta(n)
	Complexitate generala: O(n)
	*/

	int current = prim;
	while (current != -1) {
		TElem& element = e[current];
		if (element.first == c) {
			return element.second;
		}
		current = urm[current];
	}

	return NULL_TVALOARE;
}


TValoare Dictionar::sterge(TCheie c) {
	/*
	Complexitate in caz favorabil = Theta(1)
	Complexitate in caz defavorabil = Theta(n)
	Complexitate in caz mediu = Theta(n)
	Complexitate generala: O(n)
	*/
	int current = prim;
	int anterior = -1;
	while (current != -1) {
		TElem& element = e[current];
		if (element.first == c) {

			TValoare vechi = element.second;


			//Corectare indici
			if (anterior == -1) {
				prim = urm[current];
				
				//Eliberare spatiu de memorie
				urm[current] = primLiber;
				primLiber = current;
				//
			}
			else {
				urm[anterior] = urm[current];
				//Eliberare spatiu de memorie
				urm[current] = primLiber;
				primLiber = current;
				//

			}
			//Scade dimensiunea
			n--;
			return vechi;
		}
		anterior = current;
		current = urm[current];
	}
	return NULL_TVALOARE;
}


int Dictionar::dim() const {
	/*
	Complexitate in cazul favorabil = Complexitate in cazul defavorabil = Complexitate in cazul mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/
	return n;
}

bool Dictionar::vid() const {
	/*
	Complexitate in cazul favorabil = Complexitate in cazul defavorabil = Complexitate in cazul mediu = Theta(1)
	Complexitate generala = Theta(1)
	*/
	if (n == 0)return true;
	return false;
}


IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}



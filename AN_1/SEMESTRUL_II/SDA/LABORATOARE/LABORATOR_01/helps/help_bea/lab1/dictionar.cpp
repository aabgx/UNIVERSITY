#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

Dictionar::Dictionar() {
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	/* de adaugat */
	//setam capacitatea
	this->cp = cp;

	//alocam spatiu de memorare pentru vector
	e = new TElem[cp];

	//setam numarul de elemente
	this->n = 0;

}

Dictionar::~Dictionar() {
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	/* de adaugat */
	//eliberam zona de memorare alocata vectorului
	delete[] e;
}

void Dictionar::redim() {
	/*complexitate:
	* CF=CD=CT=theta(n)
	*/
	// alocam un spatiu de capacitate dubla
	TElem* eNou = new TElem[2 * cp];
	//TValoare* vNou = new int[2 * cp];

	//copiem vechile valori in noua zona
	for (int i = 0; i < n; i++) {
		//eNou[i] = e[i];
		eNou[i].first = this->e[i].first;
		eNou[i].second = this->e[i].second;

	}

	//dublam capacitatea
	cp = 2 * cp;

	//eliberam vechea zona
	delete[] e;

	//vectorul indica spre noua zona
	e = eNou;
}

TValoare Dictionar::adauga(TCheie c, TValoare v) {
	/*complexitate:
	* CF=O(1)
	* CD=theta(n)
	* CT=O(n)
	*/
	/* de adaugat */
	//daca s-a atins capacitatea maxima, redimensionam
	//if (n == cp)
		//redim();
		//TElem *eNou = new int[2 * cp];
	//adaugam la sfarsit
	
	for(int i=0;i<n;i++)
		if (this->e[i].first == c) {
			TCheie vechi = this->e[i].second;
			this->e[i].second = v;
			return vechi;
		}
	if (n == cp)
		redim();
	this->e[n].first = c;
	this->e[n++].second = v;
	return NULL_TVALOARE;
}



//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare Dictionar::cauta(TCheie c) const {
	/*complexitate:
	*  CF=theta(1)
	*  CD=theta(n)
	*  CT=O(n)
	*/
	/* de adaugat */
	for (int i = 0; i < n; i++)
		if (this->e[i].first == c)
			return this->e[i].second;
	return NULL_TVALOARE;
}


TValoare Dictionar::sterge(TCheie c) {
	/*complexitate:
	* CF=theta(n)
	* CD=theta(n^2)
	* CT=O(n)
	*/
	/* de adaugat */
	for (int i = 0; i < n; i++) {
		if (this->e[i].first == c) {
			TValoare v = this->e[i].second;
			for (int j = i; j < n - 1; j++) {
				this->e[j].first = this->e[j + 1].first;
				this->e[j].second = this->e[j + 1].second;
			}
			n--;
			return v;
		}
	}
	return NULL_TVALOARE;
}


int Dictionar::dim() const {
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	/* de adaugat */
	return n;
}

bool Dictionar::vid() const {
	/* de adaugat */
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	if (n > 0)
		return false;
	return true;
}


IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}



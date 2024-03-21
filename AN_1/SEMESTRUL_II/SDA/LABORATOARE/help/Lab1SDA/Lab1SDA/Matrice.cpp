#include <exception>
#include "Matrice.h"

using namespace std;

//complexitate: O(n)
Matrice::Matrice(int m, int n) { 
	if (m <= 0 || n <= 0)
		throw exception("Numarul de linii si coloane nu poate fi negativ");
	llinie = m+1;
	col = n;
	linie = new int[llinie+1];
	for (int i = 0; i < llinie; i++)
		linie[i] = 1;
	dimens = 20;
	coloana = new int[dimens];
	lcoloana = 0;
	valoare = new TElem[dimens];
}


//comlexitate: Teta(1)
int Matrice::nrLinii() const {
	return llinie-1;
}

//comlexitate: Teta(1)
int Matrice::nrColoane() const {
	return col;
}

//comlexitate: O(n)
TElem Matrice::element(int i, int j) const {
	if (i > nrLinii() || j > nrColoane())
		throw exception("Indicii nu apartin matricii");
	TElem el = NULL_TELEMENT;
	if (lcoloana != 0) {
		for (int k = linie[i]; k < linie[i + 1]; k++)
			if (coloana[k] == j)
				el = valoare[k];
	}
	return el;
}

//comlexitate: Teta(n)
void Matrice::redimens(int dim) {
	int* c = new int[dim];
	int* v = new TElem[dim];
	for (int k = 1; k <= dim/2; k++)
	{
		c[k] = coloana[k];
		v[k] = valoare[k];
	}
	delete(coloana);
	delete(valoare);
	coloana = c;
	valoare = v;
}

//comlexitate: O(n)
TElem Matrice::modifica(int i, int j, TElem e) {
	if (i > nrLinii() || j > nrColoane())
		throw exception("Indicii nu apartin matricii");
	TElem el = NULL_TELEMENT;
	bool exista = false;
	for (int k = linie[i]; k < linie[i + 1]; k++)
		if (coloana[k] == j)
		{
			el = valoare[k];
			exista = true;
			valoare[k] = e;
		}
	if (not exista)
	{
		if (lcoloana == dimens-1) {
			dimens *= 2;
			redimens(dimens);
		}
		lcoloana++;
		for (int k = lcoloana; k >= linie[i + 1]; k--) {
			coloana[k] = coloana[k - 1];
			valoare[k] = valoare[k - 1];
		}
		coloana[linie[i + 1]] = j;
		valoare[linie[i + 1]] = e;
		for (int k = i + 1; k < llinie; k++)
			linie[k]++;
	}

	return el;
}

//comlexitate: Teta(1)
Matrice::~Matrice() {
	delete(linie);
	delete(coloana);
	delete(valoare);
}
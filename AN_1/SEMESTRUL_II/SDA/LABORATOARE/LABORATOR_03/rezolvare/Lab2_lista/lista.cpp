#include "iteratorLP.h"
#include "lista.h"
#include <exception>
#include <iostream>
//Teta(1)
int Lista::aloca()
{
	int i = prim_liber;
	prim_liber = urm[prim_liber];
	return i;
}
//Teta(1)
void Lista::dealoca(int poz)
{
	urm[poz] = prim_liber;
	prim_liber = poz;
}
//O(cp)
void Lista::resize()
{

	TElem* elems_nou = new TElem[2 * cp];
	TElem* urm_nou = new int[2 * cp];
	TElem* ant_nou = new int[2 * cp];

	for (int i = 0; i < cp; i++) {
		elems_nou[i] = elems[i];
		urm_nou[i] = urm[i];
		ant_nou[i] = ant[i];
	}
	for (int i = cp; i < 2 * cp; i++)
	{
		urm_nou[i] = i + 1;
		ant_nou[i] = i - 1;
	}
	urm_nou[2 * cp - 1] = -1; //echivalent NIL
	ant_nou[0] = -1; //echivalent NIL

	delete[] elems;
	delete[] urm;
	delete[] ant;

	elems = elems_nou;
	urm = urm_nou;
	ant = ant_nou;
	prim_liber = cp;
	cp = 2 * cp;
}
//O(cp)
Lista::Lista() {
	/* de adaugat */
	primul = -1;
	ultimul = -1;
	cp = 1; //daca pun 5 de ex nu mai merge
	n = 0;
	prim_liber = 0;
	elems = new TElem[cp];
	urm = new int[cp];
	ant = new int[cp];

	for (int i = 0;i < cp;i++) {
		urm[i] = i + 1;
		ant[i] = i - 1;
	}

	urm[cp - 1] = -1;
	ant[0] = -1;

}
//Teta(1)
int Lista::dim() const {
	/* de adaugat */
	return n;
}
//Teta(1)
bool Lista::vida() const {
	/* de adaugat */
	return (primul == -1);
}
//Teta(1)
IteratorLP Lista::prim() const {
	/* de adaugat */
	return IteratorLP(*this);
}
//Teta(1)
TElem Lista::element(IteratorLP poz) const {
	/* de adaugat */
	
	if (!poz.valid())
		throw std::exception();
	return poz.element();
}
//Teta(1)
TElem Lista::sterge(IteratorLP& poz) {
	/* de adaugat */
	if (!poz.valid())
		throw std::exception();
	TElem e = poz.element();
	if (primul == ultimul)
	{
		dealoca(primul);
		primul = ultimul = -1;
		n--;
		return e;
	}
	int curent = poz.curent;
	poz.urmator();
	if (ant[curent] == -1)
	{
		int nod_urmator = urm[curent];
		ant[nod_urmator] = -1;
		dealoca(curent);
		primul = nod_urmator;
		n--;
		return e;
	}
	if (urm[curent] == -1)
	{
		int nod_anterior = ant[curent];
		urm[nod_anterior] = -1;
		dealoca(curent);
		ultimul = nod_anterior;

		/*if (!poz.valid())
			throw std::exception();*/
		n--;
		return e;
	}

	int nod_anterior = ant[curent];
	int nod_urmator = urm[curent];

	urm[nod_anterior] = nod_urmator;
	ant[nod_urmator] = nod_anterior;

	dealoca(curent);
	n--;
	return e;
}
//O(n)
//CF Theta(1)
//CD Theta(n)
int Lista::elimina_k(int k) {

	IteratorLP poz = IteratorLP(*this);
	int nr_sterse = 0,cnt;
	if (k > 0) {
		while (poz.valid())
		{
			sterge(poz);
			cnt = 0;
			while (cnt < k && poz.valid())
				poz.urmator();
		}
	}
	else throw std::exception();

	return nr_sterse;
}

/*
* CF - Teta(1) elem e primul din lista
* CD - Teta(n) elem e ultimul sau nu exista in lista
* CM - O(n) 
* Overall - O(n)
*/
IteratorLP Lista::cauta(TElem e) const {
	/* de adaugat */
	IteratorLP poz =  IteratorLP(*this);
	while (poz.valid())
	{
		if (poz.element() == e)
			return poz;
		poz.urmator();
	}
	return poz;
}
//Teta(1)
TElem Lista::modifica(IteratorLP poz, TElem e) {
	/* de adaugat */
	if (!poz.valid())
		throw std::exception();
	int pozz = poz.curent;
	TElem el_vechi = poz.element();
	elems[pozz] = e;
	return el_vechi;
}
//Teta(1)
void Lista::adauga(IteratorLP& poz, TElem e) {
	/* de adaugat */
	if (prim_liber == -1) resize();
	if (!poz.valid())
		throw std::exception();
	int nod = poz.curent;
	
	if (urm[nod] == -1)
	{
		int nod_de_adaugat = aloca();
		elems[nod_de_adaugat] = e;
		urm[nod] = nod_de_adaugat;
		poz.urmator();
		n++;
		return;
	}
	poz.urmator();
	int nod_curr = poz.curent;
	poz.anterior();
	int nod_de_adaugat = aloca();
	elems[nod_de_adaugat] = e;
	urm[nod] = nod_de_adaugat;
	ant[nod_curr] = nod_de_adaugat;
	poz.urmator();

	n++;
 	
}
//Teta(1)
void Lista::adaugaInceput(TElem e) {
	/* de adaugat */
	if (prim_liber == -1) resize();
	if (vida())
	{
		int q = aloca();
		elems[q] = e;
		primul = ultimul = q;
		n++;
		return;
	}
	if (primul == ultimul)
	{
		if (prim_liber == -1) resize();
		int q = aloca();
		elems[q] = e;
		ant[ultimul] = q;
		primul = q;
		n++;
		return;
	}
	if (prim_liber == -1) resize();
	int q = aloca();
	elems[q] = e;
	ant[primul]=q;
	primul = q;
	n++;
}
//Teta(1)
void Lista::adaugaSfarsit(TElem e) {
	/* de adaugat */
	if (prim_liber == -1) resize();
	if (vida())
	{
		int q = aloca();
		elems[q] = e;
		primul = ultimul = q;
		n++;
		return;
	}
	if (primul == ultimul)
	{
		if (prim_liber == -1) resize();
		int q = aloca();
		elems[q] = e;
		urm[primul] = q;
		ultimul = q;
		urm[ultimul] = -1;
		n++;
		return;
	}
	if (prim_liber == -1) resize();
	int q = aloca();
	elems[q] = e;
	urm[ultimul] = q;
	ultimul = q;
	urm[ultimul] = -1;
	n++;
}
//Teta(1)
Lista::~Lista() {
	/* de adaugat */
	delete[] elems;
	delete[] urm;
	delete[] ant;
}

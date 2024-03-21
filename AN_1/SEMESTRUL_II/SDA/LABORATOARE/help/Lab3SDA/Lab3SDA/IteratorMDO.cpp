#include "IteratorMDO.h"
#include "MDO.h"

//Theta(1)
IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
	lista = dict.lista;
	cheie = dict.lista->prim;
	if (cheie != NULL)
		val = cheie->valori->prim;
}

//Theta(1)
void IteratorMDO::prim() {
	cheie = dict.lista->prim;
	if (cheie != NULL)
		val = cheie->valori->prim;
}

//Theta(1)
void IteratorMDO::urmator() {
	if (!valid())
		throw exception();
	if (val->urm != NULL)
		val = val->urm;
	else {
		cheie = cheie->urm;
		if (cheie != NULL)
			val = cheie->valori->prim;
	}
}

//Theta(1)
bool IteratorMDO::valid() const {
	return cheie!=NULL;
}

//Theta(1)
TElem IteratorMDO::element() const {
	if (!valid())
		throw exception();
	return pair <TCheie, TValoare>(cheie->e, val->e);
}

//Theta(1)
bool IteratorMDO::validk(int k) const {
	return dict.nrchei() > k;
}

//O(n) ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
void IteratorMDO::avanseazaKPasi(int k)
{
	if (!validk(k) || k <= 0)
		throw exception();
	int x = 0;
	while (x < k && cheie->urm!=NULL) {
		cheie = cheie->urm;
		x++;
	}
}

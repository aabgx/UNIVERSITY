#include "IteratorMDO.h"
#include "MDO.h"

//Theta(1)
IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
	curent = min(dict.rad);
	poz = 0;
}

//O(n), unde n=nr de chei ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
Nod* IteratorMDO::min(Nod* k)
{
	if (k == NULL)
		return NULL;
	while (k->st != NULL)
		k = k->st;
	return k;
}

//Theta(1)
void IteratorMDO::prim() {
	curent = min(dict.rad);
	poz = 0;
}

//O(n), unde n=nr de chei ( caz favorabil: Theta(1), caz defavorabil: Theta(n) )
void IteratorMDO::urmator() {
	if (poz < curent->lg - 1)
		poz++;
	else {
		if (curent->dr != NULL)
			curent = min(curent->dr);
		else {
			Nod* prec = curent->parinte;
			while (prec != NULL && curent == prec->dr) {
				curent = prec;
				prec = curent->parinte;
			}
			curent = prec;
			poz = 0;
		}
	}
}

//Theta(1)
bool IteratorMDO::valid() const {
	return curent != NULL;
}

//Theta(1)
TElem IteratorMDO::element() const {
	return pair <TCheie, TValoare>(curent->e, curent->v[poz]);
}


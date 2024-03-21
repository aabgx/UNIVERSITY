#include "IteratorLP.h"
#include "Lista.h"
#include <exception>

IteratorLP::IteratorLP(const Lista& l) :lista(l) {
	/* de adaugat */
	crt = lista.prim;
}

void IteratorLP::prim() {
	/* de adaugat */
	crt = lista.prim;
}

void IteratorLP::urmator() {
	/* de adaugat */
	if (valid())
		crt = lista.urmator[crt];
	else
		throw std::exception();
}

bool IteratorLP::valid() const {
	/* de adaugat */
	if (crt == -1)
		return false;
	else return true;
}

TElem IteratorLP::element() const {
	/* de adaugat */
	if (valid())
		return lista.elems[crt];
	else throw std::exception();
	return -1;
}



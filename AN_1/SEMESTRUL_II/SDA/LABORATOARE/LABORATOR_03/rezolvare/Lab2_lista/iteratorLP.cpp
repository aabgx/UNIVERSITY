#include "iteratorLP.h"
#include "lista.h"
#include <exception>
#include <stdlib.h>

//Teta(1)
IteratorLP::IteratorLP(const Lista& l) :lista(l) {
	/* de adaugat */
	curent = l.primul;
	
}
//Teta(1)
void IteratorLP::prim() {
	/* de adaugat */
	curent = lista.primul;

}
//Teta(1)
void IteratorLP::urmator() {
	/* de adaugat */

	if (!valid())
		throw std::exception();
	curent = lista.urm[curent];

}
//Teta(1)
void IteratorLP::anterior() {
	
	if (!valid())
		throw std::exception();
	curent = lista.ant[curent];

}
//Teta(1)
bool IteratorLP::valid() const {
	/* de adaugat */
	return curent != -1;
}
//Teta(1)
TElem IteratorLP::element() const {
	/* de adaugat */
	if (!valid()) {
		throw std::exception();
	}
	return lista.elems[curent];
}



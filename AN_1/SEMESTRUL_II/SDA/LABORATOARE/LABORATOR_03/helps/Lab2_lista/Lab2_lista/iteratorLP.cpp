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
	curent = curent->urmator();

}

//Teta(1)
void IteratorLP::anterior() {
	/* de adaugat */
	
	if (!valid())
		throw std::exception();
	curent = curent->anterior();

}

//Teta(1)
bool IteratorLP::valid() const {
	/* de adaugat */
	return curent != NULL;
}

//Teta(1)
TElem IteratorLP::element() const {
	/* de adaugat */
	if (!valid()) {
		throw std::exception();
	}
	return curent->element();
}



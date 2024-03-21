#include "IteratorDictionar.h"
#include "Dictionar.h"
#include <iostream>

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d) {
	/* de adaugat */
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	curent = 0;
}


void IteratorDictionar::prim() {
	/* de adaugat */
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	curent = 0;
}


void IteratorDictionar::urmator() {
	/* de adaugat */
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	if (valid() == false)
		throw std::exception();
	else
		curent++;
}


TElem IteratorDictionar::element() const {
	/* de adaugat */
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	if (valid())
		return dict.e[curent];
	else
		throw std::exception();
}


bool IteratorDictionar::valid() const {
	/* de adaugat */
	/*complexitate:
	* CF=CD=CT=theta(1)
	*/
	return curent < dict.dim();
}


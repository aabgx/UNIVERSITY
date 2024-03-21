#include "IteratorColectie.h"
#include "Colectie.h"


IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	index = 0;
}

TElem IteratorColectie::element() const{
	return col.v[index].e;
}

bool IteratorColectie::valid() const {
	if (index < col.n)
	{
		return true;
	}
	return false;
}

void IteratorColectie::urmator() {
	index++;
}
void IteratorColectie::precedent() {
	index--;
}

void IteratorColectie::prim() {
	index = 0;
}

#include "IteratorMultime.h"
#include "Multime.h"


IteratorMultime::IteratorMultime(const Multime& m) : multime(m) {
	/* de adaugat */
	for (int i = 0; i <= (2 * m.max); ++i) {
		if (m.elems[i] == 1) {
			this->current = i;
			return;
		}
	}

}


void IteratorMultime::prim() {
	/* de adaugat */

	for (int i = 0; i <= (2 * this->multime.max); ++i) {
		if (this->multime.elems[i] == 1) {
			this->current = i;
			return;
		}
	}
}


void IteratorMultime::urmator() {
	/* de adaugat */
	
	for (int i = this->current+1; i <= (2 * this->multime.max); ++i) {
		if (this->multime.elems[i] == 1) {
			this->current = i;
			return;
		}
	}
	this->current = 2 * this->multime.max + 1;
}


TElem IteratorMultime::element() const {
	/* de adaugat */

	return this->current-this->multime.idx_of_0;
}

bool IteratorMultime::valid() const {
	/* de adaugat */
	bool ok = true;
	for (int i = 0; i <= (2 * this->multime.max); ++i) {
		if (this->multime.elems[i] == 1) {
			ok = false;
			break;
		}
	}
	if (ok == true)return false;
	if (this->current <= 2*this->multime.max)return true;
	return false;
}

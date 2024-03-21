#include "IteratorMDO.h"
#include "MDO.h"

//Theta(1)
IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
	k = dict.l.prim;
	if (k != -1)
		v = dict.l.val[k].prim;
}

//Theta(1)
void IteratorMDO::prim() {
	k = dict.l.prim;
	if (k != -1)
		v = dict.l.val[k].prim;
}

//Theta(1)
void IteratorMDO::urmator() {
	if (!valid())
		throw exception();
	if (dict.l.val[k].urm[v] == -1) {
		k = dict.l.urm[k];
		if(k!=-1)
			v = dict.l.val[k].prim;
	}
	else
		v = dict.l.val[k].urm[v];
}

//Theta(1)
bool IteratorMDO::valid() const {
	return k!=-1;
}

//Theta(1)
TElem IteratorMDO::element() const {
	if (!valid())
		throw exception();
	return pair <TCheie, TValoare>(dict.l.e[k], dict.l.val[k].e[v]);
}


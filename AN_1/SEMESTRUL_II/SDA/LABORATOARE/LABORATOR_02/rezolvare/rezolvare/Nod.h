#pragma once

typedef int TElem;
class Nod;
typedef Nod* PNod;

class Nod
{
public:
	friend class Colectie;

	Nod(TElem el, PNod urm, PNod prec, TElem freq);

	TElem element();
	void setElement(TElem newElem);

	TElem getFreq();
	void setFreq(TElem newFreq);

	PNod urmator();
	PNod precedent();

private:
	TElem el;
	TElem freq;
	PNod urm;
	PNod prec;
};
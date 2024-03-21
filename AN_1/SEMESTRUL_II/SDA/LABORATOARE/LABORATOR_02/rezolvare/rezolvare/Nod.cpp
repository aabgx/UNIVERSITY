#include "Colectie.h"
#include "IteratorColectie.h"
#include "Nod.h"
#include <iostream>

Nod::Nod(TElem el, PNod urm, PNod prec, TElem freq)
{
	this->el = el;
	this->urm = urm;
	this->prec = prec;
	this->freq = freq;
}

TElem Nod::element()
{
	return el;
}

void Nod::setElement(TElem newElem)
{
	el = newElem;
}

TElem Nod::getFreq()
{
	return freq;
}

void Nod::setFreq(TElem newFreq)
{
	freq = newFreq;
}

PNod Nod::urmator()
{
	return urm;
}

PNod Nod::precedent()
{
	return prec;
}
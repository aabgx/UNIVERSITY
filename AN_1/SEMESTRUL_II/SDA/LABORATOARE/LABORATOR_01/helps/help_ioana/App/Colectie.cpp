#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;

bool rel(TElem e1, TElem e2) {
	if (e1 <= e2)
	{
		return true;
	}
	return false;
}

Colectie::Colectie() {
	this->cp = 10;
	v = new pereche[this->cp];
	this->n = 0;
}

void Colectie::adauga(TElem e) {
	pereche el;
	el.e = e;
	bool ok = 0;
	IteratorColectie ic = iterator();
	if (n == cp)
	{
		pereche* vNou = new pereche[2 * cp];
		while(ic.valid())
		{
			vNou[ic.index] = v[ic.index];
			ic.urmator();
		}
		cp = cp * 2;
		delete[] v;
		v = vNou;
	}
	if (vida())
	{
		el.frecventa = 1;
		this->v[0] = el;
		n++;
	}
	else
	{
		if (cauta(e))
		{
			ic.prim();
			while(ic.valid())
			{
				if (ic.element() == e)
				{
					this->v[ic.index].frecventa++;
					break;
				}
				ic.urmator();
			}
		}
		else
		{
			el.frecventa = 1;
			ic.prim();
			while(ic.valid())
			{
				if (!rel(ic.element(), el.e))
				{
					IteratorColectie jc = iterator();
					jc.index = n;
					while(jc.index > ic.index)
					{
						this->v[jc.index] = this->v[jc.index - 1];
						jc.precedent();
					}
					ok = 1;
					this->v[ic.index] = el;
					n++;
					break;
				}
				ic.urmator();
			}
			ic.index = n;
			if (ic.element() <= e && !ok)
			{
				this->v[ic.index] = el;
				n++;
			}
		}
	}
	
}


bool Colectie::sterge(TElem e) {
	if (cauta(e))
	{
		IteratorColectie ic = iterator();
		while (ic.valid())
		{
			if (ic.element() == e)
			{
				if (this->v[ic.index].frecventa > 1)
				{
					this->v[ic.index].frecventa--;
				}
				else
				{
					IteratorColectie jc = iterator();
					jc.index = ic.index;
					while (jc.valid())
					{
						this->v[jc.index] = this->v[jc.index + 1];
						jc.urmator();
					}
					n--;
				}
				break;
			}
			ic.urmator();

		}
		return true;
	}
	return false;
}


bool Colectie::cauta(TElem elem) const {
	IteratorColectie ic = iterator();
	while(ic.valid())
	{
		if (ic.element() == elem)
		{
			return true;
		}
		ic.urmator();
	}
	return false;
}


int Colectie::nrAparitii(TElem elem) const {
	IteratorColectie ic = iterator();
	while(ic.valid())
	{
		if (ic.element() == elem)
		{
			return this->v[ic.index].frecventa;
		}
		ic.urmator();
	}
	return 0;
}



int Colectie::dim() const {
	int s = 0;
	IteratorColectie ic = iterator();
	while(ic.valid())
	{
		s += this->v[ic.index].frecventa;
		ic.urmator();
	}
	return s;
}


bool Colectie::vida() const {
	if (n == 0)
	{
		return true;
	}
	return false;

}


IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	delete[] v;
}

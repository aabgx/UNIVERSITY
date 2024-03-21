#include "IteratorColectie.h"
#include "Colectie.h"
#include <exception>

IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	current = col.prim; //pointer spre primul nod, la inceput
	elem = 1; //al catelea e din felul lui
}

TElem IteratorColectie::element() const {
	if (valid())
		return current->element(); //putina confuzie, dar al doilea element() se refera la Nod
	throw std::exception();
}

TElem IteratorColectie::frecventa() const
{
	return current->getFreq();
}

void IteratorColectie::incFreq()
{
	current->setFreq(current->getFreq() + 1);
}

void IteratorColectie::decFreq()
{
	current->setFreq(current->getFreq() - 1);
}

bool IteratorColectie::valid() const {
	return current != nullptr;
}

//Complexitate: Best=Average=Worst=Theta(1)
void IteratorColectie::urmator() {
	if (!valid())
		throw std::exception();
	if (elem < frecventa())
		elem++;
	else
	{
		current = current->urmator();
		elem = 1;
		return;
	}
}

//Complexitate: Best=Average=Worst=Theta(1)
void IteratorColectie::precedent() {
	if (!valid())
		throw std::exception();
	if (elem > 1)
		elem--;
	else
	{
		current = current->precedent();
		elem = current->getFreq();
		return;
	}
}

void IteratorColectie::prim() {
	current = col.prim; //reseteaza pozitia la inceput
	elem = 1;
}

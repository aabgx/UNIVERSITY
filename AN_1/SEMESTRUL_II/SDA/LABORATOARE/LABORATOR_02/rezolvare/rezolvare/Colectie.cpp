#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>
#include <map>

using namespace std;

bool rel(TElem e1, TElem e2) {
	return true;
}

Colectie::Colectie() {
	prim = nullptr; //pointer la primul nod
	len = 0; //lungimea actuala
}

//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall O(n)
void Colectie::adauga(TElem e) {
	//Worst
	if (cauta(e))
		//daca il gaseste doar creste frecventa
	{
		IteratorColectie itr = iterator();
		itr.prim();
		while (e != itr.element())
			itr.urmator();
		itr.incFreq();
	}
	//Best
	else
		//daca nu-l gaseste, il adauga la inceput
	{
		PNod q = new Nod(e, nullptr, nullptr, 1);
		q->urm = prim;
		prim = q; //prim 


	}
	len++;
}

//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall O(n)
bool Colectie::sterge(TElem e) {
	if (cauta(e))
	{	IteratorColectie itr = iterator();
		itr.prim();
		while (e != itr.element())
			itr.urmator();
		itr.decFreq();

		//daca frecventa devine 0, avem de sters tot nodul
		//Worst
		if (itr.frecventa() == 0)
		{
			PNod p = prim;

			//daca e pe prima pozitie, trebuie sa il schimbam pe prim
			if (p->el == itr.element())
			{
				prim = prim->urm; //ia implicit toate datele
				delete p;
			}

			//daca e oriunde altundeva
			else
			{
				while (p->urm->el != itr.element())
					p = p->urm;
				//acum p->urm e un pointer la nodul de sters
				PNod q = p->urm;
				//PNod r = q->urm;
				p->urm = q->urm; //actualizam urm
				//r->prec = p;

				delete q;
			}
		}
		len--;
		return true;
	}
	return false;
}

//COMPLEXITATE:  Best= Theta(1) Average=Worst=Theta(n), Overall O(n)
int Colectie::sterge_elem(TElem e) {
	int cnt = 0;
	if (cauta(e))
	{	
		IteratorColectie itr = iterator();
		itr.prim();
		while (e != itr.element())
			itr.urmator();
		while (itr.frecventa() != 0) {
			itr.decFreq(); cnt=cnt+1;
		}
			PNod p = prim;

			//daca e pe prima pozitie, trebuie sa il schimbam pe prim
			if (p->el == itr.element())
			{
				prim = prim->urm; //ia implicit toate datele
				delete p;
			}

			//daca e oriunde altundeva
			else
			{
				while (p->urm->el != itr.element())
					p = p->urm;
				//acum p->urm e un pointer la nodul de sters
				PNod q = p->urm;
				p->urm = q->urm; //actualizam urm

				delete q;
			}
		len=len-cnt;
	}
	return cnt;
}
/*
	sterge_elem(e ~input,cnt ~output)
	[
	cnt <- 0
	daca cauta(e) != NIL atunci
			iterator.prim //pentru a puncta primul element din colectie
			cat timp e != iterator.element executa
					iterator.urmator
					sfarsit cat timp
			cat timp itr.frecventa != NIL executa
					itr.decFreq 
					cnt<-cnt+1
					sfarsit cat timp
			daca itr.element = prim.element
					prim<-prim.urmator
					sterge(prim)
				altfel
						p<-prim
						cat timp p.urmator != itr.element
								p<-p.urmator
								sfarsit cat timp
						aux<- p.urmator
						p.urmator <- aux.urmator
						sterge(aux)					
	]


*/

//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall O(n)
bool Colectie::cauta(TElem elem) const {
	IteratorColectie itr = iterator();
	itr.prim();
	while (itr.valid())
	{
		if (itr.element() == elem)
			return true;
		itr.urmator();
	}
	return false;
}

//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall=O(n)
int Colectie::nrAparitii(TElem elem) const {
	IteratorColectie itr = iterator();
	itr.prim();

	while (itr.valid())
	{
		if (itr.element() == elem)
			return itr.frecventa();
		itr.urmator();
	}
	return 0;
}


//Complexitate: Best=Average=Worst case=Theta(1)
int Colectie::dim() const {
	return len;
}

//Complexitate: Best=Average=Worst case=Theta(1)
bool Colectie::vida() const {
	IteratorColectie itr = iterator();
	itr.prim();
	if (itr.valid() == true) return false;
	else return true;
}


IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}

//Complexitate:Best=Average=Worst case=Theta(n)
Colectie::~Colectie() {
	while (prim)
	{
		PNod p = prim;
		prim = prim->urm;
		delete p;
	}
}

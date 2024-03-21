#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>
using namespace std;


Nod::Nod(TElem e,TElem fr,PNod urm) {
	this->e=e;
	this->fr=fr;
	this->urm=urm;
	this->ind=0;
}


TElem Nod::element() {
	return e;
}


TElem Nod::frecventa() {
	return fr;
}



TElem Nod::indicator(){
	return ind;
}


PNod Nod::urmator() {
	return urm;
}


TElem Nod::modifica_frecventa()
{
	if (fr==1)
		fr=0;
	else
		fr=fr-1;
	return fr;
}



TElem Nod::salveaza_frecventa()
{
	fr=ind;
	return fr;
}



TElem Nod::increase()
{
	ind++;
	return ind;
}



TElem Nod::zero()
{
	ind=0;
	return ind;
}




//done
Colectie::Colectie() {
	prim=nullptr;
	indice=nullptr;
	anterior=nullptr;
	this->dimensiune=0;
}



//functie pentru adaugarea unui element in colectie (sau cresterea frecventei)
//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall O(n)
void Colectie::adauga(TElem elem) {
	if(cauta(elem)==false)
	{
		PNod q=new Nod(elem,1,nullptr);
		q->urm=prim; //q devine capat
		prim=q; //programul puncteaza catre noul nod q
		indice=prim;
		printf("%d %d aa\n", prim->fr, prim->e);
	}
	else 
	{
			while(indice->e!=elem && indice!=nullptr)
			indice=indice->urm;
			indice->fr++;
			printf("%d %d aa\n", indice->fr, indice->e);
			indice=prim;
	}
	dimensiune++;
}


//functie pentru stergerea unui element din colectie (sau scaderea frecventei)
//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall O(n)
bool Colectie::sterge(TElem elem) {
	if(cauta(elem)==false || dimensiune==0)
	{
		printf("%d %d\n",elem,dimensiune);
		return false;
	}
	else 
	{
		indice=prim;
		anterior=nullptr;
		if (indice!=nullptr && indice->e==elem)
		{
			if (indice->fr>1)
				indice->fr--;
			else
			{
				prim=indice->urm;
				delete indice;
			}
		}
		else 
		{
			while(indice!=nullptr && indice->e!=elem)
			{
				anterior=indice;
				indice=indice->urm;
			}
			if(indice->fr>1)
				indice->fr--;
			else 
			{
				anterior->urm=indice->urm;
				delete indice;
			}
		}
		printf("%d %d\n", elem, dimensiune);
		indice=prim;
		dimensiune--;
		return true;
	}
}


//functie care cauta un element si returneaza true daca exista, false altfel
//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall O(n)
bool Colectie::cauta(TElem elem) {
	indice=prim;
	while(indice!=nullptr)
	{
		if(indice->e==elem)
		{
			indice=prim;
			return true;
		}
		else 
			indice=indice->urm;
	}
	indice=prim;
	return false;
}


//functie care returneaza numarul de aparitii al unui element(daca el exista)
//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall=O(1)
int Colectie::nrAparitii(TElem elem) {
	if(cauta(elem)==false)
		return 0;
	else
	{
		int nr=0;
		indice=prim;
		while(indice->e!=elem && indice!=nullptr)
			indice=indice->urm;
		printf("verifica aici %d  %d\n",indice->e,indice->fr);
		if(indice->fr>1)
			nr=indice->fr;
		else 
			nr=1;
		indice=prim;
		//printf("%d\n",nr);
		return nr;
	}
}



//functie care returneaza numarul de elemente care apar o singura data in colectie
//Complexitate: Best=Average=Worst=Theta(n) n=nr elemente distincte 
int Colectie::numaraElementeUnice() const{
	int nr=0;
	PNod r=new Nod(-1, -1, nullptr);
	r=prim;
	while(r!=nullptr)
	{
		if(r->fr==1)
			nr++;
		r=r->urm;
	}
	return nr;
}

/*------------------------
	numaraElementeUnice()
	Pnod r;
	nr=0
	r<-prim
	cat timp r!=NIL
		daca [r].fr==1
			nr++
		r<-[r].urm
	return nr
-------------------------*/


//functie care returneaza dimensiunea colectiei
//Complexitate: Best=Average=Worst case=Theta(1)
int Colectie::dim() const {
	return this->dimensiune;
}



//functie care verifica daca colectia este vida
//Complexitate: Best=Average=Worst case=Theta(1)
bool Colectie::vida() const {
	if(prim==nullptr)
		return true;
	else 
		return false;
}

//functie care returneaza un iterator pe colectie
IteratorColectie Colectie::iterator() const {
	return IteratorColectie(*this);
}

//functie care elibereaza memoria alocata nodurilor listei
//Complexitate:Best=Average=Worst case=Theta(n)
Colectie::~Colectie() {
	while(prim!=nullptr)
	{
		PNod p=prim;
		prim=prim->urm;
		delete p;
	}
}


#include "IteratorColectie.h"
#include "Colectie.h"
#include <stdio.h>
#include <iostream>
#include <stdexcept>
#include <exception>
using namespace std;

IteratorColectie::IteratorColectie(const Colectie& col) : 
	colectie(col) {
	curent=col.prim;
}



void IteratorColectie::prim() {
	curent=colectie.prim;
}


//functie pe iterator care puncteaza spre urmatorul element din colectie
//Complexitate: Best=Theta(1), Average=Worst=Theta(n), Overall=O(n) //dim
void IteratorColectie::urmator() {
	if(curent->frecventa()>1)
	{
		curent->modifica_frecventa();
		curent->increase();
		printf("%d recunoastere\n", curent->indicator());
	}
	else if(curent->frecventa()==1)
	{
		curent->increase();
		curent->salveaza_frecventa();
		if(curent->urmator()!=nullptr)
		{
			curent=curent->urmator();
			curent->zero();
		}
		else 
			curent=nullptr;
	}
}



//done
bool IteratorColectie::valid() const {
	return curent!=nullptr;
}



TElem IteratorColectie::element() const {
	return curent->element();
}


//added
TElem IteratorColectie::frecventa() const {
	return curent->frecventa();
}
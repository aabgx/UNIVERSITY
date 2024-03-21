#include "iteratorLP.h"
#include "lista.h"
#include <exception>
#include <iostream>

//Teta(1)
Nod::Nod(TElem e,PNod urm, PNod ant)
{
	this->e = e;
	this->ant = ant;
	this->urm = urm;
}

//Teta(1)
TElem Nod::element() {
	return e;
}
//Teta(1)
PNod Nod::urmator() {
	return urm;
}
//Teta(1)
PNod Nod::anterior() {
	return ant;
}
//Teta(1)
Lista::Lista() {
	/* de adaugat */
	primul = NULL;
	ultimul = NULL;
}

/*
*	Teta(1) CF - lista vida/ 1 singur el
*	O(n) CM = CD - n elemente
*/
int Lista::dim() const {
	/* de adaugat */
	int dimensiune = 0;
	PNod q = primul;
	while (q != NULL)
	{
		q = q->urm;
		dimensiune++;
	}
	return dimensiune;
}

//Teta(1)
bool Lista::vida() const {
	/* de adaugat */
	return (primul == NULL);
}

//Teta(1)
IteratorLP Lista::prim() const {
	/* de adaugat */
	return IteratorLP(*this);
}

//Teta(1)
TElem Lista::element(IteratorLP poz) const {
	/* de adaugat */
	
	if (!poz.valid())
		throw std::exception();
	return poz.element();
}

//Teta(1)
TElem Lista::sterge(IteratorLP& poz) {
	/* de adaugat */
	if (!poz.valid())
		throw std::exception();
	TElem e = poz.element();
	if (primul == ultimul)
	{
		delete primul;
		primul = ultimul = NULL;
		return e;
	}
	PNod curent = poz.curent;
	poz.urmator();
	if (curent->ant == NULL)
	{
		PNod nod_urmator = curent->urm;
		nod_urmator->ant = nullptr;
		delete curent;
		primul = nod_urmator;
		return e;
	}
	if (curent->urm == nullptr)
	{
		PNod nod_anterior = curent->ant;
		nod_anterior->urm = nullptr;
		delete curent;
		ultimul = nod_anterior;
		
		if (!poz.valid())
			throw std::exception();
		return e;
	}

	PNod nod_anterior = curent->ant;
	PNod nod_urmator = curent->urm;

	nod_anterior->urm = nod_urmator;
	nod_urmator->ant = nod_anterior;

	delete curent;
	return e;
}


/*
* Teta(1) - CF elem e primul din lista
* Teta(n) - CD elem e ultimul sau nu exista in lista
* O(n) -CM
* Overall - O(n)
*/
IteratorLP Lista::cauta(TElem e) const {
	/* de adaugat */
	IteratorLP poz =  IteratorLP(*this);
	while (poz.valid())
	{
		if (poz.element() == e)
			return poz;
		poz.urmator();
	}
	return poz;
}

//Teta(1)
TElem Lista::modifica(IteratorLP poz, TElem e) {
	/* de adaugat */
	if (!poz.valid())
		throw std::exception();
	TElem el_modifiat = poz.element();
	PNod nod_poz = poz.curent;
	nod_poz->e = e;
	return el_modifiat;
}

//Teta(1)
void Lista::adauga(IteratorLP& poz, TElem e) {
	/* de adaugat */
	
	if (!poz.valid())
		throw std::exception();
	PNod nod = poz.curent;
	if (nod->urm == NULL)
	{
		PNod nod_de_adaugat = new Nod(e, NULL, nod);
		nod->urm = nod_de_adaugat;
		poz.urmator();
		return;
	}
	poz.urmator();
	PNod nod_curr = poz.curent;
	poz.anterior();
	PNod nod_de_adaugat = new Nod(e, nod_curr, nod);
	nod->urm = nod_de_adaugat;
	nod_curr->ant = nod_de_adaugat;
	poz.urmator();
	
}

//Teta(1)
void Lista::adaugaInceput(TElem e) {
	/* de adaugat */
	if (vida())
	{
		PNod q = new Nod(e, NULL, NULL);
		primul = ultimul = q;
		return;
	}
	if (primul == ultimul)
	{
		PNod q = new Nod(e, ultimul, NULL);
		ultimul->ant = q;
		primul = q;
		return;
	}
	PNod q = new Nod(e,primul,NULL);
	primul->ant = q;
	primul = q;
}

//Teta(1)
void Lista::adaugaSfarsit(TElem e) {
	/* de adaugat */
	if (vida())
	{
		PNod q = new Nod(e, NULL, NULL);
		primul = ultimul = q;
		return;
	}
	if (primul == ultimul)
	{
		PNod q = new Nod(e, NULL,primul);
		primul->urm = q;
		ultimul = q;
		return;
	}
	PNod q = new Nod(e, NULL,ultimul);
	ultimul->urm = q;
	ultimul = q;
}

bool esteDivizibil_cu_3(TElem e)
{
	return e%3 == 0;
}

/*
* 
* Subalgoritm filtreaza(Lista lista, Conditie cond)
* pre: lista apartine Lista, cond - Conditie care returneaza true/false
* post: lista` - apartine Lista, cu e - TElem care respecta conditia
* poz <- list.primul
* cat timp poz.valid() executa
*		daca conditie(poz.elemet() = conditie)
*			PNod nod <- poz.curent
*			sterge nod
*		
*		poz.urmator()
* 
*/
/*
* Overall - O(n) CF = CM = CD - verifica toate elem sa aiba respectiva conditie
*/
void Lista::filtreaza(Conditie cond)
{
	IteratorLP poz = IteratorLP(*this);
	while (poz.valid())
	{
		if (cond(poz.element()))
		{
			PNod nod = poz.curent;
			delete nod;
		}
		poz.urmator();
	}
}

/*
* Teta(1) - CF - lista are un sg elem
* Teta(n) - CM = CD 
* Overall - O(n)
*/
Lista::~Lista() {
	/* de adaugat */
	while (primul != NULL)
	{
		PNod p = primul;
		primul = primul->urm;
		delete p;
	}
}

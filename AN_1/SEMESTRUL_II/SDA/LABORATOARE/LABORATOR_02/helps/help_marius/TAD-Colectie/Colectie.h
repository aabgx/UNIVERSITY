#pragma once
#define NULL_TELEM -1




typedef int TElem;

class IteratorColectie;

//referire a clasei nod; mai jos tipul PNod ca fiind adresa unui nod
class Nod;
typedef Nod* PNod;


class Nod
{
public:
	friend class Colectie;

	//constructorul

	Nod(TElem e,TElem fr, PNod urm);
	TElem element();
	TElem frecventa();
	TElem indicator();
	PNod urmator();
	TElem modifica_frecventa();
	TElem salveaza_frecventa();
	TElem increase();
	TElem zero();

private:
	TElem e;
	TElem fr;
	TElem ind;
	PNod urm;
};








class Colectie
{
	friend class IteratorColectie;

private:
	//prim este adresa primului Nod din lista
	PNod prim;
	PNod indice;
	PNod anterior;
	int dimensiune;
public:

	//constructorul implicit
	Colectie();

	//adauga un element in colectie
	void adauga(TElem e);

	//sterge o aparitie a unui element din colectie
	//returneaza adevarat daca s-a putut sterge
	bool sterge(TElem e);

	//verifica daca un element se afla in colectie
	bool cauta(TElem elem);

	//returneaza numar de aparitii ale unui element in colectie
	int nrAparitii(TElem elem);

	//returneaza numarul elementelor unice dintr-o colectie
	int numaraElementeUnice() const;

	//intoarce numarul de elemente din colectie;
	int dim() const;

	//verifica daca colectia e vida;
	bool vida() const;

	//returneaza un iterator pe colectie
	IteratorColectie iterator() const;

	// destructorul colectiei
	~Colectie();

};
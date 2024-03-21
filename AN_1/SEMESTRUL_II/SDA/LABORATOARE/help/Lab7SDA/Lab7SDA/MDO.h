#pragma once
#include <vector>

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

struct Nod {
	TCheie e;
	TValoare* v;
	int lg;
	int cp;
	Nod* st;
	Nod* dr;
	Nod* parinte;
};

class MDO {
	friend class IteratorMDO;
private:
	/* aici e reprezentarea */
	Nod* rad;
	Relatie rel;
	Nod* adauga_rec(Nod* r, Nod* prec, TCheie c, TValoare v);
	void distruge_rec(Nod* r);
	void redimens(Nod* r);
	Nod* cauta_rec(Nod* r, TCheie c) const;
	bool sterge_rec(Nod* r, Nod* prec, TCheie c, TValoare v);
	int dim_rec(Nod* r) const;
public:

	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	// adauga o pereche (cheie, valoare) in MDO
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MDO 
	int dim() const;

	//verifica daca MultiDictionarul Ordonat e vid 
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	IteratorMDO iterator() const;

	int diferentaCheieMaxMin() const;

	// destructorul 	
	~MDO();

};

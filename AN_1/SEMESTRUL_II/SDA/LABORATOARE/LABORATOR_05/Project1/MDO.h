#pragma once
#pragma once

#include <vector>

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO {
	friend class IteratorMDO;
private:
	/* aici e reprezentarea */
	TCheie* chei; //pointer la vectorul de chei
	TValoare** valori; //pointer la pointerul vectorului de valori
	int* stanga;
	int* dreapta;

	int size;
	int cap_valori;
	int cap;

	Relatie rel;

	int radacina;
	int primLiber;

	void actualizeazaPrimLiber();
	int removeElem(int r, TElem e, bool& removed);
	int getMax(int r);

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

	// destructorul 	
	~MDO();

	int diferentaValoareMaxMin() const;
	int cheie_max();

};

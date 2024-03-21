#pragma once
#include <vector>

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

struct LDIvalori {
	int cp;
	int prim, ultim, primLiber;
	TValoare* e;
	int* urm;
	int* prec;
};
struct LDIchei {
	int cp;
	int prim, ultim, primLiber;
	TCheie* e;
	LDIvalori* val;
	int* urm;
	int* prec;
};

class MDO {
	friend class IteratorMDO;
private:
	/* aici e reprezentarea */
	LDIchei l;
	Relatie r;
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

	void aloca();
	void dealoca(int i);
	void redimens();
	void alocaVal(LDIvalori& v);
	void dealocaVal(LDIvalori& v, int i);
	void redimensVal(LDIvalori& v);
	int diferentaCheieMaxMin() const;
	// destructorul 	
	~MDO();

};
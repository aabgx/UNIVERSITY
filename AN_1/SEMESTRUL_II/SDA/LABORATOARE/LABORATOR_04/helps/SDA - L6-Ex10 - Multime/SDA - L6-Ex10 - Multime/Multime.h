#pragma once

#define NULL_TELEM 80030
#define INIT 80030
#define MAX 80040
//#define NIL 80040
//#define sters 80030

typedef int TElem;

class IteratorMultime;

class Multime {
	friend class IteratorMultime;

private:
	int m;
	TElem e[MAX];
	int urm[MAX];
	int primLiber, dimensiune;

	//functia de dispersie
	int d(TElem e) const;

	//functia care actualizeaza primLiber
	void actPrimLiber();

public:
	//constructorul implicit
	Multime();

	//adauga un element in multime
	//returneaza adevarat daca elementul a fost adaugat (nu exista deja in multime)
	bool adauga(TElem e);

	//sterge un element din multime
	//returneaza adevarat daca elementul a existat si a fost sters
	bool sterge(TElem e);

	//verifica daca un element se afla in multime
	bool cauta(TElem elem) const;


	//intoarce numarul de elemente din multime;
	int dim() const;

	//verifica daca multimea e vida;
	bool vida() const;

	//returneaza un iterator pe multime
	IteratorMultime iterator() const;

	// destructorul multimii
	~Multime();
};





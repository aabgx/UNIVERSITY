#pragma once

typedef int TElem;


#define NULL_TELEMENT 0

class Nod;

typedef Nod* PNod;

struct Triplet {
	int i, j;
	TElem val;
};


class Nod {
private:
	/*
	Aici e reprezentarea interna
	*/
	Triplet util;
	PNod urm;

public:
	friend class Matrice;

	/*
	Constructorul nodului
	*/
	Nod(TElem valoare, int i, int j, PNod urm);

	/*
	Seteaza valoarea unui nod
	*/
	void set_valoare(TElem valoare);

	/*
	Determina urmatorul nod
	*/
	PNod urmator();

};
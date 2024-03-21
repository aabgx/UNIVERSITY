#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include "cheltuiala.h"

typedef Cheltuiala Cheltuiala;
typedef struct {
	Cheltuiala* elems;
	int n;
	int cp;
} Vector;

Vector creare_vector_dinamic();
/*
Creeaza o lista goala in care urmeaza sa stocam elemente.

return: lista goala creata
*/

void distruge_vector_dinamic(Vector* v);
/*
Distruge lista impreuna cu fiecare element al sau

v: pointer pt. lista de distrus
*/

Cheltuiala get(Vector* v, int poz);
/*
Returneaza elementul de pe pozitia poz din vector.

v: pointer pt. vector
poz: pozitia elementului de returnat

return: elementul de pe pozitia poz din vector
*/

Cheltuiala set(Vector* v, int poz, Cheltuiala el);
/*
* Pune elementul el pe pozitia poz din vectorul v.
*
v: pointer pt. vector
poz: pozitia din vector
el: elementul de pus pe pozitia poz din vector

return: elementul inlocuit
*/

int size(Vector* v);
/*
Returneaza numarul de elemente in vector.

v: pointer pt. vector

return: numarul de elemente din vector
*/

void adauga(Vector* v, Cheltuiala el);
/*
Adauga un element in vector.

v: pointer pt. vector
el: elementul de adaugat in vector

efect: elementul va fi adaugat in vector
*/

Cheltuiala sterge(Vector* v, int poz);
/*
Sterge un element din vector

v: pointer pt. vector
poz: pozitia de sters din vector

return: elementul sters
efect: elementul a fost sters din vector
*/

Vector copiere(Vector* v);
/*
Creaza o copie a vectorului.

v: pointer pt. vector

return: copia vectorului
*/

void test_creare_vector_dinamic();
void test_iterare();
void test_copiere();



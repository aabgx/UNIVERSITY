#pragma once
#include "vector.h"
#include <stdlib.h>
#include <string.h>
#include <assert.h>

Vector creare_vector_dinamic() {
	Vector v;
	v.cp = 2;
	v.n = 0;
	v.elems = malloc(sizeof(Cheltuiala) * v.cp);
	return v;
}

void distruge_vector_dinamic(Vector* v) {
	for (int i = 0; i < v->n; i++) {
		Cheltuiala el = get(v, i);
		distruge_cheltuiala(&el);
	}
	v->n = 0;
	free(v->elems);
}

Cheltuiala get(Vector* v, int poz) {
	return v->elems[poz];
}

Cheltuiala set(Vector* v, int poz, Cheltuiala el) {
	Cheltuiala el_vechi = v->elems[poz];
	v->elems[poz] = el;
	return el_vechi;
}

int size(Vector* v) {
	return v->n;
}

void adauga(Vector* v, Cheltuiala el) {
	if (v->n == v->cp) {
		int n_cp = v->cp * 2;
		Cheltuiala* spatiu = malloc(sizeof(Cheltuiala) * n_cp);
		for (int i = 0; i < v->n; i++) {
			spatiu[i] = v->elems[i];
		}
		free(v->elems);
		v->elems = spatiu;
		v->cp = n_cp;
	}

	v->elems[v->n] = el;
	v->n++;
}


Cheltuiala sterge(Vector* v, int poz) {
	Cheltuiala el = v->elems[poz];
	for (int i = poz; i < v->n - 1; i++) {
		v->elems[i] = v->elems[i + 1];
	}
	v->n--;
	return el;
}

Vector copiere(Vector* v) {
	Vector copie_v = creare_vector_dinamic();
	for (int i = 0; i < v->n; i++) {
		Cheltuiala el = get(v, i);
		adauga(&copie_v, copiere_cheltuiala(el));
	}
	return copie_v;
}


//TESTE

void test_creare_vector_dinamic() {
	Vector v = creare_vector_dinamic();
	assert(size(&v) == 0);
	distruge_vector_dinamic(&v);

}
void test_iterare() {
	Vector v = creare_vector_dinamic();
	Cheltuiala ch1 = creare_cheltuiala("apa", 23, 35);
	Cheltuiala ch2 = creare_cheltuiala("gaz", 44, 102);
	Cheltuiala ch3 = creare_cheltuiala("curent", 33, 200);

	adauga(&v, ch1);
	adauga(&v, ch2);
	adauga(&v, ch3);


	assert(size(&v) == 3);
	Cheltuiala ch = get(&v, 0);

	assert(strcmp(ch.tip, "apa") == 0);
	assert(ch.nr == 23);
	assert(ch.suma == 35);

	distruge_vector_dinamic(&v);
	assert(size(&v) == 0);

}

void test_copiere() {
	Vector v1 = creare_vector_dinamic();
	adauga(&v1, creare_cheltuiala("apa", 23, 35));
	adauga(&v1, creare_cheltuiala("gaz", 44, 102));

	assert(size(&v1) == 2);
	Vector v2 = copiere(&v1);
	assert(size(&v2) == 2);
	Cheltuiala ch = get(&v2, 0);
	assert(strcmp(ch.tip, "apa") == 0);

	distruge_vector_dinamic(&v1);
	distruge_vector_dinamic(&v2);

}
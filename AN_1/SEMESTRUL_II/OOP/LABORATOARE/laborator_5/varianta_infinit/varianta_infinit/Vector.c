#pragma once
#include "Vector.h"
#include <string.h>
#include <assert.h>
#include <stdlib.h>

Vector* createEmpty(DestroyFct f) {
	Vector* v = malloc(sizeof(Vector));
	v->capacitate = 1;
	v->elems = malloc(sizeof(ElemType) * v->capacitate);
	v->length = 0;
	v->dfnc = f;
	return v;
}

void destroyVector(Vector* v) {
	for (int i = 0; i < v->length; i++) {
		v->dfnc(v->elems[i]);
	}
	v->length = 0;
	free(v->elems);
	free(v);
}


ElemType get(Vector* v, int poz) {
	return v->elems[poz];
}
ElemType setElem(Vector* v, int poz, ElemType el) {
	ElemType replaced = v->elems[poz];
	v->elems[poz] = el;
	return replaced;
}


int size(Vector* v) {
	return v->length;
}

void add(Vector* v, ElemType el) {
	if (v->length >= v->capacitate) {

		int newCapacity = v->capacitate * 2;
		ElemType* aux = malloc(sizeof(ElemType) * newCapacity);
		for (int i = 0; i < v->length; i++) {
			aux[i] = v->elems[i];
		}
		free(v->elems);
		v->elems = aux;
		v->capacitate = newCapacity;
	}
	v->elems[v->length] = el;
	v->length++;
}
ElemType stergere(Vector* v, int poz) {

	ElemType el = v->elems[poz];
	for (int i = poz; i < v->length - 1; i++) {
		v->elems[i] = v->elems[i + 1];
	}
	v->length--;
	return el;
}

Vector* copyVector(Vector* v, CopyFct copyFct) {
	Vector* v_copy = createEmpty(v->dfnc);
	for (int i = 0; i < v->length; i++) {
		ElemType el = get(v, i);
		add(v_copy, copyFct(el));
	}
	return v_copy;
}

void testCreateVector() {
	Vector* v = createEmpty(destroyMedicament);
	assert(size(v) == 0);
	destroyVector(v);

}
void testIterate() {
	Vector* v = createEmpty(destroyMedicament);
	Medicament* m1 = createMedicament("11", "nurofen", 23,1);
	Medicament* m2 = createMedicament("22", "ketonal", 44,2);
	Medicament* m3 = createMedicament("33", "paracetamol", 23,3);

	add(v, m1);
	add(v, m2);
	add(v, m3);

	assert(size(v) == 3);
	Medicament* m = get(v, 0);

	assert(strcmp(m->cod, "11") == 0);
	assert(strcmp(m->nume, "nurofen") == 0);
	assert(m->concentratie == 23);

	destroyVector(v);

}

void testCopy() {
	Vector* v1 = createEmpty(destroyMedicament);
	add(v1, createMedicament("11", "nurofen", 23,1));
	add(v1, createMedicament("22", "ketonal", 44,2));

	assert(size(v1) == 2);
	Vector* v2 = copyVector(v1, copyMedicament);
	assert(size(v2) == 2);
	Medicament* m = get(v2, 0);
	assert(strcmp(m->nume, "nurofen") == 0);
	destroyVector(v1);
	destroyVector(v2);

}

void testDelete() {
	Vector* v1 = createEmpty(destroyMedicament);
	add(v1, createMedicament("11", "nurofen", 23,1));
	add(v1, createMedicament("22", "ketonal", 44,2));


	assert(size(v1) == 2);
	Medicament* m = stergere(v1, 0);

	assert(strcmp(m->cod, "11") == 0);
	assert(strcmp(m->nume, "nurofen") == 0);
	assert(m->concentratie == 23);
	assert(m->cantitate == 1);
	destroyMedicament(m);

	assert(size(v1) == 1);
	destroyVector(v1);

}
void testSet() {
	Vector* v1 = createEmpty(destroyMedicament);
	add(v1, createMedicament("11", "nurofen", 23,1));
	assert(size(v1) == 1);
	Medicament* replaced = setElem(v1, 0, createMedicament("22", "ketonal", 44,2));
	Medicament* m = get(v1, 0);
	assert(strcmp(m->cod, "22") == 0);
	assert(strcmp(m->nume, "ketonal") == 0);
	assert(m->concentratie == 44);
	destroyMedicament(replaced);
	destroyVector(v1);
}

void testVectorVoid() {
	Vector* songVector = createEmpty(destroyMedicament);
	add(songVector, createMedicament("11", "nurofen", 23,1));
	add(songVector, createMedicament("22", "ketonal", 44,2));

	Vector* songVector2 = createEmpty(destroyMedicament);
	add(songVector2, createMedicament("33", "paracetamol", 34,3));
	add(songVector2, createMedicament("44", "sortret", 30,4));
	add(songVector2, createMedicament("55", "magneziu", 57,5));

	Vector* undoVector = createEmpty(destroyVector);

	add(undoVector, songVector);
	assert(size(undoVector) == 1);
	add(undoVector, songVector2);
	assert(size(undoVector) == 2);
	destroyVector(undoVector);
}
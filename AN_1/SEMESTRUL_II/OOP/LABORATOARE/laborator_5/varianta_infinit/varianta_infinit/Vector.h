#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include "Medicament.h"

typedef void* ElemType;

typedef void (*DestroyFct)(ElemType);
typedef ElemType(*CopyFct)(ElemType);

typedef struct {
	ElemType* elems;
	int length;
	int capacitate;
	DestroyFct dfnc;
} Vector;

Vector* createEmpty(DestroyFct f);
/*
* Creeaza o lista goala cu o functie de distrugere.
*/

void destroyVector(Vector* v);
/*
* Distruge lista data si elementele sale.
*
* v: pointer la lista de distrus (Vector*)
*/

ElemType get(Vector*, int poz);
/*
* Returneaza elementul de pe pozitia poz din lista data.
*
* v: (adresa pentru) lista data (Vector*)
* poz: pozitia din lista (int)
*
* return: elementul de pe pozitia poz din lista data (ElemType)
*/

ElemType setElem(Vector* v, int poz, ElemType el);
/*
* Pune elementul el pe pozitia poz din lista v.
*
* v: pointer la lista data (Vector*)
* poz: pozitia din lista (int)
* el: elementul care va fi pus pe pozitia data din lista
*
* return: elem. inlocuit
*/

int size(Vector* v);
/*
* Returneaza numarul de elemente in lista
*
* v: pointer la lista data (Vector*)
*
* return: numarul de elemente din lista (int)
*/

void add(Vector* v, ElemType el);
/*
* Adauga un element in lista
*
* v: pointer la lista data (Vector*)
* el: elementul care se adauga in lista
*
* elementul va fi adaugat in lista
*/

ElemType stergere(Vector* v, int poz);
/*
* Sterge un element din lista
*
* v: pointer la lista data (Vector*)
* poz: pozitia de pe care se sterge din lista
*
* return: elementul sters din lista (ElemType)
* elementul e sters din lista
*/

Vector* copyVector(Vector* v, CopyFct copyFct);
/*
* Face o copie a listei date.
*
* v: pointer la lista care se copiaza (Vector*)
*
* return: copie a listei date (Vector)
*/


void testCreateVector();
void testIterate();
void testCopy();
void testSet();
void testDelete();
void testVectorVoid();



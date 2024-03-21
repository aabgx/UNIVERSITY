#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include "vector.h"

int adauga_cheltuiala_service(Vector* v, char* tip, int nr, int suma);
/*
Adauga o melodie in vector.

v: pointer la vector
tip: tipul cheltuileii - sir
nr: numarul apartamentului - int
suma: suma totala a cheltuielii - int

return: 1 daca s-a efectuat, 0 altfel
efect: cheltuiala este adaugata in vector*/


int sterge_cheltuiala_service(Vector* v, char* tip, int nr);
/*
Sterge cheltuiala
v: pointer la vector

tip: tipul cheltuileii - sir
nr: numarul apartamentului - int

return: 1 daca s-a efectuat, 0 altfel
efect: cheltuiala este stearsa din vector
*/

int modifica_cheltuiala_service(Vector* v, char* tip, int nr, int suma_noua);
/*
Modifica o cheltuiala

v: pointer la vector
tip: tipul cheltuileii - sir (care va ramane acelasi)
nr: numarul apartamentului - int (care va ramane acelasi)
suma_noua: suma totala a cheltuielii - int (va inlocui suma veche)

return: 1 daca s-a efectuat, 0 altfel
efect: cheltuiala cu tip si nr dat va avea suma_noua ca suma
*/

int gaseste_cheltuiala_service(Vector* v, char* tip, int nr);
/*
Gaseste cheltuiala cu tip si nr dat.

v: pointer la vector
tip: tipul cheltuileii - sir
nr: numarul apartamentului - int

return: pozitia din lista a cheltuielii cautate, -1 daca nu exista
*/

Vector filtreaza_cheltuieli_service(Vector* v, char* tip);
/*
Filtreaza vectorul de cheltuieli dupa un tip dat

v: pointer la vector
tip: tipul cheltuileii - sir

return: vectorul de cheltuieli care au tipul tip
*/

Vector sorteaza_cheltuieli_service(Vector* v);
/*
Sorteaza crescator vectorul de cheltuieli dupa suma.

v: pointer la vector

return: vectorul de cheltuieli sortat crescator dupa suma
*/


int compara_2_numere(int a, int b);
/*
Compara 2 numere intregi.
a: primul numar
b: al doilea numar

return: 1 daca a este mai mare, b altfel
*/

Vector sortare(Vector* v, int (*functie)(int, int));
/*
Sorteaza crescator vectorul de cheltuieli dupa suma.

v: pointer la vector
(*functie)(int, int):o functie de comparare predefinita

return: vectorul de cheltuieli sortat crescator dupa suma
*/

void test_adauga_cheltuiala_service();
void test_sterge_cheltuiala_service();
void test_modifica_cheltuiala_service();
void test_gaseste_cheltuiala_service();
void test_filtreaza_cheltuieli_service();
void test_sorteaza_cheltuieli_service();
void test_sortare();


#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

typedef struct {
	char* tip;
	int nr;
	int suma;
} Cheltuiala;

Cheltuiala creare_cheltuiala(char* tip, int nr, int suma);
/*
Creeaza o noua cheltuiala.
tip: tipul cheltuielii - sir
nr: numarul apartamentului - int
suma: suma totala a cheltuielii - int

return: cheltuiala creata
*/

void distruge_cheltuiala(Cheltuiala* ch);
/*
Distruge o cheltuiala existenta.
*/

int validator_cheltuiala(Cheltuiala ch);
/*
Valideaza o cheltuiala:
-tipul sa fie un sir nevid
-nr sa fie un numar intreg >0
-suma sa fie un nr intreg >=0

return: 1 daca melodia este valida, 0 daca nu
*/

Cheltuiala copiere_cheltuiala(Cheltuiala ch);
/*
Creeaza o copie a unei cheltuieli.
*/


void test_creaza_distruge();
void test_validator();

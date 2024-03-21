#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
typedef struct {
	char* cod;
	char* nume;
	int concentratie;
	int cantitate;
} Medicament;

/*
* Creeaza un nou medicament.
*
* cod: cod unic al medicamentului (string)
* nume: numele medicamentului (string)
* concentratie: concentratia medicamentului (int)
* return melodia creata (Medicament)
*/

Medicament* createMedicament(char* cod, char* nume, int concentratie, int cantitate);

/*
* Creeaza o copie a medicamentului
*/
Medicament* copyMedicament(Medicament* m);

/*
* Distruge melodie
*/
void destroyMedicament(Medicament* m);

/*
* Valideaza medicament
* Este valid daca stringurile au minim un caracter si concentratia sa nu fie negativa.
* m: medicamentul de validat (Medicament)
*
* return: 1 daca medicamentul este valid, 0 daca nu
*/
int valideazaMedicament(Medicament* m);

void testCreateDestroy();
void testValideaza();
#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include "Vector.h"

typedef struct {
	Vector* allMed;
	Vector* undoVector;
}MedStore;

MedStore createMedStore();
/*
* Creeaza un MedStore cu o lista de melodii si o lista pentru undo.
*/

void destroyMedStore(MedStore* store);
/*
* Elibereaza memoria alocata pentru un MedStore
*/

int addMedicament(MedStore* store, char* cod, char* nume, int concentratie);
/*
* Adauga un medicament la lista.
*
* store: MedStore in care se adauga (MedStore*)
* cod: cod unic al medicamentului (string)
* nume: numele medicamentului (string)
* concentratie: concentratia medicamentului (int)
* cantitate: numarul de medicamente din stoc (int)
*
* return: 1 daca melodia a fost adaugata cu succes, 0 altfel
* medicamentul este adaugat in lista
*/

int deleteMedicament(MedStore* store, char* cod, char* nume);
/*
* Sterge o melodie
*
* store: MedStore din care se sterge (MedStore*)
* cod: cod unic al medicamentului (string)
* nume: numele medicamentului (string)
*
* return: 1 daca melodia a fost stearsa cu succes, 0 altfel (int)
* medicamentul este sters din lista
*/

int modifyMedicament(MedStore* store, char* cod, char* nume, int concentratie, int cantitateNoua);
/*
* Modifica un medicament
*
* store: MedStore din care se sterge (MedStore*)
* cod: cod unic al medicamentului (string)
* nume: numele medicamentului (string)
* concentratieNoua: noua concentratie a medicamentului (int)
*
* return: 1 daca melodia a fost modificata cu succes, 0 altfel (int)
* concentratia medicamentului va fi modificata
*/

int findMedicament(MedStore* store, char* cod, char* nume);
/*
* Gaseste o melodie cu titlu si artist dat
*
* store: MedStore din care se sterge (MedStore*)
* cod: cod unic al medicamentului (string)
* nume: numele medicamentului (string)
*
* return: pozitia din lista a medicamentului cautat, -1 daca medicamentul nu exista in lista
*/

Vector* filterMedicamente(MedStore* store, char* nume);
/*
* Filtreaza lista demedicamente dupa un nume dat
*
* store: MedStore din care se sterge (MedStore*)
* nume: numele medicamentului dupa care se filtreaza (string)

*
* return: lista cu melodiile care au numele nume (Vector*)
*/
Vector* filterMedicamenteConcentratie(MedStore* store, int concentratie);

Vector* sortNume(MedStore* store);
/*
* Returneaza o lista sortata de medicamente dupa numele acestora.

* store: MedStore din care se sterge (MedStore*)
*
* return: lista sortata dupa criteriul decsris (Vector*)
*/

int undo(MedStore* store);
Vector* filterNou(MedStore* store);

void testAddService();
void testFindService();
void testDeleteService();
void testModifyService();
void testFilterService();
void testFilterServiceConcentratie();
void testSortNumeService();
void testUndo();
void testNou();

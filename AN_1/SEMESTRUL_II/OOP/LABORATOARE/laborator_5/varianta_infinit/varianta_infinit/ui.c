#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <stdio.h>
#include <assert.h>
#pragma warning(disable : 4996)
#include "service.h"


void testAll() {
	testCreateDestroy();
	testValideaza();
	testCreateVector();
	testIterate();
	testVectorVoid();

	testSet();
	testCopy();
	testDelete();
	testAddService();
	testFindService();

	testDeleteService();
	testModifyService();

	testFilterService();
	testFilterServiceConcentratie();
	testSortNumeService();
	testUndo();
	testNou();
}

void printAllMed(Vector* v) {
	if (v->length == 0)
		printf("Nu exista medicamente.\n");
	else
	{
		printf("Medicamentele existente sunt:\n");
		for (int i = 0; i < size(v); i++) {
			Medicament* m = get(v, i);
			printf("Cod: %s | Nume: %s | Concentratie: %d | Cantitate: %d \n", m->cod, m->nume, m->concentratie, m->cantitate);
		};
		printf("\n");
	};
}

void printMenu() {
	printf("0. Iesire\n1. Adaugare\n2. Actualizare\n");
	printf("3. Stergere\n41. Filtrare dupa nume\n42. Filtrare dupa concentratie\n");
	printf("5. Sortare dupa nume\n");

	printf("6. Tiparire\n7. Undo\n8. Filtrare min. 2 produse sortate dupa nume\n\n");
}
void uiAdd(MedStore* store) {
	char cod[30], nume[30];
	int concentratie,cantitate;
	printf("Cod: ");
	assert(scanf("%s", cod) > 0);
	printf("Nume: ");
	assert(scanf("%s", nume) > 0);
	printf("Concentratie: ");
	scanf_s("%d", &concentratie);
	printf("Cantitate: ");
	scanf_s("%d", &cantitate);

	int successful = addMedicament(store, cod, nume, concentratie,cantitate);
	if (successful)
		printf("Medicament adaugat cu succes!\n\n");
	else
		printf("Medicament invalid!\n\n");

}
void uiModify(MedStore* store) {
	char cod[30], nume[30];
	int cantitateNoua, concentratie;
	printf("Cod: ");
	assert(scanf("%s", cod) > 0);
	printf("Nume: ");
	assert(scanf("%s", nume) > 0);
	printf("Concentratie: ");
	scanf_s("%d", &concentratie);
	printf("Cantitate noua: ");
	scanf_s("%d", &cantitateNoua);

	int successful = modifyMedicament(store, cod, nume, concentratie, cantitateNoua);
	if (successful)
		printf("Medicament modificat cu succes!\n\n");
	else
		printf("Nu exista acest medicament!\n\n");
}
void uiDelete(MedStore* store) {
	char cod[30], nume[30];

	printf("Cod: ");
	assert(scanf("%s", cod) > 0);
	printf("Nume: ");
	assert(scanf("%s", nume) > 0);

	int successful = deleteMedicament(store, cod, nume);
	if (successful)
		printf("Medicament sters cu succes!\n\n");
	else
		printf("Nu exista acest medicament!\n\n");
}
void uiFilter(MedStore* store) {
	char nume[30];

	printf("Nume de cautat: ");
	assert(scanf("%s", nume) > 0);

	Vector* vectorFiltrat = filterMedicamente(store, nume);
	printAllMed(vectorFiltrat);
	destroyVector(vectorFiltrat);

}
void uiFilterConcentratie(MedStore* store) {
	int concentratie;

	printf("Concentratie de cautat: ");
	scanf_s("%d", &concentratie);

	Vector* vectorFiltrat = filterMedicamenteConcentratie(store, concentratie);
	printAllMed(vectorFiltrat);
	destroyVector(vectorFiltrat);

}

void uiSortNume(MedStore* store) {
	Vector* listaSortata = sortNume(store);
	printAllMed(listaSortata);
	destroyVector(listaSortata);
}

void uiUndo(MedStore* store) {
	int successful = undo(store);
	if (successful)
		printf("Undo realizat cu succes!\n\n");
	else
		printf("Nu se mai poate face undo!\n\n");
}

void uiNou(MedStore* store) {

	Vector* listaSortata = filterNou(store);
	printAllMed(listaSortata);
	destroyVector(listaSortata);
}

void run() {
	MedStore store = createMedStore();
	int running = 1;
	while (running) {
		printMenu();
		int cmd;
		printf("Comanda dvs este: ");
		scanf_s("%d", &cmd);
		switch (cmd) {
		case 1:
			uiAdd(&store);
			break;
		case 2:
			uiModify(&store);
			break;
		case 3:
			uiDelete(&store);
			break;
		case 41:
			uiFilter(&store);
			break;
		case 42:
			uiFilterConcentratie(&store);
			break;
		case 5:
			uiSortNume(&store);
			break;
		case 6:
			printAllMed(store.allMed);
			break;
		case 7:
			uiUndo(&store);
			break;
		case 8:
			uiNou(&store);
			break;
		case 0:
			running = 0;
			destroyMedStore(&store);
			break;
		default:
			printf("Comanda invalida!\n");

		}
	}
}
int main() {
	testAll();
	run();
	_CrtDumpMemoryLeaks();
}

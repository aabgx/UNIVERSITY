#pragma once
#include "service.h"
#include "customSort.h"
#include <assert.h>
#include <string.h>


MedStore createMedStore()
{
	MedStore store;
	store.allMed = createEmpty(destroyMedicament);
	store.undoVector = createEmpty(destroyVector);
	return store;
}

void destroyMedStore(MedStore* store) {
	destroyVector(store->allMed);
	destroyVector(store->undoVector);
}

int addMedicament(MedStore* store, char* cod, char* nume, int concentratie,int cantitate) {
	Medicament* m = createMedicament(cod, nume, concentratie,cantitate);


	int successful = valideazaMedicament(m);
	if (!successful) {
		destroyMedicament(m);
		return 0;
	}
	Vector* toUndo = copyVector(store->allMed, copyMedicament);
	add(store->allMed, m);
	add(store->undoVector, toUndo);

	return 1;

}
int findMedicament(MedStore* store, char* cod, char* nume) {
	int poz_to_delete = -1;

	for (int i = 0; i < store->allMed->length; i++) {
		Medicament* m = get(store->allMed, i);
		if (strcmp(m->cod, cod) == 0 && strcmp(m->nume, nume) == 0) {
			poz_to_delete = i;
			break;
		}
	}
	return poz_to_delete;
}
int deleteMedicament(MedStore* store, char* cod, char* nume) {
	int poz_to_delete = findMedicament(store, cod, nume);
	if (poz_to_delete != -1) {
		Vector* toUndo = copyVector(store->allMed, copyMedicament);
		add(store->undoVector, toUndo);

		Medicament* m = stergere(store->allMed, poz_to_delete);
		destroyMedicament(m);
		return 1;
	}
	else
		return 0;
}
int modifyMedicament(MedStore* store, char* cod, char* nume, int concentratie, int cantitateNoua) {
	int poz_to_delete = findMedicament(store, cod, nume);
	if (poz_to_delete != -1) {
		Vector* toUndo = copyVector(store->allMed, copyMedicament);
		add(store->undoVector, toUndo);

		Medicament* medicamentNou = createMedicament(cod, nume, concentratie, cantitateNoua);
		Medicament* medicamentReplaced = setElem(store->allMed, poz_to_delete, medicamentNou);
		destroyMedicament(medicamentReplaced);
		return 1;
	}
	else
		return 0;
}
Vector* filterMedicamente(MedStore* store, char* nume) {
	if (strcmp(nume, "") != 0) {
		Vector* listaFiltrata = createEmpty(destroyMedicament);
		for (int i = 0; i < store->allMed->length; i++) {
			Medicament* m = get(store->allMed, i);
			if (strcmp(nume, m->nume) == 0)
				add(listaFiltrata, createMedicament(m->cod, m->nume, m->concentratie,m->cantitate));
		}
		return listaFiltrata;
	}
	else {
		return copyVector(store->allMed, copyMedicament);
	}

}

Vector* filterMedicamenteConcentratie(MedStore* store, int concentratie) {
	if (concentratie>=0) {
		Vector* listaFiltrata = createEmpty(destroyMedicament);
		for (int i = 0; i < store->allMed->length; i++) {
			Medicament* m = get(store->allMed, i);
			if (concentratie >=  m->concentratie)
				add(listaFiltrata, createMedicament(m->cod, m->nume, m->concentratie,m->cantitate));
		}
		return listaFiltrata;
	}
	else {
		return copyVector(store->allMed, copyMedicament);
	}

}

int cmpNume(Medicament* m1, Medicament* m2) {
	return strcmp(m1->nume, m2->nume);
}

Vector* sortNume(MedStore* store) {
	Vector* medVector = copyVector(store->allMed, copyMedicament);
	sort(medVector, cmpNume);
	return medVector;
}

Vector* filterNou(MedStore* store) {

		Vector* listaFiltrata = createEmpty(destroyMedicament);
		for (int i = 0; i < store->allMed->length; i++) {
			Medicament* m = get(store->allMed, i);
			if (2 <= m->cantitate)
				add(listaFiltrata, createMedicament(m->cod, m->nume, m->concentratie, m->cantitate));
		}

		Vector* medVector = copyVector(listaFiltrata, copyMedicament);
		destroyVector(listaFiltrata);

		sort(medVector, cmpNume);
		return medVector;

}


int undo(MedStore* store) {
	if (size(store->undoVector) == 0)
		//nothing to undo
		return 0;
	Vector* v = stergere(store->undoVector, store->undoVector->length - 1);
	destroyVector(store->allMed);
	store->allMed = v;
	return 1;
}

void testAddService() {
	MedStore store = createMedStore();
	int successful1 = addMedicament(&store, "11", "nurofen", 56,12);
	assert(successful1 == 1);

	int successful2 = addMedicament(&store, "", "", 56,23);
	assert(successful2 == 0);

	int successful3 = addMedicament(&store, "11", "nurofen", -56,77);
	assert(successful3 == 0);

	Vector* allMed = filterMedicamente(&store, "");
	assert(size(allMed) == 1);
	destroyVector(allMed);

	destroyMedStore(&store);


}
void testFindService() {
	MedStore store = createMedStore();

	int successful1 = addMedicament(&store, "11", "nurofen", 56,12);
	assert(successful1 == 1);

	int successful2 = addMedicament(&store, "22", "paracetamol", 85,34);
	assert(successful2 == 1);

	int successful3 = addMedicament(&store, "33", "ketonal", 76,47);
	assert(successful3 == 1);

	assert(size(store.allMed) == 3);
	int poz = findMedicament(&store, "22", "paracetamol");

	assert(poz == 1);

	destroyMedStore(&store);

}
void testModifyService() {
	MedStore store = createMedStore();

	int successful1 = addMedicament(&store, "11", "nurofen", 56,12);
	assert(successful1 == 1);

	int successful2 = addMedicament(&store, "22", "paracetamol", 85,13);
	assert(successful2 == 1);

	int successful3 = addMedicament(&store, "33", "ketonal", 76,2);
	assert(successful3 == 1);

	assert(size(store.allMed) == 3);
	int modify_success = modifyMedicament(&store, "22", "paracetamol", 85,1);
	assert(modify_success == 1);
	int modify_success2 = modifyMedicament(&store, "44", "ketonal", 23,2);
	assert(modify_success2 == 0);
	destroyMedStore(&store);
}
void testDeleteService() {
	MedStore store = createMedStore();


	int successful1 = addMedicament(&store, "11", "nurofen", 56, 12);
	assert(successful1 == 1);

	int successful2 = addMedicament(&store, "22", "paracetamol", 85, 13);
	assert(successful2 == 1);

	int successful3 = addMedicament(&store, "33", "ketonal", 76, 2);
	assert(successful3 == 1);

	assert(size(store.allMed) == 3);
	int succesful_del = deleteMedicament(&store, "22", "paracetamol");
	assert(succesful_del == 1);
	int succesfully_del = deleteMedicament(&store, "44", "vitamine");
	assert(succesfully_del == 0);
	destroyMedStore(&store);
}

void testFilterService() {
	MedStore store = createMedStore();

	int successful1 = addMedicament(&store, "11", "nurofen", 56,1);
	assert(successful1 == 1);

	int successful2 = addMedicament(&store, "22", "vitamine", 85,2);
	assert(successful2 == 1);

	int successful3 = addMedicament(&store, "33", "ketonal", 76,3);
	assert(successful3 == 1);

	int successful4 = addMedicament(&store, "44", "vitamine", 23,4);
	assert(successful4 == 1);

	assert(size(store.allMed) == 4);

	Vector* listaFiltrata = filterMedicamente(&store, "vitamine");
	assert(size(listaFiltrata) == 2);
	destroyVector(listaFiltrata);

	listaFiltrata = filterMedicamente(&store, "paracetamol");
	assert(size(listaFiltrata) == 0);
	destroyVector(listaFiltrata);

	destroyMedStore(&store);
}

void testFilterServiceConcentratie() {
	MedStore store = createMedStore();

	int successful1 = addMedicament(&store, "11", "nurofen", 22,1);
	assert(successful1 == 1);

	int successful2 = addMedicament(&store, "22", "vitamine", 85,2);
	assert(successful2 == 1);

	int successful3 = addMedicament(&store, "33", "ketonal", 76,3);
	assert(successful3 == 1);

	int successful4 = addMedicament(&store, "44", "vitamine", 22,4);
	assert(successful4 == 1);

	assert(size(store.allMed) == 4);

	Vector* listaFiltrata = filterMedicamenteConcentratie(&store, 55);
	assert(size(listaFiltrata) == 2);
	destroyVector(listaFiltrata);

	listaFiltrata = filterMedicamenteConcentratie(&store, 11);
	assert(size(listaFiltrata) == 0);
	destroyVector(listaFiltrata);

	destroyMedStore(&store);
}


void testSortNumeService() {
	MedStore store = createMedStore();
	addMedicament(&store, "11", "vitamine", 23,1);
	addMedicament(&store, "22", "aciclovir", 59,2);
	addMedicament(&store, "33", "b_vitamin", 67,3);
	addMedicament(&store, "44", "insulina", 12,4);

	Vector* vectorSortat = sortNume(&store);
	Medicament* m1 = get(vectorSortat, 0);
	Medicament* m2 = get(vectorSortat, 1);
	Medicament* m3 = get(vectorSortat, 2);
	Medicament* m4 = get(vectorSortat, 3);

	assert(strcmp(m1->nume, "aciclovir") == 0);
	assert(strcmp(m2->nume, "b_vitamin") == 0);
	assert(strcmp(m3->nume, "insulina") == 0);
	assert(strcmp(m4->nume, "vitamine") == 0);

	destroyVector(vectorSortat);
	destroyMedStore(&store);

}

void testNou() {
	MedStore store = createMedStore();

	int successful1 = addMedicament(&store, "11", "aciclovir", 22, 1);
	assert(successful1 == 1);

	int successful2 = addMedicament(&store, "22", "insulina", 85, 2);
	assert(successful2 == 1);

	int successful3 = addMedicament(&store, "33", "b_vitamin", 76, 0);
	assert(successful3 == 1);

	int successful4 = addMedicament(&store, "44", "vitamine", 22, 4);
	assert(successful4 == 1);

	assert(size(store.allMed) == 4);

	Vector* listaFiltrata = filterNou(&store);
	assert(size(listaFiltrata) == 2);

	Medicament* m1 = get(listaFiltrata, 0);
	Medicament* m2 = get(listaFiltrata, 1);

	assert(strcmp(m1->nume, "insulina") == 0);
	assert(strcmp(m2->nume, "vitamine") == 0);


	destroyVector(listaFiltrata);
	destroyMedStore(&store);
}

void testUndo() {

	MedStore store = createMedStore();
	assert(addMedicament(&store, "11", "nurofen", 23,1) == 1);
	assert(modifyMedicament(&store, "11", "nurofen", 23,10) == 1);
	assert(deleteMedicament(&store, "11", "nurofen") == 1);

	undo(&store);

	Vector* crtMedVector = filterMedicamente(&store, "");
	assert(size(crtMedVector) == 1);
	Medicament* m = get(crtMedVector, 0);
	assert(m->concentratie == 23);
	destroyVector(crtMedVector);

	undo(&store);

	crtMedVector = filterMedicamente(&store, "");
	assert(size(crtMedVector) == 1);
	Medicament* m1 = get(crtMedVector, 0);
	assert(m1->concentratie == 23);
	destroyVector(crtMedVector);

	undo(&store);
	crtMedVector = filterMedicamente(&store, "");
	assert(size(crtMedVector) == 0);
	destroyVector(crtMedVector);

	int moreUndo = undo(&store);
	assert(moreUndo == 0);
	destroyMedStore(&store);
}
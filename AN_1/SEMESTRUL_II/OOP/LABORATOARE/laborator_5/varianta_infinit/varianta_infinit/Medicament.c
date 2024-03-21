#pragma once
#include "Medicament.h"
#include <string.h>
#include <assert.h>
#include <stdlib.h>

Medicament* createMedicament(char* cod, char* nume, int concentratie, int cantitate) {
	Medicament* m = malloc(sizeof(Medicament));
	int nrC = (int)strlen(cod) + 1;
	m->cod = malloc(nrC * sizeof(char));
	strcpy_s(m->cod, nrC, cod);

	nrC = (int)strlen(nume) + 1;
	m->nume = malloc(nrC * sizeof(char));
	strcpy_s(m->nume, nrC, nume);

	m->concentratie = concentratie;
	m->cantitate = cantitate;

	return m;
}

void destroyMedicament(Medicament* m) {
	free(m->cod);
	free(m->nume);
	free(m);
}
Medicament* copyMedicament(Medicament* m) {
	return createMedicament(m->cod, m->nume, m->concentratie,m->cantitate);
}

int valideazaMedicament(Medicament* m) {
	if (strlen(m->cod) <= 0)
		return 0;
	if (strlen(m->nume) <= 0)
		return 0;
	if (m->concentratie < 0)
		return 0;
	if (m->cantitate < 0)
		return 0;
	return 1;
}

void testCreateDestroy() {
	Medicament* m = createMedicament("11", "nurofen", 22,120);

	assert(strcmp(m->cod, "11") == 0);
	assert(strcmp(m->nume, "nurofen") == 0);
	assert(m->concentratie == 22);

	destroyMedicament(m);

}

void testValideaza() {
	Medicament* m1 = createMedicament("", "nurofen", 23,23);
	assert(valideazaMedicament(m1) == 0);

	Medicament* m2 = createMedicament("11", "", 23,120);
	assert(valideazaMedicament(m2) == 0);

	Medicament* m3 = createMedicament("11", "nurofen", -8, 48);
	assert(valideazaMedicament(m3) == 0);

	Medicament* m4 = createMedicament("11", "nurofen", 23, -8);
	assert(valideazaMedicament(m4) == 0);

	Medicament* m5 = createMedicament("11", "nurofen", 23, 75);
	assert(valideazaMedicament(m5) == 1);

	destroyMedicament(m1);
	destroyMedicament(m2);
	destroyMedicament(m3);
	destroyMedicament(m4);
	destroyMedicament(m5);


}


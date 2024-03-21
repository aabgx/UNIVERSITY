#pragma once
#include "cheltuiala.h"
#include <string.h>
#include <stdlib.h>
#include <assert.h>

Cheltuiala creare_cheltuiala(char* tip, int nr, int suma) {
	Cheltuiala ch;
	int lung = (int)strlen(tip) + 1;
	ch.tip = malloc(lung * sizeof(char));
	strcpy_s(ch.tip, lung, tip);

	ch.nr = nr;
	ch.suma = suma;

	return ch;
}

void distruge_cheltuiala(Cheltuiala* ch) {
	free(ch->tip);
	ch->nr = -1;
	ch->suma = -1;
}

Cheltuiala copiere_cheltuiala(Cheltuiala ch) {
	return creare_cheltuiala(ch.tip, ch.nr, ch.suma);
}

int validator_cheltuiala(Cheltuiala ch) {
	if (strlen(ch.tip) == 0 || ch.nr <= 0 || ch.suma < 0)
		return 0;
	return 1;
}


//TESTE

void test_creaza_distruge() {
	Cheltuiala ch = creare_cheltuiala("apa", 23, 35);

	assert(strcmp(ch.tip, "apa") == 0);
	assert(ch.nr == 23);
	assert(ch.suma == 35);

	distruge_cheltuiala(&ch);

}

void test_validator() {

	Cheltuiala ch1 = creare_cheltuiala("apa", -5, 35);
	assert(validator_cheltuiala(ch1) == 0);
	distruge_cheltuiala(&ch1);

	Cheltuiala ch2 = creare_cheltuiala("", 23, 35);
	assert(validator_cheltuiala(ch2) == 0);
	distruge_cheltuiala(&ch2);

	Cheltuiala ch3 = creare_cheltuiala("apa", 23, -35);
	assert(validator_cheltuiala(ch3) == 0);
	distruge_cheltuiala(&ch3);

	Cheltuiala ch4 = creare_cheltuiala("apa", 23, 35);
	assert(validator_cheltuiala(ch4) == 1);
	distruge_cheltuiala(&ch4);

}
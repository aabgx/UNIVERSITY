#pragma once
#include "service.h"
#include <assert.h>
#include <string.h>

int adauga_cheltuiala_service(Vector* v, char* tip, int nr, int suma) {
	Cheltuiala ch = creare_cheltuiala(tip, nr, suma);
	int successful = validator_cheltuiala(ch);
	if (!successful) {
		distruge_cheltuiala(&ch);
		return 0;
	}
	adauga(v, ch);
	return 1;

}
int gaseste_cheltuiala_service(Vector* v, char* tip, int nr) {
	int poz_de_sters = -1;
	for (int i = 0; i < v->n; i++) {
		Cheltuiala ch = get(v, i);
		if (strcmp(ch.tip, tip) == 0 && ch.nr == nr) {
			poz_de_sters = i;
			break;
		}
	}
	return poz_de_sters;
}
int sterge_cheltuiala_service(Vector* v, char* tip, int nr) {
	int poz_de_sters = gaseste_cheltuiala_service(v, tip, nr);
	if (poz_de_sters != -1) {
		Cheltuiala ch = sterge(v, poz_de_sters);
		distruge_cheltuiala(&ch);
		return 1;
	}
	else
		return 0;
}
int modifica_cheltuiala_service(Vector* v, char* tip, int nr, int suma_noua) {
	int poz_de_sters = gaseste_cheltuiala_service(v, tip, nr);

	if (poz_de_sters != -1) {
		Cheltuiala n_ch = creare_cheltuiala(tip, nr, suma_noua);
		Cheltuiala ch = get(v, poz_de_sters);
		distruge_cheltuiala(&ch);
		set(v, poz_de_sters, n_ch);
		return 1;
	}
	else
		return 0;
}
Vector filtreaza_cheltuieli_service(Vector* v, char* tip) {

	if (strcmp(tip, "") != 0) {
		Vector lista_buna = creare_vector_dinamic();
		for (int i = 0; i < v->n; i++) {
			Cheltuiala ch = get(v, i);
			if (strcmp(tip, ch.tip) == 0)
				adauga(&lista_buna, creare_cheltuiala(ch.tip, ch.nr, ch.suma));
		}
		return lista_buna;
	}
	else {
		return copiere(v);
	}

}

Vector sorteaza_cheltuieli_service(Vector* v) {
	int ok=1;
	Vector lista_buna = copiere(v);
	Cheltuiala ch_aux;
	while (ok) {
		ok = 0;
		for (int i = 0; i < lista_buna.n -1; i++) {
			
			if (lista_buna.elems[i].suma > lista_buna.elems[i+1].suma) {
				ch_aux = lista_buna.elems[i];
				lista_buna.elems[i] = lista_buna.elems[i + 1];
				lista_buna.elems[i + 1] = ch_aux;
				ok = 1;
			}

			
		}
	}
	/*distruge_cheltuiala(&ch_aux);*/
	return lista_buna;


}

int compara_2_numere(int a, int b) {
	if (a >= b) return 1;
	else return 0;
}

Vector sortare(Vector* v, int (*functie)(int,int)) {
	
	int ok = 1;
	Vector lista_buna = copiere(v);
	Cheltuiala ch_aux;
	while (ok) {
		ok = 0;
		for (int i = 0; i < lista_buna.n - 1; i++) {
			
			if ((*functie)(lista_buna.elems[i].suma,lista_buna.elems[i + 1].suma)==1) {
				ch_aux = lista_buna.elems[i];
				lista_buna.elems[i] = lista_buna.elems[i + 1];
				lista_buna.elems[i + 1] = ch_aux;
				ok = 1;
			}


		}
	}
	/*distruge_cheltuiala(&ch_aux);*/
	return lista_buna;
}




//TESTE

void test_adauga_cheltuiala_service() {
	Vector v = creare_vector_dinamic();

	int successful1 = adauga_cheltuiala_service(&v, "apa", 23, 35);
	assert(successful1 == 1);

	int successful2 = adauga_cheltuiala_service(&v, "", -1, 56);
	assert(successful2 == 0);

	int successful3 = adauga_cheltuiala_service(&v, "curent", 44, -6);
	assert(successful3 == 0);

	assert(size(&v) == 1);
	Cheltuiala ch = get(&v, 0);

	assert(strcmp(ch.tip, "apa") == 0);
	assert(ch.nr == 23);
	assert(ch.suma == 35);
	distruge_vector_dinamic(&v);


}

void test_gaseste_cheltuiala_service() {
	Vector v = creare_vector_dinamic();

	int successful1 = adauga_cheltuiala_service(&v, "apa", 23, 35);
	assert(successful1 == 1);

	int successful2 = adauga_cheltuiala_service(&v, "curent", 44, 102);
	assert(successful2 == 1);

	int successful3 = adauga_cheltuiala_service(&v, "gaz", 29, 200);
	assert(successful3 == 1);

	assert(size(&v) == 3);
	int poz = gaseste_cheltuiala_service(&v, "gaz", 29);

	assert(poz == 2);

	distruge_vector_dinamic(&v);
	assert(size(&v) == 0);
}

void test_modifica_cheltuiala_service() {
	Vector v = creare_vector_dinamic();

	int successful1 = adauga_cheltuiala_service(&v, "apa", 23, 35);
	assert(successful1 == 1);

	int successful2 = adauga_cheltuiala_service(&v, "curent", 44, 102);
	assert(successful2 == 1);

	int successful3 = adauga_cheltuiala_service(&v, "gaz", 29, 200);
	assert(successful3 == 1);

	assert(size(&v) == 3);

	int success = modifica_cheltuiala_service(&v, "gaz", 29, 120);
	assert(success == 1);
	int success2 = modifica_cheltuiala_service(&v, "apa", 29, 25);
	assert(success2 == 0);
	distruge_vector_dinamic(&v);
}
void test_sterge_cheltuiala_service() {
	Vector v = creare_vector_dinamic();

	int successful1 = adauga_cheltuiala_service(&v, "apa", 23, 35);
	assert(successful1 == 1);

	int successful2 = adauga_cheltuiala_service(&v, "curent", 44, 102);
	assert(successful2 == 1);

	int successful3 = adauga_cheltuiala_service(&v, "gaz", 29, 200);
	assert(successful3 == 1);

	assert(size(&v) == 3);

	int del1 = sterge_cheltuiala_service(&v, "gaz", 29);
	assert(del1 == 1);
	int del2 = sterge_cheltuiala_service(&v, "apa", 44);
	assert(del2 == 0);
	int del3 = sterge_cheltuiala_service(&v, "apa", 23);
	assert(del3 == 1);
	distruge_vector_dinamic(&v);
	assert(size(&v) == 0);
}

void test_filtreaza_cheltuieli_service() {
	Vector v = creare_vector_dinamic();

	int successful1 = adauga_cheltuiala_service(&v, "apa", 23, 35);
	assert(successful1 == 1);

	int successful2 = adauga_cheltuiala_service(&v, "curent", 44, 102);
	assert(successful2 == 1);

	int successful3 = adauga_cheltuiala_service(&v, "gaz", 29, 200);
	assert(successful3 == 1);

	int successful4 = adauga_cheltuiala_service(&v, "apa", 100, 67);
	assert(successful4 == 1);

	assert(size(&v) == 4);

	Vector lista_noua = filtreaza_cheltuieli_service(&v, "apa");
	assert(size(&lista_noua) == 2);
	distruge_vector_dinamic(&lista_noua);

	lista_noua = filtreaza_cheltuieli_service(&v, "internet");
	assert(size(&lista_noua) == 0);
	distruge_vector_dinamic(&lista_noua);

	lista_noua = filtreaza_cheltuieli_service(&v, "");
	assert(size(&lista_noua) == 4);
	distruge_vector_dinamic(&lista_noua);

	distruge_vector_dinamic(&v);

}

void test_sorteaza_cheltuieli_service() {
	Vector v = creare_vector_dinamic();

	int successful1 = adauga_cheltuiala_service(&v, "apa", 23, 35);
	assert(successful1 == 1);

	int successful2 = adauga_cheltuiala_service(&v, "curent", 44, 102);
	assert(successful2 == 1);

	int successful3 = adauga_cheltuiala_service(&v, "gaz", 29, 200);
	assert(successful3 == 1);

	int successful4 = adauga_cheltuiala_service(&v, "apa", 100, 67);
	assert(successful4 == 1);

	assert(size(&v) == 4);

	Vector lista_noua = sorteaza_cheltuieli_service(&v);

	assert(size(&lista_noua) == 4);

	Cheltuiala ch1 = lista_noua.elems[0], ch2 = lista_noua.elems[3];

	assert(strcmp(ch1.tip, "apa") == 0);
	assert(ch1.nr == 23);
	assert(ch1.suma == 35);

	assert(strcmp(ch2.tip, "gaz") == 0);
	assert(ch2.nr == 29);
	assert(ch2.suma == 200);

	distruge_vector_dinamic(&lista_noua);
	distruge_vector_dinamic(&v);



}
void test_sortare() {
	Vector v = creare_vector_dinamic();

	int successful1 = adauga_cheltuiala_service(&v, "apa", 23, 35);
	assert(successful1 == 1);

	int successful2 = adauga_cheltuiala_service(&v, "curent", 44, 102);
	assert(successful2 == 1);

	int successful3 = adauga_cheltuiala_service(&v, "gaz", 29, 200);
	assert(successful3 == 1);

	int successful4 = adauga_cheltuiala_service(&v, "apa", 100, 67);
	assert(successful4 == 1);

	assert(size(&v) == 4);

	Vector lista_noua = sortare(&v, compara_2_numere);

	assert(size(&lista_noua) == 4);

	Cheltuiala ch1 = lista_noua.elems[0], ch2 = lista_noua.elems[3];

	assert(strcmp(ch1.tip, "apa") == 0);
	assert(ch1.nr == 23);
	assert(ch1.suma == 35);

	assert(strcmp(ch2.tip, "gaz") == 0);
	assert(ch2.nr == 29);
	assert(ch2.suma == 200);

	distruge_vector_dinamic(&lista_noua);
	distruge_vector_dinamic(&v);



}
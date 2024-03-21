#pragma once
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <stdio.h>
#include <assert.h>
#pragma warning(disable : 4996)
#include "service.h"
#include "vector.h"


void test_all() {
	
	test_creaza_distruge();
	test_validator();

	test_creare_vector_dinamic();
	test_iterare();
	test_copiere();

	test_adauga_cheltuiala_service();
	test_modifica_cheltuiala_service();
	test_sterge_cheltuiala_service();
	test_gaseste_cheltuiala_service();
	test_filtreaza_cheltuieli_service();
	test_sorteaza_cheltuieli_service();
	//test_sortare();
}

void print_all(Vector* v) {
	if (v->n == 0)
		printf("Nu s-a adaugat nicio cheltuiala!\n");
	else
	{
		printf("Cheltuielile sunt:\n");
		for (int i = 0; i < size(v); i++) {
			Cheltuiala ch = get(v, i);
			printf("Tip: %s | Nr.apartament: %d | Suma: %d\n", ch.tip, ch.nr, ch.suma);
		};
	};
}

void print_meniu() {
	printf("1. adaugare, 2. modificare cheltuiala, 3. stergere, 4. filtrare, 5. afisare, 6. sortare, 7. iesire\n");
}
void adaugare_ui(Vector* v) {
	char tip[30];
	int nr, suma;

	printf("Tipul cheltuielii:");
	assert(scanf("%s", tip)>0); ///pt warning, zice ca e nefolosit ce citesc cu scanf altfel
	printf("Nr. apartament:");
	scanf_s("%d", &nr);
	printf("Suma totala este:");
	scanf_s("%d", &suma);

	int ok = adauga_cheltuiala_service(v, tip, nr, suma);
	if (ok)
		printf("Cheltuiala adaugata cu succes.\n");
	else
		printf("Cheltuiala invalida.\n");

}
void modificare_ui(Vector* v) {
	char tip[30];
	int nr, suma_noua;

	printf("Tipul cheltuielii:");
	assert(scanf("%s", tip)>0);
	printf("Nr. apartament:");
	scanf_s("%d", &nr);
	printf("Suma totala noua este:");
	scanf_s("%d", &suma_noua);

	int ok = modifica_cheltuiala_service(v, tip, nr, suma_noua);
	if (ok)
		printf("Cheltuiala modificata cu succes.\n");
	else
		printf("Nu exista cheltuiala ceruta.\n");
}
void stergere_ui(Vector* v) {
	char tip[30];
	int nr;
	printf("Tipul cheltuielii:");
	assert(scanf("%s", tip)>0);
	printf("Nr. apartament:");
	scanf_s("%d", &nr);

	int ok = sterge_cheltuiala_service(v, tip, nr);
	if (ok)
		printf("Cheltuiala stearsa cu succes.\n");
	else
		printf("Nu exista cheltuiala ceruta.\n");
}
void filtrare_ui(Vector* v) {
	char tip[30];
	printf("Tipul cheltuielii este:");
	assert(scanf("%s", tip)>0);

	Vector lista_buna = filtreaza_cheltuieli_service(v, tip);
	print_all(&lista_buna);
	distruge_vector_dinamic(&lista_buna);

}

void sortare_ui(Vector* v) {
	printf("Lista sortata dupa suma este: \n");
	Vector lista_buna = sorteaza_cheltuieli_service(v);
	print_all(&lista_buna);
	distruge_vector_dinamic(&lista_buna);
}

void consola() {
	Vector lista_cheltuieli = creare_vector_dinamic();
	int ok = 1;
	while (ok) {
		print_meniu();
		int cmd;
		printf("Comanda dvs este:");
		scanf_s("%d", &cmd);
		switch (cmd) {
		case 1:
			adaugare_ui(&lista_cheltuieli);
			printf("\n");
			break;
		case 2:
			modificare_ui(&lista_cheltuieli);
			printf("\n");
			break;
		case 3:
			stergere_ui(&lista_cheltuieli);
			printf("\n");
			break;
		case 4:
			filtrare_ui(&lista_cheltuieli);
			printf("\n");
			break;
		case 5:
			print_all(&lista_cheltuieli);
			printf("\n");
			break;
		case 6:
			sortare_ui(&lista_cheltuieli);
			printf("\n");
			break;
		case 7:
			ok = 0;
			distruge_vector_dinamic(&lista_cheltuieli);
			break;
		default:
			printf("Comanda invalida!\n");
			printf("\n");

		}
	}

}

int main() {
	test_all();
	consola();
	_CrtDumpMemoryLeaks();

}

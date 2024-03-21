#pragma once
#include "activitate.h"
#include <string>
#include <assert.h>
using namespace std;
//aici pun metodele din clasa

string Activitate::get_titlu() {
	return this->titlu;
}
void Activitate::set_titlu(const string& titlu_nou) {
	this->titlu = titlu_nou;
}

string Activitate::get_descriere() {
	return this->descriere;
}
void Activitate::set_descriere(const string& descriere_noua) {
	this->descriere = descriere_noua;
}

int Activitate::get_durata() {
	return this->durata;
}
void Activitate::set_durata(const int& durata_noua) {
	this->durata = durata_noua;
}

string Activitate::get_tip() {
	return this->tip;
}
void Activitate::set_tip(const string& tip_nou) {
	this->tip = tip_nou;
}

bool Activitate::eq(Activitate a) {
	if (this->titlu == a.get_titlu() && this->descriere == a.get_descriere())
		return true;
	return false;
}
	string Activitate::str() {
		string durata_asta = to_string(this->durata);
		return "Titlu: " + this->titlu + " Descriere: " + this->descriere + " Tip: " + this->tip + " Durata: " + durata_asta;
}

bool cmp_titlu(Activitate& a1,Activitate& a2) {
	return a1.get_titlu() < a2.get_titlu();
}

bool cmp_descriere(Activitate& a1,Activitate& a2) {
	return a1.get_descriere() < a2.get_descriere();
}

bool cmp_tip_durata(Activitate& a1,Activitate& a2) {
	if (a1.get_tip() == a2.get_tip())
		return a1.get_durata() < a2.get_durata();
	else return a1.get_tip() < a2.get_tip();
}


	
void test_activitate() {
	Activitate a{ "titlu", "descriere","tip",120};
	assert(a.get_titlu() == "titlu");
	assert(a.get_descriere() == "descriere");
	assert(a.get_tip() == "tip");
	assert(a.get_durata() == 120);

	a.set_titlu("alt_titlu");
	a.set_descriere("alta_descriere");
	a.set_tip("alt_tip");
	a.set_durata(130);

	assert(a.get_titlu() == "alt_titlu");
	assert(a.get_descriere() == "alta_descriere");
	assert(a.get_tip() == "alt_tip");
	assert(a.get_durata() == 130);
}

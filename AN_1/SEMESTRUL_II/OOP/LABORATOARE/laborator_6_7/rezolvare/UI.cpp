#pragma once
#include "UI.h"
#include <iostream>
#include <string>
#include <exception>
using namespace std;

Consola::Consola(const Service& srv) {
	this->srv = srv;
}

void Consola::print_menu() {

	cout << "\nComenzi: 1. adaugare 2.stergere 3.modificare 4.afisare 5.cauta 6. filtrare 7.sortare 8.iesire" << endl;
}
void Consola::str_ui() {
	cout << "Activitatile disponibile sunt: " <<endl<< this->srv.str_srv();

}

void Consola::adauga_ui() {
	string titlu, descriere, tip,durata;
	cout << "Titlu: "; 
	getline(cin>>ws, titlu);
	cout << "Descriere: "; 
	getline(cin>>ws, descriere);
	cout << "Tip: ";
	getline(cin>>ws, tip);
	cout << "Durata: "; 
	getline(cin >> ws, durata);
	try {
		int durata_int = stoi(durata);
		Activitate a(titlu, descriere, tip, durata_int);
		try {
			this->srv.adauga_srv(titlu, descriere, tip, durata_int);
			cout << "Activitate adaugata cu succes!" << endl;
		}
		catch (exception&) {
			cout << "Activitatea deja exista!\n" << endl;
		}
	}
	catch (exception&) {
		cout << "Durata trebuie sa fie un numar!\n";
	}
}

void Consola::sterge_ui() {
	string titlu;
	cout << "Titlu: "; getline(cin >> ws, titlu);
	try {
		this->srv.sterge_srv(titlu);
		cout << "Activitate stearsa cu succes!" << endl;
	}
	catch (exception&) {
		cout << "Nu exista activitate cu acest titlu!" << endl;
	}
}

void Consola::modificare_ui() {
	string titlu, descriere, tip;
	int durata;
	cout << "Titlu: ";
	getline(cin >> ws, titlu);
	cout << "Descriere noua: ";
	getline(cin >> ws, descriere);
	cout << "Tip nou: ";
	getline(cin >> ws, tip);
	cout << "Durata noua: ";
	cin >> durata;

	try {
		this->srv.modifica_srv(titlu, descriere, tip, durata);
		cout << "Activitate modificata cu succes!\n" << endl;
	}

	catch (exception&) {
		cout << "Nu exista activitate cu acest titlu!\n" << endl;
	}

}

void Consola::cauta_ui() {
	string titlu;
	cout << "Titlu: ";
	getline(cin >> ws, titlu);

	Activitate a = this->srv.cauta_srv(titlu);
	if (a.get_titlu() != "null") cout << "Activitatea cautata este: \n" + a.str()<<endl<<endl;
	else cout << "Nu exista activitate cu acest titlu!\n" << endl;
}

void Consola::filtrare_ui() {

	cout << "Filtrare dupa 1.descriere 2.tip: ";
	string optiune;
	getline(cin >> ws, optiune);
	try {
		int o = stoi(optiune);
		if (o != 1 && o != 2) throw exception();
		if (o == 1) {
			string descriere;
			cout << "Descrierea de afisat: ";
			getline(cin >> ws, descriere);

			VectorDinamic<Activitate> lista;
			lista = this->srv.filtrare_descriere(descriere);
			if (lista.size() != 0) cout << de_afisat(lista);
			else cout << "Nu exista astfel de activitati!" << endl;
		}
		else if (o == 2) {
			string tip;
			cout << "Tipul de afisat: ";
			getline(cin >> ws, tip);

			VectorDinamic<Activitate> lista;
			lista = this->srv.filtrare_tip(tip);
			if (lista.size() != 0) cout << de_afisat(lista);
			else cout << "Nu exista astfel de activitati!" << endl;
		}
		else cout << "Comanda invalida!!" << endl;
	}
	catch (exception&) { cout << "Optiunea trebuie sa fie un numar : 1 sau 2!\n\n"; }
}

string Consola::de_afisat(VectorDinamic<Activitate> lista) {
	string lista_de_afisat = "";

	for (int i = 0; i < lista.size(); ++i)
	{
		lista_de_afisat = lista_de_afisat + lista[i].str() + "\n";
	}
	return lista_de_afisat;
}

void Consola::sortare_ui() {
	cout << "Criterii disponibile: 1.titlu, 2.descriere, 3.tip+durata: ";
	string optiune;
	getline(cin >> ws, optiune);
	try {
		int o = stoi(optiune);
		if(o!=1 && o!=2 && o!=3) throw exception();

		if (o == 1)
			cout << de_afisat(srv.sortare_titlu());
		else if (o == 2)
			cout << de_afisat(srv.sortare_descriere());
		else if (o == 3)
			cout << de_afisat(srv.sortare_tip_durata());
	}
	catch (exception&) {
		cout << "Trebuie sa introduceti 1, 2 sau 3!" << endl;
	}
}

void Consola::run() {
	int running = 1;
	int cmd;
	while (running) {
		print_menu();
		cout << "Comanda dvs. este: ";
		cin >> cmd;
		switch (cmd)
		{
		case 1:
			adauga_ui();
			break;
		case 2:
			sterge_ui();
			break;
		case 3:
			modificare_ui();
			break;
		case 4:
			str_ui();
			break;
		case 5:
			cauta_ui();
			break;
		case 6:
			filtrare_ui();
			break;
		case 7:
			sortare_ui();
			break;
		case 8:
			running = 0;
			break;
		default:
			break;
		}

	}
}
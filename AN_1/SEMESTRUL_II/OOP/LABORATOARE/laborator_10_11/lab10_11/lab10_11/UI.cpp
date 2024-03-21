#pragma once
#include "UI.h"
#include <iostream>
#include <string>
#include <exception>
//#include <filesystem>
using namespace std;

Consola::Consola(const Service& srv) {
	this->srv = srv;
}

void Consola::print_menu() {

	cout << "\nComenzi: 1. adaugare 2.stergere 3.modificare 4.afisare 5.cauta 6. filtrare 7.sortare 8.iesire";
	cout << "\nComenzi manipulare program: 9.adaugare 10.golire 11.adaugare random 12.afisare 13.filtrare_fara_un_tip";
	cout << "\n14. undo 15. export program\n\n";
}
void Consola::str_ui() {
	cout << "Activitatile disponibile sunt: " << endl << this->srv.str_srv();

}

void Consola::undo() {
	try {
		srv.undo();
		cout << "Undo realizat cu succes!\n";
	}
	catch (exception&) {
		cout << "Nu se mai poate fac undo!\n";
	}
}

void Consola::salveaza_program() {
	string fisier;
	cout << "Nume fisier:";
	getline(cin >> ws, fisier);
	if (fisier.size() > 4)
	{
		string extensie = fisier.substr(fisier.size() - 5, 5);
		if (extensie == ".html") {
			srv.salveaza_program(fisier);
			cout << "Export efectuat!\n";
		}
		else { cout << "Nu ati dat un fisier html!!!"; }
	}
	else cout << "Nu ati dat un fisier html!!!";
}

void Consola::adauga_program_ui() {
	string titlu;
	cout << "Titlu: ";
	getline(cin >> ws, titlu);
	try {
		this->srv.adauga_program_srv(titlu);
		cout << "Activitate adaugata cu succes in program!";
	}
	catch (exception&) { cout << "Nu exista activitate cu acest titlu sau ea a fost deja adaugata!\n"; }
}

void Consola::adauga_random_ui() {
	string n;
	int n_int;
	cout << "Nr. de activitati de adaugat: ";
	getline(cin >> ws, n);
	try {
		n_int = stoi(n);
		this->srv.adauga_random_srv(n_int);
		cout << "Activitate adaugata cu succes in program!";
	}
	catch (exception&) { cout << "Trenuie sa introduceti un numar intreg!\n\n"; }
}

void Consola::print_program() {
	cout << "\nActivitatile din program sunt: " << endl << this->srv.str_program();
}
void Consola::goleste_program_ui() {
	this->srv.goleste_program_srv();
}

void Consola::adauga_ui() {
	string titlu, descriere, tip, durata;
	cout << "Titlu: ";
	getline(cin >> ws, titlu);
	cout << "Descriere: ";
	getline(cin >> ws, descriere);
	cout << "Tip: ";
	getline(cin >> ws, tip);
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
	catch (ExceptiiValidare& ve) {
		cout << ve.getErrorMessages();
	}
	catch (exception&) {
		cout << "Durata trebuie sa fie numar!";
	}
}

void Consola::sterge_ui() {
	string titlu;
	cout << "Titlu: "; getline(cin >> ws, titlu);
	try {
		this->srv.sterge_srv(titlu);
		cout << "Activitate stearsa cu succes!" << endl;
	}
	catch (ExceptiiValidare&) {
		cout << "Nu exista activitate cu acest titlu!" << endl;
	}
}

void Consola::sterge_noua_ui() {
	string tip;
	cout << "Tip: "; getline(cin >> ws, tip);
	vector<Activitate> activities = this->srv.stergere_dupa_tip(tip);
	for (auto el : activities) {
		std::cout << el.str() << endl;
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

	catch (ExceptiiValidare&) {
		cout << "Nu exista activitate cu acest titlu!\n" << endl;
	}

}

void Consola::cauta_ui() {
	string titlu;
	cout << "Titlu: ";
	getline(cin >> ws, titlu);

	Activitate a = this->srv.cauta_srv(titlu);
	if (a.get_titlu() != "null") cout << "Activitatea cautata este: \n" + a.str() << endl << endl;
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

			vector<Activitate> lista;
			lista = this->srv.filtrare_descriere(descriere);
			if (lista.size() != 0) cout << de_afisat(lista);
			else cout << "Nu exista astfel de activitati!" << endl;
		}
		else if (o == 2) {
			string tip;
			cout << "Tipul de afisat: ";
			getline(cin >> ws, tip);

			vector<Activitate> lista;
			lista = this->srv.filtrare_tip(tip);
			if (lista.size() != 0) cout << de_afisat(lista);
			else cout << "Nu exista astfel de activitati!" << endl;
		}
		else cout << "Comanda invalida!!" << endl;
	}
	catch (exception&) { cout << "Optiunea trebuie sa fie un numar : 1 sau 2!\n\n"; }
}

string Consola::de_afisat(vector<Activitate> lista) {
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
		if (o != 1 && o != 2 && o != 3) throw exception();

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
		case 9:
			adauga_program_ui();
			break;
		case 10:
			goleste_program_ui();
			break;
		case 11:
			adauga_random_ui();
			break;
		case 12:
			print_program();
			break;
		case 13:
			sterge_noua_ui();
		case 14:
			undo();
		case 15:
			salveaza_program();
		default:
			break;
		}

	}
}
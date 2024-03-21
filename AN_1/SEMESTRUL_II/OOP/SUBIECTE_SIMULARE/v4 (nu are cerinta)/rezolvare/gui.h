//v3 GUI.H
#pragma once
#include "service.h"

#include "QWidget"
#include "QListWidget"
#include "QPushButton"
#include "QRadioButton"
#include "QLineEdit"
#include "QLabel"
#include "QHBoxLayout"
#include "QVBoxLayout"
#include "QCheckBox"
#include "QComboBox"


class GUI :public QWidget {
	friend class Service;
private:
	Service& srv;
	QWidget* wnd = new QWidget, * left = new QWidget, * right = new QWidget; //formez scheletul
	QHBoxLayout* layout = new QHBoxLayout; //stanga si dreapta se adauga orizontal
	QVBoxLayout* stanga = new QVBoxLayout, * dreapta = new QVBoxLayout; //in stanga si dreapta se adauga vertical

	//campurile de care am nevoie in stanga si dreapta
	QPushButton* refresh = new QPushButton,* modifica_buget= new QPushButton;
	QLabel* lbl_pierderi = new QLabel;
	QLineEdit* txt_pierderi = new QLineEdit;

	//QComboBox* combo_box;
	QRadioButton* r2=new QRadioButton, * r3 = new QRadioButton, * r5 = new QRadioButton;

	QListWidget* list = new QListWidget;
	QListWidget* list_depasit = new QListWidget;

	//initializeaza interfata
	void init_gui();

	//conecteaza butoanele din interfata cu functiile aferente
	void connect_signals();

	//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
	void reload_list(vector<Proiect> p);
	void reload_list_depasit(vector<Proiect> p);

	//modifica in functie de selectarile utilizatorului un proiect din prima lista
	void modificare(int n);

	//face refresh la cele 2 liste, luand in considerare ultimele modificari si afiseaza pierderile intr un qlineedit
	void refresh_la_liste();


public:
	explicit GUI(Service& srv) :srv(srv) {

		init_gui();
		connect_signals();
		reload_list(srv.get_all_srv());
		reload_list_depasit(srv.get_all_srv());
	}
	void run() { this->wnd->show(); };



};
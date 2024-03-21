#pragma once
#include "service.h"

#include "QWidget"
#include "QListWidget"
#include "QPushButton"
#include "QLineEdit"
#include "QLabel"
#include "QHBoxLayout"
#include "QVBoxLayout"
#include "QCheckBox"

#include <QSlider>
#include <QSpinBox>


class GUI :public QWidget {
	friend class Service;
private:
	Service& srv;
	QWidget* wnd = new QWidget, * left = new QWidget, * right = new QWidget; //formez scheletul
	QHBoxLayout* layout = new QHBoxLayout; //stanga si dreapta se adauga orizontal
	QVBoxLayout* stanga = new QVBoxLayout, * dreapta = new QVBoxLayout; //in stanga si dreapta se adauga vertical

	//campurile de care am nevoie in stanga si dreapta
	QPushButton* adauga = new QPushButton, * sortare = new QPushButton;
	QLabel* lbl_nume = new QLabel, * lbl_tara = new QLabel;
	QLineEdit* txt_nume = new QLineEdit, * txt_tara = new QLineEdit;
	QCheckBox* check = new QCheckBox;

	QSlider* slider = new QSlider(Qt::Horizontal);;
	QSpinBox* spin_box = new QSpinBox;

	QListWidget* list = new QListWidget;

	//initializeaza interfata
	void init_gui();

	//conecteaza butoanele din interfata cu functiile aferente
	void connect_signals();

	//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
	void reload_list(vector<Jucator> jucator);

	//adauga un jucator pe baza nume, tara, rank si calculeaza un punctaj aleator pe baza rank ului
	void adauga_jucator();

	//sorteaza pe baza rank jucatorii si afiseaza lista
	void sortare_rank();

	//daca un checkbox e selectat coloreaza verde jucatorii cu rank>250
	void coloreaza();

public:
	explicit GUI(Service& srv) :srv(srv) {

		init_gui();
		connect_signals();
		reload_list(srv.get_all_srv());
	}
	void run() { this->wnd->show(); };



};
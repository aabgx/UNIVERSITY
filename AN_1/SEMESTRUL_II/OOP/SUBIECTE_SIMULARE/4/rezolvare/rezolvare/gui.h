#pragma once
#include "service.h"

#include "QWidget"
#include "QListWidget"
#include "QPushButton"
#include "QLineEdit"
#include "QLabel"
#include "QHBoxLayout"
#include "QVBoxLayout"
#include "QRadioButton"
#include <QTableWidget>

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
	QPushButton* recomandate = new QPushButton, * refresh = new QPushButton, * sortare = new QPushButton;
	QLabel* lbl_age = new QLabel;
	QLineEdit* txt_age = new QLineEdit;

	QTableWidget* list = new QTableWidget;
	//QListWidget* list = new QListWidget;

	//initializeaza interfata
	void init_gui();

	//conecteaza butoanele din interfata cu functiile aferente
	void connect_signals();

	//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
	void reload_list(vector<Joc> jucator);


public:
	//porneste aplicatia cu datele din fisier, conecteaza butoanele
	explicit GUI(Service& srv) :srv(srv) {

		init_gui();
		connect_signals();
		reload_list(srv.get_all_srv());
	}
	void run() { this->wnd->show(); };



};
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
	QPushButton* recalc_ranking = new QPushButton,* elimina_jucatori = new QPushButton,* adauga_puncte= new QPushButton;
	QRadioButton* finala = new QRadioButton(QString::fromStdString("FINALA")),* semifinala = new QRadioButton(QString::fromStdString("SEMIFINALA")),* sferturi = new QRadioButton(QString::fromStdString("SFERTURI"));
	QLabel* lbl_nume = new QLabel, * lbl_nrpct = new QLabel;
	QLineEdit* txt_nume = new QLineEdit, * txt_nrpct = new QLineEdit;

	QSlider* slider = new QSlider(Qt::Horizontal);;
	QSpinBox* spin_box = new QSpinBox;

	QListWidget* list = new QListWidget;

	//initializeaza interfata
	void init_gui();

	//conecteaza butoanele din interfata cu functiile aferente
	void connect_signals();

	//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
	void reload_list(vector<Jucator> jucator);

	//Ia din line edit numele jucatorului si nr de puncte (trebuie sa ii faca update).
	//parametrul nr indica daca punctele au fost castigate in finala, semifinala sau sferturi, pe baza acestei informatii calculand nr. de puncte ce trebuie adaugate
	//trimite rezultatul la o functie de update pt a face efectiv schimbarea
	void modifica_puncte(int nr);

	//Face un update a numarului de puncte (cu nr_pct) la jucatorul cu numele nume.
	void update(string nume, int nr_pct);

	//Reface ierarhizarea setand rank-ul jucatorilor pe baza numarului de puncte pe care il au.
	void update_rank();

	//Sterge din lista de jucatori ultimii nr jucatori.
	void sterge();

public:
	//porneste aplicatia cu datele din fisier, conecteaza butoanele
	explicit GUI(Service& srv) :srv(srv) {

		init_gui();
		connect_signals();
		reload_list(srv.get_all_srv());
	}
	void run() { this->wnd->show(); };



};
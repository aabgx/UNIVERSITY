#pragma once
#include "service.h"

#include "QWidget"
#include "QListWidget"
#include "QPushButton"
#include "QLineEdit"
#include "QLabel"
#include "QHBoxLayout"
#include "QVBoxLayout"

#include <QSlider>
#include <QSpinBox>
#include <QVBoxLayout>


class GUI :public QWidget {
	friend class Service;
private:
	Service& srv;
	QWidget* wnd = new QWidget, * left = new QWidget, * right = new QWidget; //formez scheletul
	QHBoxLayout* layout = new QHBoxLayout; //stanga si dreapta se adauga orizontal
	QVBoxLayout* stanga = new QVBoxLayout, * dreapta = new QVBoxLayout; //in stanga si dreapta se adauga vertical

	//campurile de care am nevoie in stanga si dreapta
	QPushButton* adauga = new QPushButton, * match = new QPushButton;
	QLabel* lbl_nume = new QLabel, * lbl_tara = new QLabel;
	QLineEdit* txt_nume = new QLineEdit, * txt_tara = new QLineEdit;

	QSlider* slider = new QSlider(Qt::Horizontal);;
	QSpinBox* spin_box = new QSpinBox;

	QListWidget* list = new QListWidget;

	//pt tari
	QVBoxLayout* layout_radio=new QVBoxLayout;
	QWidget* widget_radio = new QWidget;

	//initializeaza interfata
	void init_gui();

	//conecteaza butoanele din interfata cu functiile aferente
	void connect_signals();

	//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
	void reload_list(vector<Jucator> jucator);

	//face update la lista afisata, vectorul dat ca parametru devine lista ce se afiseaza
	void reload_list_update(vector<Jucator> jucator);

	//adauga pe baza nume, tara, puncte si calculeaza rank ul (il reface si pe al celorlalti)
	void gui_add();

	//face un meci cu 2 jucatori random si le atribuie un scor random dintr-o lista
	void match_players();

	//face butoane dinamice cu tarile existente, daca sunt apasate arata in lista jucatori apartinatori ai acelor tari
	void actualizare_radio_box();

public:
	explicit GUI(Service& srv) :srv(srv) {

		init_gui();
		connect_signals();
		reload_list(srv.get_all_srv());
	}
	void run() { this->wnd->show(); };



};
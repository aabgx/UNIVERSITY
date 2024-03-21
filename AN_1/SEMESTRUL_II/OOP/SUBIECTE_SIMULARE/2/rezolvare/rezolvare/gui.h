#pragma once
#include "service.h"

#include "QWidget"
#include "QListWidget"
#include "QPushButton"
#include "QLineEdit"
#include "QLabel"
#include "QHBoxLayout"
#include "QVBoxLayout"

class GUI :public QWidget {
private:
	Service& srv;
	QWidget* wnd = new QWidget, * left = new QWidget, * right = new QWidget; //formez scheletul
	QHBoxLayout* layout = new QHBoxLayout; //stanga si dreapta se adauga orizontal
	QVBoxLayout* vL = new QVBoxLayout, * vLText = new QVBoxLayout; //in stanga si dreapta se adauga vertical

	//campurile de care am nevoie in stanga si dreapta
	QPushButton* sortare_model = new QPushButton, * sortare_pret = new QPushButton, * nesortat = new QPushButton;
	QLabel* lbl_model = new QLabel, * lbl_an = new QLabel;
	QLineEdit* txt_model = new QLineEdit, * txt_an = new QLineEdit;
	QListWidget* list = new QListWidget;

	void init_gui();
	void connect_signals();
	void reload_list(vector<Device> doctors);

public:
	explicit GUI(Service& srv) :srv(srv) {

		init_gui();
		connect_signals();
		reload_list(srv.get_all_srv());
	}
	void run() { this->wnd->show(); };



};
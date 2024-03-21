#pragma once
#include <utility>

#include "service.h"
#include "QWidget"
#include "QTableWidget"
#include "QSlider"
#include "Qt"
#include "QPushButton"
#include "QLineEdit"
#include "QLabel"
#include "QLayout"
#include "observer.h"
#include "QListWidget"

//////////////////////////////////////////////////////////////////////////////WINDOW///////////////////////////////////////////////////////////////////////////////
class Window : public QWidget, public Observer {
private:
	Service& srv;
	string str;

	QListWidget* list = new QListWidget;
	QHBoxLayout* main_layout = new QHBoxLayout;
	QPushButton* btn1 = new QPushButton, * btn2 = new QPushButton, * btn3 = new QPushButton;

public:
	/*constructor*/
	Window(Service& srv, string str) :srv{ srv }, str{ str }{
		this->srv.add_observer(this);
		setWindowTitle(QString::fromStdString(str));
		init_wnd();
		connect();
		load_list(srv.filter_by_stare(str));
	};

	/*initializez interfata ferestrei (lista si butoane)*/
	void init_wnd() {
		this->setLayout(main_layout);
		btn1->setText("closed");
		btn2->setText("open");
		btn3->setText("inprogress");
		main_layout->addWidget(list);
		main_layout->addWidget(btn1);
		main_layout->addWidget(btn2);
		main_layout->addWidget(btn3);
	}

	/*conectez butoanele ferestrei*/
	void connect() {
		QObject::connect(btn1, &QPushButton::clicked, [this] {
			auto lst = list->selectionModel()->selectedIndexes();
			auto it = lst.begin();
			if (it)
			{
				int i = (*it).row();
				auto taskuri = srv.filter_by_stare(str);
				srv.schimba_stare(taskuri[i].get_id(), "closed");
				load_list(srv.filter_by_stare(str));
				srv.notify();
			}
			});
		QObject::connect(btn2, &QPushButton::clicked, [this] {
			auto lst = list->selectionModel()->selectedIndexes();
			auto it = lst.begin();
			if (it)
			{
				int i = (*it).row();
				auto taskuri = srv.filter_by_stare(str);
				srv.schimba_stare(taskuri[i].get_id(), "open");
				load_list(srv.filter_by_stare(str));
				srv.notify();
			}
			});
		QObject::connect(btn3, &QPushButton::clicked, [this] {
			auto lst = list->selectionModel()->selectedIndexes();
			auto it = lst.begin();
			if (it)
			{
				int i = (*it).row();
				auto taskuri = srv.filter_by_stare(str);
				srv.schimba_stare(taskuri[i].get_id(), "inprogress");
				load_list(srv.filter_by_stare(str));
				srv.notify();
			}
			});
	}

	/*functie pentru reincarcarea listei
	v->vector<Task>: noul vector pe care vrem sa-l incarcam in lista*/
	void load_list(vector<Task> v) {
		this->list->clear();

		for (auto& el : v) {
			list->addItem(QString::fromStdString(std::to_string(el.get_id()) + " " + el.get_descriere() + " " + el.get_stare()));
		}
	}

	/*cand se da notify clasa face reload la lista ei, cu noile date */
	void update() override {
		load_list(srv.filter_by_stare(str));
	}

};

//////////////////////////////////////////////////////////////////////////GUI///////////////////////////////////////////////////////////////////////////////////////////

class GUI :public QWidget, public Observer {
private:
	Service& srv;
	QTableWidget* table = new QTableWidget;

	QWidget* main_wnd = new QWidget, * right_comp = new QWidget, * comps = new QWidget, * left_comp = new QWidget;
	QPushButton* add_btn = new QPushButton, * search_btn = new QPushButton;
	QVBoxLayout* main_layout = new QVBoxLayout;
	QHBoxLayout* layout_comps = new QHBoxLayout;
	QVBoxLayout* layout_left = new QVBoxLayout;
	QVBoxLayout* layout_right = new QVBoxLayout;

	QLabel* lbl_id = new QLabel, * lbl_descriere = new QLabel, * lbl_stare = new QLabel, * lbl_programatori = new QLabel;
	QLineEdit* txt_id = new QLineEdit, * txt_descriere = new QLineEdit, * txt_stare = new QLineEdit, * txt_programatori = new QLineEdit;
	QLineEdit* txt_filtrare = new QLineEdit;
	QLabel* lbl_filtrare = new QLabel;
	
	/*initializez interfata ferestrei (tabel si butoane)*/
	void init_GUI();

	/*conectez butoanele*/
	void connect_slots();

	/*functie pentru reincarcarea tabelului
	v->vector<Task>: noul vector pe care vrem sa-l incarcam in tabel*/
	void reload_list(vector<Task>lst);

	/*deschide ferestrele aditionale*/
	void add_window();


public:

	explicit GUI(Service& srv) :srv{ srv } {

		init_GUI();
		connect_slots();
		reload_list(srv.get_all_srv());
		add_window();

	};

	void run() {
		this->show();
	}

	/*cand se da notify clasa face reload la lista ei, cu noile date */
	virtual void update() override {
		reload_list(srv.get_all_srv());
	}

};
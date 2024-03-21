#include <vector>
#include <string>
#include <QtWidgets/QApplication>
#include <QLabel>
#include <QPushButton>
#include <QHBoxLayout>
#include <QFormLayout>
#include <QLineEdit>
#include <QTableWidget>
#include <QListWidget>
#include <QMessageBox>
#include <QHeaderView>
#include <QGroupBox>
#include <QRadioButton>
#include "service.h"

using std::vector;
using std::string;


class ConsoleGUIProgram :public QWidget,public Observable {
private:
	Service& srv;

	//add
	QLabel* lblTitluP = new QLabel("Titlu:");
	QLineEdit* editTitluP;
	QPushButton* btnAddProgram;

	//add random
	QLabel* lblNr = new QLabel("Numar:");
	QLineEdit* editNr;
	QPushButton* btnAddRandom;

	//goleste program
	QPushButton* btnGoleste;

	//export
	QLabel* lblFile = new QLabel("Fisier:");
	QLineEdit* editFile;
	QPushButton* btnExport;


	//QTableWidget* tableActivities;
	QListWidget* tableActivities;


	void initializeGUIProgComponents();
	void connectSignalsSlotsProg();
	void reloadActivityListFromProg(vector<Activitate> activities);

public:
	ConsoleGUIProgram(Service& srv) : srv{ srv } {
		initializeGUIProgComponents();
		connectSignalsSlotsProg();
		reloadActivityListFromProg(srv.get_all_program_srv());
	}

	void update();
	void gui_AddProgram();
	void gui_AddProgramRandom();
	void gui_Export();
};

class ConsoleGUI : public QWidget {
private:
	QVBoxLayout* btn_layout;
	Service& srv;
	ConsoleGUIProgram gui_2{srv};



	//add
	QLabel* lblTitluP = new QLabel("Titlu:");
	QLineEdit* editTitluP;
	QPushButton* btnAddProgram;

	//add random
	QLabel* lblNr = new QLabel("Numar:");
	QLineEdit* editNr;
	QPushButton* btnAddRandom;

	//goleste program
	QPushButton* btnGoleste;

	//export
	QLabel* lblFile = new QLabel("Fisier:");
	QLineEdit* editFile;
	QPushButton* btnExport;

	//program crud
	QPushButton* btnCRUD;

	//program draw
	QPushButton* btnDRAW;


	///////////////////////////////////////////////////////////
	//add
	QGroupBox* groupBoxAdd = new QGroupBox(tr("Adaugare"));

	QLabel* lblTitlu = new QLabel("Titlu:");
	QLabel* lblDescriere = new QLabel("Descriere:");
	QLabel* lblTip = new QLabel("Tip:");
	QLabel* lblDurata = new QLabel("Durata:");

	QLineEdit* editTitlu;
	QLineEdit* editDescriere;
	QLineEdit* editTip;
	QLineEdit* editDurata;

	//dell
	QGroupBox* groupBoxDell = new QGroupBox(tr("Stergere"));

	QLabel* lblTitluD = new QLabel("Titlu:");
	QLineEdit* editTitluD;

	//modify
	QGroupBox* groupBoxMod = new QGroupBox(tr("Modificare"));

	QLabel* lblTitluM = new QLabel("Titlu:");
	QLabel* lblDescriereM = new QLabel("Descriere noua:");
	QLabel* lblTipM = new QLabel("Tip nou:");
	QLabel* lblDurataM = new QLabel("Durat noua:");
	QLineEdit* editTitluM;
	QLineEdit* editDescriereM;
	QLineEdit* editTipM;
	QLineEdit* editDurataM;

	//find
	QGroupBox* groupBoxFind = new QGroupBox(tr("Cautare"));

	QLabel* lblTitluF = new QLabel("Titlu:");
	QLineEdit* editTitluF;

	QPushButton* btnAddActivity;
	QPushButton* btnDellActivity;
	QPushButton* btnModifyActivity;
	QPushButton* btnFindActivity;

	//filter
	QGroupBox* groupBoxFilter = new QGroupBox(tr("Filtrare"));

	QLabel* lblFilterDescriere = new QLabel{ "Descriere dupa care se filtreaza:" };
	QLineEdit* editFilterDescriere;
	QLabel* lblFilterTip = new QLabel{ "Tipul dupa care se filtreaza:" };
	QLineEdit* editFilterTip;
	QPushButton* btnFilterDescriere;
	QPushButton* btnFilterTip;

	//sort
	QGroupBox* groupBoxSort = new QGroupBox(tr("Tip sortare"));

	QRadioButton* radioSortTitlu = new QRadioButton(QString::fromStdString("Titlu"));
	QRadioButton* radioSortDescriere = new QRadioButton(QString::fromStdString("Descriere"));
	QRadioButton* radioSortTipDurata = new QRadioButton(QString::fromStdString("Tip+Durata"));
	QPushButton* btnSortActivities;

	QPushButton* btnReloadData;

	//QTableWidget* tableActivities;
	QListWidget* tableActivities;

	//tip
	QPushButton* btnTip;

	//undo
	QPushButton* btnUndo;

	////initializez butoane pt program
	//QPushButton* btn_add_program, * btn_add_random_program, * btn_goleste_program;

	//void initializeGUIComponents2();
	void initializeGUIComponents();

	void connectSignalsSlots();

	void reloadActivityList(vector<Activitate> activities);

	//PROGRAM
	QPushButton* btnProgram;
	void gui_AddProgram();
	void gui_AddProgramRandom();
	void gui_Export();
	void reloadActivityListFromProg(vector<Activitate> activities);



public:
	ConsoleGUI(Service& srv) : srv{ srv } {
		initializeGUIComponents();
		connectSignalsSlots();
		reloadActivityList(srv.get_all_srv());
	}

	void gui_AddActivity();
	void gui_DellActivity();
	void gui_ModifyActivity();
	void gui_addTip();
};
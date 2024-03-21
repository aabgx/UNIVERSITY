#include "interfata.h"

//NOU**************************************************

void ConsoleGUIProgram::initializeGUIProgComponents() {
	QHBoxLayout* lyMain = new QHBoxLayout;
	this->setLayout(lyMain);


	//COMPONENTA LEFT
	QWidget* left = new QWidget;
	QVBoxLayout* lyLeft = new QVBoxLayout;
	left->setLayout(lyLeft);

	//adaugare
	QWidget* form = new QWidget;
	QFormLayout* lyForm = new QFormLayout;
	form->setLayout(lyForm);
	this->editTitluP = new QLineEdit;

	lyForm->addRow(lblTitluP, editTitluP);
	btnAddProgram = new QPushButton("Adauga activitate in program");
	lyForm->addWidget(btnAddProgram);

	lyLeft->addWidget(form);

	//adaugare random
	QWidget* formAddR = new QWidget;
	QFormLayout* lyFormAddR = new QFormLayout;
	formAddR->setLayout(lyFormAddR);
	this->editNr = new QLineEdit;

	lyFormAddR->addRow(lblNr, editNr);
	btnAddRandom = new QPushButton("Adauga activitati random in program");
	lyFormAddR->addWidget(btnAddRandom);

	lyLeft->addWidget(formAddR);

	//export
	QWidget* formEx = new QWidget;
	QFormLayout* lyFormEx = new QFormLayout;
	formEx->setLayout(lyFormEx);
	this->editFile = new QLineEdit;

	lyFormEx->addRow(lblFile, editFile);
	btnExport = new QPushButton("Export");
	lyFormEx->addWidget(btnExport);

	lyLeft->addWidget(formEx);

	//goleste program
	btnGoleste = new QPushButton("Goleste lista activitati!");
	lyLeft->addWidget(btnGoleste);

	//COMPONENTA RIGHT
	QWidget* right = new QWidget;
	QVBoxLayout* lyRight = new QVBoxLayout;
	right->setLayout(lyRight);

	//int noLines = 10;
	//int noColums = 4;
	//this->tableActivities = new QTableWidget(noLines, noColums);

	////setez header-ul
	//QStringList tblHeaderList;
	//tblHeaderList << "Titlu" << "Descriere" << "Tip" << "Durata";
	//this->tableActivities->setHorizontalHeaderLabels(tblHeaderList);

	////optiune pentru a redimensiona celulele din tabel in functie de continut
	//this->tableActivities->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);
	this->tableActivities = new QListWidget;
	lyRight->addWidget(tableActivities);

	lyMain->addWidget(left);
	lyMain->addWidget(right);

}

void ConsoleGUIProgram::reloadActivityListFromProg(vector<Activitate> activities) {
	/*this->tableActivities->clearContents();
	this->tableActivities->setRowCount(activities.size());

	int lineNumber = 0;
	for (auto& activity : activities) {
		this->tableActivities->setItem(lineNumber, 0, new QTableWidgetItem(QString::fromStdString(activity.get_titlu())));
		this->tableActivities->setItem(lineNumber, 1, new QTableWidgetItem(QString::fromStdString(activity.get_descriere())));
		this->tableActivities->setItem(lineNumber, 2, new QTableWidgetItem(QString::fromStdString(activity.get_tip())));
		this->tableActivities->setItem(lineNumber, 3, new QTableWidgetItem(QString::number(activity.get_durata())));
		lineNumber++;
	}*/
	this->tableActivities->clear();
	for (auto& activity : activities)
	{
		this->tableActivities->addItem(activity.str().c_str());
	}

}

void ConsoleGUIProgram::connectSignalsSlotsProg() {
	QObject::connect(btnAddProgram, &QPushButton::clicked, this, &ConsoleGUIProgram::gui_AddProgram);
	QObject::connect(btnAddRandom, &QPushButton::clicked, this, &ConsoleGUIProgram::gui_AddProgramRandom);

	QObject::connect(btnGoleste, &QPushButton::clicked, [&]() {
		srv.goleste_program_srv();
		QMessageBox::information(this, "Info", QString::fromStdString("Lista de activitati din program a fost golita!"));
		reloadActivityListFromProg(srv.get_all_program_srv());
		});

	QObject::connect(btnExport, &QPushButton::clicked, this, &ConsoleGUIProgram::gui_Export);
}


void ConsoleGUIProgram::gui_AddProgram() {
	try {
		string titlu = editTitluP->text().toStdString();

		editTitluP->clear();

		srv.adauga_program_srv(titlu);
		reloadActivityListFromProg(this->srv.get_all_program_srv());

		QMessageBox::information(this, "Info", QString::fromStdString("Activitate adaugata cu succes in program!"));
	}
	catch (exception&) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Nu exista activitate cu acest titlu sau ea a fost deja adaugata!"));
	}
}

void ConsoleGUIProgram::gui_AddProgramRandom() {
	try {
		string n = editNr->text().toStdString();
		int nr;

		editNr->clear();

		nr = stoi(n);
		srv.adauga_random_srv(nr);
		reloadActivityListFromProg(this->srv.get_all_program_srv());

		QMessageBox::information(this, "Info", QString::fromStdString("Activitati adaugate random cu succes in program!"));
	}
	catch (exception&) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Trebuie sa introduceti un numar!"));
	}
}

void ConsoleGUIProgram::gui_Export() {
	try {
		string fileName = editFile->text().toStdString();

		editFile->clear();

		srv.salveaza_program(fileName);
		reloadActivityListFromProg(this->srv.get_all_program_srv());

		QMessageBox::information(this, "Info", QString::fromStdString("Programul a fost adaugat in fisier!"));
	}
	catch (exception&) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Fisierul nu se poate deschide"));
	}
}

//VECHI*********************************************
void ConsoleGUI::initializeGUIComponents() {
	QHBoxLayout* lyMain = new QHBoxLayout;
	this->setLayout(lyMain);


	//COMPONENTA LEFT
	QWidget* left = new QWidget;
	QVBoxLayout* lyLeft = new QVBoxLayout;
	left->setLayout(lyLeft);

	//adaugare
	QWidget* form = new QWidget;
	QFormLayout* lyForm = new QFormLayout;
	form->setLayout(lyForm);
	this->editTitlu = new QLineEdit;
	this->editDescriere = new QLineEdit;
	this->editTip = new QLineEdit;
	this->editDurata = new QLineEdit;

	lyForm->addRow(lblTitlu, editTitlu);
	lyForm->addRow(lblDescriere, editDescriere);
	lyForm->addRow(lblTip, editTip);
	lyForm->addRow(lblDurata, editDurata);
	btnAddActivity = new QPushButton("Adauga activitate");
	lyForm->addWidget(btnAddActivity);

	lyLeft->addWidget(form);

	//stergere
	QWidget* formDell = new QWidget;
	QFormLayout* lyFormDell = new QFormLayout;
	formDell->setLayout(lyFormDell);

	this->editTitluD = new QLineEdit;
	lyFormDell->addRow(lblTitluD, editTitluD);

	btnDellActivity = new QPushButton("Sterge activitate");
	lyFormDell->addWidget(btnDellActivity);
	lyLeft->addWidget(formDell);

	//modificare
	QWidget* formMod = new QWidget;
	QFormLayout* lyFormMod = new QFormLayout;
	formMod->setLayout(lyFormMod);

	this->editTitluM = new QLineEdit;
	this->editDescriereM = new QLineEdit;
	this->editTipM = new QLineEdit;
	this->editDurataM = new QLineEdit;

	lyFormMod->addRow(lblTitluM, editTitluM);
	lyFormMod->addRow(lblDescriereM, editDescriereM);
	lyFormMod->addRow(lblTipM, editTipM);
	lyFormMod->addRow(lblDurataM, editDurataM);

	btnModifyActivity = new QPushButton("Modifica activitate");
	lyFormMod->addWidget(btnModifyActivity);
	lyLeft->addWidget(formMod);

	//cautare
	QWidget* formFind = new QWidget;
	QFormLayout* lyFormFind = new QFormLayout;
	formFind->setLayout(lyFormFind);

	this->editTitluF = new QLineEdit;
	lyFormFind->addRow(lblTitluF, editTitluF);

	btnFindActivity = new QPushButton("Cauta activitate");
	lyFormFind->addWidget(btnFindActivity);
	lyLeft->addWidget(formFind);

	//filtrare
	QWidget* formFilter = new QWidget;
	QFormLayout* lyFormFilter = new QFormLayout;
	formFilter->setLayout(lyFormFilter);
	editFilterDescriere = new QLineEdit;
	lyFormFilter->addRow(lblFilterDescriere, editFilterDescriere);
	btnFilterDescriere = new QPushButton("Filtreaza dupa descriere");
	lyFormFilter->addWidget(btnFilterDescriere);

	editFilterTip = new QLineEdit;
	lyFormFilter->addRow(lblFilterTip, editFilterTip);
	btnFilterTip = new QPushButton("Filtreaza dupa tip");
	lyFormFilter->addWidget(btnFilterTip);

	lyLeft->addWidget(formFilter);


	//sortare
	QVBoxLayout* lyRadioBox = new QVBoxLayout;
	this->groupBoxSort->setLayout(lyRadioBox);
	lyRadioBox->addWidget(radioSortTitlu);
	lyRadioBox->addWidget(radioSortDescriere);
	lyRadioBox->addWidget(radioSortTipDurata);

	btnSortActivities = new QPushButton("Sorteaza activitati");
	lyRadioBox->addWidget(btnSortActivities);
	lyRadioBox->addWidget(groupBoxSort);

	lyLeft->addWidget(groupBoxSort); //adaugam in partea stanga.

	//reload data
	btnReloadData = new QPushButton("Reload data");
	lyLeft->addWidget(btnReloadData);

	//undo
	btnUndo = new QPushButton("Undo");
	lyLeft->addWidget(btnUndo);


	//COMPONENTA RIGHT
	QWidget* right = new QWidget;
	QVBoxLayout* lyRight = new QVBoxLayout;
	right->setLayout(lyRight);

	//int noLines = 10;
	//int noColums = 4;
	//this->tableActivities = new QTableWidget(noLines, noColums);

	////setez header-ul
	//QStringList tblHeaderList;
	//tblHeaderList << "Titlu" << "Descriere" << "Tip" << "Durata";
	//this->tableActivities->setHorizontalHeaderLabels(tblHeaderList);

	////optiune pentru a redimensiona celulele din tabel in functie de continut
	//this->tableActivities->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);
	this->tableActivities = new QListWidget;

	lyRight->addWidget(tableActivities);

	lyMain->addWidget(left);
	lyMain->addWidget(right);

	//tip
	QWidget* formTip = new QWidget;
	QFormLayout* lyFormTip = new QFormLayout;
	formTip->setLayout(lyFormTip);
	btnTip = new QPushButton("Elemente de tip");
	lyFormTip->addWidget(btnTip);

	lyRight->addWidget(btnTip);

	lyMain->addWidget(left);
	lyMain->addWidget(right);
	QWidget* btn_widget = new QWidget;
	btn_layout = new QVBoxLayout;
	btn_widget->setLayout(btn_layout);
	lyMain->addWidget(btn_widget);
	gui_addTip();

	
}

void ConsoleGUI::reloadActivityList(vector<Activitate> activities) {
	/*this->tableActivities->clearContents();
	this->tableActivities->setRowCount(activities.size());

	int lineNumber = 0;
	for (auto& activity : activities) {
		this->tableActivities->setItem(lineNumber, 0, new QTableWidgetItem(QString::fromStdString(activity.get_titlu())));
		this->tableActivities->setItem(lineNumber, 1, new QTableWidgetItem(QString::fromStdString(activity.get_descriere())));
		this->tableActivities->setItem(lineNumber, 2, new QTableWidgetItem(QString::fromStdString(activity.get_tip())));
		this->tableActivities->setItem(lineNumber, 3, new QTableWidgetItem(QString::number(activity.get_durata())));
		lineNumber++;
	}*/
	this->tableActivities->clear();
	for (auto& activity : activities)
	{
		this->tableActivities->addItem(activity.str().c_str());
	}
}

void ConsoleGUI::connectSignalsSlots() {
	QObject::connect(btnAddActivity, &QPushButton::clicked, this, &ConsoleGUI::gui_AddActivity);

	QObject::connect(btnDellActivity, &QPushButton::clicked, this, &ConsoleGUI::gui_DellActivity);

	QObject::connect(btnModifyActivity, &QPushButton::clicked, this, &ConsoleGUI::gui_ModifyActivity);

	QObject::connect(btnFindActivity, &QPushButton::clicked, [&]() {
		string find = this->editTitluF->text().toStdString();
		Activitate activity = srv.cauta_srv(find);
		vector<Activitate> foundActivity;
		foundActivity.push_back(activity);
		this->reloadActivityList(foundActivity);
		editTitluF->clear();
		});

	QObject::connect(btnFilterDescriere, &QPushButton::clicked, [&]() {
		string filterC = this->editFilterDescriere->text().toStdString();
		this->reloadActivityList(srv.filtrare_descriere(filterC));
		editFilterDescriere->clear();
		});

	QObject::connect(btnFilterTip, &QPushButton::clicked, [&]() {
		string filterC = this->editFilterTip->text().toStdString();
		this->reloadActivityList(srv.filtrare_tip(filterC));
		editFilterTip->clear();
		});

	QObject::connect(btnSortActivities, &QPushButton::clicked, [&]() {
		if (radioSortTitlu->isChecked())
			this->reloadActivityList(srv.sortare_titlu());
		else if (radioSortDescriere->isChecked())
			this->reloadActivityList(srv.sortare_descriere());
		else if (radioSortTipDurata->isChecked())
			this->reloadActivityList(srv.sortare_tip_durata());
		});

	QObject::connect(btnReloadData, &QPushButton::clicked, [&]() {
		this->reloadActivityList(srv.get_all_srv());
		});

	QObject::connect(btnUndo, &QPushButton::clicked, [&]() {
		try {
			srv.undo();
			this->reloadActivityList(srv.get_all_srv());
			gui_addTip();
		}
		catch (exception&) { QMessageBox::warning(this, "Warning", QString::fromStdString("Nu se mai poate face undo!")); }
		});

	QObject::connect(btnTip, &QPushButton::clicked, this, &ConsoleGUI::gui_addTip);
}

void ConsoleGUI::gui_AddActivity() {
	try {
		string titlu = editTitlu->text().toStdString();
		string descriere = editDescriere->text().toStdString();
		string tip = editTip->text().toStdString();
		double durata = editDurata->text().toDouble();

		editTitlu->clear();
		editDescriere->clear();
		editTip->clear();
		editDurata->clear();

		srv.adauga_srv(titlu, descriere, tip, durata);
		reloadActivityList(this->srv.get_all_srv());

		QMessageBox::information(this, "Info", QString::fromStdString("Activitate adaugata cu succes!"));
		gui_addTip();
	}
	catch (ExceptiiValidare& ve) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(ve.getErrorMessages()));
	}
	catch (exception&) {
		QMessageBox::warning(this, "Warning" , QString::fromStdString("Durata trebuie sa fie un numar!"));
	}
}

void ConsoleGUI::gui_DellActivity() {
	try {
		string titlu = editTitluD->text().toStdString();

		editTitluD->clear();

		srv.sterge_srv(titlu);
		reloadActivityList(srv.get_all_srv());

		QMessageBox::information(this, "Info", QString::fromStdString("Activitatea stearsa cu succes!"));
		gui_addTip();
	}
	catch (ExceptiiValidare&) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Nu exista activitate cu acest titlu!"));
	}

}

void ConsoleGUI::gui_ModifyActivity() {
	try {
		string titlu = editTitluM->text().toStdString();
		string descriere = editDescriereM->text().toStdString();
		string tip = editTipM->text().toStdString();
		double durata = editDurataM->text().toDouble();

		editTitluM->clear();
		editDescriereM->clear();
		editTipM->clear();
		editDurataM->clear();

		srv.modifica_srv(titlu, descriere, tip, durata);
		reloadActivityList(srv.get_all_srv());

		QMessageBox::information(this, "Info", QString::fromStdString("Activitatea modificata cu succes!"));
		gui_addTip();
	}
	catch (ExceptiiValidare& re) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Nu exista activitate cu acest titlu!"));
		
	}
}

void ConsoleGUI::gui_addTip() {
	vector<Activitate> activities = srv.get_all_srv();
	vector<std::pair<string, int>> tipuri;
	for (auto a : activities) {
		bool ok = false;
		int i = 0;
		for (auto t : tipuri) {
			if (t.first == a.get_tip()) {
				ok = true;
				break;
			}
		}
		if (ok == true)
			tipuri[i].second++;
		else tipuri.emplace_back(a.get_tip(), 1);
	}
	QLayoutItem* item;
	while ((item = btn_layout->takeAt(0)) != NULL)
	{
		delete item->widget();
		delete item;
	}


	for (auto t : tipuri) {
		string tip = t.first;
		int nr = t.second;
		auto item = new QRadioButton(QString::fromStdString(tip));

		QObject::connect(item, &QRadioButton::clicked, [nr] {
			string n = std::to_string(nr);
			auto* lbl = new QLabel(QString::fromStdString(n));
			lbl->show();
			});
		btn_layout->addWidget(item);
	}
}

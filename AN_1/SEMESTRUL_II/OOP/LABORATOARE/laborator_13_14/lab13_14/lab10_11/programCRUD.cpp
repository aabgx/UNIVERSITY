#pragma once
#include "programCRUD.h"
#include "MyTableModel.h"

void programCRUDGUI::run() {
    this->program.addObserver(this);

    this->initComponents();
    this->table->setModel(model);
    this->connectSignals();
    this->update();
    wnd->show();
}

void programCRUDGUI::initComponents() {
    wnd->setLayout(layout);

    table->setSelectionMode(QAbstractItemView::SingleSelection);
    layout->addWidget(table);

    slider->setMinimum(0);
    slider->setMaximum(40);
    slider->setOrientation(Qt::Horizontal);
    slider->setTickPosition(QSlider::TicksAbove);
    layout->addWidget(slider);

    layout->addWidget(btnadd);
    layout->addWidget(btnempty);
}

void programCRUDGUI::connectSignals() {
    QObject::connect(btnadd, &QPushButton::clicked, [this]() {

        int number = slider->value();
        srv.adauga_random_srv(number);
        program.notify();
        });
    QObject::connect(btnempty, &QPushButton::clicked, [this]() {
        program.goleste_program();
        program.notify();
        });
}

void programCRUDGUI::populateTable(QTableView* table,vector<Activitate>& all) {
    /*this->table->clearContents();
    this->table->setRowCount(static_cast<int>(all.size()));

    int lineNumber = 0;
    for (Activitate& activity : all) {
        this->table->setItem(lineNumber, 0, new QTableWidgetItem(QString::fromStdString(activity.get_titlu())));
        this->table->setItem(lineNumber, 1, new QTableWidgetItem(QString::fromStdString(activity.get_descriere())));
        this->table->setItem(lineNumber, 2, new QTableWidgetItem(QString::fromStdString(activity.get_tip())));
        this->table->setItem(lineNumber, 3, new QTableWidgetItem(QString::number(activity.get_durata())));
        lineNumber++;
    }*/
    this->model->setAct(all);
}

void programCRUDGUI::update() {
    //this->model = new MyTableModel{ srv.get_all_program_srv() };
    this->model->setAct(srv.get_all_program_srv());
    //this->populateTable(table, this->program.get_all());
}
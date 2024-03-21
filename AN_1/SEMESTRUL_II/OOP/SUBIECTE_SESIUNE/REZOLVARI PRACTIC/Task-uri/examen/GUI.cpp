#pragma once
#include "GUI.h"
#include "qdebug.h"
#include "service.h"
#include "QMessageBox"
#include "observer.h"
#include <qwidget.h>
#include <qlabel.h>
#include <qlayout.h>
#include <qpushbutton.h>
#include <QHeaderView>

void GUI::init_GUI() {
    this->srv.add_observer(this); //adaug in observer fereasta principala
    table = new QTableWidget;

    setLayout(main_layout);

    left_comp->setLayout(layout_left);
    right_comp->setLayout(layout_right);


    main_layout->addWidget(comps);
    comps->setLayout(layout_comps);
    layout_comps->addWidget(left_comp);
    layout_comps->addWidget(right_comp);


    add_btn->setText("&ADAUGA");
    search_btn->setText("&CAUTA");
    lbl_id->setText("ID");
    lbl_descriere->setText("DESCRIERE");
    lbl_stare->setText("STARE");
    lbl_programatori->setText("PROGRAMATORI");

    lbl_filtrare = new QLabel;
    txt_filtrare = new QLineEdit;
    lbl_filtrare->setText("FILTRARE");

    layout_left->addWidget(table);

    layout_right->addWidget(lbl_filtrare);
    layout_right->addWidget(txt_filtrare);
    //layout_right->addWidget(search_btn);
    layout_right->addWidget(lbl_id);
    layout_right->addWidget(txt_id);
    layout_right->addWidget(lbl_descriere);
    layout_right->addWidget(txt_descriere);
    layout_right->addWidget(lbl_stare);
    layout_right->addWidget(txt_stare);
    layout_right->addWidget(lbl_programatori);
    layout_right->addWidget(txt_programatori);
    layout_right->addWidget(add_btn);

}

void GUI::connect_slots() {
    QObject::connect(table->selectionModel(), &QItemSelectionModel::selectionChanged, [this] {
        if (table->selectionModel()->selectedIndexes().isEmpty()) {
            txt_id->setText("");
            return;
        }
        else {
            auto row = table->selectionModel()->selectedIndexes().at(0).row();
            auto id = table->model()->data(table->model()->index(row, 1), Qt::DisplayRole).toString();
            txt_id->setText(id);
        }
        });

    QObject::connect(add_btn, &QPushButton::clicked, [this] {
        int id = txt_id->text().toInt();
        string descriere = txt_descriere->text().toStdString();
        string stare = txt_stare->text().toStdString();
        string programatori = txt_programatori->text().toStdString();
        try {
            srv.add(id, descriere, stare, programatori);
        }
        catch (ServiceException&) {
            QMessageBox::information(this, "NU SE POATE ADAUGA", "verificati artibutele:\n\nID-UNIC\n\nDESCRIERE-NEVIDA\n\nSTARE: CLOSED/INPROGRESS/OPEN\n\nPROGRAMATORI: 1 MINIM - 4 MAXIM");
        }
        reload_list(srv.get_all_srv());
        txt_id->setText("");
        txt_descriere->setText("");
        txt_stare->setText("");
        txt_programatori->setText("");
        srv.notify();
        });

    QObject::connect(txt_filtrare, &QLineEdit::textChanged, [this] {
        string programator = txt_filtrare->text().toStdString();
        if(programator=="") reload_list(srv.get_all_srv());
        else reload_list(srv.filtrare(programator));
        });

}

void GUI::reload_list(vector<Task>lst) {

    lst = srv.sort_lista(lst);
    table->clear();
    QStringList tr;
    tr << "ID" << "DESCRIERE" << "STARE" << "NR.PROGRAMATORI";
    this->table->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

    table->setColumnCount(4);
    table->setHorizontalHeaderLabels(tr);
    table->setRowCount(lst.size());

    for (int i = 0; i < lst.size(); i++) {
        table->setCellWidget(i, 0, new QLabel(to_string(lst[i].get_id()).c_str()));
        table->setCellWidget(i, 1, new QLabel(lst[i].get_descriere().c_str()));
        table->setCellWidget(i, 2, new QLabel(lst[i].get_stare().c_str()));
        table->setCellWidget(i, 3, new QLabel(to_string(lst[i].get_programatori().size()).c_str()));
    }
}

void GUI::add_window() {
    Window* wnd1 = new Window(srv, "open");
    Window* wnd2 = new Window(srv, "inprogress");
    Window* wnd3 = new Window(srv, "closed");

    wnd1->show();
    wnd2->show();
    wnd3->show();
}
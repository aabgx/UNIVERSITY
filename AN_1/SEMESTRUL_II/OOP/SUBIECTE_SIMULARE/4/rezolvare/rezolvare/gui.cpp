//4 GUI.CPP
#include "gui.h"
#include "sstream"
#include "QMessageBox"
#include "QRadioButton"
#include "QFormLayout"

//initializeaza interfata
void GUI::init_gui() {

    wnd->setLayout(layout);
    layout->addWidget(left);
    layout->addWidget(right);

    right->setLayout(dreapta);
    dreapta->addWidget(lbl_age);
    dreapta->addWidget(txt_age);
    dreapta->addWidget(recomandate);
    dreapta->addWidget(refresh);
    dreapta->addWidget(sortare);

    left->setLayout(stanga);
    stanga->addWidget(list);

    lbl_age->setText("VARSTA");
    sortare->setText("&SORTARE");
    refresh->setText("&REFRESH");
    recomandate->setText("&RECOMANDATE");

    list->setSelectionMode(QAbstractItemView::SingleSelection);
}

//conecteaza butoanele din interfata cu functiile aferente
void GUI::connect_signals() {
    /*QObject::connect(list, &QTableWidget::itemSelectionChanged, [this] {
        auto c = list->selectedItems();
        if (c.isEmpty()) {
            txt_age->setText("");
        }
        else {
            auto txt = c[0]->text().toStdString();
            istringstream data(txt);
            string titlu;
            data >> titlu;

            txt_age->setText(QString::fromStdString(to_string(srv.cauta(titlu).get_age())));
        }
        });*/

    QObject::connect(list, &QTableWidget::itemSelectionChanged, [this]() {
        auto lst = list->selectionModel()->selectedIndexes();
        auto it = lst.begin();
        if (it) {
            int i = it->row();
            string a = to_string(srv.get_all_srv()[i].get_age());
            txt_age->setText(a.c_str());
        }

        });

    QObject::connect(sortare, &QPushButton::clicked, [this] {
        reload_list(srv.sortare_pret());
        });
    QObject::connect(refresh, &QPushButton::clicked, [this] {
        reload_list(srv.get_all_srv());
        });
    QObject::connect(recomandate, &QPushButton::clicked, [this] {
        reload_list(srv.filtru_varsta(12));
        });
}

//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
//void GUI::reload_list(vector<Joc> jocuri) {
//    list->clear();
//    for (auto& joc : jocuri)
//    {
//        auto j = new QListWidgetItem(joc.str().c_str());
//        if (joc.get_platforma() == "PC")
//            j->setBackground(Qt::black);
//        else if (joc.get_platforma() == "PlayStation")
//            j->setBackground(Qt::blue);
//        else if (joc.get_platforma() == "XBOX")
//            j->setBackground(Qt::green);
//        else
//            j->setBackground(Qt::red);
//        list->addItem(j);
//    }
//}

//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
void GUI::reload_list(vector<Joc> jocuri) {
    list->clear();
    this->list->clearContents();
    this->list->setRowCount(jocuri.size());
    list->setColumnCount(2);

    QStringList s;
    s << "Titlu" << "Pret";
    list->setHorizontalHeaderLabels(s);
    QLabel* l1;
    for (int i = 0; i < jocuri.size(); i++) {
        list->setCellWidget(i, 0, new QLabel(jocuri[i].get_titlu().c_str()));
        list->setCellWidget(i, 1, new QLabel(to_string(jocuri[i].get_pret()).c_str()));
    }
}


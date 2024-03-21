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
    dreapta->addWidget(lbl_pierderi);
    dreapta->addWidget(txt_pierderi);
    dreapta->addWidget(refresh);

    left->setLayout(stanga);
    stanga->addWidget(list);
    //dreapta->addWidget(combo_box);
    stanga->addWidget(r2);
    stanga->addWidget(r3);
    stanga->addWidget(r5);
    stanga->addWidget(modifica_buget);
    stanga->addWidget(list_depasit);

    lbl_pierderi->setText("PIERDERI");

    refresh->setText("&REFRESH");
    modifica_buget->setText("&MODIFICA BUGET");
    r2->setText("&X2");
    r3->setText("&X3");
    r5->setText("&X5");

    list->setSelectionMode(QAbstractItemView::SingleSelection);
    list_depasit->setSelectionMode(QAbstractItemView::SingleSelection);

}

//conecteaza butoanele din interfata cu functiile aferente
void GUI::connect_signals() {

    QObject::connect(modifica_buget, &QPushButton::clicked, [&]() {
        if (r2->isChecked())
            modificare(1);
        else if (r3->isChecked())
            modificare(2);
        else if (r5->isChecked())
            modificare(3);
        });
    QObject::connect(refresh, &QPushButton::clicked, this, &GUI::refresh_la_liste);
}

//face refresh la cele 2 liste, luand in considerare ultimele modificari si afiseaza pierderile intr un qlineedit
void GUI::refresh_la_liste() {
    int calc_pierderi;

    calc_pierderi = srv.calculeaza();

    txt_pierderi->setText(QString::fromStdString(to_string(calc_pierderi)));

    reload_list_depasit(srv.get_all_srv());
}

//modifica in functie de selectarile utilizatorului un proiect din prima lista
void GUI::modificare(int n) {

    auto c = list->selectedItems();
    if (c.isEmpty()) {
        QMessageBox::warning(this, "Warning", QString::fromStdString("Selectati un element din prima lista!"));
    }
    else {
        auto txt = c[0]->text().toStdString();
        istringstream data(txt);
        string nume;
        data >> nume;

        //GASESTE NUMELE CORECT !
        /*QMessageBox msgBox;
        msgBox.setText(nume.c_str());
        msgBox.exec();*/


        //caut in service dupa nume si modific
        vector<Proiect> lst=srv.cauta_modifica(srv.get_all_srv(),nume,n);
        srv.get_all_srv() = lst;
        reload_list(srv.get_all_srv());
    }
}

//pune in lista totala ce se afiseaza in interfata grafica o lista data ca parametru
void GUI::reload_list(vector<Proiect> proiecte) {
    list->clear();
    for (Proiect proiect : proiecte)
    {
        auto p = new QListWidgetItem(proiect.str().c_str());
        list->addItem(p);
    }
}

//pune in lista de depasiri ce se afiseaza in interfata grafica o lista data ca parametru
void GUI::reload_list_depasit(vector<Proiect> proiecte) {
    list_depasit->clear();
    for (Proiect proiect : proiecte)
    {
        auto p = new QListWidgetItem(proiect.str().c_str());
        if (proiect.get_tip()=="modern")   p->setBackground(Qt::magenta);
        if (proiect.get_buget_e() > proiect.get_buget_a()) list_depasit->addItem(p);
    }
}
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
    dreapta->addWidget(lbl_nume);
    dreapta->addWidget(txt_nume);
    dreapta->addWidget(lbl_nrpct);
    dreapta->addWidget(txt_nrpct);
    dreapta->addWidget(finala);
    dreapta->addWidget(semifinala);
    dreapta->addWidget(sferturi);
    dreapta->addWidget(adauga_puncte);
    dreapta->addWidget(slider);
    dreapta->addWidget(spin_box);
    dreapta->addWidget(elimina_jucatori);

    slider->setRange(0, srv.get_all_srv().size());
    spin_box->setRange(0, srv.get_all_srv().size());

    left->setLayout(stanga);
    stanga->addWidget(list);
    stanga->addWidget(recalc_ranking);

    lbl_nume->setText("NUME");
    lbl_nrpct->setText("NR. PUNCTE");
    recalc_ranking->setText("&RECALCULEAZA RANKING");
    elimina_jucatori->setText("&ELIMINA JUCATORI");
    adauga_puncte->setText("&ADAUGA PUNCTE");

    list->setSelectionMode(QAbstractItemView::SingleSelection);
}

//conecteaza butoanele din interfata cu functiile aferente
void GUI::connect_signals() {

    //sincronizare slider si spinbox
    QObject::connect(spin_box, SIGNAL(valueChanged(int)), slider, SLOT(setValue(int)));
    QObject::connect(slider, SIGNAL(valueChanged(int)), spin_box, SLOT(setValue(int)));

    QObject::connect(adauga_puncte, &QPushButton::clicked, [&]() {
        if (finala->isChecked())
            modifica_puncte(1);
        else if (semifinala->isChecked())
            modifica_puncte(2);
        else if (sferturi->isChecked())
            modifica_puncte(3);
        });

    QObject::connect(recalc_ranking, &QPushButton::clicked, this, &GUI::update_rank);
    QObject::connect(elimina_jucatori, &QPushButton::clicked, this, &GUI::sterge);
}

//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
void GUI::reload_list(vector<Jucator> jucatori) {
    list->clear();
    for (auto& jucator : jucatori)
    {
        auto j = new QListWidgetItem(jucator.str().c_str());
        list->addItem(j);
    }
}

//Ia din line edit numele jucatorului si nr de puncte (trebuie sa ii faca update).
//parametrul nr indica daca punctele au fost castigate in finala, semifinala sau sferturi, pe baza acestei informatii calculand nr. de puncte ce trebuie adaugate
//trimite rezultatul la o functie de update pt a face efectiv schimbarea
void GUI::modifica_puncte(int nr) {
    string nume = txt_nume->text().toStdString();
    int nrpct = txt_nrpct->text().toInt();
    Jucator j = srv.cauta(nume);
    if (nr == 1)
        update(nume, nrpct);
    if (nr == 2)
    {
        nrpct = 0.75 * nrpct;
        update(nume, nrpct);
    }
    if (nr == 3)
    {
        nrpct = 0.5 * nrpct;
        update(nume, nrpct);
    }
        
}
 
//Face un update a numarului de puncte (cu nr_pct) la jucatorul cu numele nume.
void GUI::update(string nume, int nr_pct) {
    vector<Jucator> lst = srv.get_all_srv();
    for (auto& el : lst) {
        if (el.get_nume() == nume)
            el.set_puncte(el.get_puncte() + nr_pct);
    }
    srv.get_all_srv() = lst;
    reload_list(srv.get_all_srv());
}

//Reface ierarhizarea setand rank-ul jucatorilor pe baza numarului de puncte pe care il au.
void GUI::update_rank()
{
    vector<Jucator> lst = srv.sortare_puncte();
    int nr = 1;
    for (auto& el : lst) {
        el.set_rank(nr);
        nr++;
    }
    srv.get_all_srv() = lst;
    reload_list(srv.get_all_srv());
}

//Sterge din lista de jucatori ultimii nr jucatori.
void GUI::sterge() {
    vector<Jucator> lst;
    int nr = slider->value();
    if (nr >= srv.get_all_srv().size()) QMessageBox::warning(this, "Error", QString::fromStdString("Nu puteti elimina atat de multi jucatori!"));
    else lst=srv.elimina(nr);
    
    srv.get_all_srv() = lst;
    reload_list(srv.get_all_srv());
}

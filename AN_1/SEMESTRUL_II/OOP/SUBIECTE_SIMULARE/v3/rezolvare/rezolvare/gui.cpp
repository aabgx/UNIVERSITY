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
    dreapta->addWidget(lbl_tara);
    dreapta->addWidget(txt_tara);
    dreapta->addWidget(slider);
    dreapta->addWidget(spin_box);
    slider->setRange(0, 15);
    spin_box->setRange(0, 15);
    dreapta->addWidget(adauga);

    left->setLayout(stanga);
    stanga->addWidget(list);
    stanga->addWidget(sortare);
    stanga->addWidget(check);

    lbl_nume->setText("NUME");
    lbl_tara->setText("TARA");

    adauga->setText("&ADAUGA");
    sortare->setText("&SORTARE RANK");

    list->setSelectionMode(QAbstractItemView::SingleSelection);

}

//conecteaza butoanele din interfata cu functiile aferente
void GUI::connect_signals() {
    QObject::connect(check, &QCheckBox::clicked, this, &GUI::coloreaza);
    QObject::connect(adauga, &QPushButton::clicked, this, &GUI::adauga_jucator);
    QObject::connect(sortare, &QPushButton::clicked, this, &GUI::sortare_rank);

    //sincronizare slider si spinbox
    QObject::connect(spin_box, SIGNAL(valueChanged(int)), slider, SLOT(setValue(int)));
    QObject::connect(slider, SIGNAL(valueChanged(int)), spin_box, SLOT(setValue(int)));
}

//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
void GUI::reload_list(vector<Jucator> jucatori) {
    list->clear();
    for (auto& jucator : jucatori)
    {   
        auto j = new QListWidgetItem(jucator.str().c_str());
        if (check->isChecked() && jucator.get_puncte() > 250) j->setBackground(Qt::green);
        list->addItem(j);
    }
}
void GUI::coloreaza() {
    reload_list(srv.get_all_srv());
}


//sorteaza pe baza rank jucatorii si afiseaza lista
void GUI::sortare_rank() {
    reload_list(srv.sortare_rank());
}

//adauga un jucator pe baza nume, tara, rank si calculeaza un punctaj aleator pe baza rank ului
void GUI::adauga_jucator() {
    string nume = txt_nume->text().toStdString();
    string tara = txt_tara->text().toStdString();
    int rank = spin_box->value();

    int puncte, min = 0, max = 5000;
    bool ok = true, nu_adauga = false;

    txt_nume->clear();
    txt_tara->clear();
    slider->setValue(0);
    spin_box->clear();

    vector<Jucator> lst = srv.sortare_rank();

    for (auto& el : lst) {
        if (el.get_rank() == rank) {
            QMessageBox::warning(this, "Error", QString::fromStdString("Acest rank exista deja!!")); nu_adauga = true; break;
        }
        if (el.get_rank() < rank) max = el.get_puncte();
        if (el.get_rank() > rank && ok == true) { min = el.get_puncte(); ok = false; } //sa nu dea update la min decat la primul rank gasit mai mare decat ce vreau sa adaug   
    }

    /* string msg = to_string(min) + " " +to_string(max);
    QMessageBox msgBox;
    msgBox.setText(msg.c_str());
    msgBox.exec();*/

    if (nu_adauga == false) {
        puncte = min + rand() % (max - min);

        this->srv.adauga(nume, tara, puncte, rank);

        reload_list(srv.get_all_srv());
    }
}


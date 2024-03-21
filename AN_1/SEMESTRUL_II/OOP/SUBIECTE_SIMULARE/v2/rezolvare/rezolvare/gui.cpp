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
    slider->setRange(0, 5000);
    spin_box->setRange(0, 5000);

    left->setLayout(stanga);
    stanga->addWidget(list);
    stanga->addWidget(adauga);
    stanga->addWidget(match);

    lbl_nume->setText("NUME");
    lbl_tara->setText("TARA");

    adauga->setText("&ADAUGA");
    match->setText("&MATCH");

    //pt tari
    widget_radio->setLayout(layout_radio);
    layout->addWidget(widget_radio);
}

//conecteaza butoanele din interfata cu functiile aferente
void GUI::connect_signals() {

    QObject::connect(adauga, &QPushButton::clicked, this, &GUI::gui_add);
    QObject::connect(match, &QPushButton::clicked, this, &GUI::match_players);
    QObject::connect(spin_box, SIGNAL(valueChanged(int)),slider, SLOT(setValue(int)));
    QObject::connect(slider, SIGNAL(valueChanged(int)),spin_box, SLOT(setValue(int)));
}

//face update la lista afisata, vectorul dat ca parametru devine lista ce se afiseaza
void GUI::reload_list_update(vector<Jucator> jucatori) {
    srv.get_all_srv() = jucatori;
    list->clear();
    for (auto& jucator : jucatori)
    {
        auto j = new QListWidgetItem(jucator.str().c_str());
        list->addItem(j);
    }
}

//pune in lista ce se afiseaza in interfata grafica o lista data ca parametru
void GUI::reload_list(vector<Jucator> jucatori) {
    list->clear();
    for (auto& jucator : jucatori)
    {
        auto j = new QListWidgetItem(jucator.str().c_str());
        list->addItem(j);
    }
    actualizare_radio_box();
}

//adauga pe baza nume, tara, puncte si calculeaza rank ul (il reface si pe al celorlalti)
void GUI::gui_add() {
        string nume = txt_nume->text().toStdString();
        string tara = txt_tara->text().toStdString();
        int puncte = spin_box->value();

        int rank;

        txt_nume->clear();
        txt_tara->clear();

        //nu conteaza ce pun la rank, o sa ii fac update
        this->srv.adauga_srv(nume,tara,puncte,2);

        reload_list_update(srv.update_ranks(srv.sortare_puncte()));
        //tari();
}

//face un meci cu 2 jucatori random si le atribuie un scor random dintr-o lista
void GUI::match_players() {
    vector<Jucator> v = srv.get_all_srv();
    int random1 = rand() % v.size();
    Jucator j1 = v[random1];
    int random2 = rand() % v.size();
    Jucator j2 = v[random2];

    while (j1.get_rank() == j2.get_rank())
    {
        random2 = rand() % v.size();
        j2 = v[random2];
    }

    vector<string> lst = {"6-1","6-2","6-3","6-4","7-5"};
    random1 = rand() % lst.size();
    random2 = rand() % lst.size();
    string scor1 = lst[random1];
    string scor2 = lst[random2];

    QMessageBox msgBox;
    string msg = to_string(j1.get_rank()) + " " + j1.get_nume() + " vs. " + to_string(j2.get_rank()) + " " + j2.get_nume() +'\n'+ "Scor: "+scor1+" " + scor2;
    msgBox.setText(msg.c_str());
    msgBox.exec();
}


//face butoane dinamice cu tarile existente, daca sunt apasate arata in lista jucatori apartinatori ai acelor tari
void GUI::actualizare_radio_box() {
    QLayoutItem* child;
    while ((child = layout_radio->takeAt(0)) != NULL) {
        delete child->widget();
        delete child;
    }
    auto lst = srv.get_all_srv();
    for (auto s : lst) {
        QRadioButton* r = new QRadioButton;
        r->setText(s.get_tara().c_str());
        layout_radio->addWidget(r);
        QObject::connect(r, &QRadioButton::clicked, [r, this, s]() {
            reload_list(srv.filtrare_tara(s.get_tara()));
            });
    }
}
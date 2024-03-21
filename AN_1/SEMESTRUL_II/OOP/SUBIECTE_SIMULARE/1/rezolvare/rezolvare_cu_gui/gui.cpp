#include "gui.h"
#include "sstream"

void GUI::init_gui() {

    wnd->setLayout(layout);
    layout->addWidget(left);
    layout->addWidget(right);

    right->setLayout(vLText);
    vLText->addWidget(lblCnp);
    vLText->addWidget(txtCnp);
    vLText->addWidget(lblNume);
    vLText->addWidget(txtNume);
    vLText->addWidget(lblSectie);
    vLText->addWidget(txtSectie);

    left->setLayout(vL);
    vL->addWidget(list);
    vL->addWidget(filtNume);
    vL->addWidget(filtSectie);
    vL->addWidget(filtTot);

    lblCnp->setText("CNP");
    lblNume->setText("NUME");
    lblSectie->setText("SECTIE");
    filtNume->setText("&FiltruNume");
    filtSectie->setText("&FiltruSectie");
    filtTot->setText("&FiltruTot");

    list->setSelectionMode(QAbstractItemView::SingleSelection);

}

void GUI::connect_signals() {

    QObject::connect(list, &QListWidget::itemSelectionChanged, [this] {
        auto c = list->selectedItems();
        if (c.isEmpty()) {
            txtCnp->setText("");
        }
        else {
            auto txt = c[0]->text().toStdString();
            istringstream data(txt);
            string nume;
            data >> nume;
            string prenume;
            data >> prenume;

            txtCnp->setText(QString::fromStdString(to_string(srv.cauta_srv(nume, prenume))));
        }
        });

    QObject::connect(filtNume, &QPushButton::clicked, [this] {
        string nume = txtNume->text().toStdString();
        auto lst = srv.filtrare_nume(nume);
        reload_list(lst);
        });
    QObject::connect(filtSectie, &QPushButton::clicked, [this] {
        string sectie = txtSectie->text().toStdString();
        auto lst = srv.filtrare_sectie(sectie);
        reload_list(lst);
        });
    QObject::connect(filtTot, &QPushButton::clicked, [this] {
        string nume = txtNume->text().toStdString();
        string sectie = txtSectie->text().toStdString();
        auto lst = srv.filtrare_tot(nume,sectie);
        reload_list(lst);
        });
}

void GUI::reload_list(vector<Doctor> doctors) {

    list->clear();
    for (auto& doctor : doctors)
    {   
        auto d = new QListWidgetItem(QString::fromStdString(doctor.str()));
        if (doctor.get_concediu() == 0) {
            d->setBackground(Qt::green);
        }
        else {
            d->setBackground(Qt::red);
        }
        list->addItem(d);
    }
}
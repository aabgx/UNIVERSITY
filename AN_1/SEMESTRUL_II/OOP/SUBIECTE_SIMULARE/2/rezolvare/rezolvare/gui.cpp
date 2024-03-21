#include "gui.h"
#include "sstream"

void GUI::init_gui() {

    wnd->setLayout(layout);
    layout->addWidget(left);
    layout->addWidget(right);

    right->setLayout(vLText);
    vLText->addWidget(lbl_model);
    vLText->addWidget(txt_model);
    vLText->addWidget(lbl_an);
    vLText->addWidget(txt_an);


    left->setLayout(vL);
    vL->addWidget(list);
    vL->addWidget(sortare_model);
    vL->addWidget(sortare_pret);
    vL->addWidget(nesortat);

    lbl_model->setText("MODEL");
    lbl_an->setText("AN");

    sortare_model->setText("&SORTARE_MODEL");
    sortare_pret->setText("&SORTARE_PRET");
    nesortat->setText("&NESORTAT");

    list->setSelectionMode(QAbstractItemView::SingleSelection);

}

void GUI::connect_signals() {

    QObject::connect(list, &QListWidget::itemSelectionChanged, [this] {
        auto c = list->selectedItems();
        if (c.isEmpty()) {
            txt_model->setText("");
            txt_an->setText("");
        }
        else {
            auto txt = c[0]->text().toStdString();
            istringstream data(txt);
            string model;
            data >> model;
            int pret;
            data >> pret;

            txt_model->setText(QString::fromStdString(srv.cauta_srv(model, pret).get_model()));
            txt_an->setText(QString::fromStdString(to_string(srv.cauta_srv(model, pret).get_an())));
        }
        });

    QObject::connect(nesortat, &QPushButton::clicked, [this] {
        reload_list(srv.get_all_srv());
        });
    QObject::connect(sortare_model, &QPushButton::clicked, [this] {
        reload_list(srv.sortare_model());
        });
    QObject::connect(sortare_pret, &QPushButton::clicked, [this] {
        reload_list(srv.sortare_pret());
        });
}

void GUI::reload_list(vector<Device> devices) {

    list->clear();
    for (auto& device : devices)
    {
        auto d = new QListWidgetItem(QString::fromStdString(device.str()));
        if (device.get_culoare() == "rosu") {
            d->setBackground(Qt::red);
        }
        else if (device.get_culoare() == "albastru") {
            d->setBackground(Qt::blue);
        }
        else if (device.get_culoare() == "galben") {
            d->setBackground(Qt::yellow);
        }
        else{
            d->setBackground(Qt::black);
        }
        list->addItem(d);
    }
}
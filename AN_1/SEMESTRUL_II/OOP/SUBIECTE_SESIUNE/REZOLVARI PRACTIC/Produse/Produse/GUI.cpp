#pragma once
#include "GUI.h"

void GUI::initGui() {
    table->setSelectionMode(QAbstractItemView::SingleSelection);
    table->setModel(model);

    this->setLayout(main_layout);
    main_layout->addWidget(table);

    right_comp->setLayout(right_layout);
    right_layout->addWidget(lbl_id);
    right_layout->addWidget(txt_id);
    right_layout->addWidget(lbl_titlu);
    right_layout->addWidget(txt_titlu);
    right_layout->addWidget(lbl_tip);
    right_layout->addWidget(txt_tip);
    right_layout->addWidget(lbl_pret);
    right_layout->addWidget(txt_pret);
    right_layout->addWidget(lbl_pret_sld);
    right_layout->addWidget(sld_pret);
    right_layout->addWidget(btn_add);

    main_layout->addWidget(right_comp);

    sld_pret->setMinimum(1);
    sld_pret->setMaximum(100);

}

void GUI::connectSlots() {
    QObject::connect(table->selectionModel(), &QItemSelectionModel::selectionChanged, [this] {
        if (table->selectionModel()->selectedIndexes().isEmpty())
        {
            txt_titlu->setText("");
            txt_pret->setText("");
            txt_id->setText("");
            txt_tip->setText("");
            return;
        }
        auto row = table->selectionModel()->selectedIndexes().at(0).row();
        txt_titlu->setText(table->model()->data(table->model()->index(row, 1), Qt::DisplayRole).toString());
        txt_id->setText(table->model()->data(table->model()->index(row, 0), Qt::DisplayRole).toString());
        txt_tip->setText(table->model()->data(table->model()->index(row, 2), Qt::DisplayRole).toString());
        txt_pret->setText(table->model()->data(table->model()->index(row, 3), Qt::DisplayRole).toString());
        });

    QObject::connect(btn_add, &QPushButton::clicked, [this] {
        auto id = txt_id->text().toInt();
        auto nume = txt_titlu->text().toStdString();
        auto tip = txt_tip->text().toStdString();
        auto pret = txt_pret->text().toDouble();
        try {
            srv.adauga(id, nume, tip, pret);
        }
        catch (ServiceException&) {
            QMessageBox::information(this, "Info", "Error");
        }
        reload_list();
        });

    QObject::connect(sld_pret, &QSlider::valueChanged, [this] {
        reload_list();
        });
}

void GUI::reload_list() {
    model->set_produse(srv.get_all_srv(), sld_pret->value());
}

void GUI::open_dinamic() {
    auto lst = srv.get_tipuri();
    for (auto& el : lst) {
        auto wnd = new TipWnd{ srv,el.first,el.second };
        wnd->run();
    }
}

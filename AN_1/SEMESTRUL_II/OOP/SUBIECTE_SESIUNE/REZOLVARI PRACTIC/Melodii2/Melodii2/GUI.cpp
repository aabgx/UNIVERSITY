#pragma once
#include <QPainter>
#include "GUI.h"
#include "qdebug.h"

void GUI::init_GUI() {

    painter = new Painter{ this->srv.genuri_fr() };
    painter->setLayout(layout_paint);

    model = new MyTableModel{ srv.get_all_srv(),srv.artisti_fr(), srv.genuri_fr() };
    table->setSelectionMode(QAbstractItemView::SingleSelection);
    table->setModel(model);

    this->setLayout(main_layout);
    left_comp->setLayout(layout_left);
    right_comp->setLayout(layout_right);
    layout_comps->addWidget(left_comp);
    layout_comps->addWidget(right_comp);
    comps->setLayout(layout_comps);
    main_layout->addWidget(comps);

    delete_btn->setText("&DELETE");
    add_btn->setText("&ADAUGA");
    lbl_titlu->setText("TITLU");
    lbl_artist->setText("ARTIST");
    lbl_gen->setText("GEN");

    layout_left->addWidget(table);

    layout_right->addWidget(delete_btn);
    layout_right->addWidget(lbl_titlu);
    layout_right->addWidget(txt_titlu);
    layout_right->addWidget(lbl_artist);
    layout_right->addWidget(txt_artist);
    layout_right->addWidget(lbl_gen);
    layout_right->addWidget(txt_gen);
    layout_right->addWidget(add_btn);

}

void GUI::connect_slots() {
    QObject::connect(table->selectionModel(), &QItemSelectionModel::selectionChanged, [this] {
        if (table->selectionModel()->selectedIndexes().isEmpty()) {
            txt_titlu->setText("");
            return;
        }
        else {
            auto row = table->selectionModel()->selectedIndexes().at(0).row();
            auto titlu = table->model()->data(table->model()->index(row, 1), Qt::DisplayRole).toString();
            txt_titlu->setText(titlu);
        }
        });
    QObject::connect(add_btn, &QPushButton::clicked, [this] {
        auto titlu = txt_titlu->text().toStdString();
        auto artist = txt_artist->text().toStdString();
        auto gen = txt_gen->text().toStdString();
        srv.add(titlu, artist, gen);
        reload_list();
        });
    QObject::connect(delete_btn, &QPushButton::clicked, [this] {
        if (table->selectionModel()->selectedIndexes().isEmpty()) {
            return;
        }
        auto row = table->selectionModel()->selectedIndexes().at(0).row();
        auto id = table->model()->data(table->model()->index(row, 0), Qt::DisplayRole).toInt();

        srv.remove(id);
        this->repaint();
        reload_list();
        });

}

void GUI::reload_list() {
    model->set_melodii(srv.get_all_srv(), srv.artisti_fr(), srv.genuri_fr());
}

void Painter::paintEvent(QPaintEvent* ev) {

}

void GUI::paintEvent(QPaintEvent* ev) {

    QPainter p{ this };
    p.setPen(Qt::darkMagenta);
    int pop = 0, rock = 0, folk = 0, disco = 0;
    for (auto& m : srv.get_all_srv())
    {
        if (m.get_gen() == "pop") {
            pop++;
            p.drawEllipse(QPoint(0, 0), pop * 8, pop * 8);
        }
        if (m.get_gen() == "folk") {
            folk++;
            p.drawEllipse(QPoint(p.device()->width(), 0), folk * 8, folk * 8);
        }
        if (m.get_gen() == "disco") {
            disco++;
            p.drawEllipse(QPoint(0, p.device()->height()), disco * 8, disco * 8);
        }
        if (m.get_gen() == "rock") {
            rock++;
            p.drawEllipse(QPoint(p.device()->width(), p.device()->height()), rock * 8, rock * 8);
        }
    }
}
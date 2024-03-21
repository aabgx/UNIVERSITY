#pragma once
#include <QPainter>
#include "GUI.h"
#include "qdebug.h"

void GUI::init_GUI() {

    painter = new Painter{ this->srv.rank_fr() };
    painter->setLayout(layout_paint);
    model = new MyTableModel{ srv.get_all_srv(), srv.rank_fr() };
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
    update_btn->setText("&UPDATE");
    lbl_titlu->setText("TITLU");
    lbl_rank->setText("RANK");

    layout_left->addWidget(table);

    layout_right->addWidget(delete_btn);
    layout_right->addWidget(update_btn);
    layout_right->addWidget(lbl_titlu);
    layout_right->addWidget(txt_titlu);
    layout_right->addWidget(lbl_rank);
    layout_right->addWidget(sld_rank);

    sld_rank->setMaximum(10);
    sld_rank->setMinimum(0);


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
    QObject::connect(update_btn, &QPushButton::clicked, [this] {
        if (table->selectionModel()->selectedIndexes().isEmpty()) {

            return;
        }
        auto row = table->selectionModel()->selectedIndexes().at(0).row();
        auto id = table->model()->data(table->model()->index(row, 0), Qt::DisplayRole).toInt();
        auto titlu_nou = txt_titlu->text().toStdString();
        auto rank_nou = sld_rank->value();
        try {
            srv.modifica_srv(id, titlu_nou, rank_nou);
            this->repaint();
            reload_list();
        }
        catch (exception&) {
            QLabel* lbl = new QLabel;
            lbl->setText("DATE INVALIDE!");
            lbl->show();
        }
        });
    QObject::connect(delete_btn, &QPushButton::clicked, [this] {
        if (table->selectionModel()->selectedIndexes().isEmpty()) {
            return;
        }
        auto row = table->selectionModel()->selectedIndexes().at(0).row();
        auto id = table->model()->data(table->model()->index(row, 0), Qt::DisplayRole).toInt();
        try {
            srv.remove_srv(id);
        }
        catch (exception&) {
            QLabel* lbl = new QLabel;
            lbl->setText("DATE INVALIDE!");
            lbl->show();
        }
        this->repaint();
        reload_list();
        });

}

void GUI::reload_list() {

    model->set_melodii(srv.get_all_srv(), srv.rank_fr());
}


void Painter::paintEvent(QPaintEvent* ev) {

    QPainter p{ this };
    p.setPen(Qt::darkYellow);
    int rank = 0;
    for (auto& c : ranks) {
        //qDebug()<<"Da";
        int x = (rank + 1) * 50;
        //qDebug()<< x<<" "<<x*c;
        p.drawLine(x, 0, x, x * c * 10);
        rank++;
    }

}

void GUI::paintEvent(QPaintEvent* ev) {

    QPainter p{ this };
    p.setPen(Qt::darkRed);
    int rank = 0;
    for (auto& c : srv.rank_fr()) {
        int x = (rank + 1) * 10;
        p.drawLine(x, p.device()->height(), x, p.device()->height() - c * 5);
        rank++;
    }
}

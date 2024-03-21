#pragma once
#include <QWidget>
#include <QTableView>
#include <utility>
#include "QLayout"
#include "QPushButton"
#include "QSlider"
#include "QLineEdit"
#include "QLabel"
#include "QMessageBox"
#include "service.h"
#include "MODEL.h"
#include "observer.h"

class TipWnd :public Observer {
private:
    Service& srv;
    int nr;
    string tip;
    QLabel* lbl = new QLabel;
    QWidget* wnd = new QWidget;
    QHBoxLayout* layout = new QHBoxLayout;
public:
    TipWnd(Service& srv, string tip, int nr) :srv{ srv }, tip(tip), nr{ nr }{
        this->srv.addObserver(this);
        auto txt = to_string(nr);
        lbl->setText(QString::fromStdString(txt));
        wnd->setWindowTitle(QString::fromStdString(tip));
        wnd->setLayout(layout);
        layout->addWidget(lbl);
    }
    ~TipWnd() {
        this->srv.removeObserver(this);
    }
    void update() override {
        nr = srv.nr_tipuri(tip);
        auto txt = to_string(nr);
        lbl->setText(QString::fromStdString(txt));
    }
    void run() { wnd->show(); }
};

class GUI : public QWidget {
private:
    Service& srv;
    QWidget* main_wnd = new QWidget, * left_comp = new QWidget, * right_comp = new QWidget;
    QHBoxLayout* main_layout = new QHBoxLayout;
    QVBoxLayout* right_layout = new QVBoxLayout;

    QTableView* table = new QTableView;
    MyTableModel* model = new MyTableModel{ srv.get_all_srv() ,0};

    QSlider* sld_pret = new QSlider(Qt::Horizontal);
    QLabel* lbl_pret_sld = new QLabel{ "PRET FILTRARE" }, * lbl_id = new QLabel{ "ID" }, * lbl_titlu = new QLabel{"TITLU" }, * lbl_tip = new QLabel{ "TIP" }, * lbl_pret = new QLabel{ "Pret" };
    QLineEdit* txt_titlu = new QLineEdit, * txt_id = new QLineEdit, * txt_tip = new QLineEdit, * txt_pret = new QLineEdit;

    QPushButton* btn_add = new QPushButton{ "ADD" };

    void initGui();
    void connectSlots();
    void reload_list();
    void open_dinamic();

public:
    GUI(Service& srv) : srv{ srv } {
        initGui();
        connectSlots();
        //reload_list();
    };

    void run() {
        this->show();
        open_dinamic();
    }

};
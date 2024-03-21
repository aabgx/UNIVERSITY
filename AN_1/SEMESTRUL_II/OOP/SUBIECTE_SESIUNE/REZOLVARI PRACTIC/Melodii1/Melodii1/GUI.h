#pragma once
#include <utility>

#include "service.h"
#include "QWidget"
#include "MODEL.h"
#include "QTableView"
#include "QSlider"
#include "Qt"
#include "QPushButton"
#include "QLineEdit"
#include "QLabel"
#include "QLayout"

using namespace std;

class Painter : public QWidget {
private:
    vector<int> ranks;
public:

    explicit Painter(vector<int> ranks) :ranks(ranks) {
        this->repaint();
    }
    void paintEvent(QPaintEvent* ev) override;

};

class GUI :public QWidget {
private:
    Service& srv;
    MyTableModel* model;
    QTableView* table = new QTableView;

    QWidget* main_wnd = new QWidget, * left_comp = new QWidget, * right_comp = new QWidget, * comps = new QWidget;
    QPushButton* update_btn = new QPushButton, * delete_btn = new QPushButton;
    QVBoxLayout* main_layout = new QVBoxLayout;
    QHBoxLayout* layout_comps = new QHBoxLayout;
    QVBoxLayout* layout_left = new QVBoxLayout;
    QVBoxLayout* layout_right = new QVBoxLayout;
    QVBoxLayout* layout_paint = new QVBoxLayout;

    QLabel* lbl_titlu = new QLabel, * lbl_rank = new QLabel;
    QLineEdit* txt_titlu = new QLineEdit;
    QSlider* sld_rank = new QSlider(Qt::Horizontal);
    Painter* painter;

    void init_GUI();
    void connect_slots();
    void reload_list();
    void paintEvent(QPaintEvent* ev) override;

public:
    explicit GUI(Service& srv) :srv{ srv } {
        init_GUI();
        connect_slots();
        reload_list();
        this->repaint();


    };
    void run() {
        this->show();
    }

};

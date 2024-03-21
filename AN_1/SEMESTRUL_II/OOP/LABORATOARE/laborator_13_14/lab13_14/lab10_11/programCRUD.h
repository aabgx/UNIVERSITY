#pragma once
#include "program.h"
#include "Observer.h"
#include "service.h"
#include <qwidget.h>
#include <qpushbutton.h>
#include <qtablewidget.h>
#include <qslider.h>
#include <qlayout>
#include <vector>
#include "MyTableModel.h"

using std::vector;

class programCRUDGUI : public QWidget, public Observer {
private:
    Program& program;
    Service& srv;
    QWidget* wnd;
    QHBoxLayout* layout;
    //QTableWidget* table;
    QTableView* table;
    QSlider* slider;
    QPushButton* btnadd;
    QPushButton* btnempty;

    MyTableModel* model;


    void initComponents();
    void connectSignals();

    //void populateTable(QTableWidget* table,vector<Activitate>& all);
    void populateTable(QTableView* table, vector<Activitate>& all);
public:
    explicit programCRUDGUI(Program& program, Service& srv) :program{ program }, srv{ srv }{
        wnd = new QWidget;
        layout = new QHBoxLayout;
        btnadd = new QPushButton("GENERARE ACTIVITATI RANDOM");
        btnempty = new QPushButton("GOLESTE PROGRAM");
        slider = new QSlider;
        //table = new QTableWidget(0, 4);
        table = new QTableView;
        this->model = new MyTableModel{ srv.get_all_program_srv() };

    };
    void run();

    void update() override;

    ~programCRUDGUI() {
        this->program.removeObserver(this);
    }
};

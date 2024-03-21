#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_examen.h"

class examen : public QMainWindow
{
    Q_OBJECT

public:
    examen(QWidget *parent = Q_NULLPTR);

private:
    Ui::examenClass ui;
};

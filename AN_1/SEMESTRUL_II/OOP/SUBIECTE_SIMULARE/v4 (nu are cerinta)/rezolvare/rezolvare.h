#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_rezolvare.h"

class rezolvare : public QMainWindow
{
    Q_OBJECT

public:
    rezolvare(QWidget *parent = Q_NULLPTR);

private:
    Ui::rezolvareClass ui;
};

#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_Melodii2.h"

class Melodii2 : public QMainWindow
{
    Q_OBJECT

public:
    Melodii2(QWidget *parent = Q_NULLPTR);

private:
    Ui::Melodii2Class ui;
};

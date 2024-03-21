#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_rezolvare_cu_gui.h"

class rezolvare_cu_gui : public QMainWindow
{
    Q_OBJECT

public:
    rezolvare_cu_gui(QWidget *parent = Q_NULLPTR);

private:
    Ui::rezolvare_cu_guiClass ui;
};

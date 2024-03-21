#pragma once
#include "Produse.h"
#include <QtWidgets/QApplication>
#include "domain.h"
#include "repo.h"
#include "service.h"
#include "GUI.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    Repo repo;
    Service srv{ repo };
    GUI gui(srv);
    gui.run();
    return QApplication::exec();
}

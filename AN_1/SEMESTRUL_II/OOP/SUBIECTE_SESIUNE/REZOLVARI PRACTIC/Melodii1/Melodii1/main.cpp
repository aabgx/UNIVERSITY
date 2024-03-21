#pragma once
#include "Melodii1.h"
#include <QtWidgets/QApplication>
#include "GUI.h"
#include "service.h"
#include "repo.h"

int main(int argc, char *argv[])
{
    test_creaza();
    test_set();
    test_get_all();
    test_remove();
    test_modifica();

    QApplication app(argc, argv);
    Repo repo;
    Service srv(repo);
    GUI gui(srv);
    gui.run();
    return app.exec();
}

#include "Melodii2.h"
#include <QtWidgets/QApplication>
#include "GUI.h"
#include "service.h"
#include "repo.h"

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    Repo repo;
    Service srv(repo);
    GUI gui(srv);
    gui.run();
    return app.exec();
}

#include "domain.h"
#include "repo.h"
#include "service.h"
#include "gui.h"
#include "rezolvare.h"
#include <QtWidgets/QApplication>

int main(int argc, char* argv[])
{
    test_domain();
    test_repo();
    test_service();

    Repo repo;
    Service srv(repo);
    QApplication app(argc, argv);

    GUI gui(srv);
    gui.run();

    return app.exec();
}

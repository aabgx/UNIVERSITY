#include "examen.h"
#include <QtWidgets/QApplication>
#include "domain.h"
#include "repo.h"
#include "service.h"
#include "GUI.h"

int main(int argc, char *argv[])
{
    test_creaza();
    test_set();
    test_adauga();
    test_save();
    test_sort_lista();
    test_get_all_srv();
    test_sort_lista();
    test_valideaza();
    test_add();
    test_filtrare();
    test_filter_by_stare();
    test_schimba_stare();

    QApplication a(argc, argv);
    Repo repo;
    Service srv{ repo };
    GUI app{ srv };
    app.show();
    return a.exec();
}

#pragma once
#include "repo.h"
#include "observer.h"

using namespace std;

class ServiceException :public exception {

};


class Service : public Observable {
private:
    Repo& repo;

public:
    /*constructorul*/
    explicit Service(Repo& repo) :repo(repo) {};

    /*Sorteaza lista data ca argument dupa starea elementelor.
    vect->vector<Task>: lista de sortat
    return->vector<Task>: vectorul vect sortat*/
    vector<Task>sort_lista(vector<Task> vect);

    /*un getter pentru toate elementele din repo, sortate dupa stare, pentru a putea lucra cu acestea
    return->vector<Task>: o copie a vectorului cu care lucram, deja sortata*/
    vector<Task> get_all_srv() { return sort_lista(repo.get_all()); };

    /*valideaza elementele componente ale unui Task dupa anumite criterii
    exception: daca unul sau mai multe elemente nu sunt valide*/
    void valideaza(int id, string descriere, string stare, vector<string> programatori);

    /*adauga un Task la lista 
    id->int: id-ul unic al fiecarui Task
    descriere->string: descrierea Task-ului
    stare->string: starea Task-ului(open,closed sau inprogress
    programatori->vector<string>: programatorii asignati pentru un task
    dupa executie task-ul cu aceste elemente este adaugat in lista daca e valid, altfel nu se intampla nimic*/
    void add(int id, string descriere, string stare, string programatori);

    /*filtreaza dupa numele unui programator
    pr->string: numele care se cauta in lista de programatori
    return->vector<Task>: lista cu toate task-urile existente care contin pr ca si programator*/
    vector<Task> filtrare(string pr);

    /*filtreaza dupa stare
    str->string: starea care se vrea cautata
    return->vector<Task>: lista cu toate task-urile existente care au ca stare str*/
    vector<Task> filter_by_stare(string str);

    /*schimba starea unui task, cautandu-l dupa id
    id->int: id-ul task-ului de modificat
    stare->string: noua stare, care o va inlocui pe cea veche
    dupa executie task-ul indicat va avea starea stare*/
    void schimba_stare(int id, string stare);
};

void test_sort_lista();
void test_get_all_srv();
void test_valideaza();
void test_add();
void test_filtrare();
void test_filter_by_stare();
void test_schimba_stare();
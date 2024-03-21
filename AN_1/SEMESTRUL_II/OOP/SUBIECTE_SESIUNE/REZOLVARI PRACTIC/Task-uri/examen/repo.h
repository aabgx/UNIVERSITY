#pragma once
#include "vector"
#include "domain.h"
class RepoException :public exception {

};

class Repo {
private:
    vector<Task> v;

    /*functie pentru a extrage datele din fisier
    dupa executie, in vectorul v (al clasei) vor fi toate elementele din fisierul "input.txt"*/
    void load_from_file();

public:
    /*functie pentru a salva o lista dorita in fisier
    lst->vector<Task>: lista pe care vrem sa o salvam in fisier
    dupa executie lista lst va fi salvata in fisierul "input.txt"*/
    void save_to_file(vector<Task> lst);

    /*constructorul de copiere al clasei - nu vrem sa se poata copia Repo-ul si sa avem dubluri*/
    Repo(const Repo& ot) = delete;

    /*constructorul, initial vrem sa se incarce in lista ceea ce avem in fisier*/
    Repo() {
        load_from_file();
    };

    /*functie de preluat elementele din repo, pentru a putea lucra cu datele de aici
    return->vector<Task>: se returneaza exact repo-ul, nu o copie*/
    vector<Task>& get_all() { load_from_file(); return v; };

    /*Adauga Task-ul t la vectorul din Repo si salveaza noua lista in fisier
    t->Task: task-ul de adaugat*/
    void adauga(const Task& t) {
        v.push_back(t);
        save_to_file(v);
    };
};

void test_adauga();
void test_save();
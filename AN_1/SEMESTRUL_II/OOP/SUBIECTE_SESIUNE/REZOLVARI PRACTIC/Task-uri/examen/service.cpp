#pragma once
#include "service.h"
#include "algorithm"
#include <fstream>
#include <sstream>
#include <string>
#include <cassert>


vector<Task> Service::sort_lista(vector<Task> vect) {
    vector<Task> rez;
    sort(vect.begin(), vect.end(), [](Task& t1, Task& t2) {
        return t1.get_stare() < t2.get_stare();
        });

    return vect;
}

void Service::valideaza(int id, string descriere, string stare, vector<string> programatori) {
    for (auto& p : get_all_srv()) {
        if (p.get_id() == id)
            throw ServiceException();
    }
    if (descriere == "" || (stare != "closed" && stare != "inprogress" && stare != "open") || programatori.size() < 1 || programatori.size() > 4)
        throw ServiceException();
}

void Service::add(int id, string descriere, string stare, string programatori) {
    vector<string>lst;
    stringstream stream(programatori);
    string current;
    while (getline(stream, current, ',')) {
        lst.push_back(current);
    }

    valideaza(id, descriere, stare, lst);
    repo.adauga(Task(id, descriere, stare, lst));
}

vector<Task> Service::filtrare(string pr) {
    auto lst = get_all_srv();
    vector<Task> rez;
    copy_if(lst.begin(), lst.end(), back_inserter(rez), [&](Task t) {
        for (auto& s : t.get_programatori())
            if (s == pr)return true;
        return false;
        });
    return rez;
}

vector<Task> Service::filter_by_stare(string str) {
    vector<Task> list = get_all_srv();
    vector<Task> rez;
    copy_if(list.begin(), list.end(), back_inserter(rez), [&](Task t) {
        return t.get_stare() == str;
        });
    return rez;
}

void Service::schimba_stare(int id, string stare) {
    auto& list = repo.get_all();
    for (auto& t : list) {
        if (id == t.get_id())
            t.set_stare(stare);
    }
    repo.save_to_file(list);
    notify();
}

//////////////////////////////////////////////////////////////////////TESTE///////////////////////////////////////////////////////////////////////////////////////////////////////
void test_schimba_stare() {
    Repo repo;
    Service srv(repo);

    auto aux = srv.get_all_srv();

    srv.schimba_stare(1, "inprogress");
    for (auto& el : srv.get_all_srv())
    {
        if (el.get_id() == 1) assert(el.get_stare() == "inprogress");
    }

    repo.save_to_file(aux);

}

void test_filter_by_stare() {
    Repo repo;
    Service srv(repo);
    vector<Task> lst = srv.filter_by_stare("open");
    assert(lst.size() == 8);
}

void test_filtrare() {
    Repo repo;
    Service srv(repo);
    vector<Task> lst = srv.filtrare("pr1");
    assert(lst.size() == 12);
}

void test_valideaza() {
    Repo repo;
    Service srv(repo);
    int id1 = 600, id2 = 1;
    string descriere1 = "asta e buna", descriere2 = "", stare1 = "closed", stare2 = "asta nu e buna",p1="programator1",p2="programator2";
    vector<string> programatori1 = { p1,p2 }, programatori2;
    try {
        srv.valideaza(id1,descriere1, stare1, programatori1);
        assert(true);
    }
    catch (ServiceException&) {
        assert(false);
    }

    //acum se repeta un id
    try {
        srv.valideaza(id2, descriere1, stare1, programatori1);
        assert(false);
    }
    catch (ServiceException&) {
        assert(true);
    }

    //acum nu e buna descrierea
    try {
        srv.valideaza(id1, descriere2, stare1, programatori1);
        assert(false);
    }
    catch (ServiceException&) {
        assert(true);
    }

    //acum nu e buna starea
    try {
        srv.valideaza(id1, descriere1, stare2, programatori1);
        assert(false);
    }
    catch (ServiceException&) {
        assert(true);
    }

    //acum nu sunt ok programatorii
    try {
        srv.valideaza(id1, descriere1, stare1, programatori2);
        assert(false);
    }
    catch (ServiceException&) {
        assert(true);
    }
}

void test_add() {
    Repo repo;
    Service srv(repo);
    auto aux = srv.get_all_srv();

    //asta e ok
    srv.add(600, "descriere", "open", "pr1,pr2");
    assert(srv.get_all_srv().size() == 22);

    //aici se repeta un id
    try {
        srv.add(1, "desc", "open", "pr1,pr2");
        assert(false);
    }
    catch (exception&) {
        assert(true);
    }
    assert(srv.get_all_srv().size() == 22);

    repo.save_to_file(aux);
}


void test_get_all_srv() {
	Repo repo;
	Service srv(repo);
	vector<Task> lst = srv.get_all_srv();
	assert(lst.size() == 21);
    
}

void test_sort_lista() {
    Repo repo;
    Service srv(repo);
    vector<Task> lst = srv.get_all_srv();
    assert(lst.size() == 21);

    vector<Task> new_lst = srv.sort_lista(lst);
    for (int i = 1; i < new_lst.size(); i++)
        assert(new_lst[i - 1].get_stare() <= new_lst[i].get_stare());
}
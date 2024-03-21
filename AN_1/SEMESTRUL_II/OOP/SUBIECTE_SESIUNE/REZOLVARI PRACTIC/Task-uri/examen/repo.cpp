#pragma once
#include <sstream>
#include "Repo.h"
#include "fstream"
#include "algorithm"
#include <cassert>

void Repo::load_from_file() {
    v.clear();
    ifstream f("input.txt");
    string line;
    while (getline(f, line)) {
        string descriere, stare;
        int id, programatori;
        stringstream linestream(line);
        string curent;
        int nr = 0;
        while (getline(linestream, curent, ';')) {
            if (nr == 0)
                id = stoi(curent);
            if (nr == 1)
                descriere = curent;
            if (nr == 2)
                stare = curent;
            if (nr == 3)
            {
                string current_2;
                stringstream stream_2(curent);
                vector<string>lst;
                while (getline(stream_2, current_2, ',')) {
                    lst.push_back(current_2);
                }
                v.push_back(Task(id, descriere, stare, lst));
            }
            nr++;
        }
    }
    f.close();
}

void Repo::save_to_file(vector<Task> lst) {
    ofstream g("input.txt");
    for (auto& el : lst) {
        g << el.get_id() << ";" << el.get_descriere() << ";" << el.get_stare() << ";";
        for (auto& pr : el.get_programatori())  g << pr << ',';
        g << '\n';
    }
    g.close();
}


//////////////////////////////////////////////////////////////////////////TESTE////////////////////////////////////////////////////////////////////////////////////////////////

void test_adauga() {
    Repo repo;
    auto aux = repo.get_all();
    Task t{ 1,"desc","stare",{"pr1","pr2"} };
    repo.adauga(t);
    assert(repo.get_all().size() == 22);

    repo.save_to_file(aux);
}

void test_save() {
    Repo repo;
    auto aux = repo.get_all();
    Task t(1, "desc", "open", { "pr1","pr2" });
    vector<Task>lst{ t };
    repo.save_to_file(lst);
    assert(repo.get_all().size() == 1);
    assert(repo.get_all()[0].get_id() == 1);
    assert(repo.get_all()[0].get_descriere() == "desc");
    assert(repo.get_all()[0].get_stare() == "open");
    assert(repo.get_all()[0].get_programatori()[0] == "pr1");
    assert(repo.get_all()[0].get_programatori()[1] == "pr2");

    repo.save_to_file(aux);
}
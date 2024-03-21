#pragma once
#include <utility>
#include "string"
#include <vector>
using namespace std;

class Task {
private:
    int id;
    vector<string> programatori;
    string descriere, stare;

public:
    /*constructor pentru Task
    id->int: id-ul unic al fiecarui Task
    descriere->string: descrierea Task-ului
    stare->string: starea Task-ului(open,closed sau inprogress
    programatori->vector<string>: programatorii asignati pentru un task*/
    Task(int id, string descriere, string stare, vector<string>programatori) : id(id), descriere(descriere), stare(stare), programatori(programatori) {};

    /*getter pentru id
    return->int: id-ul Task-ului*/
    int get_id() { return id; };

    /*getter pentru descriere
    return->string: descrierea Task-ului*/
    string get_descriere() { return descriere; };

    /*getter pentru stare
    return->string: starea Task-ului*/
    string get_stare() { return stare; };

    /*getter pentru programatori
    return->vector<string>: programatorii Task-ului*/
    vector<string> get_programatori() { return programatori; };

    /*setter pentru stare
    new_stare->string: starea pe care vrem sa o setam
    starea initiala a Task-ului va fi modificata cu new_stare*/
    void set_stare(string new_stare) {
        stare = new_stare;
    }
    
};

void test_creaza();
void test_set();
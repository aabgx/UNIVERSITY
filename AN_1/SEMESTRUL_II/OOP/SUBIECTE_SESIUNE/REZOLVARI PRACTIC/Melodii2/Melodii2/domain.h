#pragma once
#include <utility>

#include "string"

using namespace std;
class Melodie {
private:
    int id;
    string titlu, artist, gen;

public:
    Melodie(int id, string titlu, string artist, string gen) : id(id), titlu(titlu), artist(artist), gen(gen) {};
    int get_id() const { return id; };
    string get_gen() const { return gen; };
    string get_titlu() const { return titlu; };
    string get_artist() const { return artist; };
};
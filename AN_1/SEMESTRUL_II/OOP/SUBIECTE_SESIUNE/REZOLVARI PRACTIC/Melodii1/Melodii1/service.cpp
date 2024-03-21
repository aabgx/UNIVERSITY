#pragma once
#include "Service.h"
#include "algorithm"

void Service::modifica_srv(int id, string titlu, int rank) {
    if (rank > 10 || rank < 0 || titlu == "")
        throw ServiceException();

    repo.modifica(id, titlu, rank);
}

vector<Melodie> Service::sort_lista(vector<Melodie> vect) {
    vector<Melodie> rez;
    sort(vect.begin(), vect.end(), [](Melodie& m1,Melodie& m2) {
        return m1.get_rank() < m2.get_rank();
        });

    return vect;
}

vector<int> Service::rank_fr() {
    int rank = 0;
    vector<int> fr;
    for (int i = 0; i < 10; ++i) {
        int nr = 0;
        for (auto& m : get_all_srv())
            if (m.get_rank() == rank)
                nr++;
        fr.push_back(nr);
        rank++;
    }
    return fr;
}

void Service::remove_srv(int id) {
    string artist;
    for (auto& m : get_all_srv())
        if (id == m.get_id())
            artist = m.get_artist();
    int k = 0;
    for (auto& m : get_all_srv())
        if (m.get_artist() == artist)
            k++;
    if (k > 1)
        repo.remove(id);
    else
        throw ServiceException();

}

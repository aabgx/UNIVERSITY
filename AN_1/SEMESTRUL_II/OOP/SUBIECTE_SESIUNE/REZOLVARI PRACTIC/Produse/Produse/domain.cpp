#pragma once
#include "domain.h"

int Produs::nr_vocale() {
    int k = 0;
    for (auto& ch : nume)
        if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u')
            k++;
    return k;
}
#pragma once
#include "domain.h"
#include "assert.h"

string Device::str() {
    return this->model + " " + to_string(this->pret);
}

void test_domain() {
    Device d("tip", "model", 2000, "rosu", 3700);
    assert(d.get_tip() == "tip");
    assert(d.get_model() == "model");
    assert(d.get_an() == 2000);
    assert(d.get_culoare() == "rosu");
    assert(d.get_pret() == 3700);
}
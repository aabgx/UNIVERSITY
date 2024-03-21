#include <iostream>
#include "domain.h"
#include <cassert>

void test_creaza() {
	Melodie t(1, "titlu", "artist", 2);
	assert(t.get_id() == 1);
	assert(t.get_titlu() == "titlu");
	assert(t.get_artist() == "artist");
	assert(t.get_rank() == 2);
}

void test_set() {
	Melodie t(1, "titlu", "artist", 2);

	t.set_titlu("altul");
	assert(t.get_titlu() == "altul");

	t.set_rank(3);
	assert(t.get_rank() == 3);
}
#include "IteratorMultime.h"

#include <exception>

using namespace std;


IteratorMultime::IteratorMultime(const Multime& m) : multime(m) {
	curent = 0;

	deplasare();
}

void IteratorMultime::deplasare() {
	while ((curent < multime.m) && multime.e[curent] == INIT)
		curent++;
}


void IteratorMultime::prim() {
	curent = 0;
	deplasare();
}


void IteratorMultime::urmator() {
	if (!valid())
		throw(exception());
	curent++;
	deplasare();
}


TElem IteratorMultime::element() const {
	if (!valid())
		throw(exception());
	return multime.e[curent];
}

bool IteratorMultime::valid() const {
	if (curent < multime.m)
		return true;
	return false;
}

//IteratorMultime::IteratorMultime(const Multime& m) : multime(m) {
//	curent = 0;
//	while (multime.e[curent] == sters || multime.e[curent] == NIL)
//		curent++;
//}// Tetha(1)
//
//
//void IteratorMultime::prim() {
//	curent = 0;
//	while (multime.e[curent] == sters || multime.e[curent] == NIL)
//		curent++;
//}// Tetha(1)
//
//
//void IteratorMultime::urmator() {
//	if (!valid())
//		throw(exception());
//	do
//	{
//		curent++;
//	} while (multime.e[curent] == sters || multime.e[curent] == NIL);
//}// O(m)
//
//
//TElem IteratorMultime::element() const {
//	if (!valid())
//		throw(exception());
//	return multime.e[curent];
//}// Tetha(1)
//
//bool IteratorMultime::valid() const {
//	if (curent < multime.m)
//		return true;
//	return false;
//}// Tetha(1)


#pragma once

#include "activitate.h"
#include <vector>
#include <string>
using std::string;
using std::vector;
class ExceptiiValidare {
	vector<string> errorMsg;
public:
	ExceptiiValidare(vector<string> errorMessages) :errorMsg{ errorMessages } {};

	string getErrorMessages() {
		string fullMsg = "";
		for (const string e : errorMsg) {
			fullMsg += e + "\n";
		}
		return fullMsg;
	}
};
/*
Clasa pentru validarea activitatilor

-titlu: sa nu fie nul
-descriere: sa nu fie nul
-tip: sa nu fie nul
-durata: >0
*/
class Validator {

public:
	void valideaza(Activitate& a) {
		vector<string> errors;
		if (a.get_titlu().length() < 1 || a.get_titlu() == "null")
			errors.push_back("Titlul trebuie sa aiba cel putin un caracter.");
		if (a.get_descriere().length() < 1 || a.get_descriere() == "null")
			errors.push_back("Artistul trebuie sa aiba cel putin un caracter.");
		if (a.get_tip().length() < 1 || a.get_tip() == "null")
			errors.push_back("Tipul trebuie sa aiba cel putin un caracter.");
		if (a.get_durata() < 1 || a.get_durata() > 100)
			errors.push_back("Durata trebuie sa fie intre 1 si 100 minute.");

		if (errors.size() > 0)
			throw ExceptiiValidare(errors);
	}
};

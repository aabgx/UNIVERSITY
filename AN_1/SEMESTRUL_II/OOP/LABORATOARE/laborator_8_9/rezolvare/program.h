#pragma once
#include "Activitate.h"
#include <vector>
#include <algorithm>
#include <random> 
#include <chrono>   

using std::vector;
class Program {
	friend class Service;
private:
	vector<Activitate> program;
public:
	Program() = default;

	/*vector<Activitate>& get_all() {
		return program;
	}*/

	//Adauga o activitate a in program
	void adauga_program(const Activitate& a);

	//Elimina toate activitatile din program
	void goleste_program();

	//Adauga un numar random de activitati in program
	void adauga_random(vector<Activitate> activitatile, int n);
	
	//un vector cu toate activitatile din program
	const vector<Activitate>& get_all_program();

	void valideaza_program(Activitate& a);
	//void stergere_dupa_tip(string tip);
};

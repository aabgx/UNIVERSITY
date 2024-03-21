#include "program.h"
using std::shuffle;
#include <exception>
#include <iostream>
using namespace std;

/*void Program::valideaza_program(Activitate& a) {
	vector<Activitate> activities = this->program;
	std::string titlu = a.get_titlu();
	if (std::any_of(activities.begin(), activities.end(), [&](Activitate& activitate) {return activitate.get_titlu() == titlu;}))
	{
		throw exception("Activitatea cu acest titlu exista deja in program!");
	}

}*/

void Program::adauga_program(const Activitate& a) {
	this->program.push_back(a);
}
void Program::goleste_program() {
	this->program.clear();
}

void Program::adauga_random(vector<Activitate> activitatile, int n) {
	shuffle(activitatile.begin(), activitatile.end(), std::default_random_engine(std::random_device{}())); //amesteca vectorul v
	size_t init_size = program.size();
	while (program.size() < n+init_size && activitatile.size() > 0) {
		program.push_back(activitatile.back());
		activitatile.pop_back();
	}
}
const vector<Activitate>& Program::get_all_program() {
	return this->program;
}
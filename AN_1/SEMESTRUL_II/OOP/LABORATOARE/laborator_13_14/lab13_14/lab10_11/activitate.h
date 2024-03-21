#pragma once
#include <string>
#include <iostream>
using std::string;

class Activitate {

private:
	string titlu;
	string descriere;
	string tip;
	int durata;
public:
	Activitate(const string titlu, const string descriere, const string tip, const int durata) : titlu{ titlu }, descriere{ descriere }, tip{ tip }, durata{ durata }{};
	Activitate() {};

	//constructor
	Activitate(const Activitate& a) :titlu{ a.titlu }, descriere{ a.descriere }, tip{ a.tip }, durata{ a.durata }{};

	string get_titlu();
	void set_titlu(const string& titlu_nou);

	string get_descriere();
	void set_descriere(const string& descriere_noua);

	string get_tip();
	void set_tip(const string& tip_nou);

	int get_durata();
	void set_durata(const int& durata_noua);

	bool eq(Activitate a);
	string str();


};
bool cmp_titlu(Activitate& a1, Activitate& a2);
bool cmp_descriere(Activitate& a1, Activitate& a2);
bool cmp_tip_durata(Activitate& a1, Activitate& a2);

void test_activitate();
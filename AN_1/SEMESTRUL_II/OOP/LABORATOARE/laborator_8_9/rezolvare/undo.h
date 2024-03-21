#pragma once
#include "activitate.h"
#include "repo.h"
#include <iostream>

using namespace std;

class ActiuneUndo {
public:
	virtual void doUndo() = 0; 
	virtual ~ActiuneUndo() = default;
};


class UndoAdauga :public ActiuneUndo {
private:
	Repository& repo;
	Activitate a_adaugata; // activitatea adaugata
public:

	UndoAdauga(Repository& r, Activitate d) : repo{ r }, a_adaugata{ d }{} //constructor
	void doUndo() {
		repo.sterge(a_adaugata);
	}
};

class UndoSterge :public ActiuneUndo {
private:
	Repository& repo; // Repository-ul disciplinelir
	Activitate a_stearsa; // Disciplina stearsa
public:
	/*
	Constructorul
	*/
	UndoSterge(Repository& r, Activitate d) : repo{ r }, a_stearsa{ d }{}

	void doUndo() {
		repo.adauga(a_stearsa);
	}
};

class UndoModifica :public ActiuneUndo {
private:
	Repository& repo; // Repository-ul disciplinelir
	Activitate a_inainte; // Disciplina de dinainte de stergere
	Activitate a_dupa; // Disciplina de dupa stergere
public:
	/*
	Constructorul
	*/
	UndoModifica(Repository& r, Activitate d1, Activitate d2) : repo{ r }, a_inainte{ d1 }, a_dupa{ d2 }{}

	void doUndo() {
		repo.cauta(a_dupa.get_titlu()) = a_inainte;
	}
};
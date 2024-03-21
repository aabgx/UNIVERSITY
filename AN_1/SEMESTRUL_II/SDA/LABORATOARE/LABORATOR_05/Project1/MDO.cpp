#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>
#include <limits.h>
#include <exception>
using namespace std;

#define NULL_TCHEIE 0

//theta(n*n), unde n e capacitatea vectorului de chei
MDO::MDO(Relatie r) {
	/* de adaugat */
	this->rel = r;
	this->size = 0; //numarul de valori adaugate (dimensiunea container-ului)
	this->cap = 10; //capacitatea maxima a containerului
	this->cap_valori = 5;
	stanga = new int[cap]; //vector de int-uri pentru subarborele stang a arborelui
	dreapta = new int[cap]; //vector de int-uri pentru subarborele drept a arborelui
	chei = new TCheie[cap]; //aloc memoriei vectorului de chei
	valori = new TValoare * [cap]; //aloc memorie vectorilor de valori
	for (int i = 0; i < cap; i++)
	{
		valori[i] = new TValoare[cap_valori];//aloc memorie fiecarui vector de valori

		stanga[i] = -999;
		dreapta[i] = -999;
		chei[i] = -999;

		for (int j = 0; j < cap_valori; j++) {
			valori[i][j] = -999;//initializam cu -999 fiecare element din vectorul de valori
		}
	}
	primLiber = 0; //prima pozitie pe care se poate adauga
	radacina = -999; //momentan nu avem nici radacina
}

//BC-theta(1)-nu trb redim
//WC-AC-theta(n)-daca trebuie redimensionat
//=>O(n)
void MDO::actualizeazaPrimLiber() {
	while (chei[primLiber] != -999 && primLiber != cap)
		primLiber++;

	//daca trebuie sa redimensionam 
	if (primLiber == cap) 
	{
		TCheie* cheiNou = new TCheie[2 * cap]; //alocam memorie dubla cheilor
		TValoare** valoriNou = new TValoare * [2 * cap]; //alocam memorie dubla valorilor
		int* stangaNou = new int[2 * cap];
		int* dreaptaNou = new int[2 * cap];

		//punem informatia din vechiul vector in noul vector
		for (int i = 0; i < cap; i++) 
		{
			cheiNou[i] = chei[i];
			valoriNou[i] = valori[i];
			stangaNou[i] = stanga[i];
			dreaptaNou[i] = dreapta[i];
			valoriNou[i] = new TValoare[cap_valori];
			for (int j = 0; j < cap_valori; j++) 
			{
				int aux = valori[i][j];
				valoriNou[i][j] = aux;
			}
		}

		//initializam a doua jumatate a noului vector
		for (int i = cap; i < 2 * cap; i++) 
		{
			valoriNou[i] = new TValoare[cap_valori];
			for (int j = 0; j < cap_valori; j++) {
				valoriNou[i][j] = -999;
			}
			stangaNou[i] = -999;
			dreaptaNou[i] = -999;
			cheiNou[i] = -999;
		}

		//stergem vechii vectori
		delete[] chei;
		delete[] dreapta;
		delete[] stanga;
		for (int i = 0; i < cap; i++)
			delete[] valori[i];
		delete[] valori;

		//actualizam
		chei = cheiNou;
		valori = valoriNou;
		for (int i = 0; i < 2 * cap; i++)
			valori[i] = valoriNou[i];

		dreapta = dreaptaNou;
		stanga = stangaNou;

		//dublez capacitatea
		cap *= 2;
	}

}

//overall complexity O(h) -h inaltimea arborelui
void MDO::adauga(TCheie c, TValoare v) {

	if (dim() == 0)//daca mdo-ul nu contine nicio cheie si nicio valoare initializez radacina
		radacina = 0;

	//caut daca mai exista cheia in MDO
	int curent = radacina;
	int parinte = -1;
	while (curent != -999 && chei[curent] != c) {
		parinte = curent;
		if (rel(c, chei[curent]) == true)
			curent = stanga[curent]; //o iau in subarborele stang
		else
			curent = dreapta[curent];
	}

	//daca am gasit-o
	if (curent != -999 && chei[curent] == c) {
		//adaug in vectorul de valori
		int primLiberValori = 0;
		while (curent != -999 && primLiberValori != cap_valori && valori[curent][primLiberValori] != -999) //merg pana la primul liber din vectorul de valori
			primLiberValori++;

		if (primLiberValori < cap_valori) {
			valori[curent][primLiberValori] = v;//ii dau valoarea
		}

		//daca trebuie redimensionat vectorul de valori
		else 
		{
			TValoare* valoriNou = new TValoare[2 * cap_valori];

			for (int i = 0; i < cap_valori; i++) {
				valoriNou[i] = valori[curent][i];
			}
			for (int i = cap_valori; i < 2 * cap_valori; i++) {
				valoriNou[i] = -999;
			}
			delete[] valori[curent];//stergem
			valori[curent] = valoriNou;
			cap_valori *= 2;

			valori[curent][primLiberValori] = v;//atribuim valoarea 
		}
		size++;//crestem dim mdo-ului
		return;
	}

	//daca nu exista cheia in MDO
	else 
	{
		//daca cheia respecta relatia cu cheia parinte
		if (rel(c, chei[parinte]) == true) 
		{
			//punem cheia si valoarea la adresa primLiber
			chei[primLiber] = c;
			valori[primLiber][0] = v;

			//daca primLiber nu e radacina, pozitia nou adaugata este fiul stang al cuiva
			if (primLiber != 0)
				stanga[parinte] = primLiber;
		}

		//daca cheia nu respecta relatia cu cheia parinte
		else 
		{
			//punem cheia si valoarea la adresa primLiber
			chei[primLiber] = c;
			valori[primLiber][0] = v;

			//daca primLiber nu e radacina, pozitia nou adaugata este fiul drept al cuiva
			if (primLiber != 0)
				dreapta[parinte] = primLiber;
		}
	}
	actualizeazaPrimLiber();
	size++;
}

//cauta o cheie si returneaza vectorul de valori asociate
//overall complexity O(h)
vector<TValoare> MDO::cauta(TCheie c) const {

	int aux;
	int nod_curent = radacina;
	vector<TValoare> values;
	while (true) 
	{
		aux = nod_curent;
		//daca an gasit cheia
		if (nod_curent != -999 && chei[nod_curent] == c) 
		{
			for (int i = 0; valori[nod_curent][i] != -999; i++)
				values.push_back(valori[nod_curent][i]); //adaug valorile cheii gasite in values si returnez vectorul
			return values;
		}

		//o iau in stanga daca cheia cautata respecta relatia cu cheia nod_curent (si exista stanga)
		if (nod_curent != -999 && stanga[nod_curent] != -999 && rel(c, chei[nod_curent]) == true)
			nod_curent = stanga[nod_curent];

		//o iau in dreapta daca cheia cautata NU respecta relatia cu cheia nod_curent (si exista dreapta)
		else if (nod_curent != -999 && dreapta[nod_curent] != -999 && rel(c, chei[nod_curent]) == false)
			nod_curent = dreapta[nod_curent];

		//daca nu s-a gasit cheia, adica n-am putut-o lua nici in stanga nici in dreapta
		if (aux == nod_curent)
			return vector<TValoare>();
		/*if (stanga[nod_curent] == -1 && dreapta[nod_curent] == -1 && chei[nod_curent] != c)
			return vector<TValoare>();*/

	}

}

//Overall Complexity - O(h)
bool MDO::sterge(TCheie c, TValoare v) {

	bool removed = false;
	TElem e;
	e.first = c;
	e.second = v;
	radacina = removeElem(radacina, e, removed);
	if (removed == true)
		this->size--;
	return removed;
}

//theta(1)
int MDO::dim() const {
	return this->size; //numarul de perechi (cheie, valoare) din MDO 
}

//theta(1)
bool MDO::vid() const {
	return this->size == 0;
}

IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

//theta(n)
MDO::~MDO() {
	delete[] chei;
	delete[] stanga;
	delete[] dreapta;
	for (int i = 0; i < cap; i++)
		delete[] valori[i];
	delete[] valori;
}

//O(h)
int MDO::getMax(int r) {
	int nod_curent = r;
	int maxim = r;
	int maxValue;

	while (chei[nod_curent] != -999) 
	{
		maxValue = chei[nod_curent];
		maxim = nod_curent;

		//o luam in dreapta, sa luam valoarea maxima
		nod_curent = dreapta[nod_curent];
	}
	return maxim;
}

//Overall Complexity - O(h)
int MDO::removeElem(int r, TElem e, bool& removed) {
	//daca arborele e gol
	if (radacina == -999)//daca arborele e gol
		return r;

	bool found = false;
	int j;
	int l = 0;

	//daca am gasit cheia
	if (e.first == chei[r]) 
	{
		for (j = 0; valori[r][j] != -999; j++) {
			
			//daca gaseste valoarea se opreste
			if (valori[r][j] == e.second) 
			{
				found = true;
				break;
			}
		}

		if (found == true && valori[r][1] != -999) 
		{
			//daca a gasit elementul, muta spre stanga toate valorile din vect de valori
			for (int i = j; i < cap_valori - 1; i++)
				valori[r][i] = valori[r][i + 1];

			//ultimului ii dau -999
			valori[r][cap_valori - 1] = -999;
			removed = true;
			return r;
		}

		//daca nu mai are valori cheia respectiva, trb stearsa de tot
		if (found == true && valori[r][1] == -999) 
		{
			removed = true;

			//daca lt sau rt nu exista, pur si simplu urcam nodul urm

			//daca nu exista subarbore stang
			if (stanga[r] == -999) 
			{
				int dreptul = dreapta[r];
				chei[r] = -999;
				valori[r][0] = -999;
				dreapta[r] = -999;
				return dreptul;
			}

			//nu exista subarbore drept
			else if (dreapta[r] = -999) 
			{
				int stangul = stanga[r];
				chei[r] = -999;
				valori[r][0] = -999;
				stanga[r] = -999;
				return stangul;
			}

			//daca trebe sa stergem un nod care are descendent stang si drept, cautam un element pe care sa il punem in locul lui
			else 
			{
				//merge pe subarborele din stanga ca sa gaseasca cel mai mare element
				//adica cel mai  mare elem sigur mai mic decat nodul din dreapta si mai mare decat toti din stanga
				int max = getMax(stanga[r]);
				chei[r] = chei[max];
				dreapta[r] = dreapta[max];
				stanga[r] = stanga[max];

				TElem e2;
				e2.first = chei[max];
				e2.second = valori[max][0];
				stanga[r] = removeElem(stanga[r], e2, removed);
			}
		}
	}
	
	//continuam cautarea spre stanga daca se respecta relatia
	else if (rel(e.first, chei[r]) == true && stanga[r] != -999)
		stanga[r] = removeElem(stanga[r], e, removed);

	//spre dreapta daca nu se respecta relatia
	else if (rel(e.first, chei[r]) == false && dreapta[r] != -999)
		dreapta[r] = removeElem(dreapta[r], e, removed);

	return r;
}


/*
int MDO::cheie_max() {
	int max = INT_MIN;

	if (this->vid())
		return NULL_TCHEIE;
	else
	{
		for (int i = 0; i < this->dim(); i++)
		{
			if (chei[i] > max) max = chei[i];
		}
		return max;
	}

}
*/

/*
subalg cheie_max(dict)
	nod_curent<-dict.radacina
	daca dict.vid = adevarat atunci
		returneaza nil
	altfel
		cat timp chei[nod_curent] != nil executa
			daca max<chei[nod_curent] atunci 
				max<-chei[nod_curent]
				nod_curent<-dreapta[nod_curent]
			sf daca
		sf cat timp
	sf daca
sf subalgoritm
*/

int MDO::cheie_max() {
	int max = INT_MIN;
	int nod_curent = radacina;

	if (this->vid())
		return NULL_TCHEIE;
	else
	{
		while (chei[nod_curent] != -999)
		{
			if (max < chei[nod_curent]) max = chei[nod_curent];
			nod_curent = dreapta[nod_curent];
		}
		return max;
	}

}
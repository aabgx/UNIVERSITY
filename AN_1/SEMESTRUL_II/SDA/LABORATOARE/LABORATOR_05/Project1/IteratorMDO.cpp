#include "IteratorMDO.h"
#include "MDO.h"

//theta(1)
IteratorMDO::IteratorMDO(const MDO& d) : dict(d) {
	curent = dict.radacina;
	curentValori = 0;
}

//O(h)
void IteratorMDO::prim() {
	//facem curent sa arate spre prima pozitie din MDO
	curent = dict.radacina;
	curentValori = 0;

	while (curent != -999) 
	{
		s.push(curent);
		curent = dict.stanga[curent]; //elementul cel mai din stanga
	}
	if (s.empty() == false) 
	{
		curent = s.top();
	}
	else
		curent = -999;
}

//O(h) unde h-inaltimea arborelui
void IteratorMDO::urmator() {

	if (!valid())
		throw std::exception();

	int nod = s.top();

	//daca mai sunt de parcurs elemente in vectorul de valori iterez acest vector mai departe
	if (dict.valori[nod][curentValori] != -999 && dict.cap_valori > curentValori) 
	{
		curent = nod;
		curentValori++;
		return;
	}

	//daca nu am elemente in vectorul de valori
	if (dict.valori[nod][curentValori] == -999)
	{
		s.pop();

		//daca are fii
		if (dict.dreapta[nod] != -999) 
		{	
			//merg pe subarborele din dreapta
			nod = dict.dreapta[nod];
			while (nod != -999) 
			{
				s.push(nod);

				//il iau dinspre stanga
				nod = dict.stanga[nod];
			}
		}
		if (s.empty() == false)
		{
			curent = s.top();
			s.pop();
			curentValori = -1;
		}
		else
			curent = -999;
	}

}

//theta(1)
bool IteratorMDO::valid() const {
	if (curent == -999 || dict.valori[curent][curentValori] == -999)
		return false;
	return true;
}

//theta(1)
TElem IteratorMDO::element() const {
	if (!valid()) {
		throw std::exception();
	}
	return pair <TCheie, TValoare>(dict.chei[curent], dict.valori[curent][curentValori]);
}

void IteratorMDO::IteratorDictionar(int k)
{
	if (curent > k)
	{
		throw std::exception();
	}

}


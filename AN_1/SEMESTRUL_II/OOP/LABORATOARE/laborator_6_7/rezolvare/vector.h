#pragma once
#define INIT_CAPACITY 5

template <class TElem>

class VectorDinamic {
private:
	TElem* elems;
	int n;
	int cp;
	void resize() {
		TElem* new_elems = new TElem[cp * 2];
		for (int i = 0; i < n; i++) {
			new_elems[i] = this->elems[i];
		}
		delete[] this->elems;
		this->elems = new_elems;
		this->cp = 2 * cp;
	}

public:

//constructorul
	VectorDinamic() {
		this->n = 0;
		this->cp = INIT_CAPACITY;
		this->elems = new TElem[cp];
	}
	
//destructor
	~VectorDinamic() {
		if (elems)
			delete[] elems;
	}

//constructor de copiere
	VectorDinamic(const VectorDinamic < TElem>& v) {
		n = v.n;
		cp = v.cp;
		elems = new TElem[cp];
		for (int i = 0; i < n; i++)
			this->elems[i] = v.elems[i];
	}

//dimensiune
	int size() {
		return n;
	}

//functia care reinitializeaza lista cu lista goala
	void clear() {
		if (n != 0) {
			delete elems;
			this->n = 0;
			this->cp = INIT_CAPACITY;
			this->elems = new TElem[cp];
		}
	}

//adauga un element la final
	void push_back(TElem el) {
		if (this->n == this->cp)
			resize();
		this->elems[n++] = el;
	}

//returneaza pozitia primului element
	int begin() {
		return 0;
	}

//sterge elementul de pe pozitia poz
	void erase(int poz) {
		for (int i = poz; i < n; i++)
			elems[i] = elems[i + 1];
		n--;
	}

//functia rescrie operatorul "=" pt aceasta clasa.
	void operator=(const VectorDinamic<TElem>& v) {
		this->n = v.n;
		this->cp = v.cp;
		delete[] this->elems;
		this->elems = new TElem[cp];
		for (int i = 0; i < n; i++)
			this->elems[i] = v.elems[i];
	}

//functia rescrie operatorul "[]" pt aceasta clasa
	TElem& operator[](int i) {
		return elems[i];
	}

};




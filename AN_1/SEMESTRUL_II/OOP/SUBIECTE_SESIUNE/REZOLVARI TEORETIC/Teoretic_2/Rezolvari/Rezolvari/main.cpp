#include <string>
#include <vector>
#include <algorithm>
#include <iostream>

using namespace std;

class Meniu {
private:
	int pret=0;
public:
	virtual string descriere() = 0;
	virtual int get_pret() {
		return pret;
	}
	virtual ~Meniu() = default;
};

class CuCafea : public Meniu {
private:
	Meniu* meniu;
public:
	CuCafea(Meniu* meniu) :meniu{ meniu } {}
	string descriere() override{
		return meniu->descriere() + " cu cafea";
	}
	int get_pret()override {
		return meniu->get_pret() + 5;
	}
	~CuCafea()override {
		delete meniu;
	}
};

class CuRacoritaore : public Meniu {
private:
	Meniu* meniu;
public:
	CuRacoritaore(Meniu* meniu) :meniu{ meniu } {}
	string descriere() override {
		return meniu->descriere() + " cu racoritaore";
	}
	int get_pret()override {
		return meniu->get_pret() + 4;
	}
	~CuRacoritaore()override {
		delete meniu;
	}

};
 
class MicDejun :public Meniu {
private:
	string denumire;
public:
	MicDejun(string denumire):denumire{denumire}{}
	string descriere() override {
		return denumire;
	}
	int get_pret()override {
		if (denumire == "Ochiuri") return 10;
		return 15;
	}
	~MicDejun() = default;
};

vector<Meniu*> get_list() {
	vector<Meniu*> lst;
	//Omuleta cu racoritoare si cafea
	Meniu* m1 = new MicDejun{ "Omuleta" };
	m1 = new CuRacoritaore{ m1 };
	m1 = new CuCafea{ m1 };
	lst.push_back(m1);

	//Ochiuri si cafea
	Meniu* m2 = new MicDejun{ "Ochiuri" };
	m2 = new CuCafea(m2);
	lst.push_back(m2);

	//meniu cu Omuleta
	Meniu* m3 = new MicDejun{ "Omuleta" };
	lst.push_back(m3);
	
	return lst;
}

template<typename Nume> class Measurement {
private:
	int masuratoare;
public:
	Measurement(int masuratoare):masuratoare{ masuratoare }{}
	Measurement& operator+(int numar) {
		masuratoare = masuratoare + numar;
		return *this;
	}
	bool operator<(Measurement& m2) {
		return masuratoare < m2.masuratoare;
	}

	int value() const {
		return masuratoare;
	}
};

int main() {
	/*
	auto lst = get_list();
	sort(lst.begin(), lst.end(), [](Meniu* m1, Meniu* m2) {
		return m1->get_pret() > m2->get_pret();
		});
	for (auto& el : lst) {
		cout << el->descriere() << ' ' << el->get_pret() << '\n';
		delete el;
	}
	*/
	//creaza un vector de masuratori cu valorile (10,2,3)
	std::vector<Measurement<int>> v{ 10,2,3 };
	v[2] + 3 + 2; //aduna la masuratoarea 3 valuarea 5
	std::sort(v.begin(), v.end()); //sorteaza masuratorile
	//tipareste masuratorile (in acest caz: 2,8,10)
	for (const auto& m : v) std::cout << m.value() << ",";
	return 0;
}
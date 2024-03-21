#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;


class Transformer {
public:
	virtual void transform(vector<int>& nrs) = 0;
	virtual ~Transformer() = default;
};

class Adder : public Transformer {
private:
	int cat;
public:
	Adder(int cat): cat{cat}{}
	virtual void transform(vector<int>& nrs) override { for (auto& el : nrs) { 
		el += cat; 
	} }
	~Adder() override= default;
};

class Swaper : public Transformer {
public:
	virtual void transform(vector<int>& nrs)override { for (int i = 1; i < nrs.size(); i = i + 2) swap(nrs[i], nrs[i - 1]); }
	~Swaper() override = default;
};

class Nuller : public Adder {
public:
	Nuller(int cat) :Adder{ cat } {
	}
	virtual void transform(vector<int>& nrs)override{
		Adder::transform(nrs);
		for (auto& el : nrs)
		{
			if (el >=10) el = 0;
			
		}
	}
	~Nuller() override = default;

};

class Composer : public Transformer {
private:
	Transformer* t1, * t2;
public:
	Composer(Transformer* t1, Transformer* t2): t1{t1},t2{t2}{}
	virtual void transform(vector<int>& nrs) override {
		t1->transform(nrs);
		t2->transform(nrs);
	}
	~Composer() override {
		delete t1;
		delete t2;
	}
	
};

class Numbers:public Transformer {
private:
	Transformer* t;
	vector<int>nrs;
public:
	void add(int c) { nrs.push_back(c); }

	virtual void transform(vector<int>& lst) override{
		t->transform(lst);
	}

	vector<int> ordoneaza() {
		sort(nrs.begin(), nrs.end(), [](int a, int b) {return a > b; });
		return nrs;
	}

	Numbers(Transformer* t) :t{ t }{}
	~Numbers() override {
		delete t;
	}
};

Numbers* f() {
	return new Numbers{ new Composer{ new Nuller{9},new Composer{new Swaper{},new Adder{3}} } };
	
}

class Examen {
private:
	string descriere, ora;
public:
	Examen(string descriere,string ora):descriere{ descriere },ora{ora}{}
	const Examen(const Examen& e):descriere{e.descriere},ora{e.ora}{}

	string getDescriere() {
		return descriere+" "+ora;
	}
};

template<typename ElemType> class ToDo {
private:
	vector<ElemType>examene;
public:
	ToDo<ElemType>& operator<<(ElemType e){
		examene.push_back(e);
		return *this;
	}
	void printToDoList(ostream& os) {
		os << "De facut:";
		for (auto& el : examene) {
			os << el.getDescriere() << ';';
		}
	}
};

void todolist() {
	ToDo<Examen> todo;
	Examen oop{ "oop scris","8:00" };
	todo << oop << Examen{ "oop practic", "11:00" }; //Adauga 2 examene la todo
	std::cout << oop.getDescriere()<<endl; //tipareste la consola: oop scris ora 8:00
	//itereaza elementele adaugate si tipareste la consola lista de activitati
	//in acest caz tipareste: De facut:oop scris ora 8:00;oop practic ora 11:00
	todo.printToDoList(cout);
}

int main() {
	/*
	
	auto nmbr = f();
	nmbr->add(1);
	nmbr->add(5);
	nmbr->add(4);
	nmbr->add(10);
	nmbr->add(12);
	nmbr->add(15);
	vector<int> lst = nmbr->ordoneaza();
	for (auto& el : lst) {
		cout << el << ' ';
	}
	cout << endl;
	nmbr->transform(lst);
	for (auto& el : lst) {
		cout << el << ' ';
	}
	*/
	todolist();
	return 0;
}
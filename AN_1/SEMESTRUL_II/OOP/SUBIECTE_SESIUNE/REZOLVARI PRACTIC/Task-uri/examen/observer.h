#pragma once
#include <vector>

using namespace std;

class Observer {
public:
	virtual void update() = 0;
};

class Observable {
	vector<Observer*>observatori;
public:

	void notify() {
		for (auto& el : observatori)
			el->update();
	}

	void add_observer(Observer* obs) {
		observatori.push_back(obs);
	}
};

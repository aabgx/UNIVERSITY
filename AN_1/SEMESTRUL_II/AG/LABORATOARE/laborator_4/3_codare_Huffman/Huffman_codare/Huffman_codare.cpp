#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <queue>
using namespace std;
ifstream f("in.txt");
ofstream g("out.txt");
string s, cod[128];
int fr[128];


struct Nod {
	int lt, rt, nr_ap;
	char c;
}arbore[255];


void codare(int a, string c)
{
	Nod& nod = arbore[a];
	if (nod.lt == -1 && nod.rt == -1)
		cod[(int)nod.c] = c;
	else
	{
		codare(nod.lt, c + '0');
		codare(nod.rt, c + '1');
	}
}


int main()
{
	int n = 0, q = 0;
	auto cmp = [](int lhs, int rhs)
	{
		return arbore[lhs].nr_ap > arbore[rhs].nr_ap || (arbore[lhs].nr_ap == arbore[rhs].nr_ap && arbore[lhs].c > arbore[rhs].c);
	};
	priority_queue<int, vector<int>, decltype(cmp)> pq(cmp);
	//citim si identificam caracterele si numaram frecventa
	getline(f, s);
	for (auto c : s)
	{
		if (fr[(int)c] == 0) n++;
		fr[(int)c]++;
	}
	g << n << endl;
	//punem toate nodurile existente intr-o coada cu prioritati
	for (int c = 0; c < 128; c++)
	{
		if (fr[c] != 0)
		{
			g << (char)c << " " << fr[c] << endl;
			Nod& nod = arbore[q];
			nod.c = (char)c;
			nod.lt = nod.rt = -1;
			nod.nr_ap = fr[c];
			pq.push(q); q++;
		}
	}
	//construim arborele
	while (pq.size() >= 2)
	{
		auto lt = pq.top(); pq.pop();
		auto rt = pq.top(); pq.pop();

		Nod& nod = arbore[q];
		nod.nr_ap = arbore[lt].nr_ap + arbore[rt].nr_ap;
		nod.c = min(arbore[lt].c, arbore[rt].c);
		nod.lt = lt;
		nod.rt = rt;
		pq.push(q); q++;
	}
	//parcurgem arborele si generam codul fiecarui caracter lipind la
	//vectorul de string-uri 0 si 1
	codare(q - 1, "");
	for (auto c : s)
		g << cod[(int)c];
	f.close();
	g.close();
	return 0;
}
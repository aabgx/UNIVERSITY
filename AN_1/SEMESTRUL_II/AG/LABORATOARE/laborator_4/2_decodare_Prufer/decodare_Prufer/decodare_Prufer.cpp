#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <queue>
using namespace std;
ifstream f("in.txt");
ofstream g("out.txt");
vector <int> parinte;
queue <int> coada;
map <int, int> nu_exista;
map <int, int> nrAparitii;

int main()
{
	int n, m, x, y;
	f >> m;
	n = m + 1;
	parinte.resize(n);
	for (int i = 0; i < n; i++)
	{
		parinte[i] = -1;
		nu_exista[i] = 0;
	}
	for (int i = 0; i < m; i++)
	{
		f >> x;
		coada.push(x);
		nu_exista.erase(x);
		nrAparitii[x]++;
	}
	for (int i = 0; i < m; i++)
	{
		x = coada.front();
		coada.pop();
		y = (*nu_exista.begin()).first;
		if (nrAparitii[x] == 1)
		{
			nrAparitii.erase(x);
			nu_exista[x] = 1;
		}
		else
			nrAparitii[x]--;
		nrAparitii[y]++;
		nu_exista.erase(y);
		parinte[y] = x;
	}
	g << n << endl;
	for (int i = 0; i < n; i++)
		g << parinte[i] << " ";
	f.close();
	g.close();
	return 0;
}
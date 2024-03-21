#include <iostream>
#include <fstream>
#include <vector>
#include <stack>
using namespace std;
ifstream f("in.txt");
ofstream g("out.txt");
const int dim = 1e5 + 7;
vector <pair<int, int>> graf[dim];
int viz[5 * dim];
stack <int> stiva;
int v, m, k, urm;


void Euler()
{
	int nod, muchie, y;
	stiva.push(0);
	while (!stiva.empty())
	{
		nod = stiva.top();
		if (graf[nod].size() == 0)
		{
			stiva.pop();
			g << nod << " ";
		}
		else
		{
			y = graf[nod].back().first;
			muchie = graf[nod].back().second;
			graf[nod].pop_back();
			if (viz[muchie] == 0)
			{
				viz[muchie] = 1;
				stiva.push(y);
			}
		}
	}
}


int main()
{
	int x, y;
	f >> v >> m;
	for (int i = 0; i < m; i++)
	{
		f >> x >> y;
		graf[x].push_back({ y,i });
		graf[y].push_back({ x,i });
	}
	Euler();
	f.close();
	g.close();
	return 0;
}
#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <climits>
using namespace std;
ifstream f("in.txt");
ofstream g("out.txt");


bool BFS(vector<vector<int>>graf, int s, int d, vector<int>& parinte)
{
	vector<bool> viz;
	viz.resize(graf.size());
	parinte[s] = -1; viz[s] = true;
	queue<int> coada;
	coada.push(s);
	while (!coada.empty())
	{
		int x = coada.front();
		coada.pop();
		for (int i = 0; i < graf[x].size(); i++)
		{
			if (graf[x][i] != 0 && viz[i] == false)
			{
				parinte[i] = x;
				viz[i] = true;
				coada.push(i);
				if (i == d) return true;
			}
		}
	}
	return false;
}


int FordFulkerson(vector<vector<int>>graf, int s, int d)
{
	vector<int> parinte;
	parinte.resize(graf.size());
	int flux_maxim = 0;
	while (BFS(graf, s, d, parinte))
	{
		int flux_curent = INT_MAX;
		for (int u = d; u != s; u = parinte[u])
		{
			int v = parinte[u];
			flux_curent = min(flux_curent, graf[v][u]);
		}
		for (int u = d; u != s; u = parinte[u])
		{
			int v = parinte[u];
			graf[v][u] = graf[v][u] - flux_curent;
			graf[u][v] = graf[u][v] + flux_curent;
		}
		flux_maxim = flux_maxim + flux_curent;
	}
	return flux_maxim;
}


int main()
{
	int n, m, x, y, z;
	vector<vector<int>> graf;
	f >> n >> m;
	for (int i = 0; i < n; i++)
	{
		vector<int> linie(n);
		graf.push_back(linie);
	}
	for (int i = 0; i < m; i++)
	{
		f >> x >> y >> z;
		graf[x][y] = z;
	}
	g << FordFulkerson(graf, 0, n - 1);

}
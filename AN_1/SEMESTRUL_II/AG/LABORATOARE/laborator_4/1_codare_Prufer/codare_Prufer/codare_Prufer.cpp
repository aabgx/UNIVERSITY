#include <iostream>
#include <fstream>
#include <vector>
#include <set>
using namespace std;
ifstream f("in.txt");
ofstream g("out.txt");

int n;
vector <int> parinte, nr_succesori, k;
set <int> frunze;

int main()
{
	f >> n;
	nr_succesori.resize(n + 1);
	parinte.resize(n + 1);
	for (int i = 0; i < n; i++)
	{
		f >> parinte[i];
		if (parinte[i] != -1)
			nr_succesori[parinte[i]]++;
	}
	for (int i = 0; i < n; i++)
		if (nr_succesori[i] == 0)
			frunze.insert(i);
	while (frunze.size() > 0)
	{
		int f = *frunze.begin();
		frunze.erase(frunze.begin());
		if (parinte[f] != -1)
		{
			int pr = parinte[f];
			k.push_back(pr);
			nr_succesori[pr]--;
			if (nr_succesori[pr] == 0)
				frunze.insert(parinte[f]);
		}

	}
	g << k.size() << endl;
	for (int i = 0; i < k.size(); i++)
		g << k[i] << " ";
	g << endl;
	f.close();
	g.close();
	return 0;
}
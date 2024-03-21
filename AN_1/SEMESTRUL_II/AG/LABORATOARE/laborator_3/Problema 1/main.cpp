#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#define INF 0x3f3f3f3f

using namespace std;

int n,m, s;
vector< pair<int, int> >graf[50005];
vector <int> v;
queue <int> coada;
int d[50005];
int viz[50005];
int esteincoada[50005];

bool bellmanford(int s)
{
    for(int i=0;i<n;i++)
    {
        viz[i]=0;
        esteincoada[i]=0;
        d[i]=INF;
    }
    d[s] = 0;
    coada.push(s);
    esteincoada[s] = 1;

    while(!coada.empty())
    {
        int nodcurent = coada.front();
        viz[nodcurent]++;
        if(viz[nodcurent] >= n)
            return false;
        coada.pop();
        esteincoada[nodcurent] = 0;

        for(size_t i=0;i<graf[nodcurent].size();i++)
        {
            int vecin = graf[nodcurent][i].first;
            int cost = graf[nodcurent][i].second;
            if(d[nodcurent]+cost < d[vecin])
            {
                d[vecin] = d[nodcurent]+cost;
                if(!esteincoada[vecin])
                    coada.push(vecin);
            }
        }
    }
    return true;
}

int main(int argc, char* argv[])
{
    ifstream in(argv[1]);
    ofstream out(argv[2]);

    in>>n>>m>>s;
    //cout << n << " " << m << " " << s << '\n';
    for(int i=1;i<=m;i++)
    {
        int x,y,c;
        in>>x>>y>>c;
        graf[x].push_back(make_pair(y,c));
    }
    if(bellmanford(s))
    {
        for(int i=0;i<n;i++)
            if(d[i]==INF)
                out << "INF ";
            else
                out<<d[i]<<" ";
    }
    else
      out<<"INF";


    in.close();
    out.close();
    return 0;
}

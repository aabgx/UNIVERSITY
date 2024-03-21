#include <iostream>

using namespace std;

void parcurgere_latime(int m[20][20],int v[20], int n)
{   int ns,c[20],prim,ultim;
    ns=1; ///incepem parcurgerea cu nodul 1
    v[ns]=1; ///v este vectorul nodurilor vizitate
    prim=ultim=1;
    c[ultim]=ns;
    while(prim<=ultim)
    {
        for(int i=1;i<=n;i++)
            if(m[c[prim]][i]==1)
                if(v[i]==0)
                {
                    ultim++;
                    c[ultim]=i;
                    v[i]=1;
                }
        prim++;
    }
}
///functia pune pe rand in coada toate legaturile primului nod si apoi pe rand legaturile nodurilor adiacente
/// daca la final in coada sunnt prezente toate nodurile, graful e conex (adica toate nodurile au fost vizitate)

int main()
{   int a,b,m[20][20],n,v[20]={0},ok=1;

    ///CITIRE MATRICE
    cout<<"Introduceti numarul de noduri: ";
    cin>>n;
    cout<<"Cand doriti sa va opriti, introduceti 0."<<endl;
    while(a>0)
    {
        cin>>a;
        if(a==0) break;
        cin>>b;
        m[a][b]=1;
        m[b][a]=1;
    }

    parcurgere_latime(m,v,n);
    ///acum in v avem toate nodurile care au drum spre nodul 1
    ///daca toate nodurile au drum spre nodul 1, graful e conex, altfel nu e conex
    for(int i=1;i<=n;i++)
        if(v[i]==0) ok=0;

    if (ok==0)
        cout<<"NU ESTE CONEX!";
    else
        cout<<"ESTE CONEX!";
    return 0;
}

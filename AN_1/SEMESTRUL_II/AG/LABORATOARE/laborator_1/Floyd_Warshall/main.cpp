#include <iostream>
#include <limits.h>

using namespace std;
void afisare(int m[10][10],int n)
{   int i,j,inf=INT_MAX;
    for(i=1;i<=n;i++)
    {for(j=1;j<=n;j++)
    {   if(m[i][j] == inf) cout<<"inf";
        else
            cout<<m[i][j]<<" ";
    }
    cout<<endl;
    }
}
int main()
{   int n,m[10][10]={0},i,j,k,inf=INT_MAX;

    ///CITIRE MATRICE
    cout<<"Introduceti numarul de noduri: ";
    cin>>n;
    cout<<"Cand doriti sa va opriti, introduceti 0."<<endl;
    i=1;
    while(i>0)
    {
        cin>>i;
        if(i==0) break;
        cin>>j;
        m[i][j]=1;
        m[j][i]=1;
    }

    for(i=1;i<=n;i++)
    for(j=1;j<=n;j++)
    {
        if(m[i][j]==0 && i!=j)
            m[i][j]=inf;
    }

    cout<<endl<<"Matricea distantelor este:"<<endl;
    for (k =1;k<=n;k++)
    for (i=1;i<=n;i++)
    for (j=1;j<=n;j++)
    {   if(m[i][k]!=inf && m[k][j]!=inf)
        {
            if(m[i][k]+m[k][j]<m[i][j])
                m[i][j] = m[i][k] + m[k][j];
        }
    }

    afisare(m,n);
    return 0;
}

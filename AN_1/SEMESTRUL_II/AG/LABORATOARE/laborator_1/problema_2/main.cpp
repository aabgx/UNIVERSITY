#include <iostream>

using namespace std;
void regular(int m[20][20],int n)
{   int ok=0,cnt,grad,v[20]={0};
    for(int i=1;i<=n;i++)
    {   cnt=0;
        for(int j=1;j<=n;j++)
            if(m[i][j]==1) cnt++;
        v[i]=cnt;
    }
    ok=1;
    for(int i=1;i<n;i++)
        if(v[i]!=v[i+1]) ok=0;
    if(ok) cout<<endl<<"ESTE REGULAR";
    else cout<<endl<<"NU ESTE REGULAR";

}


int main()
{
    int a,b,m[20][20],n,v[20]={0},ok=1;

    ///CITIRE MATRICE
    cout<<"Introduceti numarul de noduri: ";
    cin>>n;
    cout<<"Cand doriti sa va opriti, introduceti 0."<<endl;
    a=1;
    while(a>0)
    {
        cin>>a;
        if(a==0) break;
        cin>>b;
        m[a][b]=1;
        m[b][a]=1;
    }

    cout<<endl<<"Nodurile izolate sunt:"<<endl;
    for(int i=1;i<=n;i++)
    {
        ok=0;
        for(int j=1;j<=n;j++)
            if(m[i][j]==1) ok=1;

        if(ok==0)
            cout<<i<<" ";
    }

    regular(m,n);


    return 0;
}

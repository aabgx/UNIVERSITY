#include <iostream>
#include <fstream>

using namespace std;
ifstream fin("graf.in");

void lista_to_incidenta(int m[20][20],int n)
{   int cnt=1,in[20][20]={0};
    for(int i=1;i<n;i++)
    for(int j=i+1;j<=n;j++)
    {
        if(m[i][j]==1)
        {
            in[i][cnt]=1;
            in[j][cnt]=1;
            cnt++;
        }
    }
    cout<<endl<<"Matricea de incidenta obtinuta din lista de adiacenta este:"<<endl;
    cnt--;
    for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=cnt;j++)
        cout<<in[i][j]<<" ";
        cout<<endl;
    }
}

void incidenta_to_lista(int in[20][20],int cnt,int n)
{   int inceput,sfarsit,ok,m[20][20]={0};
    for(int j=1;j<=cnt;j++)
    {ok=0;
        for(int i=1;i<=n;i++)
    {
        if(in[i][j]==1 && ok==0)
        {
            inceput=i;
            ok=1;
        }
        if(in[i][j]==1 && ok==1)
        {
            sfarsit=i;
        }
    }
    m[inceput][sfarsit]=1;
    m[sfarsit][inceput]=1;
    }
    cout<<endl<<"Lista de adiacenta obtinuta din matricea de incidenta este:"<<endl;
    for(int i=1;i<=n;i++)
    {   cout<<i<<": ";
        for(int j=1;j<=n;j++)
        {
         if(m[i][j]==1)
                cout<<j<<" ";
        }
        cout<<endl;
    }
}

void matrice_to_muchii(int m[20][20],int n)
{   cout<<endl<<"lista de muchii obtinuta din matricea de adiacenta este:"<<endl;
    for(int i=1;i<=n;i++)
    for(int j=i+1;j<=n;j++)
    {
        if(m[i][j]==1)
            cout<<i<<" "<<j<<endl;
    }
}

void afisare_reprezentari(int m[20][20],int in[20][20],int cnt,int n)
{
    cout<<"Matricea de adiacenta este:"<<endl;
    for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=n;j++)
            cout<<m[i][j]<<" ";

        cout<<endl;
    }
    cout<<endl<<"Lista de adiacenta este:"<<endl; ///lista de adiacenta ar fi salvata intr-o matrice identica cu matricea de adiacenta
    for(int i=1;i<=n;i++)
    {   cout<<i<<": ";
        for(int j=1;j<=n;j++)
        {
         if(m[i][j]==1)
                cout<<j<<" ";
        }
        cout<<endl;
    }
    cout<<endl<<"Matricea de incidenta este:"<<endl;
    cnt--;
    for(int i=1;i<=n;i++)
    {
        for(int j=1;j<=cnt;j++)
        cout<<in[i][j]<<" ";
        cout<<endl;
    }
}
int main()
{   int x,y,n,m[20][20]={0},in[20][20]={0},cnt=1;
    fin>>n;
    while(fin>>x>>y)
       {
           m[x][y]=1;
           m[y][x]=1;

           in[x][cnt]=1;
           in[y][cnt]=1;
           cnt++; ///numarul de muchii
       }


    afisare_reprezentari(m,in,cnt,n);
    lista_to_incidenta(m,n);
    incidenta_to_lista(in,cnt,n);
    matrice_to_muchii(m,n);
    return 0;
}

#include<iostream>
#include<limits.h>
using namespace std;

int distanta[6];

int distante_minime(int distanta[], bool vizitat[])
{   int minim=INT_MAX,ind;

    for(int i=0;i<6;i++)
    {
        if(vizitat[i]==false && distanta[i]<=minim)
        {
            minim=distanta[i];
            ind=i;
        }
    }
    return ind;
}
void dijkstra(int graf[6][6],int radacina,int sfarsit)
{
    int parinte[6],i;
    bool vizitat[6];


    for(int k = 0; k<6; k++)
    {
        parinte[k] = -1;
        distanta[k] = INT_MAX;
        vizitat[k] = false;
    }

    parinte[radacina]=0;

    distanta[radacina] = 0;
    for(i = 0; i<6; i++)
    {   int m=distante_minime(distanta,vizitat);
        if(m==sfarsit) break;
        vizitat[m]=true;
        for(int k = 0; k<6; k++)
            if(!vizitat[k] && graf[m][k] && distanta[m]!=INT_MAX && distanta[m]+graf[m][k]<distanta[k])
                {distanta[k]=distanta[m]+graf[m][k];
                parinte[k]=m;}
    }
//AFISARE DRUM
    if(distanta[sfarsit] == INT_MAX)
        cout<<"Nu exista un drum intre aceste noduri.";
    else
    {   cout<<sfarsit<<" ";
        while(parinte[sfarsit] != 0)
        {   sfarsit = parinte[sfarsit];
            cout<<sfarsit<< " ";
        }
    }

}

int main()
{
    int graf[6][6]=
    {{0, 1, 2, 0, 0, 2},
    {1, 0, 0, 5, 1, 0},
    {0, 0, 0, 0, 2, 0},
    {0, 0, 0, 0, 0, 0},
    {0, 0, 0, 6, 0, 0},
    {1, 0, 0, 0, 4, 0}};
    dijkstra(graf,0,4);
    return 0;
}

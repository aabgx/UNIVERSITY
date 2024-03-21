//MODIFICARE SURSA PENTRU IMPLEMENTARE 0
/*
                  __===*@@@ @@@@@ @@@@@
                 /       ________________________________________ ___ __ _ _
          ______|^|_    | ***                                    |___ ___ __
         /|/~~~~~~~|    |  ***                                   |____ __ _ __
        / |    {_) |    |   ***  My Source(Leo)                  |__ ___ _ __
   ____/__|)  /~~| |@@@@|    ************************************|____ ___ _
  / _____ ~~~~~~~~~|@@@@|     ***********************************|___ __ _ __
 | /     \\      --|    |_/~~~~~\_______________________/~~~~~\__|_____ __ _ _
 |/  ***  \\       |---~|/  ***  \_____________________/  ***  \_|___ _ __ _ _
 `  *****  ~~~~=====       *****                         *****
     ***                    ***                           ***
------------------------------------------------------------------------------
*/
#include <bits/stdc++.h>
#define INF 0x3f3f3f3f

using namespace std;

//typedef int cost;
//typedef int muchie;
using namespace std;

//string fisier = "johnson2";

string intrare;
string iesire;

const int CMAX = 5e4+15;


struct triplet{
    int x , y , c;
};

int muchie , cost , n , m;
int h[CMAX];
int D[CMAX];
int matrice_D[CMAX/5][CMAX/5];
int viz[CMAX];
const long long int oo = 1000*50000;
vector < pair < int , int > > graf[CMAX];
vector < triplet > muchii;

ifstream fin(intrare);
ofstream fout(iesire);
//ifstream fin(fisier+".in");
//ofstream fout(fisier+".out");

struct comp{
    bool operator()(int x, int y){
        return D[x] > D[y];
    }
};

queue < int > coada;
priority_queue < int , vector < int >, comp > Q;

void citire()
{
    int x , y , c;
    fin >> n >> m;
    for(int i=1;i<=m;i++)
    {
        fin >> x >> y >> c;
        muchii.push_back({x,y,c});
        graf[x].push_back({y,c});
    }
    //sort(muchii.begin(),muchii.end());
    //cout << "marime: " << n << '\n';
    for(int i=0;i<muchii.size();i++)
       cout << muchii[i].x << " " << muchii[i].y << " " << muchii[i].c << '\n';

}

void afisare(vector < pair < int , int > > graf[CMAX]){
    for(int i=0;i<=n;i++)
    {
        cout << "Nodul: " << i << " : ";
        for(int j=0;j<graf[i].size();j++)
        {
            fout << i << " " << graf[i][j].first << " " << graf[i][j].second << '\n';
            cout << "(" <<graf[i][j].first << "," << graf[i][j].second << ") ";
        }
        cout << '\n';
    }
}

bool BellmanFord(vector<pair<int,int>>graf[CMAX],int start){
    //cout << "marime noua: " << n << '\n';
    for(int i=0;i<=n;i++)
    {
        viz[i] = 0;
        D[i] = oo;
    }
    D[start] = 0;
    coada.push(start);
    while(!coada.empty()){
        int nodcurent = coada.front();
        viz[nodcurent]++;
        coada.pop();
        if(viz[nodcurent]>=(n+1)){
            return false;
        }
        for(int i=0;i<graf[nodcurent].size();i++)
        {
            int vecin = graf[nodcurent][i].first;
            int cost =  graf[nodcurent][i].second;
            if(D[nodcurent]+cost<D[vecin]){
                D[vecin] = D[nodcurent] + cost;
                coada.push(vecin);
            }
        }
    }
    return true;
}

void Dijkstra(vector<pair<int,int>> graf[CMAX], int start)
{
    for(int i=0;i<=n;i++)
        D[i] = oo;
    //viz[start] = 1;
    D[start] = 0;
    Q.push(start);
    while(!Q.empty()){
        int nodcurent = Q.top();
        //cout << "nodcurent: " << nodcurent << '\n';
        Q.pop();
        viz[nodcurent] = 0;
        for(int i=0;i<graf[nodcurent].size();i++)
        {
            int cost, vecin;
            vecin = graf[nodcurent][i].first;
            cost  = graf[nodcurent][i].second;
            //cout << "vecini: " << vecin << " ";
            //cout << "cost: " << cost << " ";
            if(D[nodcurent] + cost < D[vecin]){
                //cout << "intrat";
                D[vecin] = D[nodcurent] + cost;
                if(viz[vecin]==0){
                    //cout << "bagat in coada";
                    Q.push(vecin);
                    viz[vecin] = 1;
                }
            }
            //cout << " |";
        }
        //cout << '\n';
    }
}

int reponderare(int drum, int val_a , int val_b){
    return drum + val_a - val_b;
}

int reponderare_inversa(int drum, int val_a , int val_b){
    return drum - val_a + val_b;
}

/*
void BFS(int start)
{
    queue < int > quada;
    bool vizitat[CMAX];
    quada.push(start);
    while(!quada.empty()){
        int nodcurent = quada.front();
        quada.pop();
    }
}
*/


bool Johnson(){
    //Adaugam nodul 0 la graful nostru
    vector < pair < int , int > > graf_modif[CMAX];
    int h[CMAX];
    for(int i=1;i<=n;i++)
    {
        //Adaugam nodul 0
        graf[0].push_back({i,0});
    }
    if(BellmanFord(graf,0)==false){
        return false;
    }
    else{
        for(int i=1;i<=n;i++)
            h[i] = D[i];
        for(int i=0;i<muchii.size();i++){
            //reponderam graful cu ce am gasit dupa executia algoritmului Bellman Ford
            graf_modif[muchii[i].x].push_back({muchii[i].y,reponderare(muchii[i].c,D[muchii[i].x],D[muchii[i].y])});
        }

        /*
        cout << "Graf reponderat: \n";
        afisare(graf_modif);
        */

        /*
        cout << "h = ";
        for(int i=1;i<=n;i++)
            cout << h[i] << " ";
        cout << '\n';
        */

        for(int j=1;j<=n;j++)
        {
            memset(viz,0,CMAX);
            Dijkstra(graf_modif,j);

            /*
            cout << "Pentru : " << j << " avem: \n";
            //memset(D,0,CMAX);
            for(int z=1;z<=n;z++)
                cout << D[z] << " ";
            cout << '\n';
            */

            //cout << "Pentru nodul : " << j << " avem urmatoarele valori: ";
            for(int z=1;z<=n;z++){
                //facem repondnerarea inversa
                matrice_D[j][z] = reponderare_inversa(D[z],h[j],h[z]);
                //cout << matrice_D[j][z] << " ";
            }
            //cout << '\n';

        }

    }
    afisare(graf_modif);
    return true;
    /*
    for(int i=1;i<=n;i++)
        cout << D[i] << " ";
    cout << '\n';
    */
}

bool Johnson2(){
    //MODIFICARE PENTRU NODUL N+1 SI NODUL 0
    vector < pair < int , int > > graf_modif[CMAX];
    int h[CMAX];
    for(int i=0;i<n;i++)
    {
        //Adaugam nodul 0
        graf[n].push_back({i,0});
    }
    /*
    cout << "afisarea grafului dupa adaugarea noului nod" << '\n';
    afisare(graf);
    */
    if(BellmanFord(graf,n)==false){
        return false;
    }
    else{
        for(int i=0;i<n;i++){
            h[i] = D[i];
            cout << h[i] << " ";
        }
        cout << '\n';
        for(int i=0;i<muchii.size();i++){
            //reponderam graful cu ce am gasit dupa executia algoritmului Bellman Ford
            graf_modif[muchii[i].x].push_back({muchii[i].y,reponderare(muchii[i].c,D[muchii[i].x],D[muchii[i].y])});
        }

        /*
        cout << "Graf reponderat: \n";
        afisare(graf_modif);
        */

        /*
        cout << "h = ";
        for(int i=1;i<=n;i++)
            cout << h[i] << " ";
        cout << '\n';
        */

        for(int j=0;j<n;j++)
        {
            //aici aplicam dijkstra pentru toate nodurile din graf incepannd de la 0..n
            memset(viz,0,CMAX);
            Dijkstra(graf_modif,j);


            cout << "Pentru : " << j << " avem: \n";
            //memset(D,0,CMAX);
            for(int z=0;z<n;z++)
                cout << D[z] << " ";
            cout << '\n';


            //cout << "Pentru nodul : " << j << " avem urmatoarele valori: ";
            for(int z=0;z<n;z++){
                //aici facem matricea costurilor
                //facem repondnerarea inversa
                matrice_D[j][z] = reponderare_inversa(D[z],h[j],h[z]);
                //cout << matrice_D[j][z] << " ";
            }
            //cout << '\n';

        }

    }
    cout << "graful modificat: " << '\n';
    afisare(graf_modif);
    return true;
    /*
    for(int i=1;i<=n;i++)
        cout << D[i] << " ";
    cout << '\n';
    */
}
int main(int argc, char* argv[]){
    citire();


    intrare = argv[1];
    iesire = argv[2];
    //BellmaFord
    /*
    if(BellmanFord(graf,1)==true){
        for(int i=2;i<=n;i++)
            fout << D[i] << " ";
    }
    else
        fout << "Ciclu negativ!";
    */

    // resetare vector vizitati
    // memset(viz,0,CMAX);

    /*
    Dijkstra(graf,1);
    for(int i=2;i<=n;i++)
        if(D[i]!=oo)
            fout << D[i] << " ";
        else
            fout << 0 << " ";
    */

    if(Johnson2() == false)
    {
        fout << -1;

        /*
        cout << '\n';
        for(int i=0;i<=n;i++)
            cout << viz[i] << " ";
        */
    }
    else{
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++){
                if(matrice_D[i][j]>=oo){
                    fout << "INF ";
                }
                else
                    fout << matrice_D[i][j] << " ";
            }
            fout << '\n';
        }
    }
    //afisare(graf);

    fin.close();
    fout.close();
    return 0;
}

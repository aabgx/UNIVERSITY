#include <stdio.h>

//17. Determina primele 10 numere naturale strict mai mari ca 2, care au proprietatea ca toate numerele naturale strict mai mici decat ele si care sunt prime cu ele sunt numere prime.
int gcd(int a,int b)
{
    if (a == 0)
        return b;
    if (b == 0)
        return a;

    while (a != b)
    {
        if (a > b)
            a = a - b;
        else
            b = b - a;
    }
    return a;
}


int prim(int x)
{
    if (x <= 0)
        return 0;
    if (x == 1)
        return 1;
    for (int i = 2;i < x;i++)
    {
        if (x % i == 0)
            return 0;
    }
    return 1;

}
int main()
{
    int cnt = 0, nr = 3,ok;

    while (cnt < 8)
    {    
        ok = 1;
        for (int i = 1;i < nr;i++)
        {
            
            if (gcd(nr, i) == 1)
            {
                if (prim(i) == 0)
                {
                    ok = 0;
                    break;
                }
            }
        }
        if (ok == 1)
        {
            printf("%d ", nr);
            cnt++;
        }
        nr++;
    }
}

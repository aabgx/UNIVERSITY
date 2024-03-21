// problema_5.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

/*Tipareste un numar precizat de termeni din sirul

1, 2, 1, 3, 2, 1, 4, 2, 2, 5, 4, 3, 2, 1, 6, 2, 2, 3, 3, 3, 7, 6, ...

obtinut din sirul numerelor naturale prin inlocuirea fiecarui

numar natural n printr - un grup de numere.Numarul prim p este

inlocuit prin numerele p, p - 1, ...3, 2, 1, iar numarul compus n

este inlocuit prin n urmat de toti divizorii sai proprii,

un divizor d repetandu - se de d ori.*/

#include <stdio.h>


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
    int i,j,k,n,ok;
    printf("pentru a opri introduceti 0.");
    printf("\n");
    ok = 1;
    while(ok)
    { 
    scanf_s("%d",&n);
    if (n == 0) { ok = 0; break; }
    for (j = 1;j <= n;j++)
    {
        if (prim(j))
        {
            for (i = j;i >= 1;i--)
                printf("%d, ", i);
        }
        else
        {
            printf("%d, ", j);
            for (i = 2;i < j;i++)
            {
                if (j % i == 0)
                {
                    for (k = 1;k <= i;k++)
                        printf("%d, ", i);
                }
            }
        }
    }
    printf("\n");
    }
}


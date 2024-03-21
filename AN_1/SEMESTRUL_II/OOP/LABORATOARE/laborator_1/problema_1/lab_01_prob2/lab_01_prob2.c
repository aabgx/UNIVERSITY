// lab_01_prob2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <stdio.h>

int main()
{
    //1. Genereaza numere prime mai mici decat un numar natural dat.
    int div = 0, n;
    scanf_s("%d", &n);
    for (int i = 2;i <= n;i++)
    {
        div = 0;
        for (int j = 1;j <= i;j++)
        {
            if (i % j == 0)
            {
                div++;
            }
        }
    if (div == 2)
    {
        printf("%d ", i);
        }
    }
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file

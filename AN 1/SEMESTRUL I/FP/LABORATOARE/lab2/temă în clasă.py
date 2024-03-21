from math import sqrt
n=int(input('Introdu un numar: '))

n=n+1
gasit=0
if n<=1:
    print('2')
else:
    while gasit!=1:
        ok=True
        for el in range(2,int(sqrt(n))+1):
            if(n%el==0):
                ok=False
                break
        if ok:
            print(n)
            gasit=1
        else:
            n=n+1
        

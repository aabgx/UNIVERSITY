a=int(input('introdu primul nr:'))
b=int(input('introdu al doilea nr:'))

if a>b:
    r=a;
    a=b;
    b=r;
for i in range (a,0,-1):
    if a%i==0 and b%i==0:
        print(i)
        break

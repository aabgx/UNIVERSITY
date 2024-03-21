#Se dă o listă de numere întregi a1,..,an generați toate sub-secvențele cu proprietatea că suma numerelor este divizibul cu N dat.
def afis(k,x):
    lst = [str(el) for el in x[1:k+1]]
    lst = ', '.join(lst)
    print(lst)
    

def valid(k,x):
    if k == 1:
        return True
    elif x[k] > x[k-1]:
        return True
    return False

def valid1(k,x,div):
    suma=0
    for i in range(1,k+1):
        suma = suma + x[i]
    if div!=0 and suma%div == 0:
        return True
    return False

def back(k,n,x,v,div):
    for i in range (1,n+1):
    
        x[k]=v[i]
        if valid(k,x):
            if valid1(k,x,div):
                afis(k,x)
            if k<n:
                back(k+1,n,x,v,div)


def main():
    n=int(input('introduceti n:'))
    print('introduceti n elemente:')
    v=[]
    x=[]
    v.append(0)
    x.append(0)
    for i in range(1,n+1):
        v.append(int(input('v['+str(i)+']=')))
        x.append(0)

    v.sort() #ca sa pot verifica validitatea in functie de ordine

    N=int(input('introduceti divizorul dorit:'))

    back(1,n,x,v,N)

main()

def validare(st,lst,div):
    s=0
    for el in st:
        s=s+lst[el]
    if st == []:
        return False
    if div!=0 and s%div==0:
        return True
    return False


def back(lst,n,div):
    inceput = 0
    final = 0
    st = []
    for i in range(1,n+1):
        st.append(0)

    while st[inceput] < n:
        if validare(st[inceput:final+1],lst,div):
            submultime=[]
            for el in st[inceput:final+1]:
                submultime.append(lst[el])
            print(submultime)
        if final!=n-1 and st[final]!=n-1:
            final=final+1
            st[final]=st[final-1]+1
        elif st[final]==n-1:
            final=final-1
            st[final]=st[final]+1


def main():
    n=int(input('introduceti n:'))
    print('introduceti n elemente:')
    v=[]
    for i in range(1,n+1):
        v.append(int(input('v['+str(i)+']=')))


    N=int(input('introduceti divizorul dorit:'))

    back(v,n,N)

main()
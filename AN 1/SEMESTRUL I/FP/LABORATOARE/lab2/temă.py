a=input()
b=input()
ok=1
fa=[0,0,0,0,0,0,0,0,0,0]
fb=[0,0,0,0,0,0,0,0,0,0]
for cif in str(a):
    fa[int(cif)]=1
for cif in str(b):
    fb[int(cif)]=1

for el in range(10):
    if (fa[el]!=0 and fb[el]==0) or (fb[el]!=0 and fa[el]==0):
        ok=0
        print("nu au proprietatea p")
        break
if ok==1:
    print("au proprietatea p")


    
    







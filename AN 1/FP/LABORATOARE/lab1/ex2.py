from math import sqrt
a=int(input('introdu nr'))
ok=1
for i in range (2,int(sqrt(a))):
        if(a%i==0):
            ok=0
            break
if(ok==0):
    print('nu e prim')
else: print ('e prim')
  
    

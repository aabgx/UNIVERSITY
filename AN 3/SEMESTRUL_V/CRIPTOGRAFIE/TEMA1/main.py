import random
import timeit

a=65665
b=91

def measure_time(func,x,y):
    return timeit.timeit(lambda: func(x,y), globals=globals(), number=1, timer=timeit.default_timer)

#we divide one by another until we get a reminder of 0
def gcd_euclidian(a,b):
    while(b!=0):
        a, b = b, a % b
    return a

#same as above but with substraction
def gcd_substraction(a,b):
    if(a==0):
        return b
    if(b==0):
        return a
    while(a!=b):
        if(a>b):
            a-=b
        else:
            b-=a
    return a

def gcd_euclidean_extended(a, b):
    u2, u1, v2, v1 = 1, 0, 0, 1
    while b > 0:
        q= a // b
        r= a - q * b
        u= u2 - q * u1
        v= v2 - q * v1

        a= b
        b= r
        u2= u1
        u1= u
        v2= v1
        v1= v
    d= a
    u= u2
    v= v2
    return d
    

def main():
    # print("\033[32mNUMBERS: ", a, b,"\033[0m")
    # gcd_euclidian_time = measure_time(gcd_euclidian,a,b)
    # print("TIME EUCLIDIAN (ms): ", gcd_euclidian_time * 1000)

    # gcd_substraction_time = measure_time(gcd_substraction,a,b)
    # print("TIME SUBSTRACTION (ms): ", gcd_substraction_time * 1000)

    # gcd_euclidian_extended_time = measure_time(gcd_euclidean_extended,a,b)
    # print("TIME EUCLIDIAN (ms): ", gcd_euclidian_extended_time * 1000)

    # print("\033[32mRESULTS: ", "(EUCLIDIAN)-",gcd_euclidian(a,b), " (SUBSTRACTION)-",gcd_substraction(a,b)," (EUCLIDIAN EXTENDED)-" ,gcd_euclidean_extended(a,b),"\033[0m")
    



    #intre 1 si 10^6
    random_pairs = [(random.randint(1, 10 ** 6), random.randint(1, 10 ** 6)) for _ in range(10)]
    for x, y in random_pairs:
        print("\033[32mNUMBERS: ", x, y,"\033[0m")
        gcd_euclidian_time = measure_time(gcd_euclidian,x,y)
        print("TIME EUCLIDIAN (ms): ", gcd_euclidian_time * 1000)

        gcd_substraction_time = measure_time(gcd_substraction,x,y)
        print("TIME SUBSTRACTION (ms): ", gcd_substraction_time * 1000)

        gcd_euclidian_extended_time = measure_time(gcd_euclidean_extended,x,y)
        print("TIME EUCLIDIAN (ms): ", gcd_euclidian_extended_time * 1000)

        print("\033[32mRESULTS: ", gcd_euclidian(x,y),gcd_substraction(x,y), gcd_euclidean_extended(x,y),"\033[0m")
        print("---------------------------------------------------")



main()
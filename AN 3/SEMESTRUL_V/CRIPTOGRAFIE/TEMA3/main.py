#finding a prime factor of a given large number n using pollards p-1

from math import lcm,gcd
from random import randint

#nb_try in case of failure to retry with a different a
def pollards_p_minus_1(n, B=None, nb_try=None,used_a=None):
    if nb_try is None:
        nb_try=1

    if B is None:
        #default implicit bound
        B = 13

    #calculate k = lcm(1, 2, ..., B)
    k = lcm(*range(1, B + 1))

    #set for used a-s (for failure case)
    if used_a is None:
        used_a = set()

    #choose a randomly (between 1 and n-1) until a new one is found
    while True:
        a = randint(2, n - 2)
        if a not in used_a:
            used_a.add(a)
            break

    #a = a^k mod n
    a = pow(a, k, n)

    #calculate d = gcd(a - 1, n)
    d = gcd(a - 1, n)

    #output result
    if (d == 1 or d == n) and nb_try==15:
        return "FAILURE"
    elif (d == 1 or d == n) and nb_try<15:
        return pollards_p_minus_1(n,B,nb_try+1,used_a)
    else:
        return d



#print result
from termcolor import colored
def print_colored_result(number_to_factorize, result):
    if result == "FAILURE":
        colored_result = colored(result, "red")
    else:
        colored_result = colored(result, "green")

    print(f"factor of {number_to_factorize}: {colored_result}")



#TEST
#implicit bound - 1
number_to_factorize = 17 * 73
result = pollards_p_minus_1(number_to_factorize)
print_colored_result(number_to_factorize, result)

#implicit bound - 2
number_to_factorize = 547 * 2269
result = pollards_p_minus_1(number_to_factorize)
print_colored_result(number_to_factorize, result)

#explicit bound - 1
number_to_factorize = 547 * 2269
result = pollards_p_minus_1(number_to_factorize, 79)
print_colored_result(number_to_factorize, result)

#explicit bound - 2
number_to_factorize = 10061 * 10091
result = pollards_p_minus_1(number_to_factorize, 1000)
print_colored_result(number_to_factorize, result)

#example of FAILURE: prime number
number_to_factorize = 10091
result = pollards_p_minus_1(number_to_factorize, 10)
print_colored_result(number_to_factorize, result)

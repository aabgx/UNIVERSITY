import math

#check if a number is prime
def is_prime(n):
    if n < 2:
        return False
    for i in range(2, int(math.sqrt(n)) + 1):
        if n % i == 0:
            return False
    return True

#check if n is square-free (not divisible by the square of any prime)
def is_squarefree(n):
    for i in range(2, int(math.sqrt(n)) + 1):
        if n % (i ** 2) == 0:
            return False
    return True

#check if p - 1 divides n - 1 for all prime factors p of n
def satisfies_condition(n):
    for p in range(2, n):
        if n % p == 0 and is_prime(p):
            if (n - 1) % (p - 1) != 0:
                return False
    return True

#a Carmichael number is an odd composite number n which satisfies the two conditions:
def main(bound):
    carmichael_numbers = []
    for n in range(3, bound + 1, 2):
        if not is_prime(n) and is_squarefree(n) and satisfies_condition(n):
            carmichael_numbers.append(n)

    print("\033[92mCARMICHAEL NUMBERS UP TO " + "\033[91m" +str(bound) + "\033[92m:\033[0m")
    print(carmichael_numbers)


main(10000)

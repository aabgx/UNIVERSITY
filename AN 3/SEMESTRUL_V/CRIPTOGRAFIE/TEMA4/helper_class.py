import math

alphabet = '_ABCDEFGHIJKLMNOPQRSTUVWXYZ'
k = 2  #block of text size
l = 3  #decrypted block size

#functions for finding e (given phi(n))
#e = smallest prime number a.i gcd(e, phi(n)) = 1
def choose_encryption_exponent(phi_n):
    e = 2  #we start from 2
    while math.gcd(e, phi_n) != 1 or not is_prime(e):  #verify if gcd(e, phi(n)) = 1 and e is prime
        e += 1
    return e
def is_prime(num):
    if num < 2:
        return False
    for i in range(2, int(math.sqrt(num)) + 1):
        if num % i == 0:
            return False
    return True

#for when we have numbers and we want to transform them into letters (also with alphabet codes)
def decompose_in_powers_of_alphabet_size(number):
    powers_of_alphabet_size = []

    while number > 0:
        powers_of_alphabet_size.append(number % len(alphabet))
        number //= len(alphabet)

    return powers_of_alphabet_size[::-1] #return reversed list
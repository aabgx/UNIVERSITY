from helper_class import choose_encryption_exponent,alphabet, is_prime,k,l,decompose_in_powers_of_alphabet_size

def rsa_encrypt(plaintext, p, q):
    print("\033[96m")
    print("STEPS OF ENCRYPTION:")
    n = p * q
    print(f"    1. n: {n}")
    print(f"    2. phi(n): {(p - 1) * (q - 1)}")

    #choose encryption exponent
    phi_n = (p - 1) * (q - 1)  #phi(n) = (p-1)*(q-1)
    e = choose_encryption_exponent(phi_n)
    print(f"    3. e: {e}")

    #break into blocks of k letters
    blocks_plaintext = [plaintext[i:i+k] for i in range(0, len(plaintext), k)]
    print(f"    4. blocks of k letters: {blocks_plaintext}")

    #convert letters into their position in the alphabet
    blocks_numerical_representation = [[alphabet.index(char) for char in block] for block in blocks_plaintext]
    print(f"    5. numerical representation of those letters: {blocks_numerical_representation}")

    #for each block we calculate the result of encryption, for example for 3 and 1 will be: 3*27 + 1 = 82
    #b's
    encrypted_blocks_numerical_representation = [
        sum(nb * (len(alphabet)**(k - j-1)) for j, nb in enumerate(block))
        for block in blocks_numerical_representation
    ]
    print(f"    6. numerical equivalent: {encrypted_blocks_numerical_representation}")

    #encrypted = block^e mod n
    #c's
    encrypted_blocks = [pow(block, e, n) for block in encrypted_blocks_numerical_representation]
    print(f"    7. numerical equivalents encrypted: {encrypted_blocks}")

    
    rez = [decompose_in_powers_of_alphabet_size(block) for block in encrypted_blocks]
    for block in rez:
        if len(block) < l:
            while len(block) < l:
                block.insert(0,0)
    print(f"    8. decompose in power of len(alphabet) - l size blocks: {rez}")

    encrypted_text = ''.join([''.join(alphabet[num] for num in block) for block in rez])
    print(f"    9. transform those numbers into letters: {encrypted_text}")
    print("\033[0m")
    return encrypted_text

def rsa_decrypt(encrypted_text,p,q):
    print("\033[96m")
    print("STEPS OF DECRYPTION:")
    n = p * q
    print(f"    1. n: {n}")
    print(f"    2. phi(n): {(p - 1) * (q - 1)}")

    #choose encryption exponent
    phi_n = (p - 1) * (q - 1)  #phi(n) = (p-1)*(q-1)
    e = choose_encryption_exponent(phi_n)
    print(f"    3. e: {e}")

    #choose decryption exponent: d= e^-1 mod phi(n)
    d = pow(e, -1, phi_n)
    print(f"    4. d: {d}")

    #break into blocks of l letters
    blocks_encrypted_text = [encrypted_text[i:i+l] for i in range(0, len(encrypted_text), l)]
    print(f"    5. blocks of l letters: {blocks_encrypted_text}")

    #convert letters into their position in the alphabet
    blocks_numerical_representation = [[alphabet.index(char) for char in block] for block in blocks_encrypted_text]
    print(f"    6. numerical representation of those letters: {blocks_numerical_representation}")

    #c's
    decrypted_blocks_numerical_representation = [
        sum(nb * (len(alphabet)**(l - j-1)) for j, nb in enumerate(block))
        for block in blocks_numerical_representation
    ]
    print(f"    7. numerical equivalent: {decrypted_blocks_numerical_representation}")

    #decrypted = block^d mod n
    #b's
    decrypted_blocks = [pow(block, d, n) for block in decrypted_blocks_numerical_representation]
    print(f"    8. numerical equivalents decrypted: {decrypted_blocks}")

    rez = [decompose_in_powers_of_alphabet_size(block) for block in decrypted_blocks]
    for block in rez:
        if len(block) < k:
            while len(block) < k:
                block.insert(0,0)
    print(f"    9. decompose in power of len(alphabet) - k size blocks: {rez}")

    decrypted_text = ''.join([''.join(alphabet[num] for num in block) for block in rez])
    print(f"    9. transform those numbers into letters: {decrypted_text}")
    print("\033[0m")
    return decrypted_text


def validate_text(text):
    for caracter in text:
        if not (caracter.isupper() or caracter == '_'):
            return False
    return True


#TEST
# plaintext = "CAIRO_"
# encrypted_text = rsa_encrypt(plaintext, 47,79)
# print("\033[95m" + plaintext + "   -   " + encrypted_text + "\033[0m")
# decrypted_text = rsa_decrypt(encrypted_text, 47,79)
# print("\033[95m" + encrypted_text + "   -   " + decrypted_text + "\033[0m")

# encrypted_text = "_DR_DK_TF"
# decrypted_text = rsa_decrypt(encrypted_text, 37,47)
# print("\033[95m" + encrypted_text + "   -   " + decrypted_text + "\033[0m")

# plaintext = "ALGEBRA_"
# encrypted_text = rsa_encrypt(plaintext, 47,61)
# print("\033[95m" + plaintext + "   -   " + encrypted_text + "\033[0m")
# decrypted_text = rsa_decrypt(encrypted_text, 47,61)
# print("\033[95m" + encrypted_text + "   -   " + decrypted_text + "\033[0m")






def main():
    plaintext = input("Enter plaintext: ")
    if not validate_text(plaintext):
        print("Invalid plaintext")
        exit(0)
    try:
        p= int(input("Enter p: "))
        q= int(input("Enter q: "))
        if p*q < pow(27,k) or p*q > pow(27,l):
            print("p*q must be greater than 27^k and less than 27^l")
            exit(0)
    except:
        print("p and q must be integers")
        exit(0)
    else:
        if not (is_prime(int(p)) and is_prime(int(q))):
            print("p and q must be prime")
            exit(0)
        encrypted_text = rsa_encrypt(plaintext, p,q)
        print("\033[95m" + plaintext + "   -   " + encrypted_text + "\033[0m")
        decrypted_text = rsa_decrypt(encrypted_text, p,q)
        print("\033[95m" + encrypted_text + "   -   " + decrypted_text + "\033[0m")

main()
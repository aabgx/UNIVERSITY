import random
import markovify

#markovify e o reprezentare a distribuției probabilităților pentru tranzițiile între cuvinte - matrice de tranzitii
def generate_sentence(sentiment):
    if sentiment == 'pozitiv':
        with open('props/propozitii_pozitive.txt', 'r') as file:
            sentences = file.readlines()
    elif sentiment == 'negativ':
        with open('props/propozitii_negative.txt', 'r') as file:
            sentences = file.readlines()
    elif sentiment == 'surprise':
        with open('props/propozitii_surprise.txt', 'r') as file:
            sentences = file.readlines()
    elif sentiment == 'anger':
        with open('props/propozitii_anger.txt', 'r') as file:
            sentences = file.readlines()
    elif sentiment == 'love':
        with open('props/propozitii_love.txt', 'r') as file:
            sentences = file.readlines()

    text_model = markovify.Text(sentences)

    #alege el o stare initiala in functie de distributia probabilistica a cuvintelor din model
    #state_size e un fel de n_grams, cu cat e mai mare, cu atat au mai mult sens frazele (by default e 2)
    return text_model.make_sentence(init_state=None,state_size=8)

input_sentiment = 'anger'
output_sentence = generate_sentence(input_sentiment)
print("MESAJ SIMILAR GENERAT: ",output_sentence)

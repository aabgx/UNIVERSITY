import random
#nltk.download('vader_lexicon')
#nltk.download('twitter_samples')

from nltk.corpus import twitter_samples
from nltk.sentiment import SentimentIntensityAnalyzer

import os
import cv2
from fer import FER

#din toate selecteaza no_posititive mesaje positive si no_negative mesaje negative
def generator_mesaje_tweet(nr_mesaje_poz, nr_mesaje_neg):
    pos_tweets = twitter_samples.strings('positive_tweets.json')
    neg_tweets = twitter_samples.strings('negative_tweets.json')

    pos_msg = random.sample(pos_tweets, nr_mesaje_poz)
    neg_msg = random.sample(neg_tweets, nr_mesaje_neg)

    return pos_msg + neg_msg


# prob de postare a unei imagini pozitive sau negative (bazat pe imaginile anterioare)
def probabilitate_imagine(folder):
    detector = FER()

    pos_img, neg_img = 0, 0
    
    for path in os.listdir(folder):
        img = cv2.imread(os.path.join(folder, path))
        img = cv2.resize(img, (240, 240))

        rez = detector.top_emotion(img)

        #consider ca fiind pozitive doar happy si surprise, angry, disgust si sad fiind negative
        if rez[0] in ['angry', 'disgust', 'sad']:
            neg_img += 1
        if rez[0] in ['happy', 'surprise']:
            pos_img += 1

    if pos_img >= neg_img:
        print('IMAGINE POZITIVA PROCENT: ' + str(pos_img / (pos_img + neg_img)))
    else:
        print('IMAGINE NEGATIVA PROCENT: ' + str(neg_img / (pos_img + neg_img)))
        

# prob de postare a unui text pozitiv sau negativ (bazat pe textele anterioare)
def probabilitate_text(mesaje):
    #compound e un ne de la -1 la 1, -1 inseamna negativ total, 1 inseamna pozitiv total, 0 e neutru
    sia = SentimentIntensityAnalyzer()

    score = 0.0

    for mesaj in mesaje:
        score += sia.polarity_scores(mesaj)['compound']
    score /= len(mesaje)

    #50% sanse daca avem compound 0, la care adaugam proportional pana la 100% din cat de aproape e de -1 sau 1
    procent = 0.5 + 0.5 * abs(score)

    if score >= 0:
        print('TEXT POZITIV PROCENT: ',procent)
    else:
        print('TEXT NEGATIV PROCENT: ',procent)


def main():
    mesaje_user = generator_mesaje_tweet(350,200)
    probabilitate_imagine('utilizator')
    probabilitate_text(mesaje_user)

main()


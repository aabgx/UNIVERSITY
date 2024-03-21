import requests
from bs4 import BeautifulSoup
import random
import requests
from PIL import Image
from io import BytesIO
from fer import FER

def detect_emotions(path):
    detector = FER()
    emotion, score = detector.top_emotion(path)

    return emotion

def download_image(image_url, filename):
    response = requests.get(image_url)
    with open(filename, 'wb') as file:
        file.write(response.content)

def generate_image_by_emotion(emotion):
    search_query = f"{emotion} person image"
    url = f"https://www.google.com/search?q={search_query}&tbm=isch"

    #cerere GET către pagina de căutare a imaginilor
    response = requests.get(url) 

    if response.status_code == 200:
        #extage info generale despre documentul html ca sa pot face dupa findall
        soup = BeautifulSoup(response.text, 'html.parser')
        image_elements = soup.find_all('img')

        #aleg un URL acum not so random, alege pana ii place la FER
        if image_elements:
            # image_url = random.choice(image_elements)['src']
            # return image_url
        
            ok=0
            while ok==0:
                image_url = random.choice(image_elements)['src']
                download_image(image_url, 'poza.png')
                if detect_emotions('poza.png') == emotion:
                    return image_url
            
    #daca nu s-a terminat cu 200 request-ul
    return None


def display_image(url):
    response = requests.get(url)

    if response.status_code == 200:
        #deschidem imaginea
        image = Image.open(BytesIO(response.content))
        image.show()
    else:
        print("NU S-A PUTUT DESCHIDE IMAGINEA.")

def main():
    emotion = "angry"
    image_url = generate_image_by_emotion(emotion)

    if image_url:
        print("URL IMAGINE:", image_url)
        display_image(image_url)
    else:
        print("NU S-A PUTUT GENERA IMAGINE", emotion)

main()





import cv2
import os
from glob import glob
from fer import FER
from sklearn.metrics import hamming_loss
from sklearn.preprocessing import LabelEncoder
import numpy as np

labels = ["anger", "disgust", "fear", "happy", "sadness", "surprise", "neutral"]
label_mapping = {
    "angry": "anger",
    "disgust": "disgust",
    "fear": "fear",
    "happy": "happy",
    "sad": "sadness",
    "surprise": "surprise",
    "neutral": "neutral"
}

def read_faces():
    testInputSet = []
    testOutputSet = []
    images = glob('CK+48/**/*.png', recursive=True)

    label_encoder = LabelEncoder()
    label_encoder.fit(labels)

    for i in range(len(images)):
        img = cv2.imread(images[i])
        img = cv2.resize(img, (96, 96))
        directory = os.path.dirname(os.path.realpath(images[i])).split('\\')[-1]

        if directory in label_mapping:
            directory = label_mapping[directory]

        if directory in labels:
            # transform in numere: 0,1,2,3,4,5,6
            encoded_label = label_encoder.transform([directory])[0]

            # toate elementele din lista asta sunt initializate cu 0, apoi punem 1 pe pozitia encoded_label
            one_hot_label = [0] * len(labels)
            one_hot_label[encoded_label] = 1

            testOutputSet.append(one_hot_label)
        else:
            print(directory)

        testInputSet.append(img)

    return testInputSet, testOutputSet

import numpy as np

def detect_emotions():
    testInputSet, testOutputSet = read_faces()
    computed = []
    emotions_mapping = {'anger':0, 'disgust': 1, 'fear': 2, 'happy': 3, 'sadness': 4, 'surprise': 5, 'neutral': 6}
    detector = FER()

    #adaugam in computed doar emotiile cu un scor > 0.2
    for i in range(len(testInputSet)):
        emotions = detector.detect_emotions(testInputSet[i])
        predicted_labels = [emotion_label for emotion in emotions for emotion_label in emotion['emotions'] if emotion['emotions'][emotion_label] > 0.2]
        computed.append(predicted_labels)

    # print("Computed Labels: ", computed)
    # print("True Labels: ", testOutputSet)

    #transformam din etichete de tip string (emotiile propriu-zise) in siruri de 0 si 1 (1 pe pozitiile prezise)
    computed_binary = []
    for predicted_labels in computed:
        label_binary = [1 if label in predicted_labels else 0 for label in emotions_mapping]
        computed_binary.append(label_binary)

    # print("Computed Labels (Binary): ", computed_binary)
    # print(len(testOutputSet), " ",len(computed_binary))
    # print(len(testOutputSet[0]), " ", len(computed_binary[0]))
    
    #hamming loss
    hamming_loss_value = hamming_loss(testOutputSet, computed_binary)
    print("Hamming Loss: ", hamming_loss_value)


detect_emotions()


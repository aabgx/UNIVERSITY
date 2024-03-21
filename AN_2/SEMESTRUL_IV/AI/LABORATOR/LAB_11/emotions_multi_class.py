import cv2
import os
from glob import glob
from fer import FER
from sklearn.metrics import accuracy_score

def read_faces():
    testInputSet = []
    testOutputSet = []
    images = glob('CK+48/**/*.png', recursive=True)
    for i in range(len(images)):
        img = cv2.imread(images[i])
        img = cv2.resize(img, (96, 96))
        directory = os.path.dirname(os.path.realpath(images[i])).split('\\')[-1]
        if directory == "anger":
            testOutputSet.append(0)
        elif directory == "disgust":
            testOutputSet.append(1)
        elif directory == "fear":
            testOutputSet.append(2)
        elif directory == "happy":
            testOutputSet.append(3)
        elif directory == "sadness":
            testOutputSet.append(4)
        elif directory == "surprise":
            testOutputSet.append(5)
        else:
            print(directory)
        testInputSet.append(img)
    return testInputSet, testOutputSet


def detect_emotions():
    testInputSet, testOutputSet = read_faces()
    computed = []
    detector = FER()
    for i in range(len(testInputSet)):
        emotion, score = detector.top_emotion(testInputSet[i])
        # print(emotion," ",score)
        if emotion == 'angry':
            computed.append(0)
        elif emotion == 'disgust':
            computed.append(1)
        elif emotion == 'fear':
            computed.append(2)
        elif emotion == 'happy':
            computed.append(3)
        elif emotion == 'sad':
            computed.append(4)
        elif emotion == 'surprise':
            computed.append(5)
        elif emotion == 'neutral':
            computed.append(6)

    print("ACURACY: ", accuracy_score(testOutputSet, computed))

detect_emotions()
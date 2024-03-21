from glob import glob
import os
from PIL import Image
import numpy as np
import cv2
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
import tensorflow as tf
from keras import layers, models

def read_faces():
    testInputSet = []
    testOutputSet = []
    images = glob('CK+48/**/*.png', recursive=True)
    for i in range(len(images)):
        img = Image.open(images[i]).convert('RGBA')
        img_matrix = np.array(img)
        img_matrix = cv2.resize(img_matrix, (32, 32))

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
        testInputSet.append(img_matrix)
        
    testInputSet = np.array(testInputSet)
    testOutputSet = np.array(testOutputSet)

    # testInputSet este matricea de imagini
    # testOutputSet este vectorul de etichete
    return testInputSet, testOutputSet


def startClassificationEmotions():
    X_data, Y_data = read_faces()
    X = X_data.reshape(X_data.shape[0], 32, 32, 4)  # redimensionare la (32x32x4)

    # împărțire în set de antrenare și set de testare
    trainInputs, validationInputs, trainOutputs, validationOutputs = train_test_split(
        X, Y_data, test_size=0.20, random_state=42, shuffle=True
    )

    # avem nevoie de valori din [0,1]
    trainInputs = trainInputs / 255.0
    validationInputs = validationInputs / 255.0

    # creare clasificator
    model = models.Sequential()
    model.add(layers.Conv2D(48, (3, 3), activation="relu", input_shape=(32, 32, 4)))
    model.add(layers.MaxPooling2D((2, 2)))
    model.add(layers.Conv2D(64, (3, 3), activation="relu"))
    model.add(layers.MaxPooling2D((2, 2)))
    model.add(layers.Conv2D(64, (3, 3), activation="relu"))
    model.add(layers.Flatten())
    model.add(layers.Dense(64, activation="relu"))
    model.add(layers.Dense(6, activation="softmax"))
    model.compile(
        optimizer="adam",
        loss=tf.keras.losses.SparseCategoricalCrossentropy(),
        metrics=["accuracy"],
    )

    # antrenare și prezicere
    model.fit(
        trainInputs,
        trainOutputs,
        epochs=50,
        validation_data=(validationInputs, validationOutputs),
        batch_size=4,
        verbose=0,
    )
    predictedLabels = model.predict(validationInputs, verbose=0)
    predictedLabels = np.argmax(predictedLabels, axis=1)

    print("ACCURACY: ", accuracy_score(validationOutputs, predictedLabels))


startClassificationEmotions()


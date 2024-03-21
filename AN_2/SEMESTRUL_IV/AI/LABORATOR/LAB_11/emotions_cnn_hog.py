from glob import glob
import os
from PIL import Image
import numpy as np
import cv2
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
import tensorflow as tf
from keras import layers, models
from skimage.feature import hog
from skimage.color import rgb2gray

def read_faces():
    testInputSet = []
    testOutputSet = []
    images = glob('CK+48/**/*.png', recursive=True)
    for i in range(len(images)):
        img = Image.open(images[i]).convert('RGB')  # convertim la RGB
        img_matrix = np.array(img)
        img_matrix = cv2.resize(img_matrix, (48, 48))

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

    return testInputSet, testOutputSet

#informatii despre textura si contururi
def get_hog_features(images):
    hog_features = []
    for image in images:
        gray_image = rgb2gray(image)
        hog_feature = hog(gray_image, orientations=9, pixels_per_cell=(8, 8),
                          cells_per_block=(2, 2), block_norm='L2-Hys', visualize=False)
        hog_features.append(hog_feature)

    #returneaza o reprezentare vectoriala a unei imagini
    return np.array(hog_features)

def startClassificationEmotions():
    X_data, Y_data = read_faces()
    X_data = get_hog_features(X_data)
    X = X_data.reshape(X_data.shape[0], -1)  # redimensionare la un vector liniar
    
    # împărțire în set de antrenare și set de testare
    trainInputs, validationInputs, trainOutputs, validationOutputs = train_test_split(
        X, Y_data, test_size=0.20, random_state=42, shuffle=True
    )

    # avem nevoie de valori din [0,1]
    trainInputs = trainInputs / 255.0
    validationInputs = validationInputs / 255.0

    # creare clasificator
    model = models.Sequential()
    model.add(layers.Dense(64, activation="relu", input_shape=(X.shape[1],)))
    model.add(layers.Dense(128, activation="relu"))
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
        epochs=100,
        validation_data=(validationInputs, validationOutputs),
        batch_size=4,
        verbose=0,
    )
    predictedLabels = model.predict(validationInputs, verbose=0)
    predictedLabels = np.argmax(predictedLabels, axis=1)

    print("ACCURACY: ", accuracy_score(validationOutputs, predictedLabels))


startClassificationEmotions()

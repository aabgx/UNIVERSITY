from glob import glob
from PIL import Image
import numpy as np
import cv2
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
import tensorflow as tf
from keras import layers, models


def read_emojis():
    X = []
    Y = []
    images_negative = glob('dataset_emojis/negative/**/*.jpg', recursive=True)
    images_positive = glob('dataset_emojis/positive/**/*.jpg', recursive=True)

    for image_path in images_negative:
        img = Image.open(image_path)
        img_matrix = np.array(img)
        img_matrix = cv2.resize(img_matrix, (32, 32))
        X.append(img_matrix)
        Y.append(0)

    for image_path in images_positive:
        img = Image.open(image_path)
        img_matrix = np.array(img)
        img_matrix = cv2.resize(img_matrix, (32, 32))
        X.append(img_matrix)
        Y.append(1)

    X = np.array(X)
    Y = np.array(Y)

    # X este matricea de imagini
    # Y este vectorul de etichete
    return X, Y


#incercasem cu ANN si le prezicea pe toate ca fiind pozitive
def startClassificationEmojis():
    X_data, Y_data = read_emojis()
    X = []

    #redimensionare intr-o matrice liniara
    for i in range(len(X_data)):
        matrix = X_data[i].reshape((1, 32 * 32 * 4))
        X.append(matrix[0])
    X = np.array(X)

    #impartire in set de antrenare si set de testare
    trainInputs, validationInputs, trainOutputs, validationOutputs = train_test_split(X, Y_data, test_size=0.20, random_state=42, shuffle=True)


    #redimensionare seturi de date de antrenare și de testare
    trainInputs = trainInputs.reshape(trainInputs.shape[0], 32, 32, 4)
    validationInputs = validationInputs.reshape(validationInputs.shape[0], 32, 32, 4)

    #avem nevoie de valori din [0,1]
    trainInputs = trainInputs / 255.0
    validationInputs = validationInputs / 255.0

    #creare clasificator
    model = models.Sequential()
    #primul strat de convoluție, 48 de filtre, dim. 3x3, f. de activare ReLU (Rectified Linear Unit), input_shape - imagine cu înălțime și lățime de 32 de pixeli și 4 canale 
    model.add(layers.Conv2D(48, (3, 3), activation='relu', input_shape=(32, 32, 4)))
    #dim fereastra de 2x2, reduce dim spațiale ale caract extrase, selecteaza maximul din regiune, reduce nr de parametrii
    model.add(layers.MaxPooling2D((2, 2)))
    #strat de convoluție, 64 de filtre cu dimensiunea de 3x3 și funcția de activare ReLU
    model.add(layers.Conv2D(64, (3, 3), activation='relu'))
    #iar strat de Pooling pentru a reduce dimensiunile spațiale
    model.add(layers.MaxPooling2D((2, 2)))
    #aplatizare la unidimensional
    model.add(layers.Flatten())
    #stratul complet conectat (Dense), 64 de neuroni și funcția de activare ReLU, extrage și combină caractextrase anterior
    model.add(layers.Dense(64, activation='relu'))
    #ultimul strat complet conectat are 2 neuroni, corespunzător numărului de clase de ieșire
    model.add(layers.Dense(2, activation='softmax'))
    model.compile(optimizer='adam',
              loss=tf.keras.losses.SparseCategoricalCrossentropy(),
              metrics=['accuracy'])
    
    #antrenare si prezicere
    model.fit(trainInputs, trainOutputs, epochs=20, validation_data=(validationInputs, validationOutputs), batch_size=4, verbose=0)
    predictedLabels = model.predict(validationInputs, verbose=0)
    predictedLabels = np.argmax(predictedLabels, axis=1)

    print("ACCURACY: ", accuracy_score(validationOutputs, predictedLabels))


startClassificationEmojis()


from matplotlib import pyplot as plt
import numpy as np
from sklearn import datasets, neural_network

#pt CNN
import tensorflow as tf
from tensorflow import keras
from keras import layers,models


from utils import load_images, load_images_cnn, multi_class_eval, normalisation, plot_confusion_matrix

def iris():
    print('IRIS:')
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(5,), activation='relu', max_iter=200, solver='sgd',
                                              verbose=False, random_state=1, learning_rate_init=.1)
    iris = datasets.load_iris()
    inputs = iris.data.tolist() #tolist ca eray array numpy si nu-i place la functie
    outputs = iris.target
    outputNames = iris.target_names

    #impartirea datelor in 80% antrenare si 20% testare
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
    validationSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [inputs[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]

    #BY TOOL
    print ('BY TOOL:')
    trainInputs, validationInputs = normalisation(trainInputs, validationInputs)
    classifier.fit(trainInputs, trainOutputs)
    predictedLabels = classifier.predict(validationInputs)

    accuracy, precision, recall, confusionMatrix = multi_class_eval(np.array(validationOutputs), predictedLabels, outputNames)
    plot_confusion_matrix(confusionMatrix, outputNames, "IRIS CLASSIFICATION")
    print('1. ACCURACY: ', accuracy)
    print('2. ACCURACY: ', precision)
    print('3. RECALL: ', recall)

    #BY HAND -> TO BE IMPLEMENTED   

def digits():
    print('\nDIGITS:')
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(5,), activation='relu', max_iter=200, solver='sgd',
                                              verbose=False, random_state=1, learning_rate_init=.1)
    iris = datasets.load_digits()
    inputs = iris.data.tolist() #tolist ca eray array numpy si nu-i place la functie
    outputs = iris.target
    outputNames = iris.target_names

    #impartirea datelor in 80% antrenare si 20% testare
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
    validationSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [inputs[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]

    #BY TOOL
    print ('BY TOOL:')
    trainInputs, validationInputs = normalisation(trainInputs, validationInputs)
    classifier.fit(trainInputs, trainOutputs)
    predictedLabels = classifier.predict(validationInputs)

    accuracy, precision, recall, confusionMatrix = multi_class_eval(np.array(validationOutputs), predictedLabels, outputNames)
    plot_confusion_matrix(confusionMatrix, outputNames, "DIGITS CLASSIFICATION")
    print('1. ACCURACY: ', accuracy)
    print('2. PRECISION: ', precision)
    print('3. RECALL: ', recall)

    #BY HAND -> TO BE IMPLEMENTED

def sepia():
    print('\nSEPIA BY TOOL:')
    classifier = neural_network.MLPClassifier(hidden_layer_sizes=(5,), activation='relu', max_iter=200, solver='sgd',
                                              verbose=False, random_state=1, learning_rate_init=.1)
    
    sepia = load_images("imagini.txt").tolist()
    outputs = [1,0,0,1,1,0,1,0,0,1,0,1,0,1]
    outputNames = ['NO SEPIA','SEPIA']

    #impartirea datelor in 80% antrenare si 20% testare
    np.random.seed(5)
    indexes = [i for i in range(len(sepia))]
    trainSample = np.random.choice(indexes, int(0.8 * len(sepia)), replace = False)
    validationSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [sepia[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [sepia[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]

    #BY TOOL
    #ANN
    print('ANN: ')
    trainInputs, validationInputs = normalisation(trainInputs, validationInputs)
    classifier.fit(trainInputs, trainOutputs)
    predictedLabels = classifier.predict(validationInputs)

    accuracy, precision, recall, confusionMatrix = multi_class_eval(validationOutputs, predictedLabels, outputNames)
    plot_confusion_matrix(confusionMatrix, outputNames, "SEPIA CLASSIFICATION ANN")
    print('1. ACCURACY: ', accuracy)
    print('2. PRECISION: ', precision)
    print('3. RECALL: ', recall)

    #CNN
    print('CNN: ')
    sepia = load_images_cnn("imagini.txt")
    outputs = [1,0,0,1,1,0,1,0,0,1,0,1,0,1]
    outputNames = ['NO SEPIA','SEPIA']

    #impartirea datelor in 80% antrenare si 20% testare
    np.random.seed(5)
    indexes = [i for i in range(len(sepia))]
    trainSample = np.random.choice(indexes, int(0.8 * len(sepia)), replace = False)
    validationSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [sepia[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [sepia[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]
    
    trainInputs = np.array(trainInputs)
    validationInputs = np.array(validationInputs)
    trainOutputs = np.array(trainOutputs)
    validationOutputs = np.array(validationOutputs)

    #vrea sa fie date intre 0 si 1
    trainInputs = trainInputs / 255.0
    validationInputs = validationInputs / 255.0

    model = models.Sequential()
    model.add(layers.Conv2D(48, (3, 3), activation='relu', input_shape=(48, 48, 3)))
    model.add(layers.MaxPooling2D((2, 2)))
    model.add(layers.Conv2D(64, (3, 3), activation='relu'))
    model.add(layers.MaxPooling2D((2, 2)))
    model.add(layers.Conv2D(64, (3, 3), activation='relu'))
    model.add(layers.Flatten())
    model.add(layers.Dense(64, activation='relu'))
    model.add(layers.Dense(10))
    #inainte de flatten se pune dropOut care regularizeaza modelul
    # model.summary()

    model.compile(optimizer='adam',
              loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
              metrics=['accuracy'])

    model.fit(trainInputs, trainOutputs, epochs=20,validation_data=(validationInputs, validationOutputs),batch_size=4, verbose=0)

    predictedLabels = model.predict(validationInputs,verbose=0)
    #transforma valorile continue de ieșire ale unui model de rețea neuronală în etichete discrete
    predictedLabels=np.argmax(predictedLabels, axis=1)

    accuracy, precision, recall, confusionMatrix = multi_class_eval(validationOutputs, predictedLabels, outputNames)
    plot_confusion_matrix(confusionMatrix, outputNames, "SEPIA CLASSIFICATION CNN")
    print('1. ACCURACY: ', accuracy)
    print('2. PRECISION: ', precision)
    print('3. RECALL: ', recall)

iris()
digits()
sepia()
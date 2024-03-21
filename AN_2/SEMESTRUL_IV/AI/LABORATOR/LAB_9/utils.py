import itertools
from matplotlib import pyplot as plt
import numpy as np
from sklearn.discriminant_analysis import StandardScaler
from sklearn.metrics import confusion_matrix
from PIL import Image

def normalisation(trainData, testData):
    scaler = StandardScaler()
    if not isinstance(trainData[0], list):
        trainData = [[d] for d in trainData]
        testData = [[d] for d in testData]
        
        scaler.fit(trainData)  
        normalisedTrainData = scaler.transform(trainData) 
        normalisedTestData = scaler.transform(testData) 
        
        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(trainData) 
        normalisedTrainData = scaler.transform(trainData) 
        normalisedTestData = scaler.transform(testData) 
    return normalisedTrainData, normalisedTestData

#accuracy: procentul de exemple clasificate corect
#precision: procentul de exemple clasificate corect din clasa i
#recall: procentul de exemple clasificate corect din clasa i din toate exemplele care apartin clasei i
#confusion matrix: arata numarul de exemple clasificate in fiecare clasa
def multi_class_eval(realLabels, computedLabels, labelNames):
    confusionMatrix = confusion_matrix(realLabels, computedLabels)

    #cele clasificate corect vor fi pe diagonala principala
    accuracy = sum([confusionMatrix[i][i] for i in range(len(labelNames))]) / len(realLabels)
    
    precision = {}
    recall = {}
    for i in range(len(labelNames)):
        #nr total de exemple clasificate corect in clasa i / nr total de exemple clasificate in clasa i
        precision[labelNames[i]] = confusionMatrix[i][i] / sum([confusionMatrix[j][i] for j in range(len(labelNames))])
        
        #nr total de exemple clasificate corect in clasa i / nr total de exemple care apartin clasei i
        recall[labelNames[i]] = confusionMatrix[i][i] / sum([confusionMatrix[i][j] for j in range(len(labelNames))])
    return accuracy, precision, recall, confusionMatrix

def plot_confusion_matrix(cm, classNames, title):
    plt.figure()
    plt.imshow(cm, interpolation='nearest', cmap='Blues')
    plt.title('Confusion Matrix ' + title)
    plt.colorbar()
    tick_marks = np.arange(len(classNames))
    plt.xticks(tick_marks, classNames, rotation=45)
    plt.yticks(tick_marks, classNames)

    text_format = 'd'
    thresh = cm.max() / 2.
    for row, column in itertools.product(range(cm.shape[0]), range(cm.shape[1])):
        plt.text(column, row, format(cm[row, column], text_format),
                 horizontalalignment='center',
                 color='white' if cm[row, column] > thresh else 'black')

    plt.ylabel('True label')
    plt.xlabel('Predicted label')
    plt.tight_layout()

    plt.show()

def load_images(filename):
    data = []
    readFile = open(filename)
    images = readFile.read()
    images = images.splitlines()
    for img in images:
        #redimensionez la 300x300 pixeli și transform in vector prin aplatizarea pixelilor
        data.append(np.array(Image.open("imagini/" + img).resize((300, 300))).flatten())

    #transform in matrice numpy
    inputs = np.asarray(data)
    return inputs

def load_images_cnn(filename):
    data = []
    readFile = open(filename)
    images = readFile.read()
    images = images.splitlines()
    for img in images:
        #redimensionez la 300x300 pixeli și transform in vector prin aplatizarea pixelilor
        data.append(np.array(Image.open("imagini/" + img).resize((48, 48))).flatten())
        data[-1] = data[-1].reshape((48, 48, 3))

    #transform in matrice numpy
    inputs = np.asarray(data)
    return inputs

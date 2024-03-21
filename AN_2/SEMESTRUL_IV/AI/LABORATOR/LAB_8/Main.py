from sklearn import datasets
import numpy as np
from sklearn.discriminant_analysis import StandardScaler 
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, log_loss
from LR import LR

def normalisation(trainData, testData):
    scaler = StandardScaler()
    if not isinstance(trainData[0], list):
        #encode each sample into a list
        trainData = [[d] for d in trainData]
        testData = [[d] for d in testData]
        
        scaler.fit(trainData)  #  fit only on training data
        normalisedTrainData = scaler.transform(trainData) # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data
        
        #decode from list to raw values
        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(trainData)  #  fit only on training data
        normalisedTrainData = scaler.transform(trainData) # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data
    return normalisedTrainData, normalisedTestData

# functie care calculeaza eroarea de clasificare cu tool
def tool(trainInputs,trainOutputs,validationInputs,validationOutputs):
    logreg = LogisticRegression(max_iter=10000)
    logreg.fit(trainInputs, trainOutputs)
    computedValidationOutputs = logreg.predict(validationInputs)
    error = 1 - accuracy_score(validationOutputs, computedValidationOutputs)
    return error

# functie care calculeaza eroarea de clasificare by hand
def hand(trainInputs,trainOutputs,validationInputs,validationOutputs):
    logreg = LR()

    #antrenam modelul pentru fiecare clasa (avem 3 clase)
    for i in range(0,3):
        train=[1 if to==i else 0 for to in trainOutputs]
        logreg.fit(trainInputs, train)
        # logreg.fit_batch(trainInputs, train)

    #prezicem outputul pentru datele de validare
    computedValidationOutputs=logreg.predict(validationInputs)

    #probabilitatile pentru fiecare clasa
    # prob=logreg.log_loss(validationInputs)
    # print('log loss', log_loss(validationInputs, prob))

    error = 1 - accuracy_score(validationOutputs, computedValidationOutputs)
    return error

def main():
    # incarcarea datelor
    iris = datasets.load_iris()
    inputs = iris.data.tolist() #tolist ca eray array numpy si nu-i place la functie
    outputs = iris.target

    #impartirea datelor in 80% antrenare si 20% testare
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
    validationSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [inputs[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]

    # normalizarea datelor
    # trainInputs, validationInputs = normalisation(trainInputs, validationInputs)

    # antrenarea clasificatorului
    print("classification error (tool): ", tool(trainInputs,trainOutputs,validationInputs,validationOutputs))
    print("classification error (hand): ", hand(trainInputs,trainOutputs,validationInputs,validationOutputs))
    
main()
    
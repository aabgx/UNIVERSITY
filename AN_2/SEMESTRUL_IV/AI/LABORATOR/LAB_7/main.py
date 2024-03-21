#calculeaza media si deviatia standard pt params (lista)
#deviatia standard = sqrt(1/n * sum((x_i - media)^2))
import csv
import os

import numpy as np
from sklearn.linear_model import SGDRegressor
from sklearn.metrics import mean_squared_error

from BGD import HandBGDRegression

#citire daca avem 1 sau 2 variabile de intrare (trebuie data ca lista de variabile)
def loadData(fileName, inputVariabName, outputVariabName):
    data = []
    dataNames = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                dataNames = row
            else:
                data.append(row)
            line_count += 1
    selectedVariable1 = dataNames.index(inputVariabName[0])
    if len(inputVariabName)==2:
        selectedVariable2 = dataNames.index(inputVariabName[1])
        inputs = [[float(data[i][selectedVariable1]),float(data[i][selectedVariable2])] for i in range(len(data))]
    else:
        inputs = [[float(data[i][selectedVariable1])] for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]
    return inputs, outputs

#calculeaza media si deviatia standard pt params (lista)
#deviatia standard = sqrt(1/n * sum((x_i - media)^2))
def mean_deviation(params):
    mean_value = sum(params) / len(params)
    std_dev_value = (1 / len(params) * sum([ (feat - mean_value) ** 2 for feat in params])) ** 0.5 
    return mean_value,std_dev_value

#normalizarea datelor de intrare
#se reduce variabilitatea datelor și se poate evita problema gradientului explodat sau gradientului mic
#schimba distributia si scala datelor de intrare pt o antrenare mai eficienta
#x_i = (x_i - media) / deviatia standard
def normalization_inputs(params,mean1,std_dev_1,mean2,std_dev_2):
    normalized_params = [[(feat[0] - mean1) / std_dev_1,(feat[1] - mean2) / std_dev_2] for feat in params]
    return normalized_params

def normalization_inputs_one_variable(params,mean,std_dev):
    normalized_params = [[(feat - mean) / std_dev] for feat in params]
    return normalized_params

#normalizarea datelor de iesire
def normalization_outputs(params,mean,std_dev):
    normalized_params = [(feat - mean) / std_dev for feat in params]
    return normalized_params

def tool(train_inputs,train_outputs,validation_inputs,validation_outputs):
    regressor=SGDRegressor()
    for i in range(1000):
        regressor.partial_fit(train_inputs, train_outputs)
    computed_validation_outputs = regressor.predict(validation_inputs)
    error = mean_squared_error(validation_outputs, computed_validation_outputs)
    # print(regressor.intercept_,regressor.coef_)
    return error

def hand(trainInputs,trainOutputs,validationInputs,validationOutputs):
    regressor=HandBGDRegression()
    regressor.fit(trainInputs, trainOutputs)
    computedValidationOutputs = regressor.predict(validationInputs)
    error = mean_squared_error(validationOutputs, computedValidationOutputs)
    # print(regressor.intercept_,regressor.coef_)
    return error

def main():
    crtDir =  os.getcwd()
    filePath = os.path.join(crtDir,'2017.csv')
    # inputs, outputs = loadData(filePath, ['Economy..GDP.per.Capita.'], 'Happiness.Score')
    inputs, outputs = loadData(filePath, ['Economy..GDP.per.Capita.','Freedom'], 'Happiness.Score')
    
    #impartim in 80% antrenament si 20% validare
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
    validationSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [inputs[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]
    
    #normalizam datele de intrare si de iesire (pt 2 variabile independente)
    mean1,dev1=mean_deviation([t[0] for t in trainInputs])
    mean2,dev2=mean_deviation([t[1] for t in trainInputs])
    mean3,dev3=mean_deviation(trainOutputs)
    trainInputs=normalization_inputs(trainInputs, mean1, dev1,mean2,dev2)
    validationInputs=normalization_inputs(validationInputs, mean1, dev1,mean2,dev2)
    trainOutputs=normalization_outputs(trainOutputs, mean3, dev3)
    validationOutputs=normalization_outputs(validationOutputs, mean3, dev3)

    #normalizam datele de intrare si de iesire (pt o variabila independenta)
    # mean1,dev1=mean_deviation([t[0] for t in trainInputs])
    # mean3,dev3=mean_deviation(trainOutputs)
    # trainInputs=normalization_inputs_one_variable([t[0] for t in trainInputs], mean1, dev1)
    # validationInputs=normalization_inputs_one_variable([v[0] for v in validationInputs], mean1, dev1)
    # trainOutputs=normalization_outputs(trainOutputs, mean3, dev3)
    # validationOutputs=normalization_outputs(validationOutputs, mean3, dev3)
    
    print("prediction error by tool: ", tool(trainInputs,trainOutputs,validationInputs,validationOutputs))
    print("prediction error by hand: ", hand(trainInputs,trainOutputs,validationInputs,validationOutputs))
    
main()
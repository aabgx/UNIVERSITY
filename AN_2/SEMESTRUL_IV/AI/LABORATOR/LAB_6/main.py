from matplotlib import pyplot as plt
from sklearn import linear_model
from sklearn.metrics import mean_squared_error
import os
import csv
import numpy as np

from newRegression import MyLinearBivariateRegression 

def loadData(fileName, inputVariabName1,inputVariabName2, outputVariabName):
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
    selectedVariable1 = dataNames.index(inputVariabName1)
    selectedVariable2 = dataNames.index(inputVariabName2)
    inputs = [[float(data[i][selectedVariable1]),float(data[i][selectedVariable2])] for i in range(len(data))]
    selectedOutput = dataNames.index(outputVariabName)
    outputs = [float(data[i][selectedOutput]) for i in range(len(data))]
    
    return inputs, outputs

#plot data
#ox e gradul de ... si oy e cate tari sunt in acel interval
def plotDataHistogram(x, variableName):
    plt.hist(x, 10)
    plt.title('Histogram of ' + variableName)
    plt.show()

def plotData(x1, y1, x2 = None, y2 = None, x3 = None, y3 = None, title = None):
    plt.plot(x1, y1, 'ro', label = 'train data')
    if (x2):
        plt.plot(x2, y2, 'b-', label = 'learnt model')
    if (x3):
        plt.plot(x3, y3, 'g^', label = 'test data')
    plt.title(title)
    plt.legend()
    plt.show()

#mine
#trainInpots e o lista de tupluri pt ca avem 2 variabile independente
#w0,w1,w2 sunt coeficientii modelului de regresie liniara
def plotModel(trainInputs, w0, w1, w2, title):
    fig = plt.figure()
    plt.title(title)
    ax = fig.add_subplot(111, projection='3d')
    
    noOfPoints = 1000
    x1 = [el[0] for el in trainInputs]
    x2 = [el[1] for el in trainInputs]
    xref = [min(x1) + i * (max(x1) - min(x1)) / noOfPoints for i in range(noOfPoints)]
    zref = [min(x2) + i * (max(x2) - min(x2)) / noOfPoints for i in range(noOfPoints)]
    yref = [w0 + w1 * el1 + w2 * el2 for el1, el2 in zip(xref, zref)]
    
    ax.scatter(xref, zref, yref)
    ax.set_xlabel('GDP')
    ax.set_ylabel('Freedom')
    ax.set_zlabel('Happiness')
    
    plt.show(block=False)

def main():
    crtDir =  os.getcwd()
    filePath = os.path.join(crtDir,'v3.csv')
    
    inputs, outputs = loadData(filePath, 'Economy..GDP.per.Capita.','Freedom', 'Happiness.Score')
    

    #plots--------------------------
    # gdp = [pair[0] for pair in inputs]
    # freedom = [pair[1] for pair in inputs]
    # plotDataHistogram(gdp, 'Capita. GDP')
    # plotDataHistogram(freedom, 'Freedom')
    # plotDataHistogram(outputs, 'Happiness score')


    # plotData(gdp, outputs,[], [], [], [], 'capita vs. hapiness')
    # plotData(freedom, outputs,[], [], [], [], 'freedom vs. hapiness')
    #-------------------------------

    # split data into training data (80%) and testing data (20%)
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace = False)
    validationSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    validationInputs = [inputs[i] for i in validationSample]
    validationOutputs = [outputs[i] for i in validationSample]
    

    #1. BY TOOL
    # training step
    #invata sa prezica pe baza celor 80% din date
    regressor = linear_model.LinearRegression()
    regressor.fit(trainInputs, trainOutputs)

    #makes predictions for test data
    # prezice restul de 20% din date
    computedValidationOutputs = regressor.predict(validationInputs)

    #plot the model (learnt by the tool)
    w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
    plotModel(trainInputs, w0, w1, w2, 'learnt model (tool regression)')
    
    #compute the differences between the predictions and real outputs
    print("BY TOOL: ")
    error = 0.0
    for t1, t2 in zip(computedValidationOutputs, validationOutputs):
        error += (t1 - t2) ** 2
    error = error / len(validationOutputs)
    print("prediction error (manual): ", error)
    
    error = mean_squared_error(validationOutputs, computedValidationOutputs)
    print("prediction error (tool):   ", error)



    #2. BY HAND -> TO BE IMPLEMENTED
    myRegressor=MyLinearBivariateRegression()
    x1, x2 = zip(*trainInputs)
    myRegressor.fit(x1,x2,trainOutputs)
    w1,w2,w0=myRegressor.coef_1,myRegressor.coef_2,myRegressor.intercept_

    #makes predictions for test data
    # prezice restul de 20% din date
    myComputedValidationOutputs = myRegressor.predict(validationInputs)

    plotModel(trainInputs, w0, w1, w2, 'learnt model (manual regression)')

    #compute the differences between the predictions and real outputs
    print("BY HAND: ")
    error = 0.0
    for t1, t2 in zip(myComputedValidationOutputs, validationOutputs):
        error += (t1 - t2) ** 2
    error = error / len(validationOutputs)
    print("prediction error (manual): ", error)
    
    error = mean_squared_error(validationOutputs, myComputedValidationOutputs)
    print("prediction error (tool):   ", error)

    _ = input("Press [enter] to continue.")

main()
import numpy as np
from math import exp
from math import log2
from numpy.linalg import inv
 
class MyLinearBivariateRegression:
    # def __init__(self, alpha=0.01, n_iterations=10000):
    #     #alpha e rata de invatare: cât de mult trebuie să ajusteze coeficienții modelului la fiecare actualizare a gradientului
    #     self.alpha = alpha
    #     self.n_iterations = n_iterations
    #     self.intercept=0.0
    #     self.coef_1=0.0
    #     self.coef_2=0.0
    
    def __init__(self):
        self.intercept=0.0
        self.coef_1=0.0
        self.coef_2=0.0

    # #metoda gradientului descendent
    # def fit(self, x1, x2, y):
    #     n = len(y)
    #     #creem matricea cu 1 pe prima linie, apoi x1,x2
    #     X = np.column_stack((np.ones(n), x1, x2))
    #     coef = np.zeros(3)

    #     for i in range(self.n_iterations):
    #         #produsul scalar dintre matricea X si coeficienti
    #         #coeficientii sunt 0 la inceput, apoi se inbunatatesc 
    #         y_pred = np.dot(X, coef)
    #         error = y_pred - y
    #         gradient = np.dot(X.T, error) / n
    #         coef -= self.alpha * gradient
            
    #     self.intercept_ = coef[0]
    #     self.coef_1 = coef[1]
    #     self.coef_2 = coef[2]

    #pe v2 iese determinatul 0, deci nu merge (caracteristici proportionale)
    #metoda celor mai mici patrate?
    def fit(self, x1, x2, y):
        n = len(x1)

        # calculez media fiecarei variabile
        x1_media = sum(x1) / n
        x2_media = sum(x2) / n
        y_media = sum(y) / n

        # calculez diferenta dintre valoarea fiecarui element si media (deviatia)
        x1_deviatie = [xi - x1_media for xi in x1]
        x2_deviatie = [xi - x2_media for xi in x2]
        y_deviatie = [yi - y_media for yi in y]
    
        # calculez suma patratelor deviatiei pentru fiecare variabila independenta
        ss_x1 = sum([xi**2 for xi in x1_deviatie])
        ss_x2 = sum([xi**2 for xi in x2_deviatie])
    
        # suma produselor intre perechi de deviatii
        ss_x1x2 = sum([xi * xj for xi, xj in zip(x1_deviatie, x2_deviatie)])
        ss_x1y = sum([xi * yj for xi, yj in zip(x1_deviatie, y_deviatie)])
        ss_x2y = sum([xi * yj for xi, yj in zip(x2_deviatie, y_deviatie)])

        # calculez coeficientii (aplicam formulele - poze)
        impartim_la = ss_x1 * ss_x2 - ss_x1x2**2

        self.coef_1 = (ss_x2 * ss_x1y - ss_x1x2 * ss_x2y) / impartim_la
        self.coef_2 = (ss_x1 * ss_x2y - ss_x1x2 * ss_x1y) / impartim_la
        self.intercept_ = y_media - self.coef_1 * x1_media - self.coef_2 * x2_media

    # predict the output for a given input
    def predict(self, validationInput):
        #y = w0 + w1 * x1 + w2 * x2
        x1 = [x[0] for x in validationInput]
        x2 = [x[1] for x in validationInput]
        if isinstance(x1, list) and isinstance(x2, list):
            return [self.intercept_ + self.coef_1 * val1 + self.coef_2 * val2 for val1, val2 in zip(x1, x2)]
        else:
            return self.intercept_ + self.coef_1 * x1 + self.coef_2 * x2
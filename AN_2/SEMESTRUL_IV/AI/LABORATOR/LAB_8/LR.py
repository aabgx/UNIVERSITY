from math import exp

# transform x real intr-un numar real intre 0 si 1
def sigmoid(x):
    return 1 / (1 + exp(-x))
    
#linear regression
class LR:
    def __init__(self):
        self.intercept_ = []
        self.coef_ = []

    #socastic
    def fit(self, x, y, learningRate = 0.001, noEpochs = 1000):
        #vector cu probabilitati pt loss
        vector=[]

        #adaugam noi coeficienti pentru fiecare clasa
        self.coef_.append([0.0 for _ in range(1 + len(x[0]))])

        for _ in range(noEpochs):
            for i in range(len(x)): 
                ycomputed = sigmoid(self.eval(x[i], self.coef_[-1]))
                crtError = ycomputed - y[i]

                for j in range(0, len(x[0])):
                    # actualizam coeficientii
                    # w[j] = w[j] - learningRate * crtError * x[i][j]
                    self.coef_[-1][j + 1] = self.coef_[-1][j + 1] - learningRate * crtError * x[i][j]
                
                # actualizam interceptul
                self.coef_[-1][0] = self.coef_[-1][0] - learningRate * crtError * 1
        
        # adaugam un nou coeficient pentru fiecare clasa
        self.intercept_.append(self.coef_[-1][0])
        self.coef_[-1] = self.coef_[-1][1:]

    #batch
    def fit_batch(self, x, y, learningRate=0.001, noEpochs=1000):
        self.coef_.append([0.0 for _ in range(1 + len(x[0]))])

        for _ in range(noEpochs):
            crtError=[]
            medii=[0]*len(x[0])

            for i in range(len(x)): 
                #vad care e predictia si calculez eroarea
                ycomputed = sigmoid(self.eval(x[i], self.coef_[-1])) 
                crtError.append(ycomputed - y[i])

                #calculez gradientul pt fiecare caracteristica a observatiei si il adaug mediei
                #trebuie sa tinem minte eroarea pt fiecare var independenta (actualizare doar la final)
                for j in range(0,len(x[0])):
                    medii[j] += crtError[-1] * x[i][j]

            #pt intercept (aici doar nu mai inmultim cu x[i][j])
            err=sum(crtError)/len(crtError)

            #actualizam coeficientii si interceptul
            #coef=coef-rata_invatare*gradient (/len(x) pt ca am facut suma mediilor)
            for j in range(len(x[0])): 
                self.coef_[-1][j] = self.coef_[-1][j] - learningRate * (medii[j] / len(x))

            #scadem din interceptul curent eroarea_medie*learningRate
            self.coef_[-1][len(x[0])] = self.coef_[-1][len(x[0])] - learningRate * err * 1
        
        #actualizam interceptul (adaugam unul nou pt fiecare clasa)
        self.intercept_.append(self.coef_[-1][0])
        self.coef_[-1] = self.coef_[-1][1:]
 
    #predictia modelului pt o singura observatie
    #y=coef[0]+coef[1]*x[0]+coef[2]*x[1]+...+coef[n]*x[n-1]
    def eval(self, xi, coef):
        yi = coef[0]
        for j in range(len(xi)):
            yi += coef[j + 1] * xi[j]
        return yi

    def predictOneSample(self, sampleFeatures):
        #calculam probabilitatea apartenentei la o anumita clasa
        coefficients0 = [self.intercept_[0]] + [c for c in self.coef_[0]]
        computedValue0 = sigmoid(self.eval(sampleFeatures, coefficients0))

        coefficients1 = [self.intercept_[1]] + [c for c in self.coef_[1]]
        computedValue1 = sigmoid(self.eval(sampleFeatures, coefficients1))

        coefficients2 = [self.intercept_[2]] + [c for c in self.coef_[2]]
        computedValue2 = sigmoid(self.eval(sampleFeatures, coefficients2))

        #alegem clasa cu probabilitatea maxima
        if max(computedValue0,computedValue1,computedValue2)==computedValue0:
            computedLabel=0
        elif max(computedValue0,computedValue1,computedValue2)==computedValue1:
            computedLabel=1
        else:
            computedLabel=2

        return computedLabel,computedValue0,computedValue1,computedValue2

    def predict(self, inTest):
        computedLabels = [self.predictOneSample(sample)[0] for sample in inTest]
        return computedLabels
    
    def log_loss(self,input):
        [self.predictOneSample(sample)[1:] for sample in input]
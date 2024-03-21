#batch gradient descent
#celalalt e socastic gradient descent
class HandBGDRegression:
    def __init__(self):
        self.intercept_ = 0.0
        self.coef_ = []

    #predictia modelului pt o singura observatie
    #yi e interceptul (initial) + suma (coeficienti * valoarea caracteristica corespunzatoare)
    def eval(self, xi):
        yi = self.coef_[-1]
        #for de la 0 la 1 ca avem 2 var independente
        for j in range(len(xi)):
            yi += self.coef_[j] * xi[j]
        return yi 

    #x contine variabilele independente (o poz pt fiecare observatie, o pereche)
    #y contine variabila dependenta
    def fit(self, x, y, learningRate = 0.001, noEpochs = 1000):
        #un elem in plus pt intercept
        #restul sunt coeficientii pt variabilele de intrare
        self.coef_ = [0.0 for _ in range(len(x[0]) + 1)]

        for _ in range(noEpochs):
            crtError=[]
            medii=[0]*len(x[0])

            for i in range(len(x)):
                #vad care e predictia si calculez eroarea
                ycomputed = self.eval(x[i])
                #asta e y estimat - y real (paranteza din formula)
                crtError.append(ycomputed - y[i])

                #calculez gradientul pt fiecare caracteristica a observatiei si il adaug mediei
                #gradientul reprezintă direcția în care trebuie să se miște coeficienții modelului de regresie pentru a minimiza eroarea medie pătratică
                for j in range(0,len(x[0])):
                    #trebuie sa tinem minte eroarea pt fiecare var independenta (actualizare doar la final)
                    #am medii[0] si medii[1]
                    #inmultim cu x1 (sau x2) din formula
                    medii[j]+=x[i][j]*crtError[-1]

            #pt intercept (aici doar nu mai inmultim cu x[i][j])
            err=sum(crtError)/len(crtError)

            #actualizam coeficientii si interceptul
            #coef=coef-rata_invatare*gradient (/len(x) pt ca am facut suma mediilor)
            for j in range(0, len(x[0])):
                self.coef_[j] = self.coef_[j] - learningRate * (medii[j]/len(x))
            
            #scadem din interceptul curent eroarea_medie*learningRate
            self.coef_[len(x[0])] = self.coef_[len(x[0])] - learningRate * err * 1
        
        #actualizam coeficientii si interceptul
        self.intercept_ = self.coef_[-1]
        self.coef_ = self.coef_[:-1]

    def predict(self, x):
        yComputed = [self.eval(xi) for xi in x]
        return yComputed
    
    #gradientul e un vector care are componente derivatele partiale ale functiei de loss dupa fiecare coeficient
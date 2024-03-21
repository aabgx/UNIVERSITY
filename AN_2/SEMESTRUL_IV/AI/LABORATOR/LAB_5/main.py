from cmath import sqrt
from sklearn.metrics import hamming_loss, log_loss
from sklearn.metrics._classification import accuracy_score, precision_score, recall_score

def readCSVInt(filename):
    with open(filename, "r") as file:
        lines = file.readlines()
        matrix = []
        for line in lines:
            if line.startswith("Weight"):
                continue
            line = line.strip()
            matrix.append([int(x) for x in line.split(",")])

    mid=len(matrix[0])//2
    matrix1 = [row[:mid] for row in matrix]
    matrix2 = [row[mid:] for row in matrix]
        
    return matrix1,matrix2
    

def readCSVString(filename):
    with open(filename) as file:
        lines = file.readlines()
        matrix =[]
        for line in lines:
            if line.startswith("Type"):
                continue
            line = line.strip()
            matrix.append([x for x in line.split(",")])

    mid=len(matrix[0])//2
    matrix1 = [row[:mid] for row in matrix]
    matrix2 = [row[mid:] for row in matrix]
        
    return matrix1,matrix2

#eroare loss
#loss pt problema multi-target
def eroare_multi_target(realOutputs,computedOutputs):
    sumaerr=0
    sumaloss=0
    for i in range(len(realOutputs)):
        c=computedOutputs[i]
        r=realOutputs[i]
        err=0
        loss=0
        for i in range(len(c)):
            err+=abs(c[i]-r[i])
            loss+=(c[i]-r[i])**2
        sumaerr+=err/len(c) #adica /3 la noi
        sumaloss+=loss/len(c)

    return sumaerr/len(realOutputs), sumaloss / len(realOutputs)

#loss pt clasificare multi-class
def apr_multi_class(realLabels,computedLabels,labelNames):
    acc = accuracy_score(realLabels, computedLabels)
    precision = precision_score(realLabels, computedLabels, average = None, labels = labelNames)
    recall = recall_score(realLabels, computedLabels, average = None, labels = labelNames)
    loss=hamming_loss(realLabels, computedLabels)
    return acc, precision, recall,loss

#loss pt clasificare binara
def loss_binara():
    realLabels=['spam', 'spam', 'ham', 'ham', 'spam', 'ham']
    computedLabels= [ [0.7, 0.3], [0.2, 0.8], [0.4, 0.6], [0.9, 0.1], [0.7, 0.3], [0.4, 0.6]]
    return log_loss(realLabels, computedLabels)

def main():
    #eroare si loss pt multi-target
    input,output = readCSVInt("sport.csv")
    eroare,loss=eroare_multi_target(input, output)
    print("EROARE: ", eroare, "LOSS: ", loss)

    #acuratete, precizie, recall, loss pt clasificare multi-class
    input,output = readCSVString("flowers.csv")
    lista_atribute=list(set([x[0] for x in input] + [x[0] for x in output]))
    # print(lista_atribute)
    acc,precision,recall,loss=apr_multi_class(input, output, lista_atribute)
    print('ACC: ', acc, 'PRECISION: ', precision, 'RECALL: ', recall,"LOSS: ",loss)

    #loss pt clasificare binara
    print("LOSS BINARA (EMAILS): ", loss_binara())
main()
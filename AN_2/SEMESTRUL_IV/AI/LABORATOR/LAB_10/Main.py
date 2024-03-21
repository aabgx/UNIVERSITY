import csv
import os
import time
import numpy as np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
import gensim
from sklearn.cluster import KMeans
from sklearn.metrics import accuracy_score
from sklearn.linear_model import LogisticRegression
from sklearn.semi_supervised import LabelPropagation

#pentru word2vec
#model: model de incorporare a cuvintelor (reprezentare a cuvintelor sub formă de vectori numerici)
#data: lista de fraze
def featureComputation(model, data):
    features = []

    #despartire fraze pe cuvinte
    phrases = [ phrase.split() for phrase in data]

    #calculam vectorul mediu pentru fiecare fraza (cuvantul sa aiba mai mult de 2 litere si sa fie in model)
    for phrase in phrases:
        vectors = [model[word] for word in phrase if (len(word) > 2) and (word in model.index_to_key)]

        #fraza nu contine cuvinte care sa indeplineasca caracteristicile
        if len(vectors) == 0:
            result = [0.0] * model.vector_size

        #calculează caracteristica efectivă pentru frază (medie a vectorilor din lista)
        else:
            result = np.sum(vectors, axis=0) / len(vectors)

        features.append(result)
    return features

#alg. nesupervizat de grupare a datelor
#se antreneaza doar pe trainFeatures
def kmeans_tool(trainFeatures,testFeatures,labelNames,testOutputs):
    #impartim in 2 grupuri
    unsupervisedClassifier = KMeans(n_clusters=2, n_init='auto',random_state=0)

    #antrenam modelul si prezicem (in 0 sau 1)
    unsupervisedClassifier.fit(trainFeatures)
    computedTestIndexes = unsupervisedClassifier.predict(testFeatures)
    computedTestOutputs = [labelNames[value] for value in computedTestIndexes]

    #returnam acuratetea
    return accuracy_score(testOutputs, computedTestOutputs)

#regresie liniara
#se antreneaza pe trainInputs si trainOutputs
def log_reg(trainInputs,trainOutputs,validationInputs,validationOutputs):
    logreg = LogisticRegression(max_iter=10000)
    logreg.fit(trainInputs, trainOutputs)
    computedValidationOutputs = logreg.predict(validationInputs)
    return accuracy_score(validationOutputs, computedValidationOutputs)

#nu ține cont de sensul cuvintelor sau de contextul în care apar în text
#captureaza doar frecventa in cuvintelor in document
#NESUPERVIZAT, SUPERVIZAT SI BAG OF WORDS
def bag_of_words(trainInputs,testInputs,testOutputs,labelNames,trainOutputs):
    #transformam datele intr-un format de tip bag of words
    vectorizer = CountVectorizer()
    trainFeatures = vectorizer.fit_transform(trainInputs)
    testFeatures = vectorizer.transform(testInputs) 

    #afisam acuratetea nesupervizat si supervizat
    print("BAG OF WORDS ACC (NESUPERVIZAT): ", kmeans_tool(trainFeatures, testFeatures, labelNames, testOutputs))
    print("BAG OF WORDS ACC (SUPERVIZAT): ", log_reg(trainFeatures,trainOutputs,testFeatures,testOutputs))
    
#Term Frequency (TF) - nr aparitii in document / nr total de cuvinte din document
#Inverse Document Frequency (IDF) - log(nr total de documente / nr documente in care apare cuvantul)
#TF-IDF = TF * IDF
#NESUPERVIZAT, SUPERVIZAT SI TF-IDF
def tf_idf(trainInputs,testInputs,testOutputs,labelNames,trainOutputs):
    #transformam datele intr-un format de tip tf-idf
    vectorizer = TfidfVectorizer(max_features=50)
    trainFeatures = vectorizer.fit_transform(trainInputs)
    testFeatures = vectorizer.transform(testInputs)

    #afisam acuratetea nesupervizat si supervizat
    print("TF-IDF ACC (NESUPERVIZAT): ",kmeans_tool(trainFeatures, testFeatures, labelNames, testOutputs))
    print("TF-IDF ACC (SUPERVIZAT): ", log_reg(trainFeatures,trainOutputs,testFeatures,testOutputs))
    
#NESUPERVIZAT, SUPERVIZAT SI WORD2VEC
def word2vec(trainInputs,testInputs,testOutputs,labelNames,trainOutputs):
    #incarcam modelul
    crtDir =  os.getcwd()
    modelPath = os.path.join(crtDir, 'GoogleNews-vectors-negative300.bin')
    word2vecModel300 = gensim.models.KeyedVectors.load_word2vec_format(modelPath, binary=True,limit=500000) 

    #calculam caracteristicile
    trainFeatures = featureComputation(word2vecModel300, trainInputs)
    testFeatures = featureComputation(word2vecModel300, testInputs)

    #afisam acuratetea nesupervizat si supervizat
    print("WORD2VEC ACC (NESUPERVIZAT): ",kmeans_tool(trainFeatures, testFeatures, labelNames, testOutputs))
    print("WORD2VEC ACC (SUPERVIZAT): ", log_reg(trainFeatures,trainOutputs,testFeatures,testOutputs))

#SEMISUPERVIZAT SI N-GRAMS
def semi_supervizat(trainInputs, trainOutputs, testInputs, testOutputs, labelNames):
    # transformarea textului în reprezentări numerice bazate pe n-grams și ponderi TF-IDF
    # vom avea unigrams (cate un cuvant) si bigrams (cate 2 cuvinte) pt acest exemplu
    ngram_vectorizer = TfidfVectorizer(ngram_range=(1,2), max_features=50)

    trainFeatures = ngram_vectorizer.fit_transform(trainInputs)
    testFeatures = ngram_vectorizer.transform(testInputs)

    y = []
    for output in trainOutputs:
        if output == labelNames[0]:
            y.append(0)
        else:
            y.append(1)

    label_prop_model = LabelPropagation(kernel='knn', n_neighbors=5, max_iter=200)

    # setam maxim 30% din date ca fiind neetichetate
    rng = np.random.RandomState(int(time.time()))
    random_unlabeled_points = rng.rand(len(y)) < 0.3
    labels = np.copy(y)
    labels[random_unlabeled_points] = -1
    label_prop_model.fit(trainFeatures.toarray(), labels)

    computedTestIndexes = label_prop_model.predict(testFeatures)
    computedTestOutputs = [labelNames[value] for value in computedTestIndexes]

    print("SEMI-SUPERVIZAT ACC:", accuracy_score(testOutputs, computedTestOutputs))

#citirea datelor
def get_data():
    crtDir =  os.getcwd()
    fileName = os.path.join(crtDir, 'reviews_mixed.csv')

    data = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            data.append(row)

    inputs=[]
    for i in range(len(data)):
        line=""
        for j in range(len(data[i])-1):
            line+=data[i][j] + " "
        inputs.append(line)

    outputs = [data[i][-1] for i in range(len(data))]
    labelNames = list(set(outputs))

    return inputs,outputs,labelNames

def main():
    #luam date existente
    inputs,outputs,labelNames=get_data()

    #impartirea in 80% train si 20% test
    np.random.seed(5)
    noSamples = len(inputs)
    indexes = [i for i in range(noSamples)]
    trainSample = np.random.choice(indexes, int(0.8 * noSamples), replace = False)
    testSample = [i for i in indexes  if not i in trainSample]
    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    #aplicam cele 4 metode 
    bag_of_words(trainInputs, testInputs, testOutputs, labelNames,trainOutputs)
    print('')
    tf_idf(trainInputs, testInputs, testOutputs, labelNames,trainOutputs)
    print('')
    word2vec(trainInputs, testInputs, testOutputs, labelNames,trainOutputs)
    print('')
    semi_supervizat(trainInputs, trainOutputs, testInputs, testOutputs, labelNames)
    
main()
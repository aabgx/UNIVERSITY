from GA import GA
import math

def readFromTSP(filePath):
    # Citirea fișierului .tsp
    with open(filePath, 'r') as file:
        lines = file.readlines()
        node_coords = []
        for line in lines:
            if line.startswith('EOF'):
                break
            if line.startswith(('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')):
                parts = line.split()
                x = float(parts[1])
                y = float(parts[2])
                node_coords.append((x, y))

    # Calcularea distanțelor dintre noduri
    n = len(node_coords)
    dist_matrix = [[0] * n for i in range(n)]
    for i in range(n):
        for j in range(i + 1, n):
            dist = math.sqrt((node_coords[i][0] - node_coords[j][0]) ** 2 +
                             (node_coords[i][1] - node_coords[j][1]) ** 2)
            dist_matrix[i][j] = dist
            dist_matrix[j][i] = dist
    return dist_matrix

def main():
    #citire din fisier
    f=open("easy.txt","r")
    n=int(f.readline())
    mat=[]
    for i in range(0,n):
        linie=f.readline()
        rand=linie.split(",")
        rand_numere=[int(x) for x in rand]
        mat.append(rand_numere)
    f.close()

    #citire din fisier tsp
    # filename="berlin.tsp"
    # mat=readFromTSP(filename)
    # n=len(mat)
    
    #initializare parametrii
    param={}
    param['mat']=mat
    param['noNodes']=n
    gaParam = {'popSize' : 300, 'noGen' : 20000}
    fitness=[]
    ga = GA(gaParam, param)
    ga.initialisation()
    ga.evaluation()

    #algoritmul genetic propriu-zis
    for g in range(gaParam['noGen']):
        ga.oneGeneration ()
        fitness.append(ga.bestChromosome().fitness)

    #afisare rezultat
    rez=ga.bestChromosome().repres
    # for i in ga.population:
    #     if i.fitness==ga.bestChromosome().fitness:
    #         print(i.repres)

    rezultat=[]
    for i in range(0,len(rez)):
        rezultat.append(rez[i])
    #print(rezultat)
    print(ga.bestChromosome().fitness)

    #afisare grafic
    import matplotlib.pyplot as plot
    plot.plot(fitness)
    plot.show()

main()
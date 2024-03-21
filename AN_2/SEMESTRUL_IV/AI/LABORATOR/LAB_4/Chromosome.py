from random import randint,seed

def generateARandomPermutation(n):
    perm = [i for i in range(n)]
    pos1 = randint(0, n - 1)
    pos2 = randint(0, n - 1)
    perm[pos1], perm[pos2] = perm[pos2], perm[pos1]
    return perm

# permutation-based representation
class Chromosome:
    def __init__(self, problParam = None):
        self.__problParam = problParam  #problParam has to store the number of nodes/cities
        self.__repres = generateARandomPermutation(self.__problParam['noNodes'])
        self.__fitness = 0.0
    
    @property
    def repres(self):
        return self.__repres 
    
    @property
    def fitness(self):
        return self.__fitness 
    
    @repres.setter
    def repres(self, l = []):
        self.__repres = l 
    
    @fitness.setter 
    def fitness(self, fit = 0.0):
        self.__fitness = fit 
    
    #luam din self impartirea de la pozitia 1 la pozitia 2 si apoi completam nodurile ramase din al doilea cromozom
    def crossover(self, c):
        pos1=randint(0,self.__problParam['noNodes']//2)
        pos2=randint(self.__problParam['noNodes']//2,self.__problParam['noNodes']-1)+1

        new_repres=self.__repres[pos1:pos2]
        ramase=[i for i in c.__repres if i not in new_repres]
        new_repres+=ramase

        new_chromosome=Chromosome(self.__problParam)
        new_chromosome.repres=new_repres
        return new_chromosome

    #iau 2 pozitii random si fac swap intre ele
    def mutation(self):
        pos1 = randint(0, self.__problParam['noNodes'] - 1)
        pos2 = randint(0, self.__problParam['noNodes'] - 1)

        self.__repres[pos1],self.__repres[pos2]=self.__repres[pos2], self.__repres[pos1]

    def __str__(self):
        return "\nChromo: " + str(self.__repres) + " has fit: " + str(self.__fitness)
    
    def __repr__(self):
        return self.__str__()
    
    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness
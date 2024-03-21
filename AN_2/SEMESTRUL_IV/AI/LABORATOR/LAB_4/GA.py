from random import randint
from Chromosome import Chromosome

class GA:
    def __init__(self, param = None, problParam = None):
        self.__param = param
        self.__problParam = problParam
        self.__population = []
        
    @property
    def population(self):
        return self.__population
    
    def initialisation(self):
        for _ in range(0, self.__param['popSize']):
            c = Chromosome(self.__problParam)
            self.__population.append(c)
    
    def evaluation(self):
        for c in self.__population:
            c.fitness = self.modularity(c.repres)
            
    def bestChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness < best.fitness):
                best = c
        return best
        
    def worstChromosome(self):
        best = self.__population[0]
        for c in self.__population:
            if (c.fitness > best.fitness):
                best = c
        return best

    def selection(self):
        pos1 = randint(0, self.__param['popSize'] - 1)
        pos2 = randint(0, self.__param['popSize'] - 1)
        if (self.__population[pos1].fitness < self.__population[pos2].fitness):
            return pos1
        else:
            return pos2 
        
    #il tin pe cel mai bun si fac crossover si mutatii cu populatia
    def oneGeneration(self):
        newPop = []
        newPop.append(self.bestChromosome())
        for _ in range(self.__param['popSize']):
            p1 = self.__population[self.selection()]
            p2 = self.__population[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            newPop.append(off)
        self.__population = newPop
        self.evaluation()
        
    #functie de modularitate
    def modularity(self,repres):
        fit=0
        mat=self.__problParam["mat"]
        for i in range(self.__problParam["noNodes"]):
            if i==self.__problParam["noNodes"]-1:
                fit+=mat[repres[i]][repres[0]]
            else:
                fit+=mat[repres[i]][repres[i+1]]
        return fit 
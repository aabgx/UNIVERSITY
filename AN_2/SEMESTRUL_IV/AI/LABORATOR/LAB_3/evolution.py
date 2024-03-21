
import copy
from random import randint
from tema.Graph import Graph
from tema.chromosome import Chromosome
from tema.utils import modularity


class Evolution:
    def __init__(self, nr_cromozomi, graph: Graph):
        self.__nr_cromozomi = nr_cromozomi
        self.__cromozomi = []
        self.__graph=graph
        
    @property
    def cromozomi(self):
        return self.__cromozomi
    
    def initialisation(self):
        for _ in range(0, self.__nr_cromozomi):
            c = Chromosome(self.__graph)
            self.__cromozomi.append(c)
    
    def evaluation(self):
        for c in self.__cromozomi:
            c.fitness = modularity(self.__graph,c.impartire)
            
    def best_chromosome(self) -> Chromosome:
        best = self.__cromozomi[0]
        for c in self.__cromozomi:
            if (c.fitness > best.fitness):
                best = c
        return copy.deepcopy(best)
        
    def worst_chromosome(self):
        best = self.__cromozomi[0]
        for c in self.__cromozomi:
            if (c.fitness < best.fitness):
                best = c
        return best

    def selection(self):
        pos1 = randint(0, self.__nr_cromozomi - 1)
        pos2 = randint(0, self.__nr_cromozomi - 1)
        if (self.__cromozomi[pos1].fitness < self.__cromozomi[pos2].fitness):
            return pos1
        else:
            return pos2 
        
    #face crossover intre cromozomi random (nu tine cont de cel mai bun cromozom)
    def one_generation(self):
        newPop = []
        for _ in range(self.__nr_cromozomi):
            p1 = self.__cromozomi[self.selection()]
            p2 = self.__cromozomi[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            newPop.append(off)
        self.__cromozomi = newPop
        self.evaluation()

    #tot face crossover intre cromozomi random, dar se asigura ca il pastreaza pe cel mai bun
    def one_generation_elitism(self):
        newPop = [self.best_chromosome()]
        for _ in range(self.__nr_cromozomi - 1):
            p1 = self.__cromozomi[self.selection()]
            p2 = self.__cromozomi[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            newPop.append(off)
        self.__cromozomi = newPop
        self.evaluation()
        
    #
    def one_generation_steady_state(self):
        for _ in range(self.__nr_cromozomi):
            p1 = self.__cromozomi[self.selection()]
            p2 = self.__cromozomi[self.selection()]
            off = p1.crossover(p2)
            off.mutation()
            off.fitness = modularity(off.impartire)
            worst = self.worstChromosome()
            if (off.fitness < worst.fitness):
                worst = off  
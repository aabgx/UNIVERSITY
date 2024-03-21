import copy
from random import randint

from tema.Graph import Graph
from tema.utils import modularity

def get_random_community(graph: Graph) -> list[int]:
    n = graph.number_of_nodes()
    comunity = [0 for _ in range(n)]
    no_comunity = 1
    for i in range(n):
        adj_list = graph.get_neighbors(i)
        if comunity[i] != 0:
            continue
        comunity[i] = no_comunity

        for j in adj_list:
            if graph.get_degree(j) < graph.get_degree(i) // 2:
                ok = False
                comunity[j] = no_comunity

        no_comunity += 1
    return comunity

class Chromosome:
    #aici facem o impartire (not so) random pe comunitati
    def __init__(self, graph: Graph, impartire = None):
        self.__graph=graph
        self.__fitness=0.0
        self.__impartire=impartire

        m=graph.number_of_edges()
        n=graph.number_of_nodes()

        self.__impartire=get_random_community(self.__graph)


    @property
    def impartire(self):
        return self.__impartire
    
    @property
    def fitness(self):
        return self.__fitness 
    
    @impartire.setter
    def impartire(self, l=[]):
        self.__impartire = l

    @fitness.setter 
    def fitness(self, fit = 0.0):
        self.__fitness = fit
    
    #met 1(one-way): luam o comunitate din self si o punem in alta_impartire
    #met 2(standard uniform): o masca random (0&1) luam din self la 0 si din alta_impartire la 1
    #met 3(binomial): ???

    #folosesc one-way
    #mut o comunitate din prima cu totul
    def crossover(self, alta):
        index_random=randint(0,self.__graph.number_of_nodes()-1)

        comunitate=self.__impartire[index_random]
        alta_impartire= alta.__impartire
        #aceeasi comm de la acelasi index, dar din al 2-lea cromozom
        alta_comunitate=alta_impartire[index_random]

        impartire_noua= []

        for x in range (0,len(alta_impartire)):
            if self.__impartire[x]==comunitate:
                impartire_noua.append(alta_comunitate)
            else:
                impartire_noua.append(alta_impartire[x])

        new_chromosome = Chromosome(self.__graph, impartire_noua)

        return new_chromosome
    
    def modifica_sau_nu_imbunatatire(self,index_nod,index_vecin):
        initial = modularity(self.__graph, self.__impartire)

        noua_impartire = copy.deepcopy(self.__impartire)
        noua_impartire[index_nod] = self.__impartire[index_vecin] 

        after =  modularity(self.__graph,noua_impartire)
        if after > initial:
            self.__impartire[index_nod]=self.__impartire[index_vecin]
            
        #return modularity(self.__graph,noua_impartire)
    
    #atribui unui nod random comunitatea unuia dintre vecinii sai (best vecin)
    def mutation(self):
        # index_random=randint(0,self.__graph.number_of_nodes()-1)
        # vecini_index=self.__graph.get_neighbors(index_random)

        # best_rez = 0.0
        # best_index = index_random

        # for i in vecini_index:
        #     if self.__impartire[index_random] != self.__impartire[i]:
        #         rez_calcul = self.calculeaza_imbunatatire(index_random,i)
        #         if best_rez < rez_calcul:
        #             best_index = i
        #             best_rez = rez_calcul

        # self.__impartire[index_random] = self.__impartire[best_index]

        index_random=randint(0,self.__graph.number_of_nodes()-1)
        vecini_index=self.__graph.get_neighbors(index_random)

        # best_rez = 0.0
        # best_index = index_random

        for i in vecini_index:
            if self.__impartire[index_random] != self.__impartire[i]:
                self.modifica_sau_nu_imbunatatire(index_random,i)
                # if best_rez < rez_calcul:
                    # best_index = i
                    # best_rez = rez_calcul

        # self.__impartire[index_random] = self.__impartire[best_index]
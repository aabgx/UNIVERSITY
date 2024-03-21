import networkx

from tema.Graph import Graph

#evaluarea impartirii pe comunitati
def modularity( graph: Graph, communities):
    m=graph.number_of_edges()
    n=graph.number_of_nodes()
    modularity_constanta=2*m
    rezultat=0.0

    for i in range(n):
        for j in range(n):
            if communities[i]==communities[j]: #daca i si j sunt in aceeasi comunitate
                degrees_i=graph.get_degree(i) #nr de muchii ce ies din nodul i
                degrees_j=graph.get_degree(j) #nr de muchii ce ies din nodul j
                exista_muchie= 1 if graph.has_edge(i,j) else 0
                rezultat+=(exista_muchie-degrees_i*degrees_j/modularity_constanta)

    return rezultat*1/modularity_constanta

import numpy
import matplotlib.pyplot as plot
import warnings 
import networkx

from evolution import Evolution
from tema.Graph import Graph

warnings.simplefilter('ignore')


#reader pentru graf din fisiere
def reader(file_name):
     #aflu extensia fisierului
    file_extension = file_name.split(".")[-1]

    #decid cum citesc in functie de extensie
    if file_extension == "txt":  # Pentru fisierele txt
        graph = networkx.read_edgelist(file_name)
    elif file_extension == 'gml':  # Extensia e gml
        graph = networkx.read_gml(file_name, label='id')
    else: #este csv
        graph = networkx.Graph()
        with open(file_name, "r") as file:
            _ = file.readline()
            while True:
                line = file.readline()
                if not line:
                    break
                elems = line.split(",")
                i = elems[0]
                j = elems[1]
                graph.add_edge(i,j)
    
    return graph

def plot_communities(graph, communities):
        numpy.random.seed(123)  # to freeze the graph's view (networks uses a random view)

        pos = networkx.spring_layout(graph)  # compute graph layout
        plot.figure(figsize=(10,15))  # image is 8 x 8 inches
        networkx.draw_networkx_nodes(graph,pos,node_size=100,cmap=plot.cm.RdYlBu,node_color=communities)
        networkx.draw_networkx_edges(graph, pos, alpha=0.3)
        plot.show()

def run(nr_generatii,nr_cromozomi):
    #file_name="tema/data/dolphins/classLabeldolphins.txt"
    file_name="tema/data/football/classLabelfootball.txt"
    # file_name="tema/data/dolphins/dolphins.gml"
    # file_name="tema/data/football/football.gml"
    #file_name="tema/data/facebook.txt"
    #file_name="tema/data/karate/karate.gml"
    #file_name="tema/data/krebs/krebs.gml"
    #file_name="tema/data/emailEu.txt"
    #file_name="tema/data/emailEu.txt"

    graph=Graph(reader(file_name))

    evolutie = Evolution(nr_cromozomi,graph)
    evolutie.initialisation()
    progress = []
    for _ in range(nr_generatii):
        evolutie.one_generation_elitism()
        progress.append(evolutie.best_chromosome().fitness)
    plot.plot(progress)
    plot.show()
    plot_communities(graph.get_networkx_graph(),evolutie.best_chromosome().impartire)

run(100,5)
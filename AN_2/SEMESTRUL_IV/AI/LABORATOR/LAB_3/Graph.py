import networkx


class Graph:
    def __init__(self, graph: networkx.Graph):
        self.__graph = graph
        self.__neighbors = []
        self.__n = graph.number_of_nodes()
        self.__m = graph.number_of_edges()
        self.__self_nodes = [False for _ in range(self.__n)]
        self.__matrice = [[0 for _ in range(self.__n)] for _ in range(self.__n)]
        self.__marginal_nodes = []
        self.__is_marginal_node = [False for _ in range(self.__n)]

        for i in range(graph.number_of_nodes()):
            self.__neighbors.append([])  # add empty list for each node
        nodes = list(graph.nodes)
        for el in graph.edges:  # edge is a tuple (node1, node2)
            idx_node1 = nodes.index(el[0])
            idx_node2 = nodes.index(el[1])
            if idx_node1 == idx_node2:
                self.__self_nodes[idx_node1] = True
            self.__neighbors[idx_node1].append(
                idx_node2
            )  # add node2 to node1's list
            self.__neighbors[idx_node2].append(
                idx_node1
            )  # add node2 to node1's list
            self.__matrice[idx_node1][idx_node2] = 1
            self.__matrice[idx_node2][idx_node1] = 1

    def get_neighbors(self, node: int) -> list[int]:  # Time complexity: O(1)
        return self.__neighbors[node]

    def get_degree(self, node: int) -> list[int]:  # Time complexity: O(1)
        return len(self.__neighbors[node])

    def number_of_nodes(self) -> int:
        return self.__n

    def number_of_edges(self) -> int:
        return self.__m

    def has_edge(self, node1: int, node2: int) -> bool:
        return self.__matrice[node1][node2] == 1

    def get_networkx_graph(self) -> networkx.Graph:
        return self.__graph

    def add_marginal_node(self, marginal_node: int):
        self.__marginal_nodes.append(marginal_node)
        self.__is_marginal_node[marginal_node] = True

    def pop_marginal_node(self):
        marginal_node = self.__marginal_nodes.pop()
        self.__is_marginal_node[marginal_node] = False
        return marginal_node

    @property
    def marginal_nodes(self) -> list[int]:
        return self.__marginal_nodes

    @marginal_nodes.setter
    def marginal_nodes(self, marginal_nodes: list[int]):
        self.__marginal_nodes = marginal_nodes

    def is_marginal_node(self, node: int):
        return self.__is_marginal_node[node]

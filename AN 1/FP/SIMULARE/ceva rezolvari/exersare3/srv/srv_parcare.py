import os
import sys
sys.path.append(os.getcwd() + '\\')
from repo.repo_parcare import *

class ServiceParcare:
    def __init__(self,repo_parcare):
        self.__repo_parcare = repo_parcare
    def cautare(self,x):
        lista = []
        lista = self.__repo_parcare.cautare(x)
        return lista

    def raport(self):
        lista = self.__repo_parcare.raport()
        return lista

def test_cautare():
    repo_test = RepoParcare('./data/parcari_test.txt')
    srv_test = ServiceParcare(repo_test)

    assert(len(srv_test.cautare('Dorobantilor')) == 2)
    assert(srv_test.cautare('Dorobantilor')[0].get_nume() == 'W903')

test_cautare()
    


import os
import sys
sys.path.append(os.getcwd() + '\\')

from repo.repo_parcare import *
from srv.srv_parcare import *
from UI.consola import *

repo_parcare = RepoParcare('./data/parcari.txt')
srv_parcari = ServiceParcare(repo_parcare)

ui = consola(srv_parcari)
ui.start()
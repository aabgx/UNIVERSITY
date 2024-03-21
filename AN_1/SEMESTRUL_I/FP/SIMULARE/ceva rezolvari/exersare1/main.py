import os
import sys
sys.path.append(os.getcwd() + '\\')

from repo.repo_show import *
from service.srv_show import *
from UI.consola import *

repository = repo_show('./data/show.txt')
service = srv_show(repository)

ui = consola(service)
ui.start()
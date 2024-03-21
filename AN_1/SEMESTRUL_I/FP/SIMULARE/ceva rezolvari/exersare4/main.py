import os
import sys
sys.path.append(os.getcwd() + "\\")

from repo.repo_firma import *
from service.srv_firma import * 
from UI.consola import *

repository_firma = repo_firma('./data/firme.txt')
service_firma = srv_firma(repository_firma)

ui = consola(service_firma)
ui.start()
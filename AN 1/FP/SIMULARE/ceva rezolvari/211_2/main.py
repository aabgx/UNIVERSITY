import os
import sys
sys.path.append(os.getcwd() + '\\')

from UI.consola import *

repository = repo_playlist('./data/playlist.txt')
service = srv_playlist(repository)

ui = consola(service)
ui.start()
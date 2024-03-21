import os
import sys
sys.path.append(os.getcwd() + "\\")
from domain.validators import StudentValidator, LaboratorValidator, NotareValidator
from repository.stud import stud_repository
from repository.stud_file import stud_repository_file
from repository.lab_file import lab_repository_file
from repository.lab import lab_repository
from repository.nota import note_repository,note_repository_file
from service.lab_service import LabService
from service.stud_service import StudentService
from service.nota_service import NotaService
from ui.consola import Console

repo_stud = stud_repository_file('./data/stud.txt')
val_stud = StudentValidator()
srv_stud = StudentService(repo_stud, val_stud)

repo_lab = lab_repository_file('./data/lab.txt')
val_lab = LaboratorValidator()
srv_lab = LabService(repo_lab,val_lab)

repo_note = note_repository_file('./data/note.txt')

srv_note = NotaService(repo_note, repo_lab, repo_stud)

ui = Console(srv_stud,srv_lab,srv_note)
ui.start()

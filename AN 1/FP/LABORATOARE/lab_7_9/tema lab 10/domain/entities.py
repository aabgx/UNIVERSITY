class student:
    def __init__(self, studentID, nume, grup):
        """
        Creeaza un obiect student cu ID, nume și grupa din care face parte date.
        :param studentID: ID unic student
        :type studentID: str (numar>0)
        :param nume: numele și prenumele studentului
        :type nume: srt (>2)
        :param grup: grupa din care face parte studentul
        :type grup: str (numar>0)
        """
        self.__studentID = studentID
        self.__nume = nume
        self.__grup = grup

    def getstudentID(self):
        return self.__studentID

    def getnume(self):
        return self.__nume

    def getgrup(self):
        return self.__grup

    def setstudentID(self, value):
        self.__studentID = value

    def setnume(self, value):
        self.__nume = value

    def setgrup(self, value):
        self.__grup = value

    def __eq__(self, other):
        """
        Verifică egalitatea între studentul curent și studentul other.
        :param other: studentul pentru care se verifică egalitatea
        :type other: student
        :return: True dacă studenții sunt identici (=au același ID), False altfel
        :rtype: bool
        """
        if self.__studentID == other.getstudentID():
            return True
        return False

    def __str__(self):
        """
        Afișează în forma dorită datele pentru un anumit student.
        """
        return "ID student: " + str(self.__studentID) + '; Nume: ' + str(self.__nume) + '; Grupa: ' + str(
            self.__grup)


class laborator:
    def __init__(self,lab_prob,descriere,deadline):
        """
        Creeaza un obiect laborator cu număr laborator_număr problemă, descriere și deadline-ul de predare.
        :param nr_lab_nr_prob: numărul laboratorului și numărul problemei cerute
        :type nr_lab_nr_prob: str (>2 caractere: str_str)
        :param descriere: date despre problemă
        :type descriere: srt (>2 caractere)
        :param deadline: data limită de predare a laboratorului
        :type deadline: str (>2 caractere: zz.ll.aaaa - zi>0 si zi<31, luna>0 si luna<13, an>2019 si an<2022)
        """
        self.__lab_prob = lab_prob
        self.__descriere = descriere
        self.__deadline = deadline

    def getlab_prob(self):
        return self.__lab_prob

    def getdescriere(self):
        return self.__descriere

    def getdeadline(self):
        return self.__deadline

    def setlab_prob(self, value):
        self.__lab_prob = value

    def setdescriere(self, value):
        self.__descriere = value

    def setdeadline(self, value):
        self.__deadline = value  

    def __eq__(self, other):
        """
        Verifică egalitatea între laboratorul curent și laboratorul other.
        :param other: laboratorul pentru care se verifică egalitatea
        :type other: laborator
        :return: True dacă laboratoarele sunt identice (=au același lab_prob), False altfel
        :rtype: bool
        """
        if self.__lab_prob == other.getlab_prob():
            return True
        return False

    def __str__(self):
        """
        Afișează în forma dorită datele pentru un anumit laborator.
        """
        return "Nr. laborator și nr. problemă: " + str(self.__lab_prob) + '; Descriere: ' + str(self.__descriere) + '; deadline: ' + str(
            self.__deadline)

class notare:
    def __init__(self, student, laborator, nota):
        self.__student = student
        self.__laborator = laborator
        self.__nota = nota

    def getstudent(self):
        return self.__student

    def getlaborator(self):
        return self.__laborator

    def getnota(self):
        return self.__nota

    def setstudent(self, value):
        self.__student = value

    def setlaborator(self, value):
        self.__laborator = value

    def setnota(self, value):
        self.__nota = value

    def __eq__(self, other):
        if self.__student == other.getstudent() and self.__laborator == other.getlaborator():
            return True
        return False

    def __str__(self):
        
        return 'Student: ' + str(self.getstudent()) + ' Laborator: ' + str(self.getlaborator()) + ' Nota: ' + str(self.getnota())

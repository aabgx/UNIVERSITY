package repo;

import domain.Utilizator;

import java.time.LocalDate;
import java.util.List;

public class UtilizatorRepository extends FileRepository<String,Utilizator>{

    /***
     * constructor
     * @param fileName - numele fisierului
     */
    public UtilizatorRepository(String fileName){super(fileName);}

    /**
     * Metoda de a transforma o linie in entitate specific pentru o Utilizator
     * @param line - linia de transformat in Utilizator
     * @return Utilizatorul */
    @Override
    protected Utilizator lineToEntity(String line) {
        String []campuri = line.split(",");
        String id = campuri[0];
        String email = campuri[1];
        String parola = campuri[2];
        String nume = campuri[3];
        String prenume = campuri[4];

        return new Utilizator(id, email, parola, nume, prenume);
    }

    /**
     * Metoda de a transforma o entitate in linie specific pentru Utilizator
     * @param utilizator - utilizatorul de transformat in linie de fisier
     * @return linia*/
    @Override
    protected String entityToLine(Utilizator utilizator) {
        return utilizator.getId() + "," + utilizator.getEmail() + ","+utilizator.getParola() + ","+ utilizator.getNume() + "," + utilizator.getPrenume();
    }

}

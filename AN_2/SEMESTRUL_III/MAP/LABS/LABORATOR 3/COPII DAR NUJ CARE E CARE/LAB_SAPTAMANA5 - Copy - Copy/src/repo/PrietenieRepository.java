package repo;

import domain.Prietenie;

public class PrietenieRepository extends FileRepository<String,Prietenie>{

    public PrietenieRepository(String fileName) {
        super(fileName);
    }

    /**
     * Metoda de a transforma o linie in entitate specific pentru o Prietenie
     * @param line - linia de transformat in Prietenie
     * @return Prietenia */
    @Override
    protected Prietenie lineToEntity(String line) {
        String []campuri = line.split(",");
        String id = campuri[0];
        String idPrieten1 = campuri[1];
        String idPrieten2 = campuri[2];
        return new Prietenie(id, idPrieten1, idPrieten2);
    }

    /**
     * Metoda de a transforma o entitate in linie specific pentru Prietenie
     * @param prietenie - prietenia de transformat in linie de fisier
     * @return linia*/
    @Override
    protected String entityToLine(Prietenie prietenie) {
        return prietenie.getId() + "," + prietenie.getIdPrieten1() + "," + prietenie.getIdPrieten2();
    }
}

package repository;

import domain.Utilizator;

public class UtlizatorRepository extends FileRepository<String,Utilizator>{

    public UtlizatorRepository(String filename) {
        super(filename);
    }

    @Override
    protected Utilizator LineToEntity(String line) {
        String[] sir=line.split(",");

        return new Utilizator(sir[0],sir[1],sir[2],sir[3],sir[4]);
    }

    @Override
    protected String EntityToLine(Utilizator entity) {
        return entity.get_id()+","+entity.get_lastname()+","+entity.get_firstname()+","+entity.get_email()+","+entity.get_parola();
    }
}

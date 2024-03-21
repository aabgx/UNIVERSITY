package repository;

import domain.Prietenie;

public class PrietenieRepository extends FileRepository<String,Prietenie>{
    public PrietenieRepository(String filename) {
        super(filename);
    }

    @Override
    protected Prietenie LineToEntity(String line) {
        String[] sir=line.split(",");
        return new Prietenie(sir[0],sir[1],sir[2]);
    }

    @Override
    protected String EntityToLine(Prietenie entity) {
        return entity.get_id()+","+entity.get_prieten_1()+","+entity.get_prieten_2();
    }
}

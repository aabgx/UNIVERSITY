package socialnetwork.com.repo_db.repository;

import socialnetwork.com.domain.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FileRepository<ID,EntityType extends Entity<ID>> implements Repository<ID,EntityType> {
    private final String filename;

    /***
     * Constructorul implicit al unui FileRepository
     * @param filename numele fisierului din care se citesc dates
     */
    public FileRepository(String filename){
        this.filename=filename;
    }

    /***
     * Metoda care transforma un string intr-o entitatet
     * @param line Linia
     * @return Entitatea transformata din String
     */
    protected abstract EntityType LineToEntity(String line);

    /***
     * Transforma e entitate intr-un string
     * @param entity Entitatea
     * @return Stringul
     */
    protected abstract String EntityToLine(EntityType entity);

    /***
     * Incarca date din fisier
     * @return Lista cu entitatiile din fisier
     */
    private List<EntityType> load_from_file(){
        List<EntityType> lst = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while((line=reader.readLine())!=null){
                lst.add(LineToEntity(line));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /***
     * Salvaeaza date in fisier
     * @param lst Lista cu entitatiile care se vor salva in fisier
     */
    private void save_to_file(List<EntityType> lst) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (EntityType e : lst) {
                writer.write(EntityToLine(e));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Adauga date in fisier
     * @param entity Entitatea care va fi adaugata
     * @return Entitatea care a fost adaugata
     */
        @Override
    public EntityType add(EntityType entity) {
        List<EntityType> lst = load_from_file();
        lst.add(entity);
        save_to_file(lst);
        return entity;
    }

    /***
     * Sterge  entitate din fisier
     * @param entity Entitatea care a fost stearsa din fisier
     * @return Entitate stearsa
     */
    @Override
    public EntityType delete(EntityType entity) {
        List<EntityType> lst=load_from_file();
        lst.remove(entity);
        save_to_file(lst);
        return entity;
    }

    @Override
    public EntityType update(EntityType entity,EntityType new_entity) {
        List<EntityType> lst=load_from_file();
        lst.remove(entity);
        lst.add(new_entity);
        save_to_file(lst);
        return entity;
    }

    /***
     * Cauta o entitate dupa id-ul ei
     * @param id Id-ul entitatii
     * @return Entitatea cu respectivul ID
     */
    @Override
    public EntityType findById(Integer... id) {
        List<EntityType> lst=load_from_file();
        for(EntityType e:lst){
            if(e.get_id().equals(id)){
                return e;
            }
        }
        return null;
    }

    /***
     * Determina toate Entitatiile din fisier
     * @return O lista cu toate entitatiiles
     */
    @Override
    public List<EntityType> getAll() {
        return load_from_file();
    }
}

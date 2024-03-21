package repo.file;

import domain.Entity;
import domain.validators.Validator;
import repo.memory.InMemoryRepo;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public abstract class FileRepo<ID, E extends Entity<ID>> extends InMemoryRepo<ID, E> {

    private String fileName;

    public FileRepo(String fName, Validator<E> val){
        super(val);
        fileName= fName;
        loadData();
    }

    public abstract String entityToString(E entity);
    public abstract E StringToEntity(List<String> attributes);

    /**
     * Loads data from file
     */
    private void loadData(){
        try{
            BufferedReader reader= new BufferedReader(new FileReader(fileName));
            String linie;
            while ((linie= reader.readLine())!=null){
                List<String> data= Arrays.asList(linie.split(";"));
                E entity= StringToEntity(data);
                super.save(entity);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Wrtites data to file
     */
    private void writeToFile(){
        try{
            BufferedWriter writer= new BufferedWriter(new FileWriter(fileName));
            for(E entity: super.findAll()){
                String data= entityToString(entity);
                writer.write(data);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves emtity to file
     * @param entity
     *         entity must be not null
     * @return E, saved entity, is null if it did not already exist in file
     */
    @Override
    public E save(E entity) {
        E returned= super.save(entity);
        if(returned==null)
            writeToFile();
        return returned;
    }

    /**
     * Deletes entity with give id
     * @param id
     *      id must be not null
     * @return E, deleted entity if it exists in file, null otherwise
     */
    @Override
    public E delete(ID id) {
        E returned= super.delete(id);
        if(returned!=null)
            writeToFile();
        return returned;
    }

    /**
     * Returns entity with given id
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return E, entity with given id, is null if such entity does not exist
     */
    @Override
    public E findOne(ID id) {
        return super.findOne(id);
    }

    /**
     * Returns all entities in file
     * @return Iterable<E>, collection of antities in file
     */
    @Override
    public Iterable<E> findAll() {
        return super.findAll();
    }
}

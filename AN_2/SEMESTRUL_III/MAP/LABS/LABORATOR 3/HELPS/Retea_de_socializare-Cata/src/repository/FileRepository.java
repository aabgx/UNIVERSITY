package repository;

import domain.Entity;

import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public abstract class FileRepository<ID,EntityType extends Entity<ID>> implements Repository<ID,EntityType> {
    private final String filename;

    public FileRepository(String filename){
        this.filename=filename;
    }

    protected abstract EntityType LineToEntity(String line);

    protected abstract String EntityToLine(EntityType entity);

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
        @Override
    public EntityType adauga(EntityType entity) {
        List<EntityType> lst = load_from_file();
        lst.add(entity);
        save_to_file(lst);
        return entity;
    }

    @Override
    public EntityType sterge(EntityType entity) {
        List<EntityType> lst=load_from_file();
        lst.remove(entity);
        save_to_file(lst);
        return entity;
    }

    @Override
    public EntityType cauta_dupa_id(String id) {
        List<EntityType> lst=load_from_file();
        for(EntityType e:lst){
            if(e.get_id().equals(id)){
                return e;
            }
        }
        return null;
    }

    @Override
    public List<EntityType> get_all() {
        return load_from_file();
    }
}

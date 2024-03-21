package repos.file;

import domain.Entity;
import repos.Repository;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public abstract class FileRepository<EntityType extends Entity> implements Repository<EntityType> {
    private final String fileName;

    protected abstract EntityType lineToEntity(String line);
    protected abstract String entityToLine(EntityType entity);

    public FileRepository(String fileName) {
        this.fileName = fileName;
    }

    private List<EntityType> loadFromFile() {
        List<EntityType> entities = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    entities.add(lineToEntity(line));
                } catch (RuntimeException ignored) {
                    System.out.println(ignored.getMessage());
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return entities;
    }

    /**
     * Save data to file
     * @param entities
     */
    private void saveToFile(List<EntityType> entities) {
        //System.out.println(entities);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.fileName, false))) {
            for (EntityType entity : entities) {
                //System.out.println(entityToLine(entity));
                bufferedWriter.write(entityToLine(entity));
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Add an entity
     * @param entity
     */
    @Override
    public void add(EntityType entity) {
        List<EntityType> entities = loadFromFile();
        entities.add(entity);
        saveToFile(entities);
    }

    /**
     * Remove an entity
     * @param entity
     */
    @Override
    public void remove(EntityType entity) {
        List<EntityType> entities= loadFromFile();
        entities.remove(entity);
        saveToFile(entities);
    }

    /**
     * Get an entity by id
     * @param id
     * @return
     */
    @Override
    public EntityType getById(String id) {
        for (EntityType entity : loadFromFile()){
            if(entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Get all entities
     * @return
     */
    @Override
    public List<EntityType> getAll() {
        return loadFromFile();
    }
}

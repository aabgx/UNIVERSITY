package repos.memory;

import domain.Entity;
import repos.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<EntityType extends Entity> implements Repository<EntityType> {

    private Map<String, EntityType> entities;

    /**
     * Add an entity
     * @param entity
     */
    @Override
    public void add(EntityType entity) {
        entities.put(entity.getId(), entity);
    }

    /**
     * Remove an entity
     * @param entity
     */
    @Override
    public void remove(EntityType entity) {
        entities.remove(entity.getId());
    }

    @Override
    public EntityType getById(String id) {
        return entities.get(id);
    }

    @Override
    public List<EntityType> getAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public void update(EntityType entity) {

    }

}

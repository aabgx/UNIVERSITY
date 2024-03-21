package repos;

import java.util.List;

public interface Repository<EntityType> {
    void add(EntityType entity);
    void remove(EntityType entity);
    EntityType getById(String id);
    List<EntityType> getAll();
    void update(EntityType entity);
}

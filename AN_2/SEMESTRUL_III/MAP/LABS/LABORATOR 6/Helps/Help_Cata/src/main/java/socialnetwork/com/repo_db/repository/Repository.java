package socialnetwork.com.repo_db.repository;

import java.util.List;

public interface Repository<ID,EntityType> {
EntityType add(EntityType entity);
EntityType delete(EntityType entity);
EntityType findById(Integer... id);
EntityType update(EntityType entity,EntityType new_entity);
List<EntityType> getAll();
}

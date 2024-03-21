package repository;

import java.util.List;

public interface Repository<ID,EntityType> {
EntityType adauga(EntityType entity);
EntityType sterge(EntityType entity);
EntityType cauta_dupa_id(String id);
List<EntityType> get_all();
}

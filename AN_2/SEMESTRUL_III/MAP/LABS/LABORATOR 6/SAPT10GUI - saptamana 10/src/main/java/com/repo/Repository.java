package repo;

import java.util.List;

public interface Repository<ID,entityType> {
    void adauga(entityType entity);
    void sterge(entityType entity);
    entityType cautaId(String id);
    List<entityType> getAll();
    void update(entityType entitate,entityType nouaEntitate);
}

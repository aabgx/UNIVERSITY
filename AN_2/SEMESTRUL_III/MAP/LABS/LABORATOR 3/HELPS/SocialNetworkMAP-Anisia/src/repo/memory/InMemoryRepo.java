package repo.memory;

import domain.Entity;
import domain.validators.Validator;
import repo.Repository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepo<ID, E extends Entity<ID>> implements Repository<ID,E> {
    private Map<ID, E> entities;
    private Validator<E> validator;

    public InMemoryRepo(Validator<E> validator) {
        this.entities = new HashMap<ID, E>();
        this.validator= validator;
    }

    /**
     * Saves entity in repository
     * @param entity, entity to be saved
     *         entity must be not null
     * @return E, entity if entity exists or null if it does not
     * @throws domain.exceptions.ValidationException if entity is not valid
     */
    @Override
    public E save(E entity) {
        if(entity.getId()==null) {
            throw new IllegalArgumentException("Id-ul nu poate fi null!");
        }else if(entities.containsKey(entity.getId())){
            //System.out.println("Entitatea cu id-ul dat exista deja!");
            return entity;
        }
        validator.validate(entity);
        entities.put(entity.getId(), entity);
        return null;
    }

    /**
     * Removes entity with given id from repository
     * @param id
     *      id must be not null
     * @return E, entity with given id, null if it does not exist in repository
     */
    @Override
    public E delete(ID id) {
        if(id==null) throw new IllegalArgumentException("Id-ul nu poate fi null!");
        if(entities.containsKey(id)){
            E removed= entities.remove(id);
            return removed;
        }
        return null;
    }

    /**
     * Returns the entity with given id
     * @param id -the id of the entity to be returned
     *           id must not be null
     * @return E, entity, if entity with given id exists, false otherwise
     */
    @Override
    public E findOne(ID id) {
        if(entities.get(id)==null) return null;
        else return entities.get(id);
    }

    /**
     * Returns all entities in repository
     * @return Iterable<E>, all entities in repository
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }
}


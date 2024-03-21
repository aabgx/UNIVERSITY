using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

namespace Lab8_CSharp.repository;


class InMemoryRepository<ID,E>:IRepository<ID,E> where E:Entity<ID>
{
    //protected IDictionary<ID, E> entities = new Dictionary<ID, E>();

    protected List<E> entities = new List<E>();
    protected IValidator<E> validator;

    public InMemoryRepository(IValidator<E> validator)
    {
        this.validator = validator;
    }

    public E findOne(ID id)
    {
        throw new NotImplementedException();
    }

    
    public IEnumerable<E> findAll()
    {
        return entities;
    }

    
    public E save(E entity)
    {
        //if(entity==null)
        //    throw new ArgumentNullException("entity must not be null");
        foreach(E e in entities)
            if (e.ID.Equals(entity.ID))
                return entity;
        //if (this.entities.ContainsKey(entity.ID))
        //    return entity;
        this.entities.Add(entity);
        return default(E);
    }

    
    public E delete(ID id)
    {
        throw new NotImplementedException();
    }

    
    public E update(E entity)
    {
        throw new NotImplementedException();
    }
}
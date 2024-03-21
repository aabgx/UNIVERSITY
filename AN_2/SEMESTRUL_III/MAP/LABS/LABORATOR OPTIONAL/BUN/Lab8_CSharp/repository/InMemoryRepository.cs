using Lab8_CSharp.model;

namespace Lab8_CSharp.repository;


class InMemoryRepository<ID,E>:IRepository<ID,E> where E:Entity<ID>
{
    protected List<E> entities = new List<E>();


    public InMemoryRepository()
    {

    }

    public IEnumerable<E> findAll()
    {
        return entities;
    }

    public E findOne(ID id)
    {
        throw new NotImplementedException();
    }
    
    public E save(E entity)
    {
        throw new NotImplementedException();
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
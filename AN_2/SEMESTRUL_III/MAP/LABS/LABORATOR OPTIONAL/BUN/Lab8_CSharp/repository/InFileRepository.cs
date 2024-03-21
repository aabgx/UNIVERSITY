using Lab8_CSharp.model;
namespace Lab8_CSharp.repository;

public delegate E CreateEntity<E>(string line);

abstract class InFileRepository<ID, E> : InMemoryRepository<ID, E> where E : Entity<ID>
{
    protected string fileName;
    protected CreateEntity<E> createEntity;

    public InFileRepository(String fileName, CreateEntity<E> createEntity) : base()
    {
        this.fileName = fileName;
        this.createEntity = createEntity;
        if (createEntity != null)
            loadFromFile();
    }

    protected virtual void loadFromFile()
    {
        List<E> list = DataReader.ReadData(fileName, createEntity);
        foreach(E entity in list)
            entities.Add(entity); 
    }
}
using Lab8_CSharp.model;
using Lab8_CSharp.repository;

namespace Lab8_CSharp.service;

class ElevService
{
    private IRepository<string, Elev> repository;

    public ElevService(IRepository<string, Elev> repository)
    {
        this.repository = repository;
    }

    public List<Elev> findAllElevi()
    {
        return repository.findAll().ToList();
    }
}
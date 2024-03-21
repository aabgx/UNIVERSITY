using System.ComponentModel.DataAnnotations;
using Lab8_CSharp.model;
using Lab8_CSharp.repository;

namespace Lab8_CSharp.service;

class EchipaService
{
    private IRepository<string, Echipa> repository;

    public EchipaService(IRepository<string, Echipa> repository)
    {
        this.repository = repository;
    }
    

    public List<Echipa> findAllEchipe()
    {
        return repository.findAll().ToList();
    }
}
using System.ComponentModel.DataAnnotations;
using Lab8_CSharp.model;
using Lab8_CSharp.repository;

namespace Lab8_CSharp.service;

class JucatorService
{
    private IRepository<string, Jucator> repository;

    public JucatorService(IRepository<string, Jucator> repository)
    {
        this.repository = repository;
    }

    
    public List<Jucator> findAllJucatori()
    {
        return repository.findAll().ToList();
    }
}
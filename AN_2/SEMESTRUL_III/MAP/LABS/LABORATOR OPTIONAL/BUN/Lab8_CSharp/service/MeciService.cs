using Lab8_CSharp.model;
using Lab8_CSharp.repository;

namespace Lab8_CSharp.service;

class MeciService
{
    private IRepository<string, Meci> repository;

    public MeciService(IRepository<string, Meci> repository)
    {
        this.repository = repository;
    }

    public List<Meci> findAllMeciuri()
    {
        return repository.findAll().ToList();
    }
}
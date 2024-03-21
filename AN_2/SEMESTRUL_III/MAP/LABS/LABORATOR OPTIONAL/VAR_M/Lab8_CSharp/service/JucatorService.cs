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

    
    public List<Jucator> returneazaJucatoriiUneiEchipe(string echipa)
    {
        List<Jucator> jucatori=findAllJucatori();
        List<Jucator> jucatoriRezultat = new List<Jucator>();
        foreach(Jucator player in jucatori)
            if(echipa.Equals(player.Echipa.Nume))
                jucatoriRezultat.Add(player);
        if (jucatoriRezultat.Count==0)
            throw new Exception("Numele echipei introduse este incorect.");
        return jucatoriRezultat;
    }
}
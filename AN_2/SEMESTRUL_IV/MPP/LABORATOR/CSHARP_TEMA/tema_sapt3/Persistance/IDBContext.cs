using Microsoft.EntityFrameworkCore;
using Model;

namespace persistance
{
    public interface IDBContext
    {
        DbSet<Copil> Copii { get; set; }
        DbSet<Inscriere> Inscrieri { get; set; }
        DbSet<Proba> Probe { get; set; }
        DbSet<Utilizator> Utilizatori { get; set; }
    }
}
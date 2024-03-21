using Microsoft.EntityFrameworkCore;
using Model;

namespace persistance;

public class DBContext : DbContext
{
    public DBContext(DbContextOptions<DBContext> options)
        : base(options)
    {
    }
    public DbSet<Copil> Copii { get; set; }
    public DbSet<Inscriere> Inscrieri { get; set; }
    public DbSet<Proba> Probe { get; set; }
    public DbSet<Utilizator> Utilizatori { get; set; }
}
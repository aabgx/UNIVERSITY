using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.model;

namespace tema_evenimente.repository
{
    public class DBContext : DbContext
    {
        public DBContext(DbContextOptions<DBContext> options)
        : base(options)
        {
        }
        public DbSet<Angajat> Angajati { get; set; }
        public DbSet<Client> Clienti { get; set; }
        public DbSet<Eveniment> Evenimente { get; set; }
        public DbSet<Reprezentare> Reprezentari { get; set; }
        public DbSet<Rezervare> Rezervari { get; set; }
    }
}

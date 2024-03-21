using Laborator_8.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Transactions;

namespace Laborator_8
{
    public class RepositoryEchipa : Repository<Echipa, int>
    {
        public RepositoryEchipa(String Filename) : base(Filename) { }
        public override Echipa EntityFromLine(string line)
        {
            string[] campuri = line.Split(';');
            return new Echipa { Id= int.Parse(campuri[0]), Nume= campuri[1] };
        }
    }
}

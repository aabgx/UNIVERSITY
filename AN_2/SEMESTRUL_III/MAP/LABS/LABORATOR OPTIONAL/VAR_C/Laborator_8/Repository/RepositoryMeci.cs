using Laborator_8.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Transactions;

namespace Laborator_8
{
    public class RepositoryMeci : Repository<Meci, int>
    {
        public RepositoryMeci(String Filename) : base(Filename) { }
        public override Meci EntityFromLine(string line)
        {
            return new Meci(line);
        }
    }
}

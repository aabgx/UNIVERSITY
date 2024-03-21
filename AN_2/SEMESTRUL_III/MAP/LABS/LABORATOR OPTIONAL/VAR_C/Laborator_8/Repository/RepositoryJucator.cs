using Laborator_8.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Transactions;

namespace Laborator_8
{
    public class RepositoryJucator : Repository<Jucator, int>
    {
        public RepositoryJucator(String Filename) : base(Filename) { }
        public override Jucator EntityFromLine(string line)
        {
            return new Jucator(line);
        }
    }
}

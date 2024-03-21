using Laborator_8.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Transactions;

namespace Laborator_8
{
    public class RepositoryJucatorActiv : Repository<JucatorActiv, int>
    {
        public RepositoryJucatorActiv(String Filename) : base(Filename) { }
        public override JucatorActiv EntityFromLine(string line)
        {
            return new JucatorActiv(line);
        }
    }
}

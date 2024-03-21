using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public class Echipa : Entity<int>
    {
        public String? Nume { get; set; }

        public override string? ToString()
        {
            return Id.ToString()+";"+Nume;
        }
    }
}

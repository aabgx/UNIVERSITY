using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tema_evenimente.model
{
    public class Eveniment:Entitate<int>
    {
        public string nume { get; set; }
        public int nr_locuri { get; set; }
        public int pret { get; set; }
        public Eveniment(string nume, int nr_locuri, int pret)
        {
            this.nume = nume;
            this.nr_locuri = nr_locuri;
            this.pret = pret;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


    [Serializable]
    public class Copil: Entitate<int>
    {
        public string nume { get; set; }
        public int varsta { get; set; }

        public Copil(String nume, int varsta)
        {
            this.nume = nume;
            this.varsta = varsta;
        }

        public Copil()
        {
        }
    }


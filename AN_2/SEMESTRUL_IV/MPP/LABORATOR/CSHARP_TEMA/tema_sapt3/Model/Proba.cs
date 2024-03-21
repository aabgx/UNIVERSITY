using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Model
{
    [Serializable]
    public class Proba : Entitate<String>
    {
        public Proba(string id_dat) { id = id_dat; }
        public Proba() { }
    }
}


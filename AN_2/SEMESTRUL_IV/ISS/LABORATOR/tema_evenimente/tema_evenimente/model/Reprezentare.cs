using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tema_evenimente.model
{
    public class Reprezentare:Entitate<int>
    {
        public DateTime data { get; set; }
        public int id_eveniment { get; set; }
        public Reprezentare(DateTime data,int id_eveniment)
        {
            this.data = data;
            this.id_eveniment = id_eveniment;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tema_evenimente.model
{
    public class DTO_R_R_E
    {
        public int id_rezervare { get; set; }
        public string detalii { get; set; }
    
        public DTO_R_R_E(int id_rezervare, string detalii)
        {
            this.id_rezervare = id_rezervare;
            this.detalii = detalii;
        }
    }
}

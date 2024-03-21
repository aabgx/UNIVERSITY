using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public class JucatorActiv : Entity<int>
    {
        public int IdJucator { get; set; }
        public int IdMeci { get; set; }
        public int NrPuncteInscrise { get; set; }
        public Tip Tip { get; set; }

        public override string? ToString()
        {
            return Id.ToString() + ";" + IdJucator.ToString() +";"+ IdMeci.ToString() + ";" + NrPuncteInscrise.ToString() + ";" + Tip.ToString();
        }
        public JucatorActiv(String line)
        {
            string[] campuri = line.Split(';');
            Id = int.Parse(campuri[0]);
            IdJucator = int.Parse(campuri[1]);
            IdMeci = int.Parse(campuri[2]);
            NrPuncteInscrise = int.Parse(campuri[3]);
            Tip = (Tip)int.Parse(campuri[4]);
        }
    }
}

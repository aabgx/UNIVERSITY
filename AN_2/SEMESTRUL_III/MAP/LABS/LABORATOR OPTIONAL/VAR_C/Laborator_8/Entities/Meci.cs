using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public class Meci : Entity<int>
    {
        public Echipa Echipa1 { get; set; }
        public Echipa Echipa2 { get; set; }
        public DateTime Data { get; set; }

        public override string? ToString()
        {
            return Id.ToString() + ";" + Echipa1.ToString() + ";" + Echipa2.ToString() + ";" + Data.ToString("MM/dd/yy");
        }

        public Meci(String Line)
        {
            string[] campuri = Line.Split(';');
            Id = int.Parse(campuri[0]);
            Echipa1=new Echipa { Id= int.Parse(campuri[1]), Nume = campuri[2] };
            Echipa2=new Echipa { Id= int.Parse(campuri[3]), Nume = campuri[4] };
            Data = DateTime.ParseExact(campuri[5], "MM/dd/yy",null);
        }
    }
}

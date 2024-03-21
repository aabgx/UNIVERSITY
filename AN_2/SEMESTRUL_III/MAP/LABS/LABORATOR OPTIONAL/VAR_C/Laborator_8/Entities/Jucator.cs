using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection.Metadata.Ecma335;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public class Jucator : Elev
    {
        public Echipa? Echipa {get;set;}

        public override string? ToString()
        {
            return Id.ToString() + ";" + Scoala + ";" +Echipa.ToString();
        }

        public Jucator(String line)
        {
            string[] campuri = line.Split(';');
            Id = int.Parse(campuri[0]);
            Scoala = campuri[1];
            Echipa = new Echipa();
            Echipa.Id = int.Parse(campuri[2]);
            Echipa.Nume = campuri[3];
        }
    }
}

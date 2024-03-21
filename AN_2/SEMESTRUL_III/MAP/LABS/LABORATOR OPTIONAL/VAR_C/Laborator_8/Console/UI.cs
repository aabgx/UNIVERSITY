using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public class UI
    {
        private readonly Service srv;
        public UI(Service srv)  { this.srv = srv; }

        void printMenu()
        {
            Console.WriteLine("1. Afiseaza toti jucatorii unei echipe.");
            Console.WriteLine("2. Afiseaza toti jucatorii unei echipe dintr-un meci.");
            Console.WriteLine("3. Afiseaza toate meciuriile dintr-o perioada.");
            Console.WriteLine("4. Afiseaza scorul unui meci.");
            Console.WriteLine("X. Exit.");
        }

        String citeste(String txt)
        {
            Console.Write(txt);
            return Console.ReadLine();
        }

        void afiseazaJucatoriEchipa()
        {
            var id = int.Parse(citeste("Id echipa: "));
            foreach(var el in srv.getAllJucatori(id))
            {
                Console.WriteLine(el.ToString());
            }
        }

        void afiseazaJucatoriActiviMeci()
        {
            var id = int.Parse(citeste("Id meci: "));
            foreach (var el in srv.getAllJucatoriActivi(id))
            {
                Console.WriteLine(el.ToString());
            }
        }

        void afiseazaMeci()
        {
            var an = int.Parse(citeste("An Inceput Interval: "));
            var luna = int.Parse(citeste("Luna Inceput Interval: "));
            var zi = int.Parse(citeste("Zi Inceput Interval: "));
            var anSfarsit = int.Parse(citeste("An Sfarsit Interval: "));
            var lunaSfarsit = int.Parse(citeste("Luna Sfarsit Interval: "));
            var ziSfarsit = int.Parse(citeste("Zi Sfarsit Interval: "));
            foreach (var el in srv.getAllMeci(an, luna, zi,anSfarsit,lunaSfarsit,ziSfarsit))
            {
                Console.WriteLine(el.ToString());
            }
        }

        void afiseazaScor()
        {
            var id = int.Parse(citeste("Id meci: "));
            var scor = srv.getScor(id);
            Console.WriteLine(scor[0].ToString() + "-" + scor[1].ToString());
        }

        public void run()
        {
            while(true)
            {
                printMenu();
                var cmd = citeste("Optiune: ");
                if (cmd.Equals("1"))
                {
                    afiseazaJucatoriEchipa();
                }
                else if(cmd.Equals("2"))
                {
                    afiseazaJucatoriActiviMeci();
                }
                else if (cmd.Equals("3"))
                {
                    afiseazaMeci();
                }
                else if (cmd.Equals("4"))
                {
                    afiseazaScor();
                }
                else if (cmd.Equals("X"))
                {
                    break;
                }
                else
                {
                    Console.WriteLine("Comanda gresita!");
                }
            }
        }
    }
}

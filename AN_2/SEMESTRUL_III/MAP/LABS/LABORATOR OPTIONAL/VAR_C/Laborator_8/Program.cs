using Laborator_8.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    class Program
    {
        static void Main(string[] args)
        {
            Repository<Echipa, int> repositoryEchipa = new RepositoryEchipa("echipe.txt");
            Repository<Jucator, int> repositoryJucator = new RepositoryJucator("jucatori.txt");
            Repository<JucatorActiv, int> repositoryJucatorActiv = new RepositoryJucatorActiv("jucatori_activi.txt");
            Repository<Meci, int> repositoryMeci = new RepositoryMeci("meciuri.txt");
            Service srv = new Service(repositoryEchipa,repositoryJucator,repositoryJucatorActiv,repositoryMeci);
            UI app = new UI(srv);
            app.run();
        }
    }
}

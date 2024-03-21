using System.Diagnostics;
using System.Xml;
using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;
using Lab8_CSharp.repository;
using Lab8_CSharp.service;

namespace Lab8_CSharp;
class MainWindow
    {
        static void Main(string[] args)
        {
            //afisareJucatoriEchipaData();
            //afisareJucatoriMeci();
            //afisareMeciuriPerioadaCalendaristica();
            //afisareScorMeci();
            bool ok=true;
            string comanda;
            while (ok)
            {
                comanda = "";
                Console.WriteLine("------------------------------------------------------");
                Console.WriteLine("------------------------------------------------------");
                Console.WriteLine("Aplicatie pentru gestionarea echipelor NBA Cluj Napoca");
                Console.WriteLine("Apasati tasta corespunzatoare functionalitatii dorite");
                Console.WriteLine("1.Afisati toti jucatorii unei echipe date");
                Console.WriteLine("2.Afisati toti jucatorii activi ai unei echipe de la un anumit meci");
                Console.WriteLine("3.Sa se afiseze meciurile dintr-un interval calendaristic");
                Console.WriteLine("4.Sa se afiseze scorul final al unui meci");
                Console.WriteLine("5.Inchidere aplicatie");
                Console.WriteLine("------------------------------------------------------");
                Console.WriteLine("------------------------------------------------------");
                comanda = Console.ReadLine();
                if(comanda=="1")
                    afisareJucatoriEchipaData();
                else if (comanda=="2")
                    afisareJucatoriMeci();
                else if(comanda=="3")
                    afisareMeciuriPerioadaCalendaristica();
                else if(comanda=="4")
                    afisareScorMeci();
                else if (comanda == "5")
                    ok = false;
                else 
                    Console.WriteLine("Comanda invalida");
            }
        }

        
        private static EchipaService getEchipeService()
        {
            string fileName = "..\\..\\..\\data\\echipe.txt";
            EchipaValidator validator = new EchipaValidator();
            IRepository<string, Echipa> repository= new EchipeFileRepository(validator,fileName);
            EchipaService service = new EchipaService(repository);
            return service;
        }
        
        
        private static ElevService getEleviService()
        {
            string fileName = "..\\..\\..\\data\\elevi.txt";
            ElevValidator validator = new ElevValidator();
            IRepository<string, Elev> repository= new EleviFileRepository(validator,fileName);
            ElevService service = new ElevService(repository);
            return service;
        }
        
        
        private static JucatorService getJucatoriService()
        {
            string fileName = "..\\..\\..\\data\\jucatori.txt";
            JucatorValidator validator = new JucatorValidator();
            IRepository<string, Jucator> repository= new JucatoriFileRepository(validator,fileName);
            JucatorService service = new JucatorService(repository);
            return service;
        }
        
        
        private static JucatorActivService getJucatoriActiviService()
        {
            string fileName = "..\\..\\..\\data\\jucatoriActivi.txt";
            JucatorActivValidator validator = new JucatorActivValidator();
            IRepository<string, JucatorActiv> repository= new JucatoriActiviFileRepository(validator,fileName);
            JucatorActivService service = new JucatorActivService(repository);
            return service;
        }
        
        
        private static MeciService getMeciuriService()
        {
            string fileName = "..\\..\\..\\data\\meciuri.txt";
            MeciValidator validator = new MeciValidator();
            IRepository<string, Meci> repository= new MeciuriFileRepository(validator,fileName);
            MeciService service = new MeciService(repository);
            return service;
        }

        
        
        public static void afisareJucatoriEchipaData()
        {
            Console.Write("Introduceti echipa dorita:");
            string teamName = Console.ReadLine();
            List<Jucator> jucatoriDeAfisat = new List<Jucator>();
            try
            {
                jucatoriDeAfisat = getJucatoriService().returneazaJucatoriiUneiEchipe(teamName);
                foreach(Jucator player in jucatoriDeAfisat)
                    Console.WriteLine(player.ToString());
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        
        
        public static void afisareJucatoriMeci()
        {
            Console.Write("Introduceti pechipa ai carei jucatori doriti sa fie afisati:");
            string ech1Name= Console.ReadLine();
            Console.Write("Introduceti echipa adversa:");
            string ech2Name = Console.ReadLine();
            Console.Write("Introduceti ziua meciului(format dd/mm/yyyy):");
            string strDate= Console.ReadLine();
            List<Elev> elevi = getEleviService().findAllElevi();
            var mapElevi = new Dictionary<string, string>();
            foreach (Elev elev in elevi)
                mapElevi[elev.ID] = elev.Nume;
            try
            {
                List<JucatorActiv> jucatoriDeAfisat = getJucatoriActiviService().returneazaJucatoriActiviEchipa(ech1Name,ech2Name,strDate);
                foreach(JucatorActiv player in jucatoriDeAfisat)
                    Console.WriteLine(mapElevi[player.ID]+" ; "+player.nrPuncteInscrise+" ; "+player.tip);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        
        
        public static void afisareMeciuriPerioadaCalendaristica()
        {
            Console.Write("Introduceti prima zi(format dd//mm//yyyy):");
            string firstDay = Console.ReadLine();
            Console.Write("Introduceti ultima zi(format dd/mm//yyyy):");
            string lastDay = Console.ReadLine();
            //List<Echipa> echipe = getEchipeService().findAllEchipe();
            List<Meci> meciuriDeAfisat = new List<Meci>();
            try
            {
                meciuriDeAfisat = getMeciuriService().returneazaMeciuriPerioadaCalendaristica(firstDay, lastDay);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return;
            }
            if(meciuriDeAfisat.Count==0)
                Console.WriteLine("Nu s-a gasit niciun meci in intervalul specificat.");
            else
                foreach(Meci m in meciuriDeAfisat)
                    Console.WriteLine(m.ToString());
        }


        public static void afisareScorMeci()
        {
            Console.Write("Introduceti prima echipa:");
            string ech1Name= Console.ReadLine();
            Console.Write("Introduceti a doua echipa:");
            string ech2Name = Console.ReadLine();
            Console.Write("Introduceti ziua meciului:");
            string strDate= Console.ReadLine();
            string scor = getMeciuriService().returneazaScorMeci(ech1Name, ech2Name, strDate);
            Console.WriteLine(scor);
        }
    }
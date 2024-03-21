using System.Diagnostics;
using System.Xml;
using Lab8_CSharp.model;
using Lab8_CSharp.repository;
using Lab8_CSharp.service;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace Lab8_CSharp;
class MainWindow
    {
        static void Main()
        {
            bool exit=true;
            string cmd;
            while (exit)
            {
                cmd = "";
                Console.WriteLine("1. OBTINE JUCATORII UNEI ECHIPE ");
                Console.WriteLine("2. JUCATORII ACTIVI DE LA UN MECI ");
                Console.WriteLine("3. MECIURILE DINTR-UN ANUMIT INTERVAL");
                Console.WriteLine("4. SCORUL UNUI MECI");
                Console.WriteLine("5. EXIT");

                cmd = Console.ReadLine();
                if(cmd == "1")
                    cerinta1();
                else if (cmd == "2")
                    cerinta2();
                else if(cmd == "3")
                    cerinta3();
                else if(cmd == "4")
                    cerinta4();
                else if (cmd == "5")
                    exit = false;
                else 
                    Console.WriteLine("ALEGETI 1,2,3,4 SAU 5!!!");
            }
        }

        
        private static EchipaService getEchipeService()
        {
            string fileName = "..\\..\\..\\data\\echipe.txt";
            IRepository<string, Echipa> repository= new EchipeFileRepository(fileName);
            EchipaService service = new EchipaService(repository);
            return service;
        }
        
        
        private static ElevService getEleviService()
        {
            string fileName = "..\\..\\..\\data\\elevi.txt";
            IRepository<string, Elev> repository= new EleviFileRepository(fileName);
            ElevService service = new ElevService(repository);
            return service;
        }
        
        
        private static JucatorService getJucatoriService()
        {
            string fileName = "..\\..\\..\\data\\jucatori.txt";
            IRepository<string, Jucator> repository= new JucatoriFileRepository(fileName);
            JucatorService service = new JucatorService(repository);
            return service;
        }
        
        
        private static JucatorActivService getJucatoriActiviService()
        {
            string fileName = "..\\..\\..\\data\\jucatoriActivi.txt";
            IRepository<string, JucatorActiv> repository= new JucatoriActiviFileRepository(fileName);
            JucatorActivService service = new JucatorActivService(repository);
            return service;
        }
        
        
        private static MeciService getMeciuriService()
        {
            string fileName = "..\\..\\..\\data\\meciuri.txt";
            IRepository<string, Meci> repository= new MeciuriFileRepository(fileName);
            MeciService service = new MeciService(repository);
            return service;
        }

        
        
        public static void cerinta1()
        {
            Console.Write("Introduceti echipa dorita:");
            string echipa = Console.ReadLine();

            List<Jucator> jucatori = getJucatoriService().findAllJucatori();
            List<Jucator> rezultat = new List<Jucator>();

            rezultat = jucatori.Where(j => j.Echipa.Nume.Equals(echipa)).ToList();

            foreach (Jucator player in rezultat)
                Console.WriteLine(player.ToString());
        }

        
        
        public static void cerinta2()
        {
            Console.Write("ECHIPA JUCATORILOR DE AFISAT: ");
            string echipa1= Console.ReadLine();
            Console.Write("ECHIPA ADVERSA: ");
            string echipa2 = Console.ReadLine();
            Console.Write("DATA MECIULUI (format dd/mm/yyyy): ");
            string strDate= Console.ReadLine();

            //CAUT MECIUL DINTRE ACELE ECHIPE DIN DATA RESPECTIVA
            List<Echipa> echipe = getEchipeService().findAllEchipe();

            string echipa1id = echipe.Find(x => x.Nume.Equals(echipa1)).ID;
            string echipa2id = echipe.Find(x => x.Nume.Equals(echipa2)).ID;
            string gameId;

            if (echipa1id.CompareTo(echipa2id) > 0)
                gameId = echipa2id + echipa1id + "." + strDate;
            else
                gameId = echipa1id + echipa2id + "." + strDate;


            List<JucatorActiv> jucatoriActivi = getJucatoriActiviService().findAllJucatoriActivi();
            List<JucatorActiv> rezultat = new List<JucatorActiv>();

            rezultat = jucatoriActivi.Where(j => j.idMeci.Equals(gameId)).ToList();


            //CAUT JUCATORII ACTIVI PRINTRE TOTI JUCATORII
            List<Jucator> jucatori = getJucatoriService().findAllJucatori();
            var mapJucatorEchipa = new Dictionary<string, string>();
            foreach (Jucator j in jucatori)
                mapJucatorEchipa[j.ID] = j.Echipa.ID;

            List<JucatorActiv> rezultatBun = new List<JucatorActiv>();


            foreach (JucatorActiv j in rezultat)
                if (mapJucatorEchipa[j.ID].Equals(echipa1id))
                    rezultatBun.Add(j);

            foreach (JucatorActiv ja in rezultatBun)
                Console.WriteLine(ja.ToString());
        }

        
        
        public static void cerinta3()
        {
            Console.Write("Introduceti prima zi(format dd//mm//yyyy):");
            string primaZi = Console.ReadLine();
            Console.Write("Introduceti ultima zi(format dd/mm//yyyy):");
            string ultimaZi = Console.ReadLine();


            DateTime primaZiData, ultimaZiData;

            primaZiData = DateTime.ParseExact(primaZi, "d/M/yyyy", System.Globalization.CultureInfo.InvariantCulture);
            ultimaZiData = DateTime.ParseExact(ultimaZi, "d/M/yyyy", System.Globalization.CultureInfo.InvariantCulture);

            List<Meci> meciuri = getMeciuriService().findAllMeciuri();
            List<Meci> rezultat = new List<Meci>();

            rezultat = meciuri.Where(j => j.Date >= primaZiData && j.Date <= ultimaZiData).ToList();

            foreach (Meci meci in rezultat)
                Console.WriteLine(meci.ToString());
    }


        public static void cerinta4()
        {
            Console.Write("ECHIPA 1: ");
            string ech1Name= Console.ReadLine();
            Console.Write("ECHIPA 2: ");
            string ech2Name = Console.ReadLine();
            Console.Write("DATA: ");
            string strDate= Console.ReadLine();

            //1.CAUT ECHIPELE CU NUMELE DATE
            List<Echipa> echipe = getEchipeService().findAllEchipe();
            string echipa1Id = echipe.Find(x => x.Nume.Equals(ech1Name)).ID;
            string echipa2Id = echipe.Find(x => x.Nume.Equals(ech2Name)).ID;

            //2.CAUT JUCATORII DIN CELE 2 ECHIPE
            List<Jucator> jucatori = getJucatoriService().findAllJucatori();
            var jucatoriEchipa1 = new Dictionary<string, string>();
            foreach (Jucator player in jucatori)
                if (ech1Name.Equals(player.Echipa.Nume))
                    jucatoriEchipa1[player.ID] = player.Nume;

            var jucatoriEchipa2 = new Dictionary<string, string>();
            foreach (Jucator player in jucatori)
                if (ech2Name.Equals(player.Echipa.Nume))
                    jucatoriEchipa2[player.ID] = player.Nume;

            string gameId;
            if (echipa1Id.CompareTo(echipa2Id) > 0)
                gameId = echipa2Id + echipa1Id + "." + strDate;
            else
                gameId = echipa1Id + echipa2Id + "." + strDate;

            //3.CALCULEZ SCOR TOTAL PT FIECARE ECHIPA
            int scorE1 = 0;
            int scorE2 = 0;
            List<JucatorActiv> jucatoriActivi = getJucatoriActiviService().findAllJucatoriActivi();

            foreach (JucatorActiv j in jucatoriActivi)
            {
                if (j.idMeci.Equals(gameId) && jucatoriEchipa1.ContainsKey(j.ID))
                    scorE1 = scorE1 + j.nrPuncteInscrise;
                else if (j.idMeci.Equals(gameId) && jucatoriEchipa2.ContainsKey(j.ID))
                    scorE2 = scorE2 + j.nrPuncteInscrise;
            }

            string scor = "";
            scor = scor + ech1Name + ":" + scorE1.ToString() + " ; " + ech2Name + ":" + scorE2.ToString();
            Console.WriteLine(scor);
        }
    }
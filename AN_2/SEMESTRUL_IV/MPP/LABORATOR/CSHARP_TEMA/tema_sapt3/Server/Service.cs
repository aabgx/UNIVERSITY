using Microsoft.EntityFrameworkCore.Diagnostics;
using Model;
using persistance;
using services;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_bun.AppProtobuffs;

namespace Server
{
    public class Service: IServices
    {
        private ConcurrentDictionary<string, IObserver> loggedClients;
        private int defaultThreadsNo = 5;
        public DBContext dataBaseContext;
        
        public Service(DBContext dataBaseContext)
        {
            /*this.copilRepository = copilRepository;
            this.inscriereRepository = inscriereRepository;
            this.probaRepository = probaRepository;
            this.utilizatorRepository = utilizatorRepository;*/
            this.dataBaseContext = dataBaseContext;
            //dataBaseContext.Utilizatori = dataBaseContext.Set<Utilizator>();
            loggedClients = new ConcurrentDictionary<string, IObserver>();
            
        }

        public Utilizator getByAccount(String username, String parola)
        {
            var lista_get_all = dataBaseContext.Utilizatori.ToList();
            return dataBaseContext.Utilizatori.Where(u => u.username == username && u.parola == parola).FirstOrDefault();
        }

        public bool login(string username, string parola, IObserver client)
        {
            Utilizator u = getByAccount(username, parola);
            if (u != null)
            {
                if (loggedClients.ContainsKey(u.username))
                    throw new Exception("UTILIZATOR DEJA CONECTAT!.");
                loggedClients[username] = client;
                return true;
            }
            return false;
        }

        public void logout(string username)
        {
            IObserver localClient;
            loggedClients.TryRemove(username, out localClient);
            if (localClient == null)
                throw new Exception("User " + username + " is not logged in.");
        }

        public void createAccount(String username, String parola)
        {
            if (username == "" || parola == "")
            {
                throw new Exception("USERNAME-UL SI PAROLA NU POT FI NULE!");
            }
            else
            {
                dataBaseContext.Utilizatori.Add(new Utilizator(username, parola));
                dataBaseContext.SaveChanges();
            }
        }
        public List<int> creareVectorCnt()
        {
            var rezultat = new List<int>(9);
            for (int i = 0; i < 9; i++)
            {
                rezultat.Add(0);
            }

            foreach (Inscriere i in dataBaseContext.Inscrieri.ToList())
            {
                //System.out.println(i.getId_proba());
                if (i.id_proba.Equals("d_6_8"))
                    rezultat[0] = rezultat[0] + 1;
                else if (i.id_proba.Equals("d_9_11"))
                    rezultat[1] = rezultat[1] + 1;
                else if (i.id_proba.Equals("d_12_15"))
                    rezultat[2] = rezultat[2] + 1;
                else if (i.id_proba.Equals("c_6_8"))
                    rezultat[3] = rezultat[3] + 1;
                else if (i.id_proba.Equals("c_9_11"))
                    rezultat[4] = rezultat[4] + 1;
                else if (i.id_proba.Equals("c_12_15"))
                    rezultat[5] = rezultat[5] + 1;
                else if (i.id_proba.Equals("p_6_8"))
                    rezultat[6] = rezultat[6] + 1;
                else if (i.id_proba.Equals("p_9_11"))
                    rezultat[7] = rezultat[7] + 1;
                else if (i.id_proba.Equals("p_12_15"))
                    rezultat[8] = rezultat[8] + 1;
                Debug.WriteLine(rezultat);
            }
            return rezultat;
        }

        public List<int> gasesteParticipanti(String proba, String categorie)
        {
            String cod = proba.Substring(0, 1) + "_" + categorie;

            //System.out.println(cod);

            List<int> listaCoduri = new List<int>();
            foreach (Inscriere i in dataBaseContext.Inscrieri.ToList())
            {
                if (i.id_proba == cod)
                {
                    listaCoduri.Add(i.id_copil);
                    //System.out.println(i.getId_copil());
                }
            }
            return listaCoduri;
        }
        public String getNumeVarstaById(int id)
        {
            foreach (Copil c in dataBaseContext.Copii.ToList())
            {
                if (c.id == id)
                    return c.nume + " - " + c.varsta + " ani";
            }
            return null;
        }

        public List<String> gasesteNumeVarstaCopii(List<int> listCoduri)
        {
            List<String> listNumeCopii = new List<String>();
            foreach (int i in listCoduri)
            {
                listNumeCopii.Add(getNumeVarstaById(i));
            }
            return listNumeCopii;
        }

        public void adaugaCopilInscriere(String nume, int varsta, List<String> probe,String usernameCurent)
        {
            Copil copil = new Copil(nume, varsta);
            dataBaseContext.Copii.Add(copil);
            int id_copil = dataBaseContext.Copii.ToList().Last().id;

            String categorie;
            if (varsta >= 5 && varsta <= 8) categorie = "6_8";
            else if (varsta <= 11) categorie = "9_11";
            else categorie = "12_15";

            /*Console.WriteLine(categorie);*/

            foreach (String s in probe)
            {
                String proba = s.Substring(0, 1) + "_" + categorie;
                /*Console.WriteLine(proba);*/
                Inscriere inscriere = new Inscriere(id_copil, proba);
                dataBaseContext.Inscrieri.Add(inscriere);
                dataBaseContext.SaveChanges();
            }
            
            foreach(var client in loggedClients)
            {
                if (client.Key != usernameCurent)
                {
                IObserver observer = client.Value;
                String username = client.Key;
                Console.WriteLine("Notifying [" + username + "] about new participant.");
                Task.Run(() => observer.participantInscris());
                }
            }
        }



        public List<String> JoinInscrieriCopii(String proba, String categorie)
        {
            String de_cautat = proba.Substring(0, 1) + "_" + categorie;
            //Debug.WriteLine(de_cautat);

            var fullEntries = dataBaseContext.Copii
                .Join(dataBaseContext.Inscrieri,
                    copil => copil.id,
                    inscriere => inscriere.id_copil,
                    (copil, inscriere) => new { Copil = copil, Inscriere = inscriere }
                )
                .Where(entry => entry.Inscriere.id_proba == de_cautat)
                .Select(entry => entry.Copil.nume + " - " + entry.Copil.varsta.ToString())
                .ToList();

            return fullEntries;
        }

        public Proba addProba(Proba proba)
        {
            dataBaseContext.Probe.Add(proba);
            dataBaseContext.SaveChanges();
            return proba;
        }

        public Proba deleteProba(Proba proba)
        {
            dataBaseContext.Probe.Remove(proba);
            dataBaseContext.SaveChanges();
            return proba;
        }

        public List<Proba> getAllProba()
        {
            return dataBaseContext.Probe.ToList();
        }

        public Proba findByIdProba(String id_proba)
        {
            return dataBaseContext.Probe.Where(u => u.id == id_proba).FirstOrDefault();
        }

    }
}

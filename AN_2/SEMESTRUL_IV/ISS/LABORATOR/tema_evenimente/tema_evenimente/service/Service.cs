using Microsoft.EntityFrameworkCore;
using Microsoft.VisualBasic.ApplicationServices;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.model;
using tema_evenimente.observer;
using tema_evenimente.repository;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.ListView;

namespace tema_evenimente.service
{
    public class Service
    {
        public AngajatiRepo angajatiRepo;
        public ClientiRepo clientiRepo;
        public EvenimenteRepo evenimenteRepo;
        public ReprezentariRepo reprezentariRepo;
        public RezervariRepo rezervariRepo;

        private ConcurrentBag<IObserver> ferestre;
        public Service(AngajatiRepo angajatiRepo,ClientiRepo clientiRepo,EvenimenteRepo evenimenteRepo,ReprezentariRepo reprezentariRepo,RezervariRepo rezervariRepo)
        {
            this.angajatiRepo= angajatiRepo;
            this.clientiRepo= clientiRepo;
            this.evenimenteRepo= evenimenteRepo;
            this.reprezentariRepo= reprezentariRepo;
            this.rezervariRepo= rezervariRepo;
            ferestre= new ConcurrentBag<IObserver>();
        }

        public int getAllAngajati()
        {
            return angajatiRepo.repo.Angajati.ToList().Count;
        }

        public Client getByAccountClient(String username, String parola)
        {
            return clientiRepo.repo.Clienti.Where(u => u.username == username && u.parola == parola).FirstOrDefault();
        }
        public Angajat getByAccountAngajat(String username, String parola)
        {
            return angajatiRepo.repo.Angajati.Where(u => u.username == username && u.parola == parola).FirstOrDefault();
        }

        public int login(string username, string parola,IObserver fereastra)
        {
            Client c = getByAccountClient(username, parola);
            Angajat a = getByAccountAngajat(username, parola);
            if (c != null)
            {
                ferestre.Add(fereastra);
                return 1;
            }
            else if (a != null)
            {
                ferestre.Add(fereastra);
                return 2;
            }
            return 0;
        }

        public void createAccount(String username, String parola)
        {
            if (username == "" || parola == "")
            {
                throw new Exception("USERNAME-UL SI PAROLA NU POT FI NULE!");
            }
            else
            {
                clientiRepo.repo.Clienti.Add(new Client(username, parola));
                clientiRepo.repo.SaveChanges();
            }
        }

        public List<DTO_R_R_E> getRezervari()
        {
            var result = rezervariRepo.repo.Rezervari
                .Join(reprezentariRepo.repo.Reprezentari,
                    rezervare => rezervare.id_reprezentare,
                    reprezentare => reprezentare.id,
                    (rezervare, reprezentare) => new { Rezervare = rezervare, Reprezentare = reprezentare }
                )
                .Join(evenimenteRepo.repo.Evenimente,
                    joined => joined.Reprezentare.id_eveniment,
                    eveniment => eveniment.id,
                    (joined, eveniment) => new { Joined = joined, Eveniment = eveniment }
                )
            .Select(entry => new DTO_R_R_E(entry.Joined.Rezervare.id,
                    $"DATA: {entry.Joined.Reprezentare.data}   |   LOC: {entry.Joined.Rezervare.nr_loc}   |  EVENIMENT: {entry.Eveniment.nume}"))
                .ToList();
            return result;
        }

        public List<DTO_R_R_E> getRezervariClient(int id_client)
        {
            var result = rezervariRepo.repo.Rezervari
                .Join(reprezentariRepo.repo.Reprezentari,
                    rezervare => rezervare.id_reprezentare,
                    reprezentare => reprezentare.id,
                    (rezervare, reprezentare) => new { Rezervare = rezervare, Reprezentare = reprezentare }
                )
                .Join(evenimenteRepo.repo.Evenimente,
                    joined => joined.Reprezentare.id_eveniment,
                    eveniment => eveniment.id,
                    (joined, eveniment) => new { Joined = joined, Eveniment = eveniment }
                )
            .Where(entry => entry.Joined.Rezervare.id_client == id_client)
            .Select(entry => new DTO_R_R_E(entry.Joined.Rezervare.id,
                    $"DATA: {entry.Joined.Reprezentare.data}   |   LOC: {entry.Joined.Rezervare.nr_loc}   |  EVENIMENT: {entry.Eveniment.nume}"))
                .ToList();
            return result;
        }

        public void stergeRezervare(int id_rezervare)
        {
            var rezervare = rezervariRepo.repo.Rezervari.Find(id_rezervare);
            rezervariRepo.repo.Rezervari.Remove(rezervare);
            rezervariRepo.repo.SaveChanges();

            foreach (var client in ferestre)
            {
                IObserver observer = client;
                Task.Run(() => observer.updateTabele());
            }
        }

        public List<Eveniment> getEvenimente()
        {
            return evenimenteRepo.repo.Evenimente.ToList();
        }

        public List<Reprezentare> getReprezentariByIdEveniment(int id_eveniment)
        {
            return reprezentariRepo.repo.Reprezentari.Where(reprezentare => reprezentare.id_eveniment == id_eveniment).ToList();
        }

        public void adaugaEveniment(string nume,int nr_locuri, int pret)
        {
            Eveniment e = new Eveniment(nume, nr_locuri, pret);
            evenimenteRepo.repo.Evenimente.Add(e);
            evenimenteRepo.repo.SaveChanges();

            foreach (var client in ferestre)
            {
                IObserver observer = client;
                Task.Run(() => observer.updateTabele());
            }
        }

        public void adaugaReprezentare(int id_eveniment,DateTime data)
        {
            Reprezentare r = new Reprezentare(data,id_eveniment);
            reprezentariRepo.repo.Reprezentari.Add(r);
            reprezentariRepo.repo.SaveChanges();
        }

        public List<Eveniment> cautareNume(string nume)
        {
            string lowercaseNume = nume.ToLower();

            return evenimenteRepo.repo.Evenimente
                .Where(e => e.nume.ToLower().Contains(lowercaseNume))
                .ToList();
        }

        public List<Reprezentare> cautareData(DateTime data)
        {
            return reprezentariRepo.repo.Reprezentari
                .Where(e => e.data.Date == data.Date)
                .ToList();
        }

        public int getIdClient(string username,string parola)
        {
            return getByAccountClient(username, parola).id;
        }

        public void adaugaRezervare(int id_reprezentare,int nr_loc,int id_client,string status)
        {
            rezervariRepo.repo.Rezervari.Add(new Rezervare(id_reprezentare, nr_loc, id_client, status));
            rezervariRepo.repo.SaveChanges();

            foreach (var client in ferestre)
            {
                    IObserver observer = client;
                    Task.Run(() => observer.updateTabele());
            }
        }
    }
}

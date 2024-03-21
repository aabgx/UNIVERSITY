
using ClientTest;
using Model;
using System;
using Xunit;
using static System.Net.WebRequestMethods;

namespace ClientTest
{
    
    public class ClientTest
    {
        [Fact]
        public async Task testClient()
        {
            string baseAddress = "http://localhost:12500";
            ConcursClient client = new ConcursClient(baseAddress);

            try
            {
                // Exemplu de adăugare a unei probe
                Proba newProba = new Proba(Guid.NewGuid().ToString());
                Proba addedProba = await client.AddProbaAsync(newProba);
                Console.WriteLine("Proba a fost adăugată cu succes!");

                // Exemplu de ștergere a unei probe
                Proba deletedProba = await client.DeleteProbaAsync(addedProba);
                Console.WriteLine("Proba a fost ștearsă cu succes!");

                // Exemplu de obținere a tuturor probelor
                List<Proba> allProba = await client.GetAllProbaAsync();
                foreach (Proba proba in allProba)
                {
                    Console.WriteLine($"ID: {proba.id}");
                }

                // Exemplu de găsire a unei probe după ID
                String idProba = "d_6_8"; // ID-ul probei căutate
                Proba foundProba = await client.FindByIdProbaAsync(idProba);
                if (foundProba != null)
                {
                    Console.WriteLine($"Proba cu ID-ul {idProba} a fost găsită");
                }
                else
                {
                    Console.WriteLine($"Proba cu ID-ul {idProba} nu a fost găsită.");
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Eroare: {ex.Message}");
            }
        }
    }
}


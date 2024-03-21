using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Conventions;
using System.Diagnostics;
using tema_evenimente.controllers;
using tema_evenimente.repository;
using tema_evenimente.service;

namespace tema_evenimente
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {

            var contextOptions = new DbContextOptionsBuilder<DBContext>()
                .UseSqlite("Data Source=D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\ISS\\LABORATOR\\tema_evenimente\\tema_evenimente\\DBEvenimente.db")
                .Options;
            var context = new DBContext(contextOptions);

            var angajatiRepo=new AngajatiRepo(context);
            var clientiRepo=new ClientiRepo(context);
            var evenimenteRepo=new EvenimenteRepo(context);
            var reprezentariRepo=new ReprezentariRepo(context);
            var rezervariRepo=new RezervariRepo(context);


            Service service=new Service(angajatiRepo,clientiRepo,evenimenteRepo,reprezentariRepo,rezervariRepo);

            StartController startController = new StartController(service);
            Application.Run(new StartView(startController));


        }
    }
}
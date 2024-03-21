using log4net;
using services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mime;
using System.Text;
using System.Threading.Tasks;
using networking.servers;
using persistance;
using Microsoft.EntityFrameworkCore;

namespace Server
{
    public class StartRpcServer
    {
        private static int defaultPort = 55555;

        static void Main(string[] args)
        {
            log4net.Config.XmlConfigurator.ConfigureAndWatch(new System.IO.FileInfo(AppDomain.CurrentDomain.BaseDirectory + "App.config"));
            
            /*Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);*/
            
            /*var builder = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("C:\\Users\\Marius Andreiasi\\RiderProjects\\concursInot\\clientWF\\config.json", optional: true, reloadOnChange: true);
            IConfigurationRoot configuration = builder.Build();*/
            /*string connectionString = configuration.GetConnectionString("MyConnectionString");*/
            //Console.WriteLine(connectionString);
            /*log4net.Config.XmlConfigurator.Configure();*/

            /*ILog logger1 = LogManager.GetLogger("CopilRepository");*/
            CopilRepository copilRepository = new CopilRepository();

            /*ILog logger2 = LogManager.GetLogger("InscriereRepository");*/
            InscriereRepository inscriereRepository = new InscriereRepository();

            /*ILog logger3 = LogManager.GetLogger("ProbaRepository");*/
            ProbaRepository probaRepository = new ProbaRepository();
            
            /*ILog logger4 = LogManager.GetLogger("UtilizatorRepository");*/
            UtilizatorRepository utilizatorRepository = new UtilizatorRepository();

            var contextOptions  = new DbContextOptionsBuilder<DBContext>()
                .UseSqlite("Data Source=D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\JAVA_CSHARP_TEMA\\mpp-proiect-java-aabgx\\tema_bun\\concurs.db")
                .Options;
            //optionBuilder.UseSqlite(@"Data Source=D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\JAVA_CSHARP_TEMA\\mpp-proiect-java-aabgx\\tema_bun\\concurs.db");
            var context = new DBContext(contextOptions );

            //IServices Service = new Service(copilRepository, inscriereRepository, probaRepository,utilizatorRepository);
            IServices Service = new Service(context);
            
            int serverPort = defaultPort;
            AbstractServer server = new RpcConcurrentServer(serverPort, Service);
            try
            {
                server.start();
            }
            catch (ServerException e)
            {
                Console.Error.WriteLine("Error starting the server" + e.Message);
            }
            finally
            {
                try
                {
                    server.stop();
                }
                catch (ServerException e)
                {
                    Console.Error.WriteLine("Error stopping server " + e.Message);
                }
            }
        }
    }
}

using networking.rpcprotocol;
using services;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client
{
    public class StartRpcClient
    {
        private static int defaultPort = 55555;
        private static string defaultServer = "localhost";

        static void Main()
        {
            Debug.WriteLine("IN CLIENT START");
            Dictionary<string, string> clientProps = new Dictionary<string, string>();
            try
            {
                string[] lines = File.ReadAllLines(@"D:\FACULTATE\AN 2\SEMESTRUL IV\MPP\JAVA_CSHARP_TEMA\mpp-proiect-csharp-aabgx\tema_sapt3\Client\appclient.properties");
                foreach (string line in lines)
                {
                    int idx = line.IndexOf('=');
                    if (idx >= 0)
                    {
                        string key = line.Substring(0, idx).Trim();
                        string value = line.Substring(idx + 1).Trim();
                        clientProps[key] = value;
                    }
                }
                Console.WriteLine("Client props set.");
                foreach (var kvp in clientProps)
                {
                    Console.WriteLine(kvp.Key + "=" + kvp.Value);
                }
            }
            catch (IOException e)
            {
                Console.Error.WriteLine("CANNOT FIND appclient.properties" + e);
                return;
            }
            string serverIP = "localhost";
            int serverPort = defaultPort;

            try
            {
                serverPort = 55555;
            }
            catch (FormatException ex)
            {
                Console.Error.WriteLine("Wrong port number " + ex.Message);
                Console.WriteLine("Using default port: " + defaultPort);
            }
            Console.WriteLine("Using server IP " + serverIP);
            Console.WriteLine("Using server port " + serverPort);

            IServices server = new ServicesRpcProxy(serverIP, serverPort);

            Application.Run(new start_view(server));
        }
    }
}

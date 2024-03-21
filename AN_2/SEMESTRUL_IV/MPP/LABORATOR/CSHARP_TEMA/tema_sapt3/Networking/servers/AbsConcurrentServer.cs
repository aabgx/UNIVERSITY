using System;
using System.Net.Sockets;
using System.Threading;

namespace networking.servers;

public abstract class AbsConcurrentServer:AbstractServer
{
    protected AbsConcurrentServer(int port) : base(port)
    {
        Console.WriteLine("Concurrent AbstractServer");
    }

    protected override void processRequest(TcpClient client)
    {
        Thread tw = createWorker(client);
        tw.Start();
    }

    protected abstract Thread createWorker(TcpClient client);
}
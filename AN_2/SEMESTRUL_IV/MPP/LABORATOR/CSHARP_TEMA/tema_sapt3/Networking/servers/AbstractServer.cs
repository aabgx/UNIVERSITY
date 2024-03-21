using System;
using System.IO;
using System.Net.Sockets;
using System.Runtime.Remoting;

namespace networking.servers;

public abstract class AbstractServer
{
    private int port;
    private TcpListener server = null;

    public AbstractServer(int port) {this.port = port;}

    public void start()
    {
        server = new TcpListener(port);
        server.Start();
        try
        {
            while (true)
            {
                Console.WriteLine("Waiting for clients...");
                TcpClient client = server.AcceptTcpClient();
                Console.WriteLine("Client connected...");
                processRequest(client);
            }
        }
        catch (Exception e)
        {
            throw new ServerException("Starting server error...", e);
        } finally
        {
            stop();
        }
    }

    protected abstract void processRequest(TcpClient client);
    
    public void stop()
    {
        try
        {
            server.Stop();
        }
        catch (Exception e)
        {
            throw new ServerException("Closing server error ", e);
        }
    }
}
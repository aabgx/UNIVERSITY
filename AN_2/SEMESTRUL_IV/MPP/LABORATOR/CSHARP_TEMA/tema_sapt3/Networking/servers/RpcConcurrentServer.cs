using System;
using System.Net.Sockets;
using System.Threading;
using networking.rpcprotocol;
using networking.servers;
using services;
using tema_bun.AppProtobuffs;

public class RpcConcurrentServer : AbsConcurrentServer
{
    private IServices server;
    
    public RpcConcurrentServer(int port,IServices s) : base(port)
    {
        server=s;
        Console.WriteLine("RpcConcurrentServer");
    }

    protected override Thread createWorker(TcpClient client)
    {
        ClientRpcWorker worker = new ClientRpcWorker(server, client);
        //ProtobuffWorker worker = new ProtobuffWorker(server, client);
        Thread t = new Thread(() => worker.run());
        return t;
    }
    
    public void stop(){Console.WriteLine("Stopping services...");}
}
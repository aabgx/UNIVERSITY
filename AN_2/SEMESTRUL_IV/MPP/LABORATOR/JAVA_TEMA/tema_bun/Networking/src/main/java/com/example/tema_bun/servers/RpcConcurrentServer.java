package com.example.tema_bun.servers;


import com.example.tema_bun.IServices;
import com.example.tema_bun.objectProtocol.ClientRpcWorker;

import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer{

    private IServices server;

    public RpcConcurrentServer(int port, IServices s) {
        super(port);
        this.server = s;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ClientRpcWorker worker = new ClientRpcWorker(server, client);
        Thread t = new Thread(worker);
        return t;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}

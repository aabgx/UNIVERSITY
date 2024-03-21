using System;
using System.Diagnostics;
using System.Diagnostics.Eventing.Reader;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Newtonsoft.Json;
using services;

namespace networking.rpcprotocol;

public class ClientRpcWorker:IObserver
{
    private IServices server;
    private TcpClient connection;

    private NetworkStream stream;
    private IFormatter formatter;
    private volatile bool connected;
    public ClientRpcWorker(IServices server, TcpClient connection)
    {
        this.server = server;
        this.connection = connection;
        try
        {
            stream=connection.GetStream();
            formatter = new BinaryFormatter();
            connected=true;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }

    public virtual void run()
    {
        while(connected)
        {
            try
            {
                Object request=formatter.Deserialize(stream);
                Response response=handleRequest((Request)request);
                if (response!=null)
                {
                    sendResponse(response);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
				
            try
            {
                Thread.Sleep(1000);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        try
        {
            stream.Close();
            connection.Close();
        }
        catch (Exception e)
        {
            Console.WriteLine("Error "+e);
        }
    }

    
    private Response handleRequest(Request request)
    {
        Response response = null;
        switch (request.Type)
        {
            case RequestType.LOGIN:
                response=solveLogin(request);
                break;
            case RequestType.LOGOUT:
                response=solveLogout(request);
                break;
            case RequestType.GET_NUMBER_OF_PARTICIPANTS:
                response = solveVectorCnt(request);
                break;
            case RequestType.GET_PROBA_CATEGORIE_PARTICIPANTS:
                response = solveGetProbaCategorie(request);
                break;
            case RequestType.ADD_PARTICIPANT:
                response = solveInscriere(request);
                break;
        }
        return response;
    }

    
    
    private Response solveLogin(Request request)
    {
        Utilizator u = (Utilizator)request.Data;
        String username = u.username;
        String parola = u.parola;
        
        try
        {
            bool loggedIn;
            lock (server)
            {
                loggedIn = server.login(username, parola, this);
            }
            Debug.WriteLine("!!!a rezolvat loginul!!!");
            return new Response.Builder().type(ResponseType.OK).data(loggedIn).build();
            /*bool loggedIn = server.login(username, parola, this);
            return new Response.Builder().type(ResponseType.OK).data(loggedIn).build();*/
        }
        catch (Exception e)
        {
            connected = false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
        }
    }

    
    
    private Response solveLogout(Request request)
    {
        Utilizator u = (Utilizator) request.Data;
        String username = u.username;
        try {
            lock (server)
            {
                server.logout(username);
            }
            connected = false;
            return new Response.Builder().type(ResponseType.OK).data(true).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
        }
    }
    
    private Response solveVectorCnt(Request request)
    {
        try
        {
            List<int> result;
            lock (server)
            {
                result = server.creareVectorCnt();
            }
            return new Response.Builder().type(ResponseType.OK).data(result).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
        }
    }
    
    private Response solveGetProbaCategorie(Request request)
    {
//        System.out.println("Get all participants request");

        try {
            string proba_categorie=request.Data.ToString();
            string[] parts=proba_categorie.Split(" ");
            string proba=parts[0],categorie=parts[1];
            List<string> participanti;
            lock (server)
            {
                participanti = server.JoinInscrieriCopii(proba,categorie);
            }
            return new Response.Builder().type(ResponseType.OK).data(participanti).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
        }
    }
    
    private Response solveInscriere(Request request) {
        String[] inscriereData = (String[]) request.Data;

        String nume = inscriereData[0];
        var varsta = int.Parse(inscriereData[1]);
        
        List<string> probe = new List<string>();
        try {
            probe = JsonConvert.DeserializeObject<List<string>>(inscriereData[2]);
            /*foreach (var proba in probe)
            {
                Console.WriteLine(proba);
            }*/
        } catch (JsonSerializationException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
        }

        String usernameCurent = inscriereData[3];
        
        try {
            lock (server)
            {
                server.adaugaCopilInscriere(nume, varsta, probe,usernameCurent);
            }
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.Message).build();
        } 
    }
    
    
    
    private void sendResponse(Response response)
    {
        Console.WriteLine("sending response "+response);
        lock (stream)
        {
            formatter.Serialize(stream, response);
            stream.Flush();
        }
    }

    
    public void participantInscris()
    {
        Response resp = new Response.Builder().type(ResponseType.UPDATE).build();
        /*System.out.println("Participant added");*/
        try
        {
            Debug.WriteLine("am trimis notificare de update");
            sendResponse(resp);
            Debug.WriteLine("a trecut de sendResponse");
        }
        catch (IOException e)
        {
            Console.WriteLine(e.Message);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.Message);
        }
    }
}
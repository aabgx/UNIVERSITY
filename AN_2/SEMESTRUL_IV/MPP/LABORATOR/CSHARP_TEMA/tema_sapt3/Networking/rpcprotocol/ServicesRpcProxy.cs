using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Model;
using Newtonsoft.Json;
using services;

namespace networking.rpcprotocol;

public class ServicesRpcProxy:IServices
{
    private string host;
    private int port;
    private Queue<Response> responses;
    private EventWaitHandle _waitHandle;
    
    private IFormatter formatter;
    private TcpClient connection;
    private NetworkStream stream;
    
    private volatile bool finished;
    private IObserver client;

    public ServicesRpcProxy(string host, int port)
    {
        this.host = host;
        this.port = port;
        responses=new Queue<Response>();
        /*initializeConnection();*/
    }

    
    
    
    
    private void sendRequest(Request request)
    {
        try
        {
            formatter.Serialize(stream, request);
            stream.Flush();
        }
        catch (Exception e)
        {
            throw new Exception("Error sending object "+e);
        }
    }
    
    
    private Response readResponse()
    {
        Response response =null;
        try
        {
            _waitHandle.WaitOne();
            lock (responses)
            {
                //Monitor.Wait(responses); 
                response = responses.Dequeue();
            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
        return response;
    }
    
    
    
    private void initializeConnection()
    {
        try
        {
            connection=new TcpClient(host,port);
            stream=connection.GetStream();
            formatter = new BinaryFormatter();
            finished=false;
            _waitHandle = new AutoResetEvent(false);
            startReader();
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    
    
    
    private void closeConnection()
    {
        finished=true;
        try
        {
            stream.Close();
            connection.Close();
            _waitHandle.Close();
            client=null;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    
    
    private void startReader()
    {
        Thread tw =new Thread(run);
        tw.Start();
    }
    
    
    public virtual void run()
    {
        while(!finished)
        {
            try
            {
                Response response = (Response)formatter.Deserialize(stream);
                Debug.WriteLine("response received "+response);
                if (response.Type==ResponseType.UPDATE)
                {
                    Debug.WriteLine("am primit raspuns de update");
                    /*Console.WriteLine("am primit response de UPDATE");*/
                    client.participantInscris();
                    
                    /*AICI NU MAI AJUNGE DE CEEEEE???
                     ZICE CA E NULL POINTER CLIENT*/
                    Debug.WriteLine("am trecut de facut update");
                }
                else
                {
                    lock (responses)
                    {
                        responses.Enqueue((Response)response);
                    }
                    _waitHandle.Set();
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine("Reading error "+e);
            }
					
        }
    }
    
    
    public bool login(string data, string password, IObserver clientObserver)
    {
        initializeConnection();
        Utilizator u=new Utilizator(data,password);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(u).build();
        sendRequest(request);
        Response response = readResponse();

        Boolean loggedIn = false;

        if (response.Type == ResponseType.OK) {
            loggedIn = (Boolean) response.Data;
            if (loggedIn) {
                Debug.WriteLine("a logat clientul");
                this.client = clientObserver;
                return true;
            }
        } else if (
            response.Type == ResponseType.ERROR)
        {
            Debug.WriteLine("nu a logat clientul");
            string err = response.Data.ToString();
            closeConnection();
            throw new Exception(err);
        }
        return loggedIn;
    }

    
    
    public void logout(string email)
    {
        Utilizator u=new Utilizator(email,null);
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(u).build();
        sendRequest(req);
        Response response = readResponse();
        closeConnection();
        client = null;
        if (response.Type == ResponseType.ERROR)
        {
            String err = response.Data.ToString();
            throw new Exception(err);
        }
    }

    public Utilizator getByAccount(string username, string parola)
    {
        throw new NotImplementedException();
    }

    public void createAccount(string username, string parola)
    {
        throw new NotImplementedException();
    }

    public List<int> creareVectorCnt()
    {
        /*throw new NotImplementedException();*/
        /*initializeConnection();*/
        Request req = new Request.Builder().type(RequestType.GET_NUMBER_OF_PARTICIPANTS).build();
        sendRequest(req);
        Response response = readResponse();
        /*closeConnection();*/
        /*client = null;*/
        if (response.Type == ResponseType.ERROR)
        {
            String err = response.Data.ToString();
            throw new Exception(err);
        }
        return (List<int>) response.Data;
    }

    public List<int> gasesteParticipanti(string proba, string categorie)
    {
        throw new NotImplementedException();
    }

    public string getNumeVarstaById(int id)
    {
        throw new NotImplementedException();
    }

    public List<string> gasesteNumeVarstaCopii(List<int> listCoduri)
    {
        throw new NotImplementedException();
    }

    public void adaugaCopilInscriere(string nume, int varsta, List<string> probe,String usernameCurent)
    {
        /*initializeConnection();*/
        String[] inscriereData = new String[]{nume, varsta.ToString(), JsonConvert.SerializeObject(probe),usernameCurent};

        Request req = new Request.Builder().type(RequestType.ADD_PARTICIPANT).data(inscriereData).build();
        sendRequest(req);
        Response response = readResponse();
        Console.WriteLine(response);
        if (response.Type == ResponseType.ERROR) {
            String err = response.Data.ToString();
            Console.WriteLine(response);
            throw new Exception(err);
        }
    }

    public List<String> JoinInscrieriCopii(String proba, String categorie)
    {
        /*initializeConnection();*/
        Request req = new Request.Builder().type(RequestType.GET_PROBA_CATEGORIE_PARTICIPANTS).data(proba+" "+categorie).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR) {
            String err = response.Data.ToString();
            throw new Exception(err);
        }
        List<String> rez=(List<String>)response.Data;
        return (List<String>)response.Data;
    }

    public Proba addProba(Proba proba) {
        return new Proba();
    }

    public Proba deleteProba(Proba proba)
    {
        return new Proba();
    }

    public List<Proba> getAllProba() {
        return new List<Proba>();
    }

    public Proba findByIdProba(String proba)
    {
        return new Proba();
    }
}
using System.Diagnostics;
using System.Net.Sockets;
using Google.Protobuf;
using networking.rpcprotocol;
using Newtonsoft.Json;
using services;

namespace tema_bun.AppProtobuffs;

public class ProtobuffWorker:IObserver
{
    private IServices server;
    private TcpClient connection;

    private NetworkStream stream;
    private volatile bool connected;
    public ProtobuffWorker(IServices server, TcpClient connection)
    {
        this.server = server;
        this.connection = connection;
        try
        {
            stream=connection.GetStream();
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
                RequestP request = RequestP.Parser.ParseDelimitedFrom(stream);
                ResponseP response = handleRequest(request);
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

    
    private ResponseP handleRequest(RequestP request)
    {
        ResponseP response = null;
        switch (request.Type)
        {
            case RequestP.Types.Type.Login:
                response=solveLogin(request);
                break;
            case RequestP.Types.Type.Logout:
                response=solveLogout(request);
                break;
            case RequestP.Types.Type.GetNumberOfParticipants:
                response = solveGetNumberOfParticipants(request);
                break;
            case RequestP.Types.Type.GetProbaCategorieParticipants:
                response = solveGetProbaCategorieParticipants(request);
                break;
            case RequestP.Types.Type.Inscriere:
                response = solveInscriere(request);
                break;
        }
        return response;
    }
    
    private ResponseP solveLogin(RequestP request)
    {
        UtilizatorP utilizatorP = request.UtilizatorP;
        Utilizator u=new Utilizator(utilizatorP.Username,utilizatorP.Parola);
        String username = u.username;
        String parola = u.parola;
        try
        {
            bool loggedIn;
            lock (server)
            {
                loggedIn = server.login(username, parola, this);
                if(loggedIn==true)
                    return ProtoUtils.createLoginResponse(loggedIn);
                else
                {
                    throw new Exception("UTILIZATORUL NU EXISTA!.");
                }
            }
        }
        catch (Exception e)
        {
            connected = false;
            return ProtoUtils.createErrorResponse(e.Message);
        }
    }
    
    private ResponseP solveLogout(RequestP request)
    {
        UtilizatorP utilizatorP = request.UtilizatorP;
        Utilizator u=new Utilizator(utilizatorP.Username,utilizatorP.Parola);
        String username = u.username;
        try {
            lock (server)
            {
                server.logout(username);
            }
            connected = false;
            return ProtoUtils.createOkResponse();
        } catch (Exception e)
        {
            return ProtoUtils.createErrorResponse(e.Message);
        }
    }
    
    private ResponseP solveGetProbaCategorieParticipants(RequestP request)
    {
        string proba_categorie = request.Str;
        string[] parts = proba_categorie.Split(' ');
        string proba=parts[0],categorie=parts[1];
        List<string> participanti;
        try
        {
            lock (server)
            {
                participanti = server.JoinInscrieriCopii(proba, categorie);
                return ProtoUtils.createGetProbaCategorieParticipantiResponse(participanti);
            }
        } catch (Exception e) {
            return ProtoUtils.createErrorResponse(e.Message);
        }
    }
    
    private ResponseP solveGetNumberOfParticipants(RequestP request)
    {
        try
        {
            List<int> result;
            lock (server)
            {
                result = server.creareVectorCnt();
                Debug.WriteLine(result);
                return ProtoUtils.createVectorCntResponse(result);
            }
        } catch (Exception e) {
            return ProtoUtils.createErrorResponse(e.Message);
        }
    }
    
    private ResponseP solveInscriere(RequestP request)
    {
        var inscriereData = request.InscriereData;

        String nume = inscriereData[0];
        var varsta = int.Parse(inscriereData[1]);
        
        
        List<string> probe = new List<string>();
        string json;
        try {
            probe = JsonConvert.DeserializeObject<List<string>>(inscriereData[2]);
        } catch (JsonSerializationException e) {
            return ProtoUtils.createErrorResponse(e.Message);
        }
        
        String usernameCurent = inscriereData[3];

        try
        {
            lock (server)
            {
                server.adaugaCopilInscriere(nume,varsta,probe,usernameCurent);
            }
            return ProtoUtils.createOkResponse();
        } catch (Exception e)
        {
            return ProtoUtils.createErrorResponse(e.Message);
        }
    }
    
    public void participantInscris()
    {
        ResponseP response = ProtoUtils.createUpdateResponse();
        Console.WriteLine("Participant added");
        try
        {
            sendResponse(response);
        }
        catch (IOException e)
        {
            Console.WriteLine(e.Message);
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    
    private void sendResponse(ResponseP response)
    {
        Console.WriteLine("sending response "+response);
        lock (stream)
        {
            response.WriteDelimitedTo(stream);
            stream.Flush();
        }
    }
}
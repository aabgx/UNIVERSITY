namespace tema_bun.AppProtobuffs;

public class ProtoUtils
{
    public static ResponseP createOkResponse()
    {
        return new ResponseP {Type = ResponseP.Types.Type.Ok};
    }
    
    
    public static ResponseP createUpdateResponse()
    {
        return new ResponseP {Type = ResponseP.Types.Type.Update};
    }
    
    
    public static ResponseP createErrorResponse(string message)
    {
        return new ResponseP { Type = ResponseP.Types.Type.Error, Message=message };
    }
    
    public static ResponseP createLoginResponse(bool loggedIn)
    {
        return new ResponseP { Type = ResponseP.Types.Type.Ok, Boolean=loggedIn};
    }
    
    public static ResponseP createVectorCntResponse(List<int> vector_cnt)
    {
        ResponseP response = new ResponseP { Type = ResponseP.Types.Type.Ok };
        foreach(int value in vector_cnt)
            response.VectorCnt.Add(value);
        return response;
    }
    
    public static ResponseP createGetProbaCategorieParticipantiResponse(List<String> participanti)
    {
        ResponseP response = new ResponseP { Type = ResponseP.Types.Type.Ok };
        foreach(String p in participanti)
        {
            response.ProbaCategorie.Add(p);
        }
        return response;
    }
}
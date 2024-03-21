using System;

namespace networking.rpcprotocol;
[Serializable]
public class Request
{
    private RequestType type;
    private Object data;

    public RequestType Type { get => this.type; set => this.type = value; }
    public object Data { get => this.data; set => this.data = value; }

    public Request() { }

    public override String ToString()
    {
        return "Request{" +
               "type='" + type + '\'' +
               ", data='" + data + '\'' +
               '}';
    }
    
    public class Builder
    {
        private Request request = new Request();

        public Builder type(RequestType type)
        {
            request.Type = type;
            return this;
        }

        public Builder data(Object data)
        {
            request.Data = data;
            return this;
        }

        public Request build()
        {
            return request;
        }
    }
    /*private RequestType Type;
    private Object Data;
    
    private Request(){}


    public RequestType type() { return Type; }
    private void type(RequestType Type) { this.Type = Type;}
    
    
    
    public Object data() { return Data; }
    private void data(Object data) { this.Data = data;}
    

    
    public string toString()
    {
        return "Request{"+"type='"+Type+'\''+", data='"+Data+'\''+'}';
    }


    public class Builder
    {
        private static Request request = new Request();

        public Builder type(RequestType Type)
        {
            request.type(Type);
            return this;
        }

        public Builder data(Object Data)
        {
            request.data(Data);
            return this;
        }

        public Request build() {return request;}
    }*/
}

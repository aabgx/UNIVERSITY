using System;
using System.Data.Common;

namespace networking.rpcprotocol;
[Serializable]
public class Response
{
    private ResponseType type;
    private Object data;

    public ResponseType Type { get => this.type; set => this.type = value; }
    public object Data { get => this.data; set => this.data = value; }

    public Response() { }


    public override String ToString()
    {
        return "Response{" +
               "type='" + type + '\'' +
               ", data='" + data + '\'' +
               '}';
    }


    public class Builder
    {
        private Response response = new Response();

        public Builder type(ResponseType type)
        {
            response.Type = type;
            return this;
        }

        public Builder data(Object data)
        {
            response.Data = data;
            return this;
        }

        public Response build()
        {
            return response;
        }
    }
    /*private ResponseType Type;
    private Object Data;
    
    private Response(){}


    public ResponseType type() { return Type; }
    private void type(ResponseType Type) { this.Type = Type;}
    
    
    
    public Object data() { return Data; }
    private void data(Object data) { this.Data = data;}
    

    
    public string toString()
    {
        return "Response{"+"type='"+Type+'\''+", data='"+Data+'\''+'}';
    }


    public class Builder
    {
        private static Response response = new Response();

        public Builder type(ResponseType Type)
        {
            response.type(Type);
            return this;
        }

        public Builder data(Object Data)
        {
            response.data(Data);
            return this;
        }

        public Response build() {return response;}
    }*/
}
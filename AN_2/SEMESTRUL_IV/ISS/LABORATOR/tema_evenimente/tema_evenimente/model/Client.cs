using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tema_evenimente.model
{
    public class Client : Entitate<int>
    {
        public string username { get; set; }
        public string parola { get; set; }
        public Client(string username, string parola)
        {
            this.username = username;
            this.parola = parola;
        }
    }
}

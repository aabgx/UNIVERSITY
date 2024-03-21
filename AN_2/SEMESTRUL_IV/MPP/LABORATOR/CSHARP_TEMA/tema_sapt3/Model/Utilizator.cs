using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


    [Serializable]
    public class Utilizator : Entitate<int>
    {
        public string username { get; set; }
        public string parola { get; set; }

        public Utilizator(string username, string parola) { this.username = username; this.parola = parola; }
        public Utilizator(int id,string username, string parola) {
            this.id = id;
            this.username = username; this.parola = parola; }
    }


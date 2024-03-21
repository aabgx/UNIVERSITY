using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tema_evenimente.model
{
    public class Rezervare:Entitate<int>
    {
        public int id_reprezentare { get; set; }
        public int nr_loc { get; set; }
        public int id_client { get; set; }
        public string status { get; set; }
        public Rezervare(int id_reprezentare, int nr_loc,int id_client)
        {
            this.id_reprezentare = id_reprezentare;
            this.nr_loc = nr_loc;
            this.id_client= id_client;
            this.status = "neplatit";
        }
        public Rezervare(int id_reprezentare, int nr_loc, int id_client,string status)
        {
            this.id_reprezentare = id_reprezentare;
            this.nr_loc = nr_loc;
            this.id_client = id_client;
            this.status = status;
        }
    }
}

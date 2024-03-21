using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


    [Serializable]
    public class Inscriere : Entitate<int>
    {
        public int id_copil { get; set; }
        public String id_proba { get; set; }

        public Inscriere(int id_copil, String id_proba)
        {
            this.id_copil = id_copil;
            this.id_proba = id_proba;
        }

        public Inscriere(int id,int id_copil, String id_proba)
        {
            this.id = id;
            this.id_copil = id_copil;
            this.id_proba = id_proba;
        }

        public Inscriere()
        {
        }
    }


using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


    [Serializable]
    public class Entitate<entityType>
    {
        public entityType id { get; set; }
    }

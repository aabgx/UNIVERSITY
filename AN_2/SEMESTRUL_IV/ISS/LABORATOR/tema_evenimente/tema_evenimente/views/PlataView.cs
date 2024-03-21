using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;
using tema_evenimente.service;
using static System.Net.Mime.MediaTypeNames;

namespace tema_evenimente.controllers
{
    public partial class PlataView : Form
    {
        private int id_reprezentare;
        private int nr_loc;
        private int id_client;
        private PlataController ctrl;
        
        public PlataView(int id_reprezentare, int nr_loc, int id_client, PlataController ctrl)
        {
            InitializeComponent();
            this.id_client= id_client;
            this.nr_loc= nr_loc;
            this.id_reprezentare= id_reprezentare;
            this.ctrl= ctrl;
        }

        private void rezerva_neplatit_Click(object sender, EventArgs e)
        {
            ctrl.rezerva_neplatit_Click(this,this.id_reprezentare,this.nr_loc,this.id_client);
        }

        private void rezerva_platit_Click(object sender, EventArgs e)
        {
            ctrl.rezerva_platit_Click(this, this.nume_textbox, this.nr_card_textbox, this.cvv_textbox, this.id_reprezentare,this.nr_loc, this.id_client);
        }
    }
}

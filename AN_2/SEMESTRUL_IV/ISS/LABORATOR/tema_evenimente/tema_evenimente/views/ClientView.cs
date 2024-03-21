using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using tema_evenimente.model;
using tema_evenimente.observer;
using tema_evenimente.service;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;

namespace tema_evenimente.controllers
{
    public partial class ClientView : Form, IObserver
    {
        private ClientController ctrl;
        private int id_client;
        public ClientView(ClientController ctrl, int id_client)
        {
            InitializeComponent();
            this.ctrl = ctrl;
            this.id_client = id_client;

            //sa nu fie selecata data by default
            date_tme_picker.Checked = false;

            //load evenimente
            ctrl.load_table_evenimente(new List<Eveniment>(), this.evenimente_table);

            //load rezervari
            ctrl.load_table_rezervari(id_client, this.rezervari_table);

        }

        public void load_table_rezervari(int id_client)
        {
            ctrl.load_table_reprezentari(id_client, this.rezervari_table);
        }

        public void load_table_evenimente(List<Eveniment> evenimente)
        {
            ctrl.load_table_evenimente(evenimente, this.evenimente_table);
        }

        //cautare dupa data
        private void handle_cautare(object sender, EventArgs e)
        {
            ctrl.handle_cautare(this.date_tme_picker, this.reprezentari_table);
        }

        //cautare dupa nume
        private void nume_label_TextChanged(object sender, EventArgs e)
        {
            ctrl.nume_label_TextChanged(this.nume_label, this.evenimente_table);

        }

        public void load_table_reprezentari(int id_eveniment)
        {
            ctrl.load_table_reprezentari(id_eveniment, this.reprezentari_table);
        }

        public void load_table_reprezentari_filtered(List<Reprezentare> reprezentari)
        {
            ctrl.load_table_reprezentari_filtered(reprezentari, this.reprezentari_table);
        }

        private void evenimente_table_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            ctrl.evenimente_table_CellClick(this.evenimente_table, this.reprezentari_table);
        }

        private void adauga_button_Click(object sender, EventArgs e)
        {
            ctrl.adauga_button_Click(this.reprezentari_table, this.nr_loc_numeric, this.id_client);
        }

        public void updateTabele()
        {
            this.Invoke((MethodInvoker)delegate
            {
                ctrl.load_table_rezervari(id_client, this.rezervari_table);
                ctrl.load_table_evenimente(new List<Eveniment>(), this.evenimente_table);
            });
        }

        private void sterge_button_Click(object sender, EventArgs e)
        {
            ctrl.handle_sterge(this.id_client,this.rezervari_table);
        }
    }
}

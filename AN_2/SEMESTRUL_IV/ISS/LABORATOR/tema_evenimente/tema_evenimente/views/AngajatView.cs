using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using tema_evenimente.model;
using tema_evenimente.observer;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public partial class AngajatView : Form,IObserver
    {
        private AngajatController ctrl;
        public AngajatView(AngajatController ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;

            //pentru DateTimePicker sa includa si ora
            date_time_picker.Format = DateTimePickerFormat.Custom;
            date_time_picker.CustomFormat = "dd/MM/yyyy HH:mm";
            date_time_picker.ShowUpDown = true;

            //incarcare tabele
            ctrl.load_table_rezervari(this.rezervari_table);
            ctrl.load_table_evenimente(this.evenimente_table);
        }
        public void load_table_rezervari()
        {
            ctrl.load_table_rezervari(this.rezervari_table);
        }

        public void load_table_evenimente()
        {
            ctrl.load_table_evenimente(this.evenimente_table);
        }

        private void handle_sterge(object sender, EventArgs e)
        {
            ctrl.handle_sterge(this.rezervari_table);
        }

        public void load_table_reprezentari(int id_eveniment)
        {
            ctrl.load_table_reprezentari(id_eveniment, this.reprezentari_table);
        }

        private void evenimente_table_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            ctrl.evenimente_table_CellClick(this.evenimente_table, this.reprezentari_table);
        }

        private void adauga_e_button_Click(object sender, EventArgs e)
        {
            ctrl.adauga_e_button_Click(this.nume_textbox, this.pret_numeric, this.nr_locuri_numeric, this.evenimente_table);
        }

        private void adauga_r_button_Click(object sender, EventArgs e)
        {
            ctrl.adauga_r_button_Click(this.evenimente_table, this.date_time_picker, this.reprezentari_table);
        }

        public void updateTabele()
        {
            this.Invoke((MethodInvoker)delegate {
                load_table_rezervari();
            });
        }
    }
}

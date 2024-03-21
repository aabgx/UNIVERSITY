using Server;
using services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Client
{
    public partial class UtilizatorView : Form,IObserver
    {
        private IServices service;
        private String username;

        /*public UtilizatorView(IServices service, String username)*/
        public UtilizatorView(String username)
        {
            /*this.service = service;*/
            this.username = username;
            InitializeComponent();
            /*setCasute();
            setCombo();*/
        }

        public void setService(IServices service)
        {
            this.service = service;
            setCasute();
            setCombo();
        }

        public void setCasute()
        {
            Debug.WriteLine("ba intra si in setCasute in care trebe");
            List<int> list = service.creareVectorCnt();
            Debug.WriteLine(list[0]);
            d_6_8.Text = list[0].ToString();
            d_9_11.Text = list[1].ToString();
            d_12_15.Text = list[2].ToString();
            c_6_8.Text = list[3].ToString();
            c_9_11.Text = list[4].ToString();
            c_12_15.Text = list[5].ToString();
            p_6_8.Text = list[6].ToString();
            p_9_11.Text = list[7].ToString();
            p_12_15.Text = list[8].ToString();
        }

        public void setCombo()
        {
            this.probaCombo.Items.Add("desen");
            this.probaCombo.Items.Add("cautare de comori");
            this.probaCombo.Items.Add("poezie");

            this.categorieCombo.Items.Add("6_8");
            this.categorieCombo.Items.Add("9_11");
            this.categorieCombo.Items.Add("12_15");
        }

        private void cautaBtn_Click(object sender, EventArgs e)
        {
            if (!(probaCombo.Text.Equals("PROBA") || categorieCombo.Text.Equals("CATEGORIE VARSTA")))
            {
                String proba=probaCombo.Text;
                String proba_bun = proba.Split(" ")[0];
                Debug.WriteLine(proba);
                String categorie=categorieCombo.Text;
                listView.Items.Clear();
                /*listView.Items.Add("asta");*/
                foreach(String nume in service.JoinInscrieriCopii(proba_bun, categorie))
                {
                    listView.Items.Add(nume);
                }
            }
        }

        private void logoutBtn_Click(object sender, EventArgs e)
        {
            this.Hide();
            service.logout(username);
            var start_page = new start_view(service);
            start_page.Show();
        }

        private void inscriereBtn_Click(object sender, EventArgs e)
        {
            if((desenRadio.Checked && cautareRadio.Checked && poezieRadio.Checked)
                || !(desenRadio.Checked || cautareRadio.Checked || poezieRadio.Checked)) 
            {
                DialogResult dr;
                dr = MessageBox.Show("SELECTATI MINIM 1 SI MAXIM 2 PROBE!!!!");
            }
            else if (numeField.Text.Equals(""))
            {
                DialogResult dr;
                dr = MessageBox.Show("NUMELE NU POATE FI NULL!!!!");
            }
            else
            {
                List<String> probe = new List<String>();
                if (desenRadio.Checked) probe.Add("desen");
                if (cautareRadio.Checked) probe.Add("cautare de comori");
                if (poezieRadio.Checked) probe.Add("poezie");

                service.adaugaCopilInscriere(numeField.Text.ToString(),Convert.ToInt32( spinner.Value),probe,username);
                DialogResult dr;
                dr = MessageBox.Show("INSCRIERE EFECTUATA CU SUCCES");
                setCasute();
            }
        }
        
        /*
        public void userUpdate()
        {
            setCasute();
        }

        public delegate void seteazaCastute();
        */
        
        public void participantInscris()
        {
            /*Platform.runLater(()->{
                    setCasute();
                });*/
            /*this.BeginInvoke(new seteazaCastute(this.userUpdate));*/
            
            //doar sa ma refer la sursa (thread) unde a fost creat
            Debug.WriteLine("ce naiba se intampla 0");
            this.BeginInvoke((Action) delegate { 
                Debug.WriteLine("ce naiba se intampla1");
                setCasute();
            Debug.WriteLine("ce naiba se intampla2");
            });

        }
    }
}

using Server;
using services;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Client
{
    public partial class CreareView : Form
    {
        private IServices service;
        public CreareView(IServices service)
        {
            this.service = service;
            InitializeComponent();
        }

        private void loginBtn_Click(object sender, EventArgs e)
        {
            try
            {
                String username = usernameTextBox.Text;
                String parola = parolaTextBox.Text;
                service.createAccount(username, parola);

                this.Hide();
                var utilizator_page = new UtilizatorView(username);
                /*var utilizator_page = new UtilizatorView(service,username);*/
                utilizator_page.Show();
            }
            catch(Exception ex) {
                DialogResult dr;
                dr = MessageBox.Show("USERNAME-UL SI PAROLA NU POT FI NULE!");
            }
        }
    }
}

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
    public partial class LoginView : Form
    {
        private IServices service;
        public LoginView(IServices service)
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
                /*Utilizator entity = service.getByAccount(username, parola);*/
                
                /*var utilizator_view = new UtilizatorView(service,username);*/
                var utilizator_view = new UtilizatorView(username);
                bool ok = false;

                ok = service.login(username, parola, utilizator_view);
                if (ok)
                {
                    utilizator_view.setService(service);

                    this.Hide();
                    utilizator_view.Show();   
                }
                else
                {
                    throw new Exception("UTILIZATOR INEXISTENT!");
                }
            }
            catch (Exception ex)
            {
                DialogResult dr2;
                dr2 = MessageBox.Show(ex.Message);
            }
        }
    }
}

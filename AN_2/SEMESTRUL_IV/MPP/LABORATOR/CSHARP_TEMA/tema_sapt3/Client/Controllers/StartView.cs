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
    public partial class start_view : Form
    {
        private IServices service;
        public start_view(IServices service)
        {
            this.service = service;
            InitializeComponent();
        }

        private void loginBtn_Click(object sender, EventArgs e)
        {
            this.Hide();
            var login_page = new LoginView(this.service);
            login_page.Show();
        }

        private void creareBtn_Click(object sender, EventArgs e)
        {
            this.Hide();
            var creare_page = new CreareView(this.service);
            creare_page.Show();
        }
    }
}

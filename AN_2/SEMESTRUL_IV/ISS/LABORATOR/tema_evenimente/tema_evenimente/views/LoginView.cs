using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public partial class LoginView : Form
    {
        private LoginController ctrl;
        public LoginView(LoginController ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
        }

        public void handle_login(object sender, EventArgs e)
        {
            string username = username_textbox.Text;
            string parola = parola_textbox.Text;
            ctrl.handle_login(username, parola,this);
        }
    }
}
;
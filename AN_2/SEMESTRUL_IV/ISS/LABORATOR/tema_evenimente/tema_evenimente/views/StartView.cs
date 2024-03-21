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
    public partial class StartView : Form
    {
        private StartController ctrl;
        public StartView(StartController ctrl)
        {
            InitializeComponent();
            this.ctrl = ctrl;
        }

        public void handle_creare(object sender,EventArgs e)
        {
            ctrl.handle_creare();
        }

        public void handle_login(object sender, EventArgs e)
        {
            ctrl.handle_login();
        }
    }
}

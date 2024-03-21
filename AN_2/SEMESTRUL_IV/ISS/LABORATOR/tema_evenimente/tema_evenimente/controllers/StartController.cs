using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public class StartController
    {
        private Service service;
        public StartController(Service service)
        {
            this.service = service;
        }

        public void handle_login()
        {
            LoginController loginController=new LoginController(this.service);
            var login_page = new LoginView(loginController);
            login_page.Show();
        }

        public void handle_creare()
        {
            CreareController creareController=new CreareController(this.service);
            var creare_page = new CreareView(creareController);
            creare_page.Show();
        }
    }
}

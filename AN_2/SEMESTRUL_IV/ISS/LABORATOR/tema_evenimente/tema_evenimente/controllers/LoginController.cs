using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public class LoginController
    {
        private Service service;
        public LoginController(Service service)
        {
            this.service = service;
        }

        public void handle_login(string username,string parola, LoginView page)
        {
            try
            {
                int ok = 0;

                var ok_client = service.getByAccountClient(username, parola);
                var ok_angajat = service.getByAccountAngajat(username, parola);

                if (ok_client != null)
                {
                    int id_client = service.getIdClient(username, parola);

                    ClientController clientController = new ClientController(this.service);
                    var clientView = new ClientView(clientController, id_client);
                    service.login(username, parola, clientView);
                    page.Hide();
                    clientView.Show();
                }
                else if (ok_angajat != null)
                {
                    AngajatController angajatController=new AngajatController(this.service);
                    var angajatView = new AngajatView(angajatController);
                    service.login(username, parola, angajatView);
                    page.Hide();
                    angajatView.Show();
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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public class CreareController
    {
        private Service service;
        public CreareController(Service service)
        {
            this.service = service;
        }

        public void handle_creare(string username,string parola,CreareView page)
        {
            try
            {
                service.createAccount(username, parola);
                int id_client = service.getIdClient(username, parola);

                page.Hide();
                ClientController clientController= new ClientController(service);
                var client_page = new ClientView(clientController, id_client);
                client_page.Show();
            }
            catch (Exception ex)
            {
                DialogResult dr;
                dr = MessageBox.Show("USERNAME-UL SI PAROLA NU POT FI NULE!");
            }
        }
    }
}

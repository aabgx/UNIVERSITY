using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public class PlataController
    {
        private Service service;
        public PlataController(Service service)
        {
            this.service = service;
        }

        public void rezerva_neplatit_Click(PlataView page, int id_reprezentare,int nr_loc,int id_client)
        {
            service.adaugaRezervare(id_reprezentare, nr_loc, id_client, "neplatit");
            MessageBox.Show("REZERVARE ADAUGATA CU SUCCES!", "INFO", MessageBoxButtons.OK);
            page.Close();
        }

        public void rezerva_platit_Click(PlataView page,TextBox nume_textbox,TextBox nr_card_textbox,TextBox cvv_textbox,int id_reprezentare,int nr_loc,int id_client)
        {
            //numele sa contina doar cuvinte ce incep cu litera mare
            bool ok_cuvinte = nume_textbox.Text.Split(' ')
                .All(cuvant => Regex.IsMatch(cuvant, @"^[A-Z][a-z]+$"));

            //nr card sa aiba 10 cifre
            bool ok_nr_card = Regex.IsMatch(nr_card_textbox.Text, @"^\d{10}$");

            //cvv sa aiba 3 cifre
            bool ok_cvv = Regex.IsMatch(cvv_textbox.Text, @"^\d{3}$");

            if (ok_cuvinte && ok_cvv && ok_nr_card)
            {
                service.adaugaRezervare(id_reprezentare, nr_loc, id_client, "platit");
                MessageBox.Show("REZERVARE ADAUGATA CU SUCCES!", "INFO", MessageBoxButtons.OK);
                page.Close();
            }
            else
            {
                MessageBox.Show("EROARE LA DATE CARD!\nNUME: cuvinte ce incep cu litera mare\nNR CARD: 10 cifre\nCVV: 3 cifre", "ERROR", MessageBoxButtons.OK);
            }
        }
    }
}

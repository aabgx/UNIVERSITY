using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.model;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public class ClientController
    {
        private Service service;
        public ClientController(Service service)
        {
            this.service = service;
        }
        public void load_table_rezervari(int id_client,DataGridView rezervari_table)
        {
            // Obțineți rezultatul din service.GetRezervari()
            List<DTO_R_R_E> rezultat = service.getRezervariClient(id_client);

            // Curățați DataGridView-ul și adăugați coloanele necesare
            rezervari_table.Rows.Clear();
            rezervari_table.Columns.Clear();
            rezervari_table.Columns.Add("id_rezervare", "ID REZERVARE");
            rezervari_table.Columns.Add("detalii", "DETALII");

            // Parcurgeți fiecare element din rezultat și adăugați-l în DataGridView
            foreach (DTO_R_R_E rezervare in rezultat)
            {
                rezervari_table.Rows.Add(rezervare.id_rezervare, rezervare.detalii);
            }
            rezervari_table.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.AllCells;
        }

        public void load_table_evenimente(List<Eveniment> evenimente,DataGridView evenimente_table)
        {
            if(evenimente.Count==0) { evenimente = service.getEvenimente(); }
            // Curățați DataGridView-ul și adăugați coloanele necesare
            evenimente_table.Rows.Clear();
            evenimente_table.Columns.Clear();
            evenimente_table.Columns.Add("id", "ID EVENIMENT");
            evenimente_table.Columns.Add("nume", "NUME");

            // Parcurgeți fiecare element din rezultat și adăugați-l în DataGridView
            foreach (Eveniment e in evenimente)
            {
                evenimente_table.Rows.Add(e.id, e.nume);
            }
            evenimente_table.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }

        //cautare dupa data
        public void handle_cautare(DateTimePicker date_tme_picker, DataGridView reprezentari_table)
        {
            load_table_reprezentari_filtered(service.cautareData(date_tme_picker.Value),reprezentari_table);
        }

        //cautare dupa nume
        public void nume_label_TextChanged(TextBox nume_label,DataGridView evenimente_table)
        {
            if (nume_label.Text.Length == 0) { load_table_evenimente(service.getEvenimente(), evenimente_table); }
            else
            {
                load_table_evenimente(service.cautareNume(nume_label.Text), evenimente_table);
            }

        }

        public void load_table_reprezentari(int id_eveniment,DataGridView reprezentari_table)
        {
            // Obțineți rezultatul din service.GetRezervari()
            List<Reprezentare> rezultat = service.getReprezentariByIdEveniment(id_eveniment);

            // Curățați DataGridView-ul și adăugați coloanele necesare
            reprezentari_table.Rows.Clear();
            reprezentari_table.Columns.Clear();
            reprezentari_table.Columns.Add("id", "ID REPREZENTARE");
            reprezentari_table.Columns.Add("data", "DATA");

            // Parcurgeți fiecare element din rezultat și adăugați-l în DataGridView
            foreach (Reprezentare r in rezultat)
            {
                reprezentari_table.Rows.Add(r.id, r.data);
            }
            reprezentari_table.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }

        public void load_table_reprezentari_filtered(List<Reprezentare> reprezentari,DataGridView reprezentari_table)
        {

            // Curățați DataGridView-ul și adăugați coloanele necesare
            reprezentari_table.Rows.Clear();
            reprezentari_table.Columns.Clear();
            reprezentari_table.Columns.Add("id", "ID REPREZENTARE");
            reprezentari_table.Columns.Add("data", "DATA");

            // Parcurgeți fiecare element din rezultat și adăugați-l în DataGridView
            foreach (Reprezentare r in reprezentari)
            {
                reprezentari_table.Rows.Add(r.id, r.data);
            }
            reprezentari_table.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }

        public void evenimente_table_CellClick(DataGridView evenimente_table, DataGridView reprezentari_table)
        {
            DataGridViewRow selectedRow = evenimente_table.SelectedRows[0];
            int id_eveniment = (int)selectedRow.Cells["id"].Value;
            load_table_reprezentari(id_eveniment, reprezentari_table);
        }

        public void adauga_button_Click(DataGridView reprezentari_table, NumericUpDown nr_loc_numeric, int id_client)
        {
            if (reprezentari_table.SelectedRows.Count > 0 && nr_loc_numeric.Value != 0)
            {
                var id_reprezentare = reprezentari_table.SelectedRows[0].Cells["id"].Value;

                PlataController plataController = new PlataController(this.service);
                var plataView = new PlataView((int)id_reprezentare, (int)nr_loc_numeric.Value, id_client, plataController);
                plataView.Show();
            }
            else
            {
                MessageBox.Show("SELECTATI CEVA DIN TABELUL REPREZENTARI SI COMPLETATI NR LOC!", "ERROR", MessageBoxButtons.OK);
            }
        }

        public void handle_sterge(int id_client,DataGridView rezervari_table)
        {
            if (rezervari_table.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = rezervari_table.SelectedRows[0];

                int id_rezervare = (int)selectedRow.Cells["id_rezervare"].Value;
                service.stergeRezervare(id_rezervare);
                load_table_rezervari(id_client,rezervari_table);
            }
            else
            {
                MessageBox.Show("SELECTATI CEVA DIN TABELUL REZERVARI!", "ERROR", MessageBoxButtons.OK);
            }
        }
    }
}

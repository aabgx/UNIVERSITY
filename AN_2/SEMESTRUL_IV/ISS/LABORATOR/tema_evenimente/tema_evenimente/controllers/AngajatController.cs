using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using tema_evenimente.model;
using tema_evenimente.service;

namespace tema_evenimente.controllers
{
    public class AngajatController
    {
        private Service service;
        public AngajatController(Service service)
        {
            this.service = service;
        }

        public void load_table_rezervari(DataGridView rezervari_table)
        {
            // Obțineți rezultatul din service.GetRezervari()
            List<DTO_R_R_E> rezultat = service.getRezervari();

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

        public void load_table_evenimente(DataGridView evenimente_table)
        {
            // Obțineți rezultatul din service.GetRezervari()
            List<Eveniment> rezultat = service.getEvenimente();

            // Curățați DataGridView-ul și adăugați coloanele necesare
            evenimente_table.Rows.Clear();
            evenimente_table.Columns.Clear();
            evenimente_table.Columns.Add("id", "ID EVENIMENT");
            evenimente_table.Columns.Add("nume", "NUME");

            // Parcurgeți fiecare element din rezultat și adăugați-l în DataGridView
            foreach (Eveniment e in rezultat)
            {
                evenimente_table.Rows.Add(e.id, e.nume);
            }
            evenimente_table.AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill;
        }

        public void handle_sterge(DataGridView rezervari_table)
        {
            if (rezervari_table.SelectedRows.Count > 0)
            {
                DataGridViewRow selectedRow = rezervari_table.SelectedRows[0];

                int id_rezervare = (int)selectedRow.Cells["id_rezervare"].Value;
                service.stergeRezervare(id_rezervare);
                load_table_rezervari(rezervari_table);
            }
            else
            {
                MessageBox.Show("SELECTATI CEVA DIN TABELUL REZERVARI!", "ERROR", MessageBoxButtons.OK);
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

        public void evenimente_table_CellClick(DataGridView evenimente_table,DataGridView rezervari_table)
        {
            DataGridViewRow selectedRow = evenimente_table.SelectedRows[0];
            int id_eveniment = (int)selectedRow.Cells["id"].Value;
            load_table_reprezentari(id_eveniment,rezervari_table);
        }

        public void adauga_e_button_Click(TextBox nume_textbox,NumericUpDown pret_numeric,NumericUpDown nr_locuri_numeric,DataGridView evenimente_table)
        {
            if (nume_textbox.Text != null && pret_numeric.Value != 0 && nr_locuri_numeric.Value != 0)
            {
                service.adaugaEveniment(nume_textbox.Text, (int)pret_numeric.Value, (int)nr_locuri_numeric.Value);
                load_table_evenimente(evenimente_table);
            }
            else
            {
                MessageBox.Show("COMPLETATI TOATE CAMPURILE!", "ERROR", MessageBoxButtons.OK);
            }
        }

        public void adauga_r_button_Click(DataGridView evenimente_table,DateTimePicker date_time_picker,DataGridView reprezentari_table)
        {
            if (evenimente_table.SelectedRows.Count > 0 && date_time_picker.Value != null)
            {
                DataGridViewRow selectedRow = evenimente_table.SelectedRows[0];

                int id_eveniment = (int)selectedRow.Cells["id"].Value;
                service.adaugaReprezentare(id_eveniment, date_time_picker.Value);
                load_table_reprezentari(id_eveniment, reprezentari_table);
            }
            else
            {
                MessageBox.Show("SELECTATI CEVA DIN TABELUL EVENIMENTE SI SELECTATI O DATA!", "ERROR", MessageBoxButtons.OK);
            }
        }
    }
}

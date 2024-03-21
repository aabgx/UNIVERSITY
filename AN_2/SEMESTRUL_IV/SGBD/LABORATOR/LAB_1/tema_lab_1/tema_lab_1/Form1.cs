using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

namespace tema_lab_1
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-VLPBKIG\\SQLEXPRESS;Initial Catalog=BazaSportivaSGBD;Integrated Security=True");
        SqlDataAdapter da = new SqlDataAdapter();

        DataSet ds = new DataSet();
        DataSet ds2 = new DataSet();

        BindingSource bs = new BindingSource();

        public Form1()
        {
            InitializeComponent();
        }

        private void connectBtn_Click(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Departamente", cs);
            ds.Clear();
            da.Fill(ds);

            //interogarea zice ca rezultatul e o tabela si il luam cu Tables[0]
            dataGridView1.DataSource = ds.Tables[0];
            bs.DataSource = ds.Tables[0];
        }

        private void DataGridViewAntrenoriClicked(object sender, DataGridViewCellEventArgs e)
        {

        }

        private void DataGridViewDepartamenteClicked(object sender, DataGridViewCellEventArgs e)
        {

        }


        //PENTRU INCARCARE COPII LA SELECTAREA UNUI ELEMENT DIN PARINTE
        private void getById(int idDepartament)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Antrenori where idDepartament= @id", cs);
            da.SelectCommand.Parameters.AddWithValue("@id", idDepartament);
            ds2.Clear();

            //pune in ds2 ce returneaza data adapterul
            da.Fill(ds2);
            //apoi incarca tabelul target
            dataGridView2.DataSource = ds2.Tables[0];
        }
        private void SelectionChangedDepartamente(object sender, EventArgs e)
        {
            //daca sunt selecatte 0 sau >1 rows sa nu faca nimic
            if (dataGridView1.SelectedRows.Count != 1) return;
            int idDepartament = Int32.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString());
            textBoxIdDepartament.Text = idDepartament.ToString();
            getById(idDepartament);
        }

        private void adaugaBtnClick(object sender, EventArgs e)
        { 
            try
            {
                cs.Open();
                da.InsertCommand = new SqlCommand("Insert into Antrenori(Nume,Varsta,NrTelefon,AdresaEmail,AniExperienta,IdDepartament)" +
                "values(@Nume,@Varsta,@NrTelefon,@AdresaEmail,@AniExperienta,@IdDepartament)", cs);

                da.InsertCommand.Parameters.AddWithValue("@Nume", textBoxNume.Text);
                da.InsertCommand.Parameters.AddWithValue("@Varsta", textBoxVarsta.Text);
                da.InsertCommand.Parameters.AddWithValue("@NrTelefon", textBoxNrTelefon.Text);
                da.InsertCommand.Parameters.AddWithValue("@AdresaEmail", textBoxEmail.Text);
                da.InsertCommand.Parameters.AddWithValue("@AniExperienta", textBoxAniExperienta.Text);
                da.InsertCommand.Parameters.AddWithValue("@IdDepartament", textBoxIdDepartament.Text);

                da.InsertCommand.ExecuteNonQuery();
                getById(Int32.Parse(textBoxIdDepartament.Text));  //refresh
                MessageBox.Show("ANTRENOR ADAUGAT CU SUCCES!");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                cs.Close();
                textBoxNume.Clear();
                textBoxVarsta.Clear();
                textBoxNrTelefon.Clear();
                textBoxEmail.Clear();
                textBoxAniExperienta.Clear();
            }
        }

        private void stergeBtnClick(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count != 1) return;
            try
            {
                cs.Open();
                da.DeleteCommand = new SqlCommand("Delete from Antrenori where IdAntrenor = @IdAntrenor", cs);

                if (dataGridView2.SelectedRows.Count != 1) return;
                int idAntrenor = Int32.Parse(dataGridView2.SelectedRows[0].Cells[0].Value.ToString());
                da.DeleteCommand.Parameters.AddWithValue("@IdAntrenor", idAntrenor);

                da.DeleteCommand.ExecuteNonQuery();


                getById(Int32.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString()));  //refresh
                MessageBox.Show("ANTRENOR STERS CU SUCCES!");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                cs.Close();
            }
        }

        private void updateBtnClick(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count != 1) return;

            try
            {
                cs.Open();
                da.UpdateCommand = new SqlCommand("Update Antrenori set Nume=@Nume, Varsta=@Varsta," +
                "NrTelefon=@NrTelefon, AdresaEmail=@AdresaEmail, AniExperienta=@AniExperienta where idAntrenor=@idAntrenor", cs);
                da.UpdateCommand.Parameters.AddWithValue("@Nume", textBoxNume.Text);
                da.UpdateCommand.Parameters.AddWithValue("@Varsta", textBoxVarsta.Text);
                da.UpdateCommand.Parameters.AddWithValue("@NrTelefon", textBoxNrTelefon.Text);
                da.UpdateCommand.Parameters.AddWithValue("@AdresaEmail", textBoxEmail.Text);
                da.UpdateCommand.Parameters.AddWithValue("@AniExperienta", textBoxAniExperienta.Text);

                int idAntrenor = Int32.Parse(dataGridView2.SelectedRows[0].Cells[0].Value.ToString());
                da.UpdateCommand.Parameters.AddWithValue("@idAntrenor", idAntrenor);

                da.UpdateCommand.ExecuteNonQuery();
                getById(Int32.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString()));  //refresh
                MessageBox.Show("UPDATE ANTRENOR EFECTUAT CU SUCCES!");

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                cs.Close();
                textBoxNume.Clear();
                textBoxVarsta.Clear();
                textBoxNrTelefon.Clear();
                textBoxEmail.Clear();
                textBoxAniExperienta.Clear();
            }
        }
    }
}

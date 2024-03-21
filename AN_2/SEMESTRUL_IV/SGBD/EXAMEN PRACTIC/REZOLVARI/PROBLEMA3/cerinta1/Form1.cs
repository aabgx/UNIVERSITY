using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Windows.Forms;

namespace cerinta1
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-5LTINQ1\\SQLEXPRESS;Initial Catalog=Problema3;Integrated Security=True");
        SqlDataAdapter da = new SqlDataAdapter();

        DataSet ds = new DataSet();
        DataSet ds2 = new DataSet();

        BindingSource bs = new BindingSource();
        public Form1()
        {
            InitializeComponent();
        }


        //CONNECT INITIAL------------------------------------------------------------------------------------------------------------------------------------------------------------
        private void connectButton_Click(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Producatori", cs);
            ds.Clear();
            da.Fill(ds);

            //interogarea zice ca rezultatul e o tabela si il luam cu Tables[0]
            dataGridView1.DataSource = ds.Tables[0];
            bs.DataSource = ds.Tables[0];
        }



        //PENTRU INCARCARE COPII LA SELECTAREA UNUI ELEMENT DIN PARINTE--------------------------------------------------------------------------------------------------------------
        private void getById(int idParinte)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Biscuiti WHERE cod_p= @idParinte", cs);
            da.SelectCommand.Parameters.AddWithValue("@idParinte", idParinte);
            ds2.Clear();

            //pune in ds2 ce returneaza data adapterul
            da.Fill(ds2);
            //apoi incarca tabelul target
            dataGridView2.DataSource = ds2.Tables[0];
        }
        private void SelectionChangedDataGridView1(object sender, EventArgs e)
        {
            //daca sunt selecatte 0 sau >1 rows sa nu faca nimic
            if (dataGridView1.SelectedRows.Count != 1) return;
            int idParinte = Int32.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString());
            idParinteTextBox.Text = idParinte.ToString();
            getById(idParinte);
        }


        //ADD------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        private void adaugaBtnClick(object sender, EventArgs e)
        {
            try
            {
                cs.Open();
                da.InsertCommand = new SqlCommand("Insert into Biscuiti(nume_b,nr_calorii,pret,cod_p)" +
                "values(@nume_b,@nr_calorii,@pret,@cod_parinte)", cs);

                da.InsertCommand.Parameters.AddWithValue("@nume_b", textBox1.Text);
                da.InsertCommand.Parameters.AddWithValue("@nr_calorii", textBox2.Text);
                da.InsertCommand.Parameters.AddWithValue("@pret", textBox3.Text);
                da.InsertCommand.Parameters.AddWithValue("@cod_parinte", idParinteTextBox.Text);

                da.InsertCommand.ExecuteNonQuery();
                getById(Int32.Parse(idParinteTextBox.Text));  //refresh
                MessageBox.Show("ADD EFECTUAT CU SUCCES!");
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                cs.Close();
                textBox1.Clear();
                textBox2.Clear();
                textBox3.Clear();
            }
        }

        //DELETE----------------------------------------------------------------------------------------------------------------------------------------------------
        private void stergeBtnClick(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count != 1) return;
            try
            {
                cs.Open();
                da.DeleteCommand = new SqlCommand("DELETE FROM Biscuiti WHERE cod_b = @idCopil", cs);

                if (dataGridView2.SelectedRows.Count != 1) return;
                int idCopil = Int32.Parse(dataGridView2.SelectedRows[0].Cells[0].Value.ToString());
                da.DeleteCommand.Parameters.AddWithValue("@idCopil", idCopil);

                da.DeleteCommand.ExecuteNonQuery();


                getById(Int32.Parse(idParinteTextBox.Text));  //refresh
                MessageBox.Show("DELETE EFECTUAT CU SUCCES!");
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



        //UPDATE----------------------------------------------------------------------------------------------------------------------------------------------------
        private void updateBtnClick(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count != 1) return;

            try
            {
                cs.Open();
                da.UpdateCommand = new SqlCommand("UPDATE Biscuiti SET nume_b=@nume_b, nr_calorii=@nr_calorii,pret=@pret " +
                "WHERE cod_b=@idCopil", cs);

                da.UpdateCommand.Parameters.AddWithValue("@nume_b", textBox1.Text);
                da.UpdateCommand.Parameters.AddWithValue("@nr_calorii", textBox2.Text);
                da.UpdateCommand.Parameters.AddWithValue("@pret", textBox3.Text);


                int idCopil = Int32.Parse(dataGridView2.SelectedRows[0].Cells[0].Value.ToString());
                da.UpdateCommand.Parameters.AddWithValue("@idCopil", idCopil);

                da.UpdateCommand.ExecuteNonQuery();
                getById(Int32.Parse(idParinteTextBox.Text));  //refresh

                MessageBox.Show("UPDATE EFECTUAT CU SUCCES!");

            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                cs.Close();
                textBox1.Clear();
                textBox2.Clear();
                textBox3.Clear();
            }
        }
    }
}
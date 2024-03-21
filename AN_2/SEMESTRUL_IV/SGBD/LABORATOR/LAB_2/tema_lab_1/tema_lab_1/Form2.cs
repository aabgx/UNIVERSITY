using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace tema_lab_1
{
    public partial class Form2 : Form
    {
        static string con = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;
        SqlConnection cs = new SqlConnection(con);
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        DataSet ds2 = new DataSet();

        List<TextBox> textBoxes = new List<TextBox>();
        public Form2()
        {
            InitializeComponent();
            List<string> childColNames = new List<string>(ConfigurationManager.AppSettings["childColumns"].Split(','));

            int y = 0;
            int x = 1;
            foreach (string col in childColNames)
            {
                var tb = new TextBox();
                var lbl = new Label();
                //tb.Text = col;
                lbl.Text = col;
                Console.WriteLine(col);
                panel1.Controls.Add(lbl);
                panel1.Controls.Add(tb);
                textBoxes.Add(tb);
                tb.Top = 40 * y;
                tb.Left = 100 * x;
                lbl.Top = 40 * y;
                y++;
            }

            /*textBoxes[0].ReadOnly = true;*/
            textBoxes[textBoxes.Count - 1].ReadOnly = true;
        }

        private void connectBtn_Click(object sender, EventArgs e)
        {
            string select = ConfigurationManager.AppSettings["selectParent"];
            da.SelectCommand = new SqlCommand(select, cs);
            ds.Clear();
            da.Fill(ds);
            dataGridView1.DataSource = ds.Tables[0];
        }

        private void select_children(int idParent)
        {
            string select = ConfigurationManager.AppSettings["selectChildren"];
            da.SelectCommand = new SqlCommand(select, cs);
            string fk = ConfigurationManager.AppSettings["fk"];
            da.SelectCommand.Parameters.AddWithValue(fk, idParent);
            
            ds2.Clear();
            da.Fill(ds2);
            dataGridView2.DataSource = ds2.Tables[0];
        }

        //parent change
        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count != 1) return;
            int idParent = Int32.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString());
            textBoxes[textBoxes.Count - 1].Text = dataGridView1.SelectedRows[0].Cells[0].Value.ToString();
            select_children(idParent); //incarcare copil
        }

        //child change
        private void dataGridView2_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count != 1) return;
            List<string> childColNames = new List<string>(ConfigurationManager.AppSettings["childColumns"].Split(','));
            int nrCols = childColNames.Count;

            for (int i = 0; i < textBoxes.Count; i++)
            {
                textBoxes[i].Text = dataGridView2.SelectedRows[0].Cells[i+1].Value.ToString();
            }
        }

        private void addChild_Click(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedRows.Count != 1) return;

            try
            {
                cs.Open();
                int idParinte = Int32.Parse(dataGridView1.SelectedRows[0].Cells[0].Value.ToString());

                List<string> param = new List<string>(ConfigurationManager.AppSettings["childInsertCol"].Split(','));
                List<string> paramValues = new List<string>(ConfigurationManager.AppSettings["childColValues"].Split(','));

                string command = "insert into " + ConfigurationManager.AppSettings["childTable"] + "(" + ConfigurationManager.AppSettings["childColumns"] + ")" +
                    "values(" + ConfigurationManager.AppSettings["childInsertCol"] + ")";

                da.InsertCommand = new SqlCommand(command, cs);

                for (int i = 0; i < param.Count; i++)
                {
                    if (paramValues[i] == "int")
                    {
                        da.InsertCommand.Parameters.AddWithValue(param[i], Int32.Parse(textBoxes[i].Text));
                    }

                    if (paramValues[i] == "string")
                    {
                        da.InsertCommand.Parameters.AddWithValue(param[i], textBoxes[i].Text);
                    }
                }

                da.InsertCommand.ExecuteNonQuery();
                select_children(idParinte); //REFRESH
                MessageBox.Show("Successful add to database!");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                cs.Close();
            }
        }

        private void updateChild_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count != 1) return;
            int idChild = Int32.Parse(dataGridView2.SelectedRows[0].Cells[0].Value.ToString());

            try
            {
                cs.Open();
                int idDepartament = Int32.Parse(textBoxes[textBoxes.Count - 1].Text);
                string command = ConfigurationManager.AppSettings["updateChild"];
                List<string> updParam = new List<string>(ConfigurationManager.AppSettings["childInsertCol"].Split(','));
                List<string> paramTypes = new List<string>(ConfigurationManager.AppSettings["childColValues"].Split(','));

                da.UpdateCommand = new SqlCommand(command, cs);
                da.UpdateCommand.Parameters.AddWithValue(ConfigurationManager.AppSettings["pk"], idChild);

                Console.WriteLine(command);

                for (int i = 0; i < updParam.Count - 1; i++)
                {
                    if (paramTypes[i] == "string")
                    {
                        da.UpdateCommand.Parameters.AddWithValue(updParam[i], textBoxes[i].Text);
                    }
                    if (paramTypes[i] == "int")
                    {
                        da.UpdateCommand.Parameters.AddWithValue(updParam[i], Int32.Parse(textBoxes[i].Text));
                    }
                }

                da.UpdateCommand.ExecuteNonQuery();
                select_children(idDepartament); //REFRESH
                MessageBox.Show("Updated succesfully!");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                cs.Close();
            }
        }

        private void deleteChild_Click(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedRows.Count != 1) return;
            int idChild = Int32.Parse(dataGridView2.SelectedRows[0].Cells[0].Value.ToString());

            int idDepartament = Int32.Parse(textBoxes[textBoxes.Count - 1].Text);

            try
            {
                cs.Open();
                string command = ConfigurationManager.AppSettings["deleteChild"];

                da.UpdateCommand = new SqlCommand(command, cs);
                da.UpdateCommand.Parameters.AddWithValue(ConfigurationManager.AppSettings["pk"], idChild);

                Console.WriteLine(command);

                da.UpdateCommand.ExecuteNonQuery();
                select_children(idDepartament); //REFRESH
                MessageBox.Show("Deleted succesfully!");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            finally
            {
                cs.Close();
            }
        }

    }
}

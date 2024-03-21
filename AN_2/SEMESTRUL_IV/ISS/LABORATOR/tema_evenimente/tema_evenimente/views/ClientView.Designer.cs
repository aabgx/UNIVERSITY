namespace tema_evenimente.controllers
{
    partial class ClientView
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            rezervari_table = new DataGridView();
            evenimente_table = new DataGridView();
            label1 = new Label();
            label2 = new Label();
            sterge_button = new Button();
            adauga_button = new Button();
            date_tme_picker = new DateTimePicker();
            label3 = new Label();
            label4 = new Label();
            nume_label = new TextBox();
            reprezentari_table = new DataGridView();
            label5 = new Label();
            label6 = new Label();
            nr_loc_numeric = new NumericUpDown();
            ((System.ComponentModel.ISupportInitialize)rezervari_table).BeginInit();
            ((System.ComponentModel.ISupportInitialize)evenimente_table).BeginInit();
            ((System.ComponentModel.ISupportInitialize)reprezentari_table).BeginInit();
            ((System.ComponentModel.ISupportInitialize)nr_loc_numeric).BeginInit();
            SuspendLayout();
            // 
            // rezervari_table
            // 
            rezervari_table.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            rezervari_table.Location = new Point(34, 56);
            rezervari_table.Name = "rezervari_table";
            rezervari_table.RowTemplate.Height = 25;
            rezervari_table.Size = new Size(272, 295);
            rezervari_table.TabIndex = 0;
            // 
            // evenimente_table
            // 
            evenimente_table.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            evenimente_table.Location = new Point(477, 56);
            evenimente_table.Name = "evenimente_table";
            evenimente_table.RowTemplate.Height = 25;
            evenimente_table.Size = new Size(272, 295);
            evenimente_table.TabIndex = 1;
            evenimente_table.CellClick += evenimente_table_CellClick;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Segoe UI Black", 9F, FontStyle.Bold | FontStyle.Italic, GraphicsUnit.Point);
            label1.Location = new Point(129, 22);
            label1.Name = "label1";
            label1.Size = new Size(74, 15);
            label1.TabIndex = 2;
            label1.Text = "REZERVARI";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Segoe UI Black", 9F, FontStyle.Bold | FontStyle.Italic, GraphicsUnit.Point);
            label2.Location = new Point(569, 22);
            label2.Name = "label2";
            label2.Size = new Size(87, 15);
            label2.TabIndex = 3;
            label2.Text = "EVENIMENTE";
            // 
            // sterge_button
            // 
            sterge_button.Location = new Point(34, 409);
            sterge_button.Name = "sterge_button";
            sterge_button.Size = new Size(137, 23);
            sterge_button.TabIndex = 4;
            sterge_button.Text = "STERGE REZERVAREA";
            sterge_button.UseVisualStyleBackColor = true;
            sterge_button.Click += sterge_button_Click;
            // 
            // adauga_button
            // 
            adauga_button.Location = new Point(177, 409);
            adauga_button.Name = "adauga_button";
            adauga_button.Size = new Size(129, 23);
            adauga_button.TabIndex = 5;
            adauga_button.Text = "ADAUGA REZERVARE";
            adauga_button.UseVisualStyleBackColor = true;
            adauga_button.Click += adauga_button_Click;
            // 
            // date_tme_picker
            // 
            date_tme_picker.Location = new Point(1006, 363);
            date_tme_picker.Name = "date_tme_picker";
            date_tme_picker.Size = new Size(200, 23);
            date_tme_picker.TabIndex = 7;
            date_tme_picker.ValueChanged += handle_cautare;
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(886, 369);
            label3.Name = "label3";
            label3.Size = new Size(114, 15);
            label3.TabIndex = 8;
            label3.Text = "CAUTA DUPA DATA: ";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(443, 369);
            label4.Name = "label4";
            label4.Size = new Size(120, 15);
            label4.TabIndex = 9;
            label4.Text = "CAUTA DUPA NUME: ";
            // 
            // nume_label
            // 
            nume_label.Location = new Point(569, 363);
            nume_label.Name = "nume_label";
            nume_label.Size = new Size(200, 23);
            nume_label.TabIndex = 10;
            nume_label.TextChanged += nume_label_TextChanged;
            // 
            // reprezentari_table
            // 
            reprezentari_table.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            reprezentari_table.Location = new Point(907, 56);
            reprezentari_table.Name = "reprezentari_table";
            reprezentari_table.RowTemplate.Height = 25;
            reprezentari_table.Size = new Size(272, 295);
            reprezentari_table.TabIndex = 11;
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Font = new Font("Segoe UI Black", 9F, FontStyle.Bold | FontStyle.Italic, GraphicsUnit.Point);
            label5.Location = new Point(1003, 22);
            label5.Name = "label5";
            label5.Size = new Size(99, 15);
            label5.TabIndex = 12;
            label5.Text = "REPREZENTARI";
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Location = new Point(58, 369);
            label6.Name = "label6";
            label6.Size = new Size(92, 15);
            label6.TabIndex = 13;
            label6.Text = "NR_LOC_DORIT:";
            // 
            // nr_loc_numeric
            // 
            nr_loc_numeric.Location = new Point(156, 364);
            nr_loc_numeric.Maximum = new decimal(new int[] { 500, 0, 0, 0 });
            nr_loc_numeric.Name = "nr_loc_numeric";
            nr_loc_numeric.Size = new Size(120, 23);
            nr_loc_numeric.TabIndex = 14;
            // 
            // ClientView
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1218, 462);
            Controls.Add(nr_loc_numeric);
            Controls.Add(label6);
            Controls.Add(label5);
            Controls.Add(reprezentari_table);
            Controls.Add(nume_label);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(date_tme_picker);
            Controls.Add(adauga_button);
            Controls.Add(sterge_button);
            Controls.Add(label2);
            Controls.Add(label1);
            Controls.Add(evenimente_table);
            Controls.Add(rezervari_table);
            Name = "ClientView";
            Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)rezervari_table).EndInit();
            ((System.ComponentModel.ISupportInitialize)evenimente_table).EndInit();
            ((System.ComponentModel.ISupportInitialize)reprezentari_table).EndInit();
            ((System.ComponentModel.ISupportInitialize)nr_loc_numeric).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private DataGridView rezervari_table;
        private DataGridView evenimente_table;
        private Label label1;
        private Label label2;
        private Button sterge_button;
        private Button adauga_button;
        private DateTimePicker date_tme_picker;
        private Label label3;
        private Label label4;
        private TextBox nume_label;
        private DataGridView reprezentari_table;
        private Label label5;
        private Label label6;
        private NumericUpDown nr_loc_numeric;
    }
}
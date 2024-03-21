namespace tema_evenimente.controllers
{
    partial class AngajatView
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
            this.rezervari_table = new System.Windows.Forms.DataGridView();
            this.sterge_button = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.nume_textbox = new System.Windows.Forms.TextBox();
            this.date_time_picker = new System.Windows.Forms.DateTimePicker();
            this.nr_locuri_numeric = new System.Windows.Forms.NumericUpDown();
            this.pret_numeric = new System.Windows.Forms.NumericUpDown();
            this.label11 = new System.Windows.Forms.Label();
            this.evenimente_table = new System.Windows.Forms.DataGridView();
            this.reprezentari_table = new System.Windows.Forms.DataGridView();
            this.label9 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.label14 = new System.Windows.Forms.Label();
            this.adauga_e_button = new System.Windows.Forms.Button();
            this.adauga_r_button = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.rezervari_table)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nr_locuri_numeric)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pret_numeric)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.evenimente_table)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.reprezentari_table)).BeginInit();
            this.SuspendLayout();
            // 
            // rezervari_table
            // 
            this.rezervari_table.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.rezervari_table.Location = new System.Drawing.Point(10, 40);
            this.rezervari_table.Name = "rezervari_table";
            this.rezervari_table.RowTemplate.Height = 25;
            this.rezervari_table.Size = new System.Drawing.Size(251, 332);
            this.rezervari_table.TabIndex = 0;
            // 
            // sterge_button
            // 
            this.sterge_button.Location = new System.Drawing.Point(48, 378);
            this.sterge_button.Name = "sterge_button";
            this.sterge_button.Size = new System.Drawing.Size(156, 42);
            this.sterge_button.TabIndex = 1;
            this.sterge_button.Text = "STERGE REZERVAREA";
            this.sterge_button.UseVisualStyleBackColor = true;
            this.sterge_button.Click += new System.EventHandler(this.handle_sterge);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Segoe UI Black", 9F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.label1.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.label1.Location = new System.Drawing.Point(48, 10);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(157, 15);
            this.label1.TabIndex = 2;
            this.label1.Text = "VIZUALIZARE REZERVARI";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Segoe UI Black", 9F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.label2.Location = new System.Drawing.Point(399, 10);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(137, 15);
            this.label2.TabIndex = 3;
            this.label2.Text = "ADAUGA EVENIMENT";
            // 
            // label3
            // 
            this.label3.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label3.Location = new System.Drawing.Point(388, 9);
            this.label3.Margin = new System.Windows.Forms.Padding(0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(2, 0);
            this.label3.TabIndex = 4;
            // 
            // label4
            // 
            this.label4.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label4.Location = new System.Drawing.Point(317, 226);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(308, 2);
            this.label4.TabIndex = 6;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Segoe UI Black", 9F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.label5.Location = new System.Drawing.Point(388, 248);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(159, 15);
            this.label5.TabIndex = 7;
            this.label5.Text = "ADAUGA REPREZENTARE";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(317, 56);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(47, 15);
            this.label6.TabIndex = 8;
            this.label6.Text = "NUME: ";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(317, 100);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(76, 15);
            this.label7.TabIndex = 9;
            this.label7.Text = "NR. LOCURI: ";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(317, 151);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(36, 15);
            this.label8.TabIndex = 10;
            this.label8.Text = "PRET:";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(317, 296);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(80, 15);
            this.label10.TabIndex = 12;
            this.label10.Text = "DATA SI ORA: ";
            // 
            // nume_textbox
            // 
            this.nume_textbox.Location = new System.Drawing.Point(414, 53);
            this.nume_textbox.Name = "nume_textbox";
            this.nume_textbox.Size = new System.Drawing.Size(200, 23);
            this.nume_textbox.TabIndex = 13;
            // 
            // date_time_picker
            // 
            this.date_time_picker.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
            this.date_time_picker.Location = new System.Drawing.Point(414, 290);
            this.date_time_picker.Name = "date_time_picker";
            this.date_time_picker.Size = new System.Drawing.Size(200, 23);
            this.date_time_picker.TabIndex = 14;
            // 
            // nr_locuri_numeric
            // 
            this.nr_locuri_numeric.Location = new System.Drawing.Point(414, 100);
            this.nr_locuri_numeric.Maximum = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nr_locuri_numeric.Name = "nr_locuri_numeric";
            this.nr_locuri_numeric.Size = new System.Drawing.Size(200, 23);
            this.nr_locuri_numeric.TabIndex = 15;
            // 
            // pret_numeric
            // 
            this.pret_numeric.Location = new System.Drawing.Point(414, 151);
            this.pret_numeric.Maximum = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.pret_numeric.Name = "pret_numeric";
            this.pret_numeric.Size = new System.Drawing.Size(200, 23);
            this.pret_numeric.TabIndex = 16;
            // 
            // label11
            // 
            this.label11.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label11.Location = new System.Drawing.Point(664, 22);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(2, 420);
            this.label11.TabIndex = 18;
            // 
            // evenimente_table
            // 
            this.evenimente_table.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.evenimente_table.Location = new System.Drawing.Point(691, 40);
            this.evenimente_table.Name = "evenimente_table";
            this.evenimente_table.RowTemplate.Height = 25;
            this.evenimente_table.Size = new System.Drawing.Size(289, 332);
            this.evenimente_table.TabIndex = 19;
            this.evenimente_table.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.evenimente_table_CellClick);
            // 
            // reprezentari_table
            // 
            this.reprezentari_table.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.reprezentari_table.Location = new System.Drawing.Point(1011, 40);
            this.reprezentari_table.Name = "reprezentari_table";
            this.reprezentari_table.RowTemplate.Height = 25;
            this.reprezentari_table.Size = new System.Drawing.Size(289, 332);
            this.reprezentari_table.TabIndex = 20;
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(362, 335);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(221, 15);
            this.label9.TabIndex = 21;
            this.label9.Text = "SELECTEAZA UN EVENIMENT DIN TABEL!";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Font = new System.Drawing.Font("Segoe UI Black", 9F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.label12.Location = new System.Drawing.Point(786, 10);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(87, 15);
            this.label12.TabIndex = 22;
            this.label12.Text = "EVENIMENTE";
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Font = new System.Drawing.Font("Segoe UI Black", 9F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.label13.Location = new System.Drawing.Point(1113, 10);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(99, 15);
            this.label13.TabIndex = 23;
            this.label13.Text = "REPREZENTARI";
            // 
            // label14
            // 
            this.label14.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label14.Location = new System.Drawing.Point(295, 22);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(2, 420);
            this.label14.TabIndex = 24;
            // 
            // adauga_e_button
            // 
            this.adauga_e_button.Location = new System.Drawing.Point(399, 191);
            this.adauga_e_button.Name = "adauga_e_button";
            this.adauga_e_button.Size = new System.Drawing.Size(137, 23);
            this.adauga_e_button.TabIndex = 25;
            this.adauga_e_button.Text = "ADAUGA EVENIMENT";
            this.adauga_e_button.UseVisualStyleBackColor = true;
            this.adauga_e_button.Click += new System.EventHandler(this.adauga_e_button_Click);
            // 
            // adauga_r_button
            // 
            this.adauga_r_button.Location = new System.Drawing.Point(388, 378);
            this.adauga_r_button.Name = "adauga_r_button";
            this.adauga_r_button.Size = new System.Drawing.Size(159, 23);
            this.adauga_r_button.TabIndex = 26;
            this.adauga_r_button.Text = "ADAUGA REPREZENTARE";
            this.adauga_r_button.UseVisualStyleBackColor = true;
            this.adauga_r_button.Click += new System.EventHandler(this.adauga_r_button_Click);
            // 
            // AngajatView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1312, 462);
            this.Controls.Add(this.adauga_r_button);
            this.Controls.Add(this.adauga_e_button);
            this.Controls.Add(this.label14);
            this.Controls.Add(this.label13);
            this.Controls.Add(this.label12);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.reprezentari_table);
            this.Controls.Add(this.evenimente_table);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.pret_numeric);
            this.Controls.Add(this.nr_locuri_numeric);
            this.Controls.Add(this.date_time_picker);
            this.Controls.Add(this.nume_textbox);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.sterge_button);
            this.Controls.Add(this.rezervari_table);
            this.Name = "AngajatView";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.rezervari_table)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nr_locuri_numeric)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pret_numeric)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.evenimente_table)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.reprezentari_table)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private DataGridView rezervari_table;
        private Button sterge_button;
        private Label label1;
        private Label label2;
        private Label label3;
        private Label label4;
        private Label label5;
        private Label label6;
        private Label label7;
        private Label label8;
        private Label label10;
        private TextBox nume_textbox;
        private DateTimePicker date_time_picker;
        private NumericUpDown nr_locuri_numeric;
        private NumericUpDown pret_numeric;
        private Label label11;
        private DataGridView evenimente_table;
        private DataGridView reprezentari_table;
        private Label label9;
        private Label label12;
        private Label label13;
        private Label label14;
        private Button adauga_e_button;
        private Button adauga_r_button;
    }
}
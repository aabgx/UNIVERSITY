namespace tema_lab_1
{
    partial class Form1
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
            this.connectBtn = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.textBoxNume = new System.Windows.Forms.TextBox();
            this.textBoxVarsta = new System.Windows.Forms.TextBox();
            this.textBoxNrTelefon = new System.Windows.Forms.TextBox();
            this.textBoxEmail = new System.Windows.Forms.TextBox();
            this.textBoxAniExperienta = new System.Windows.Forms.TextBox();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.button4 = new System.Windows.Forms.Button();
            this.dataGridView2 = new System.Windows.Forms.DataGridView();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.textBoxIdDepartament = new System.Windows.Forms.TextBox();
            this.label8 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).BeginInit();
            this.SuspendLayout();
            // 
            // connectBtn
            // 
            this.connectBtn.Location = new System.Drawing.Point(128, 358);
            this.connectBtn.Name = "connectBtn";
            this.connectBtn.Size = new System.Drawing.Size(124, 56);
            this.connectBtn.TabIndex = 0;
            this.connectBtn.Text = "CONNECT";
            this.connectBtn.UseVisualStyleBackColor = true;
            this.connectBtn.Click += new System.EventHandler(this.connectBtn_Click);
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(22, 54);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.Size = new System.Drawing.Size(326, 240);
            this.dataGridView1.TabIndex = 1;
            this.dataGridView1.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.DataGridViewDepartamenteClicked);
            this.dataGridView1.SelectionChanged += new System.EventHandler(this.SelectionChangedDepartamente);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(490, 316);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(45, 13);
            this.label1.TabIndex = 2;
            this.label1.Text = "NUME: ";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(490, 345);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(56, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "VARSTA: ";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(490, 371);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(84, 13);
            this.label3.TabIndex = 4;
            this.label3.Text = "NR. TELEFON: ";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(490, 401);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(45, 13);
            this.label4.TabIndex = 5;
            this.label4.Text = "EMAIL: ";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(490, 427);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(102, 13);
            this.label5.TabIndex = 6;
            this.label5.Text = "ANI EXPERIENTA: ";
            // 
            // textBoxNume
            // 
            this.textBoxNume.Location = new System.Drawing.Point(614, 316);
            this.textBoxNume.Name = "textBoxNume";
            this.textBoxNume.Size = new System.Drawing.Size(100, 20);
            this.textBoxNume.TabIndex = 7;
            // 
            // textBoxVarsta
            // 
            this.textBoxVarsta.Location = new System.Drawing.Point(614, 342);
            this.textBoxVarsta.Name = "textBoxVarsta";
            this.textBoxVarsta.Size = new System.Drawing.Size(100, 20);
            this.textBoxVarsta.TabIndex = 8;
            // 
            // textBoxNrTelefon
            // 
            this.textBoxNrTelefon.Location = new System.Drawing.Point(614, 368);
            this.textBoxNrTelefon.Name = "textBoxNrTelefon";
            this.textBoxNrTelefon.Size = new System.Drawing.Size(100, 20);
            this.textBoxNrTelefon.TabIndex = 9;
            // 
            // textBoxEmail
            // 
            this.textBoxEmail.Location = new System.Drawing.Point(614, 394);
            this.textBoxEmail.Name = "textBoxEmail";
            this.textBoxEmail.Size = new System.Drawing.Size(100, 20);
            this.textBoxEmail.TabIndex = 10;
            // 
            // textBoxAniExperienta
            // 
            this.textBoxAniExperienta.Location = new System.Drawing.Point(614, 420);
            this.textBoxAniExperienta.Name = "textBoxAniExperienta";
            this.textBoxAniExperienta.Size = new System.Drawing.Size(100, 20);
            this.textBoxAniExperienta.TabIndex = 11;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(443, 532);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(152, 23);
            this.button2.TabIndex = 12;
            this.button2.Text = "ADAUGA";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.adaugaBtnClick);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(601, 532);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(152, 23);
            this.button3.TabIndex = 13;
            this.button3.Text = "STERGE";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.stergeBtnClick);
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(759, 532);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(152, 23);
            this.button4.TabIndex = 14;
            this.button4.Text = "UPDATE";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.updateBtnClick);
            // 
            // dataGridView2
            // 
            this.dataGridView2.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView2.Location = new System.Drawing.Point(385, 54);
            this.dataGridView2.Name = "dataGridView2";
            this.dataGridView2.Size = new System.Drawing.Size(592, 240);
            this.dataGridView2.TabIndex = 16;
            this.dataGridView2.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.DataGridViewAntrenoriClicked);
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(125, 23);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(96, 13);
            this.label6.TabIndex = 17;
            this.label6.Text = "DEPARTAMENTE";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(662, 23);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(71, 13);
            this.label7.TabIndex = 18;
            this.label7.Text = "ANTRENORI";
            // 
            // textBoxIdDepartament
            // 
            this.textBoxIdDepartament.Location = new System.Drawing.Point(877, 368);
            this.textBoxIdDepartament.Name = "textBoxIdDepartament";
            this.textBoxIdDepartament.Size = new System.Drawing.Size(100, 20);
            this.textBoxIdDepartament.TabIndex = 19;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(756, 371);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(112, 13);
            this.label8.TabIndex = 20;
            this.label8.Text = "ID. DEPARTAMENT: ";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1037, 567);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.textBoxIdDepartament);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.dataGridView2);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.textBoxAniExperienta);
            this.Controls.Add(this.textBoxEmail);
            this.Controls.Add(this.textBoxNrTelefon);
            this.Controls.Add(this.textBoxVarsta);
            this.Controls.Add(this.textBoxNume);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.connectBtn);
            this.Name = "Form1";
            this.Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button connectBtn;
        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox textBoxNume;
        private System.Windows.Forms.TextBox textBoxVarsta;
        private System.Windows.Forms.TextBox textBoxNrTelefon;
        private System.Windows.Forms.TextBox textBoxEmail;
        private System.Windows.Forms.TextBox textBoxAniExperienta;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.DataGridView dataGridView2;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox textBoxIdDepartament;
        private System.Windows.Forms.Label label8;
    }
}


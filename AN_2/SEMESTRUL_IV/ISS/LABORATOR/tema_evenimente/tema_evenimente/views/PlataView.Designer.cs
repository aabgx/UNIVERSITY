namespace tema_evenimente.controllers
{
    partial class PlataView
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
            this.label14 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.rezerva_neplatit = new System.Windows.Forms.Button();
            this.rezerva_platit = new System.Windows.Forms.Button();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.nume_textbox = new System.Windows.Forms.TextBox();
            this.nr_card_textbox = new System.Windows.Forms.TextBox();
            this.cvv_textbox = new System.Windows.Forms.TextBox();
            this.data_expirarii_picker = new System.Windows.Forms.DateTimePicker();
            this.SuspendLayout();
            // 
            // label14
            // 
            this.label14.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.label14.Location = new System.Drawing.Point(230, 16);
            this.label14.Name = "label14";
            this.label14.Size = new System.Drawing.Size(2, 200);
            this.label14.TabIndex = 25;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Segoe UI Black", 9F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.label1.Location = new System.Drawing.Point(28, 15);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(178, 15);
            this.label1.TabIndex = 26;
            this.label1.Text = "PLATA CASH LA EVENIMENT";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Segoe UI Black", 9F, ((System.Drawing.FontStyle)((System.Drawing.FontStyle.Bold | System.Drawing.FontStyle.Italic))), System.Drawing.GraphicsUnit.Point);
            this.label2.Location = new System.Drawing.Point(339, 16);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(167, 15);
            this.label2.TabIndex = 27;
            this.label2.Text = "PLATA PE LOC, CU CARDUL";
            // 
            // rezerva_neplatit
            // 
            this.rezerva_neplatit.Location = new System.Drawing.Point(67, 61);
            this.rezerva_neplatit.Name = "rezerva_neplatit";
            this.rezerva_neplatit.Size = new System.Drawing.Size(75, 23);
            this.rezerva_neplatit.TabIndex = 28;
            this.rezerva_neplatit.Text = "REZERVA";
            this.rezerva_neplatit.UseVisualStyleBackColor = true;
            this.rezerva_neplatit.Click += new System.EventHandler(this.rezerva_neplatit_Click);
            // 
            // rezerva_platit
            // 
            this.rezerva_platit.Location = new System.Drawing.Point(364, 181);
            this.rezerva_platit.Name = "rezerva_platit";
            this.rezerva_platit.Size = new System.Drawing.Size(75, 23);
            this.rezerva_platit.TabIndex = 29;
            this.rezerva_platit.Text = "REZERVA";
            this.rezerva_platit.UseVisualStyleBackColor = true;
            this.rezerva_platit.Click += new System.EventHandler(this.rezerva_platit_Click);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(238, 61);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(123, 15);
            this.label3.TabIndex = 30;
            this.label3.Text = "NUMELE DE PE CARD:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(238, 90);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(66, 15);
            this.label4.TabIndex = 31;
            this.label4.Text = "NR. CARD: ";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(238, 117);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(95, 15);
            this.label5.TabIndex = 32;
            this.label5.Text = "DATA EXPIRARII: ";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(238, 150);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(35, 15);
            this.label6.TabIndex = 33;
            this.label6.Text = "CVV: ";
            // 
            // nume_textbox
            // 
            this.nume_textbox.Location = new System.Drawing.Point(392, 53);
            this.nume_textbox.Name = "nume_textbox";
            this.nume_textbox.Size = new System.Drawing.Size(200, 23);
            this.nume_textbox.TabIndex = 34;
            // 
            // nr_card_textbox
            // 
            this.nr_card_textbox.Location = new System.Drawing.Point(392, 82);
            this.nr_card_textbox.Name = "nr_card_textbox";
            this.nr_card_textbox.Size = new System.Drawing.Size(200, 23);
            this.nr_card_textbox.TabIndex = 35;
            // 
            // cvv_textbox
            // 
            this.cvv_textbox.Location = new System.Drawing.Point(392, 142);
            this.cvv_textbox.Name = "cvv_textbox";
            this.cvv_textbox.Size = new System.Drawing.Size(200, 23);
            this.cvv_textbox.TabIndex = 36;
            // 
            // data_expirarii_picker
            // 
            this.data_expirarii_picker.Location = new System.Drawing.Point(392, 111);
            this.data_expirarii_picker.Name = "data_expirarii_picker";
            this.data_expirarii_picker.Size = new System.Drawing.Size(200, 23);
            this.data_expirarii_picker.TabIndex = 37;
            // 
            // PlataView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(612, 243);
            this.Controls.Add(this.data_expirarii_picker);
            this.Controls.Add(this.cvv_textbox);
            this.Controls.Add(this.nr_card_textbox);
            this.Controls.Add(this.nume_textbox);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.rezerva_platit);
            this.Controls.Add(this.rezerva_neplatit);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.label14);
            this.Name = "PlataView";
            this.Text = "PlataViewcs";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Label label14;
        private Label label1;
        private Label label2;
        private Button rezerva_neplatit;
        private Button rezerva_platit;
        private Label label3;
        private Label label4;
        private Label label5;
        private Label label6;
        private TextBox nume_textbox;
        private TextBox nr_card_textbox;
        private TextBox cvv_textbox;
        private DateTimePicker data_expirarii_picker;
    }
}
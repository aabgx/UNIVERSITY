namespace tema_evenimente.controllers
{
    partial class CreareView
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
            this.username_textbox = new System.Windows.Forms.TextBox();
            this.parola_textbox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.creare_button = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // username_textbox
            // 
            this.username_textbox.Location = new System.Drawing.Point(89, 31);
            this.username_textbox.Name = "username_textbox";
            this.username_textbox.Size = new System.Drawing.Size(100, 23);
            this.username_textbox.TabIndex = 0;
            // 
            // parola_textbox
            // 
            this.parola_textbox.Location = new System.Drawing.Point(89, 79);
            this.parola_textbox.Name = "parola_textbox";
            this.parola_textbox.Size = new System.Drawing.Size(100, 23);
            this.parola_textbox.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 34);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(71, 15);
            this.label1.TabIndex = 2;
            this.label1.Text = "USERNAME:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 82);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(54, 15);
            this.label2.TabIndex = 3;
            this.label2.Text = "PAROLA:";
            // 
            // creare_button
            // 
            this.creare_button.Location = new System.Drawing.Point(38, 125);
            this.creare_button.Name = "creare_button";
            this.creare_button.Size = new System.Drawing.Size(112, 33);
            this.creare_button.TabIndex = 4;
            this.creare_button.Text = "CREARE CONT";
            this.creare_button.UseVisualStyleBackColor = true;
            this.creare_button.Click += new System.EventHandler(this.handle_creare);
            // 
            // Creare
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(228, 223);
            this.Controls.Add(this.creare_button);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.parola_textbox);
            this.Controls.Add(this.username_textbox);
            this.Name = "Creare";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private TextBox username_textbox;
        private TextBox parola_textbox;
        private Label label1;
        private Label label2;
        private Button creare_button;
    }
}
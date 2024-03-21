namespace tema_evenimente.controllers
{
    partial class LoginView
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
            this.login_button = new System.Windows.Forms.Button();
            this.username_textbox = new System.Windows.Forms.TextBox();
            this.parola_textbox = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // login_button
            // 
            this.login_button.Location = new System.Drawing.Point(74, 105);
            this.login_button.Name = "login_button";
            this.login_button.Size = new System.Drawing.Size(117, 46);
            this.login_button.TabIndex = 0;
            this.login_button.Text = "LOGIN";
            this.login_button.UseVisualStyleBackColor = true;
            this.login_button.Click += new System.EventHandler(this.handle_login);
            // 
            // username_textbox
            // 
            this.username_textbox.Location = new System.Drawing.Point(103, 28);
            this.username_textbox.Name = "username_textbox";
            this.username_textbox.Size = new System.Drawing.Size(155, 23);
            this.username_textbox.TabIndex = 1;
            // 
            // parola_textbox
            // 
            this.parola_textbox.Location = new System.Drawing.Point(103, 66);
            this.parola_textbox.Name = "parola_textbox";
            this.parola_textbox.Size = new System.Drawing.Size(155, 23);
            this.parola_textbox.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 31);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(68, 15);
            this.label1.TabIndex = 3;
            this.label1.Text = "USERNAME";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 69);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(51, 15);
            this.label2.TabIndex = 4;
            this.label2.Text = "PAROLA";
            // 
            // Login
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(270, 163);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.parola_textbox);
            this.Controls.Add(this.username_textbox);
            this.Controls.Add(this.login_button);
            this.Name = "Login";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private Button login_button;
        private TextBox username_textbox;
        private TextBox parola_textbox;
        private Label label1;
        private Label label2;
    }
}
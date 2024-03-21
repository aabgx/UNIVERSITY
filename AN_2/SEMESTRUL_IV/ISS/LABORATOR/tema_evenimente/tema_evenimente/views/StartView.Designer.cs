namespace tema_evenimente.controllers
{
    partial class StartView
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
            this.creare_button = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // login_button
            // 
            this.login_button.Location = new System.Drawing.Point(46, 27);
            this.login_button.Name = "login_button";
            this.login_button.Size = new System.Drawing.Size(128, 42);
            this.login_button.TabIndex = 0;
            this.login_button.Text = "LOGIN";
            this.login_button.UseVisualStyleBackColor = true;
            this.login_button.Click += new System.EventHandler(this.handle_login);
            // 
            // creare_button
            // 
            this.creare_button.Location = new System.Drawing.Point(46, 87);
            this.creare_button.Name = "creare_button";
            this.creare_button.Size = new System.Drawing.Size(128, 42);
            this.creare_button.TabIndex = 1;
            this.creare_button.Text = "CREARE CONT";
            this.creare_button.UseVisualStyleBackColor = true;
            this.creare_button.Click += new System.EventHandler(this.handle_creare);
            // 
            // Start
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(227, 157);
            this.Controls.Add(this.creare_button);
            this.Controls.Add(this.login_button);
            this.Name = "Start";
            this.Text = "Form1";
            this.ResumeLayout(false);

        }

        #endregion

        private Button login_button;
        private Button creare_button;
    }
}
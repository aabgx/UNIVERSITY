namespace Client
{
    partial class start_view
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
            this.button1 = new System.Windows.Forms.Button();
            this.loginBtn = new System.Windows.Forms.Button();
            this.creareBtn = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(433, 0);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(57, 8);
            this.button1.TabIndex = 0;
            this.button1.Text = "button1";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // loginBtn
            // 
            this.loginBtn.Location = new System.Drawing.Point(38, 22);
            this.loginBtn.Name = "loginBtn";
            this.loginBtn.Size = new System.Drawing.Size(130, 24);
            this.loginBtn.TabIndex = 1;
            this.loginBtn.Text = "LOGIN";
            this.loginBtn.UseVisualStyleBackColor = true;
            this.loginBtn.Click += new System.EventHandler(this.loginBtn_Click);
            // 
            // creareBtn
            // 
            this.creareBtn.Location = new System.Drawing.Point(23, 52);
            this.creareBtn.Name = "creareBtn";
            this.creareBtn.Size = new System.Drawing.Size(167, 26);
            this.creareBtn.TabIndex = 2;
            this.creareBtn.Text = "CREARE CONT";
            this.creareBtn.UseVisualStyleBackColor = true;
            this.creareBtn.Click += new System.EventHandler(this.creareBtn_Click);
            // 
            // login_view
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(202, 106);
            this.Controls.Add(this.creareBtn);
            this.Controls.Add(this.loginBtn);
            this.Controls.Add(this.button1);
            this.Name = "login_view";
            this.Text = "LOGIN PAGE";
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button loginBtn;
        private System.Windows.Forms.Button creareBtn;
    }
}


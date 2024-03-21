namespace cerinta1
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            dataGridView1 = new DataGridView();
            dataGridView2 = new DataGridView();
            COFETARII = new Label();
            label2 = new Label();
            connectButton = new Button();
            addButton = new Button();
            updateButton = new Button();
            deleteButton = new Button();
            label1 = new Label();
            label3 = new Label();
            label4 = new Label();
            textBox1 = new TextBox();
            textBox2 = new TextBox();
            textBox3 = new TextBox();
            idParinteTextBox = new TextBox();
            label5 = new Label();
            ((System.ComponentModel.ISupportInitialize)dataGridView1).BeginInit();
            ((System.ComponentModel.ISupportInitialize)dataGridView2).BeginInit();
            SuspendLayout();
            // 
            // dataGridView1
            // 
            dataGridView1.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridView1.Location = new Point(12, 74);
            dataGridView1.Name = "dataGridView1";
            dataGridView1.RowTemplate.Height = 25;
            dataGridView1.Size = new Size(390, 431);
            dataGridView1.TabIndex = 0;
            dataGridView1.SelectionChanged += SelectionChangedDataGridView1;
            // 
            // dataGridView2
            // 
            dataGridView2.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            dataGridView2.Location = new Point(408, 74);
            dataGridView2.Name = "dataGridView2";
            dataGridView2.Size = new Size(390, 286);
            dataGridView2.TabIndex = 1;
            // 
            // COFETARII
            // 
            COFETARII.AutoSize = true;
            COFETARII.Location = new Point(172, 37);
            COFETARII.Name = "COFETARII";
            COFETARII.Size = new Size(62, 15);
            COFETARII.TabIndex = 2;
            COFETARII.Text = "COFETARII";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(575, 34);
            label2.Name = "label2";
            label2.Size = new Size(45, 15);
            label2.TabIndex = 3;
            label2.Text = "BRIOSE";
            // 
            // connectButton
            // 
            connectButton.Location = new Point(370, 12);
            connectButton.Name = "connectButton";
            connectButton.Size = new Size(75, 23);
            connectButton.TabIndex = 4;
            connectButton.Text = "CONNECT";
            connectButton.UseVisualStyleBackColor = true;
            connectButton.Click += connectButton_Click;
            // 
            // addButton
            // 
            addButton.Location = new Point(408, 482);
            addButton.Name = "addButton";
            addButton.Size = new Size(75, 23);
            addButton.TabIndex = 5;
            addButton.Text = "ADD";
            addButton.UseVisualStyleBackColor = true;
            addButton.Click += adaugaBtnClick;
            // 
            // updateButton
            // 
            updateButton.Location = new Point(575, 482);
            updateButton.Name = "updateButton";
            updateButton.Size = new Size(75, 23);
            updateButton.TabIndex = 6;
            updateButton.Text = "UPDATE";
            updateButton.UseVisualStyleBackColor = true;
            updateButton.Click += updateBtnClick;
            // 
            // deleteButton
            // 
            deleteButton.Location = new Point(723, 482);
            deleteButton.Name = "deleteButton";
            deleteButton.Size = new Size(75, 23);
            deleteButton.TabIndex = 7;
            deleteButton.Text = "DELETE";
            deleteButton.UseVisualStyleBackColor = true;
            deleteButton.Click += stergeBtnClick;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(436, 380);
            label1.Name = "label1";
            label1.Size = new Size(47, 15);
            label1.TabIndex = 8;
            label1.Text = "NUME: ";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(413, 411);
            label3.Name = "label3";
            label3.Size = new Size(70, 15);
            label3.TabIndex = 9;
            label3.Text = "DESCRIERE: ";
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(444, 440);
            label4.Name = "label4";
            label4.Size = new Size(39, 15);
            label4.TabIndex = 10;
            label4.Text = "PRET: ";
            // 
            // textBox1
            // 
            textBox1.Location = new Point(489, 377);
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(100, 23);
            textBox1.TabIndex = 11;
            // 
            // textBox2
            // 
            textBox2.Location = new Point(489, 406);
            textBox2.Name = "textBox2";
            textBox2.Size = new Size(100, 23);
            textBox2.TabIndex = 12;
            // 
            // textBox3
            // 
            textBox3.Location = new Point(489, 435);
            textBox3.Name = "textBox3";
            textBox3.Size = new Size(100, 23);
            textBox3.TabIndex = 13;
            // 
            // idParinteTextBox
            // 
            idParinteTextBox.Location = new Point(698, 411);
            idParinteTextBox.Name = "idParinteTextBox";
            idParinteTextBox.ReadOnly = true;
            idParinteTextBox.Size = new Size(100, 23);
            idParinteTextBox.TabIndex = 14;
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(607, 414);
            label5.Name = "label5";
            label5.Size = new Size(85, 15);
            label5.TabIndex = 15;
            label5.Text = "ID COFETARIE: ";
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(822, 524);
            Controls.Add(label5);
            Controls.Add(idParinteTextBox);
            Controls.Add(textBox3);
            Controls.Add(textBox2);
            Controls.Add(textBox1);
            Controls.Add(label4);
            Controls.Add(label3);
            Controls.Add(label1);
            Controls.Add(deleteButton);
            Controls.Add(updateButton);
            Controls.Add(addButton);
            Controls.Add(connectButton);
            Controls.Add(label2);
            Controls.Add(COFETARII);
            Controls.Add(dataGridView2);
            Controls.Add(dataGridView1);
            Name = "Form1";
            Text = "Form1";
            ((System.ComponentModel.ISupportInitialize)dataGridView1).EndInit();
            ((System.ComponentModel.ISupportInitialize)dataGridView2).EndInit();
            ResumeLayout(false);
            PerformLayout();
        }

        #endregion

        private DataGridView dataGridView1;
        private DataGridView dataGridView2;
        private Label COFETARII;
        private Label label2;
        private Button connectButton;
        private Button addButton;
        private Button updateButton;
        private Button deleteButton;
        private Label label1;
        private Label label3;
        private Label label4;
        private TextBox textBox1;
        private TextBox textBox2;
        private TextBox textBox3;
        private TextBox idParinteTextBox;
        private Label label5;
    }
}
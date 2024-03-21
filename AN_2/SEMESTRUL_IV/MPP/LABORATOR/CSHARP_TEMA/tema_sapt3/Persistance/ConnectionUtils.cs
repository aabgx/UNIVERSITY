using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;

namespace persistance
{
    public static class ConnectionUtils
    {
        public static SQLiteConnection CreateConnection()
        {

            SQLiteConnection sqlite_conn;
            // Create a new database connection:
            /*sqlite_conn = new SQLiteConnection(ConfigurationManager.AppSettings["connectionString"]);*/
            sqlite_conn = new SQLiteConnection("Data Source=D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\JAVA_CSHARP_TEMA\\mpp-proiect-java-aabgx\\tema_bun\\concurs.db");
            /*"D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\JAVA_CSHARP_TEMA\\mpp-proiect-java-aabgx\\tema_bun\\concurs.db"*/
            // Open the connection:
            try
            {
                sqlite_conn.Open();
            }
            catch (Exception ex)
            {

            }
            return sqlite_conn;
        }
    }
}

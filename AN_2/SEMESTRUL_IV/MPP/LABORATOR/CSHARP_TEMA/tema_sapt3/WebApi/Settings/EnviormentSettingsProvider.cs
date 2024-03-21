using System.Globalization;

namespace WebApi.Settings
{
    public class EnvironmentVariablesSettingsProvider : ISettingsProvider
    {
        public string BindingAddress
        {
            get
            {
                return"http://localhost";
            }
        }

        public int Port
        {
            get
            {
                return 12500;
            }
        }

        public string ConnectionString
        {
            get
            {
                return "Data Source=D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\MPP\\JAVA_CSHARP_TEMA\\mpp-proiect-java-aabgx\\tema_bun\\concurs.db";
            }
        }

    }
}

using WebApi.Settings;

namespace WebApi
{
    public class SettingsProviderFactory
    {
        public static ISettingsProvider Create()
        {
            return new EnvironmentVariablesSettingsProvider();
        }
    }
}

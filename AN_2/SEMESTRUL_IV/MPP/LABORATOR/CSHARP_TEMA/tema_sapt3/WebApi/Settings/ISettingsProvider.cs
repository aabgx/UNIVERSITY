namespace WebApi.Settings
{
    public interface ISettingsProvider
    {
        string BindingAddress { get; }
        int Port { get; }
        string ConnectionString { get; }
    }
}

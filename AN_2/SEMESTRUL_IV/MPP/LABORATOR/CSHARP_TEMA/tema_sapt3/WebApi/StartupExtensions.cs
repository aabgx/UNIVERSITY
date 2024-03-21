using Microsoft.OpenApi.Models;
using WebApi.Settings;
using WebApi;
using persistance;
using Microsoft.EntityFrameworkCore;
using services;
using Server;
using System.Configuration;

namespace WebApi
{
    internal static class StartupExtensions
    {
        public static ISettingsProvider SetupSettings(this IServiceCollection services)
        {
            ISettingsProvider settingsProvider = SettingsProviderFactory.Create();
            services.AddSingleton<ISettingsProvider>(settingsProvider);
            return settingsProvider;
        }

        public static IServiceCollection SetupSwagger(this IServiceCollection services)
        {
            services.AddSwaggerGen(options =>
            {
                options.SwaggerDoc("v1", new OpenApiInfo
                {
                    Version = "1",
                    Title = "Concurs Service",
                    Description = "This is the concurs Service.",
                    Contact = new OpenApiContact
                    {
                        Name = "Andreea",
                        Email = "andreeabugnarr@gmail.com"
                    }
                });
            });
            return services;
        }

        public static IServiceCollection SetupDBContext(this IServiceCollection services, ISettingsProvider settingsProvider)
        {
            services.AddDbContext<DBContext>(options =>
            {
                options.UseSqlite(settingsProvider.ConnectionString);
            });
            return services;
        }
    }
}

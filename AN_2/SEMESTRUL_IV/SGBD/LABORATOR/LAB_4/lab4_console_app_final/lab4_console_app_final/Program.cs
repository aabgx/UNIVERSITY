//CATA ULTIMA VARIANTA EU
using System.Data.SqlClient;

public class Program
{
    private static StreamWriter writer = null;
    private static string connectionString = "Data Source=DESKTOP-VLPBKIG\\SQLEXPRESS;Initial Catalog=BazaSportivaSGBD;Integrated Security=True";
    private static string logFilePath = "D:\\FACULTATE\\AN 2\\SEMESTRUL IV\\SGBD\\LAB_4\\lab4_console_app_final\\lab4_console_app_final\\log.txt";
    private static string firstProcedure = "Deadlock1";
    private static string secondProcedure = "Deadlock2";
    private static uint numberOfExecutions = 10000;
    private static uint maxRetries = 3;

    private static async Task ExecuteProcedure(string procedureName)
    {
        using var connection = new SqlConnection(connectionString);
        await connection.OpenAsync().ConfigureAwait(false);
        using var command = new SqlCommand($"exec {procedureName}", connection);
        await command.ExecuteNonQueryAsync().ConfigureAwait(false);
        lock (writer)
        {
            writer.WriteLine($"Procedure - {procedureName} - executed with succes");
        }
        Console.WriteLine($"Procedure - {procedureName} - executed with succes");
        await connection.CloseAsync().ConfigureAwait(false);
    }

    private static async Task ExecuteProceduresWithRetry(int iteration)
    {
        bool ok = false;
        Console.WriteLine($"ITERATION: {iteration}");
        lock (writer)
        {
            writer.WriteLine($"ITERATION: {iteration}");
        }
        for (var i = 0; i < maxRetries; i++)
        {
            try
            {
                var firstTask = ExecuteProcedure(firstProcedure);
                var secondTask = ExecuteProcedure(secondProcedure);
                await Task.WhenAll(firstTask, secondTask);
                ok = true;
                break;
            }
            catch (Exception e)
            {
                lock (writer)
                {
                    writer.WriteLine($"Procedure failed with error: {e.Message}");
                    writer.WriteLine($"Retrying... {i + 1}/{maxRetries}");
                }
                Console.WriteLine($"Procedure failed with error: {e.Message}");
                Console.WriteLine($"Retrying... {i + 1}/{maxRetries}");
            }
        }
        if (ok == false)
        {
            lock (writer)
            {
                writer.WriteLine($"FAILED after {maxRetries} retries - ITERATION: {iteration}");
            }
            Console.WriteLine($"FAILED after {maxRetries} retries - ITERATION: {iteration}");
        }
    }

    private static async Task Main(string[] args)
    {
        writer = new StreamWriter(logFilePath, true);

        for (var i = 0; i < numberOfExecutions; i++)
        {
            await ExecuteProceduresWithRetry(i);
        }
        writer.Dispose();
    }
}
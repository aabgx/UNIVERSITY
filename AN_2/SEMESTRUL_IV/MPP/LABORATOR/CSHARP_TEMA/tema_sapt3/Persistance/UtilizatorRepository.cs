using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;



namespace persistance
{
    public class UtilizatorRepository : Repository<int, Utilizator>
    {
        private readonly ILog _logger = LogManager.GetLogger(nameof(UtilizatorRepository));


        public void adauga(Utilizator entity)
        {
            _logger.Info("adauga utilizator");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "insert into Utilizatori(username, parola) values (@username,@parola)";
            command.Parameters.Add("@username", DbType.String);
            command.Parameters["@username"].Value = entity.username;
            command.Parameters.Add("@parola", DbType.String);
            command.Parameters["@parola"].Value = entity.parola;

            try
            {
                command.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

            }
        }

        public List<Utilizator> getAll()
        {
            _logger.Info("get all utilizatori");
            var list = new List<Utilizator>();
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "select * from Utilizatori";
            var reader = command.ExecuteReader();
            while (reader.Read())
            {
                var id = reader.GetInt16(0);
                var username = reader.GetString(1);
                var parola = reader.GetString(2);
                var ut = new Utilizator(id, username, parola);
/*                {
                    id = id,
                    username = username,
                    parola = parola
                };*/
                list.Add(ut);
            }
            return list;
        }

        public Utilizator cautaId(int id)
        {
            var list = getAll();
            foreach (var el in list)
            {
                if (el.id == id)
                {
                    return el;
                }
            }

            return null;
        }

        public void sterge(Utilizator entity)
        {
            _logger.Info("sterge" + " utilizator");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "delete from Utilizatori where id=@id";
            command.Parameters.Add("@id", DbType.Int16);
            command.Parameters["@id"].Value = entity.id;

            try
            {
                command.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

            }
        }

        public void update(Utilizator entitate, Utilizator nouaEntitate)
        {
            _logger.Info("update utilizator");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "update Utilizatori set username=@username, parola=@parola where id=@id";

            command.Parameters.Add("@username", DbType.String);
            command.Parameters["@username"].Value = nouaEntitate.username;

            command.Parameters.Add("@parola", DbType.String);
            command.Parameters["@parola"].Value = nouaEntitate.parola;

            command.Parameters.Add("@id", DbType.Int16);
            command.Parameters["@id"].Value = entitate.id;
            try
            {
                command.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

            }
        }
    }
}

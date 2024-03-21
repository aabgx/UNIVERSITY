using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace persistance
{
    public class CopilRepository : Repository<int, Copil>
    {
        private readonly ILog _logger = LogManager.GetLogger(nameof(CopilRepository));

        public void adauga(Copil entity)
        {
            _logger.Info("adauga copil");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "insert into Copii(nume, varsta) values (@nume,@varsta)";
            command.Parameters.Add("@nume", DbType.String);
            command.Parameters["@nume"].Value = entity.nume;
            command.Parameters.Add("@varsta", DbType.Int16);
            command.Parameters["@varsta"].Value = entity.varsta;

            try
            {
                command.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

            }
        }

        public List<Copil> getAll()
        {
            _logger.Info("get all copii");
            var list = new List<Copil>();
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "select * from Copii";
            var reader = command.ExecuteReader();
            while (reader.Read())
            {
                var id = reader.GetInt16(0);
                var nume = reader.GetString(1);
                var varsta = reader.GetInt16(2);
                var copil = new Copil()
                {
                    id = id,
                    nume=nume,
                    varsta=varsta
                };
                list.Add(copil);
            }
            return list;
        }

        public Copil cautaId(int id)
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

        public void sterge(Copil entity)
        {
            _logger.Info("sterge" + " copil");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "delete from Copii where id=@id";
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

        public void update(Copil entitate, Copil nouaEntitate)
        {
            _logger.Info("update copil");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "update Copii set nume=@nume, varsta=@varsta where id=@id";

            command.Parameters.Add("@nume", DbType.String);
            command.Parameters["@nume"].Value = nouaEntitate.nume;

            command.Parameters.Add("@varsta", DbType.Int16);
            command.Parameters["@varsta"].Value = nouaEntitate.varsta;

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
        
        public List<string> JoinCopilInscrieri(string string_id_proba)
        {
            _logger.Info("get all join");
            var list = new List<string>();
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "SELECT c.nume as nume,c.varsta as varsta FROM Copii c INNER JOIN Inscrieri i ON c.id = i.id_copil WHERE i.id_proba = ?";

            command.Parameters.AddWithValue("@proba_id", string_id_proba);
            
            var reader = command.ExecuteReader();
            while (reader.Read())
            {
                var nume = reader.GetString("nume");
                var varsta = reader.GetInt16("varsta");
                string str_format = nume + " - " + varsta + " ani";
                
                list.Add(str_format);
            }
            return list;
        }

        public override bool Equals(object? obj)
        {
            return base.Equals(obj);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

        public override string? ToString()
        {
            return base.ToString();
        }
    }
}

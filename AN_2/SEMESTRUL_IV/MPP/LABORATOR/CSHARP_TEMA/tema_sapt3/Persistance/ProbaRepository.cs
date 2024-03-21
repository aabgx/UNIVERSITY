using log4net;
using Model;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace persistance
{
    public class ProbaRepository : Repository<String, Proba>
    {
        private readonly ILog _logger = LogManager.GetLogger(nameof(ProbaRepository));
        public void adauga(Proba entity)
        {
            //_logger.Info("Adauga proba");
            //var connection = ConnectionUtils.CreateConnection();
            //var command = connection.CreateCommand();
            //command.CommandText = "insert into Probe(nume) values (@nume)";
            //command.Parameters.AddWithValue("@nume", entity.nume);

            //try
            //{
            //    command.ExecuteNonQuery();
            //}
            //catch (Exception ex)
            //{

            //}

        }

        public List<Proba> getAll()
        {
            _logger.Info("get all probe");
            var list = new List<Proba>();
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "select * from Probe";
            var reader = command.ExecuteReader();
            while (reader.Read())
            {
                var id = reader.GetString(0);
                //var nume = reader.GetString(1);
                var proba = new Proba()
                {
                    id = id,
                    //nume = nume
                };
                list.Add(proba);
            }
            return list;
        }

        public Proba cautaId(String id)
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

        public void sterge(Proba entity)
        {
            _logger.Info("sterge" +
                " proba");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "delete from Probe where id=@id";
            command.Parameters.Add("@id", DbType.String);
            command.Parameters["@id"].Value = entity.id;

            try
            {
                command.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

            }
        }

        public void update(Proba entitate, Proba nouaEntitate)
        {
            //_logger.Info("update proba");
            //var connection = ConnectionUtils.CreateConnection();
            //var command = connection.CreateCommand();
            //command.CommandText = "update Probe set nume=@nume where id=@id";
            //command.Parameters.Add("@nume", DbType.String);
            //command.Parameters["@nume"].Value = nouaEntitate.nume;
            //command.Parameters.Add("@id", DbType.String);
            //command.Parameters["@id"].Value = entitate.id;
            //try
            //{
            //    command.ExecuteNonQuery();
            //}
            //catch (Exception ex)
            //{

            //}
        }
    }
}

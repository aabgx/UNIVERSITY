using log4net;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace persistance
{
    public class InscriereRepository : Repository<int, Inscriere>
    {
        private readonly ILog _logger = LogManager.GetLogger(nameof(InscriereRepository));


        public void adauga(Inscriere entity)
        {
            _logger.Info("adauga inscriere");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "insert into Inscrieri(id_copil,id_proba) values (@id_copil,@id_proba)";
            command.Parameters.Add("@id_copil", DbType.Int32);
            command.Parameters["@id_copil"].Value = entity.id_copil;
            command.Parameters.Add("@id_proba", DbType.String);
            command.Parameters["@id_proba"].Value = entity.id_proba;

            try
            {
                command.ExecuteNonQuery();
            }
            catch (Exception ex)
            {

            }
        }

        public List<Inscriere> getAll()
        {
            _logger.Info("get all inscrieri");
            var list = new List<Inscriere>();
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "select * from Inscrieri";
            var reader = command.ExecuteReader();
            while (reader.Read())
            {
                var id = reader.GetInt32(0);
                var id_copil = reader.GetInt32(1);
                var id_proba = reader.GetString(2);
                var inscriere = new Inscriere(id, id_copil, id_proba);
/*                {
                    id = id,
                    id_copil = id_copil,
                    id_proba = id_proba
                };*/
                list.Add(inscriere);
            }
            return list;
        }

        public Inscriere cautaId(int id)
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

        public void sterge(Inscriere entity)
        {
            _logger.Info("sterge" + " inscriere");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "delete from Inscrieri where id=@id";
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

        public void update(Inscriere entitate, Inscriere nouaEntitate)
        {
            _logger.Info("update utilizator");
            var connection = ConnectionUtils.CreateConnection();
            var command = connection.CreateCommand();
            command.CommandText = "update Inscrieri set username=@username, parola=@parola where id=@id";

            command.Parameters.Add("@id_copil", DbType.Int16);
            command.Parameters["@id_copil"].Value = nouaEntitate.id_copil;

            command.Parameters.Add("@id_proba", DbType.Int16);
            command.Parameters["@id_proba"].Value = nouaEntitate.id_proba;

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

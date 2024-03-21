using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8.Repository
{
    public abstract class Repository<Entity,IdType> : IRepository<Entity,IdType>
    {
        private readonly String Filename;

        private List<Entity> loadFromFile()
        {
            List<Entity> Entities  = new List<Entity>();
            string[] lines = File.ReadAllLines(Filename);
            foreach (String line in lines)
            {
                Entities.Add(EntityFromLine(line));
            }
            return Entities;
        }

        private async void loadToFile(List<Entity> Entities) {
            List<String> lines = new List<String>();
            foreach(Entity Entity in Entities)
            {
                lines.Add(LineFromEntity(Entity));
            }
            File.WriteAllLines(Filename, lines);
        }

        public Repository(String Filename)
        {
            this.Filename = Filename;
        }

        public Entity add(Entity Entity)
        {
            List<Entity> Entities = loadFromFile();
            Entities.Add(Entity);
            loadToFile(Entities);
            return Entity;
        }

        public Entity? getById(IdType Id)
        {
            List<Entity> Entities = loadFromFile();
            foreach (Entity E in Entities)
            {
                Entity<IdType> Entity = E as Entity<IdType>;

                if (Entity.Id.Equals(Id))
                    return E;
            }
            loadToFile(Entities);
            return default(Entity);
        }

        public Entity remove(Entity Entity)
        {
            List<Entity> Entities = loadFromFile();
            Entities.Remove(Entity);
            loadToFile(Entities);
            return Entity;
        }

        public Entity update(Entity Entity1, Entity Entity2)
        {
            List<Entity> Entities = loadFromFile();
            Entities.Remove(Entity1);
            Entities.Add(Entity2);
            loadToFile(Entities);
            return Entity1;
        }

        public abstract Entity EntityFromLine(String line);

        private String LineFromEntity(Entity Entity)
        {
            return Entity.ToString();
        }

        public List<Entity> getAll()
        {
            return loadFromFile();
        }
    }
}

using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public interface IRepository<Entity,IdType>
    {
        Entity add(Entity Entity);
        Entity? remove(Entity Entity);
        Entity? update(Entity Entity1,Entity Entity2);
        Entity? getById(IdType Id);
        List<Entity> getAll();
    }
}

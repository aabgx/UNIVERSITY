using System;
using System.Collections.Generic;
using System.ComponentModel.Design;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace persistance
{
    public interface Repository<ID, entityType> where entityType: Entitate<ID>
    {
        void adauga(entityType entity);
        void sterge(entityType entity);
        entityType cautaId(ID id);
        List<entityType> getAll();
        void update(entityType entitate, entityType nouaEntitate);
    }
}

using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public class Entity<IdType>
    {
        public IdType Id { get; set; }

        public override bool Equals(Object obj)
        {
            //Check for null and compare run-time types.
            if ((obj == null) || !this.GetType().Equals(obj.GetType()))
            {
                return false;
            }
            else
            {
                Entity<IdType> other = (Entity<IdType>)obj;
                return other.Id.Equals(Id);
            }
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id);
        }


    }
}

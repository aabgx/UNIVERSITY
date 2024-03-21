using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.model;

namespace tema_evenimente.repository
{
    public class EvenimenteRepo: DbContext
    {
        public DBContext repo;
        public EvenimenteRepo(DBContext repo)
        {
            this.repo = repo;
        }
    }
}

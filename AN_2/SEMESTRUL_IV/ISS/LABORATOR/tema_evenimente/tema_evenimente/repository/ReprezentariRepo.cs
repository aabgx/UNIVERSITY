using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tema_evenimente.model;

namespace tema_evenimente.repository
{
    public class ReprezentariRepo:DbContext
    {
        public DBContext repo;
        public ReprezentariRepo(DBContext repo)
        {
            this.repo = repo;
        }
    }
}

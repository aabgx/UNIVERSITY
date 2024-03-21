using Laborator_8.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Laborator_8
{
    public class Service
    {
        private readonly Repository<Echipa, int> repositoryEchipa;
        private readonly Repository<Jucator, int> repositoryJucator;
        private readonly Repository<JucatorActiv, int> repositoryJucatorActiv;
        private readonly Repository<Meci, int> repositoryMeci;

        public Service(Repository<Echipa, int> repositoryEchipa, Repository<Jucator, int> repositoryJucator, Repository<JucatorActiv, int> repositoryJucatorActiv, Repository<Meci, int> repositoryMeci)
        {
            this.repositoryEchipa = repositoryEchipa;
            this.repositoryJucator = repositoryJucator;
            this.repositoryJucatorActiv = repositoryJucatorActiv;
            this.repositoryMeci = repositoryMeci;
        }
        public List<Jucator> getAllJucatori(int idEchipa) {
            List<Jucator> lst = new List<Jucator>();
            var jucatori = repositoryJucator.getAll();
            foreach(Jucator jucator in jucatori)
            {
                if (jucator.Echipa.Id.Equals(idEchipa)) lst.Add(jucator);
            }
            return lst;
        }

        public List<JucatorActiv> getAllJucatoriActivi(int idMeci)
        {
            List<JucatorActiv> lst = new List<JucatorActiv>();
            var jucatori = repositoryJucatorActiv.getAll();
            Meci meci = repositoryMeci.getById(idMeci);

            foreach (JucatorActiv jucator in jucatori)
            {
                if (jucator.IdMeci.Equals(meci.Id))
                {
                    Jucator juc = repositoryJucator.getById(jucator.IdJucator);
                    if (juc.Echipa.Id.Equals(meci.Echipa1.Id) || juc.Echipa.Id.Equals(meci.Echipa2.Id))
                    {
                        lst.Add(jucator);
                    }
                }
            }
            return lst;
        }

        public List<Meci> getAllMeci(int an,int luna,int zi,int anSfarsit, int lunaSfarsit, int ziSfarsit)
        {

            DateTime dataInceput = new DateTime(an,luna,zi);
            DateTime dataSfarsit = new DateTime(anSfarsit, lunaSfarsit, ziSfarsit);
            List<Meci> lst = new List<Meci>();
            var meciuri = repositoryMeci.getAll();
            foreach (Meci meci in meciuri)
            {
                if (meci.Data>=dataInceput && meci.Data<=dataSfarsit)
                {
                    lst.Add(meci);
                }
            }
            return lst;
        }
        public List<int> getScor(int idMeci)
        {
            var meci = repositoryMeci.getById(idMeci);
            var scor = new List<int>();
            int scorEchipa1 = 0;
            int scorEchipa2 = 0;
            var jucatori = getAllJucatoriActivi(idMeci);
            foreach (var jucator in jucatori)
            {
                Jucator juc = repositoryJucator.getById(jucator.IdJucator);
                if (meci.Echipa1.Id.Equals(juc.Echipa.Id)) {
                    scorEchipa1 += jucator.NrPuncteInscrise;
                }
                else
                {
                    scorEchipa2 += jucator.NrPuncteInscrise;
                }
            }

            scor.Add(scorEchipa1);
            scor.Add(scorEchipa2);
            return scor;
        }
    }
}

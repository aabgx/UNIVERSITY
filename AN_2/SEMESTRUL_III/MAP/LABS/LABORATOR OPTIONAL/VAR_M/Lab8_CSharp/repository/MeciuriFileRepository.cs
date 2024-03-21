using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;

namespace Lab8_CSharp.repository;

class MeciuriFileRepository : InFileRepository<string, Meci>
{
    public MeciuriFileRepository(IValidator<Meci> validator,string fileName) : base(validator,fileName,null)
    {
        loadFromFile();
    }

    private new void loadFromFile()
    {
        List<Echipa> echipe = DataReader.ReadData<Echipa>
            ("..\\..\\..\\data\\echipe.txt", EntityToFileMapping.createEchipa);

        using (StreamReader sr = new StreamReader(fileName))
        {
            string line;
            while ((line = sr.ReadLine()) != null)
            {
                string[] fields = line.Split(',');
                Echipa echipa1=echipe.Find(x => x.ID.Equals(fields[0]));
                Echipa echipa2=echipe.Find(x => x.ID.Equals(fields[1]));
                string idMeci = fields[0] + fields[1] + "." + fields[2];
                Meci Meci = new Meci()
                {
                    ID=idMeci,
                    Echipa1=echipa1,
                    Echipa2=echipa2,
                    Date = DateTime.ParseExact(fields[2], "d/M/yyyy",System.Globalization.CultureInfo.InvariantCulture)
                };
                //entities[Meci.ID] = Meci;
                entities.Add(Meci);
            }
        }
    }
}
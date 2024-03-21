using Lab8_CSharp.model;
namespace Lab8_CSharp.repository;

class JucatoriFileRepository : InFileRepository<string, Jucator>
{
    public JucatoriFileRepository(string fileName) : base(fileName, null)
    {
        loadFromFile();
    }

    private new void loadFromFile()
    {
        List<Elev> elevi = DataReader.ReadData<Elev>
            ("..\\..\\..\\data\\elevi.txt", EntityToFileMapping.createElev);
        
        List<Echipa> echipe = DataReader.ReadData<Echipa>
            ("..\\..\\..\\data\\echipe.txt", EntityToFileMapping.createEchipa);

        using (StreamReader sr = new StreamReader(fileName))
        {
            string line;
            while ((line = sr.ReadLine()) != null)
            {
                string[] fields = line.Split(',');
                Elev elev = elevi.Find(x => x.ID.Equals(fields[0]));
                Echipa echipa = echipe.Find(x => x.ID.Equals(fields[1]));
                Jucator jucator = new Jucator()
                {
                    ID = elev.ID,
                    Nume = elev.Nume,
                    Scoala = elev.Scoala,
                    Echipa = echipa
                };
                entities.Add(jucator);
            }
        }
    }
}

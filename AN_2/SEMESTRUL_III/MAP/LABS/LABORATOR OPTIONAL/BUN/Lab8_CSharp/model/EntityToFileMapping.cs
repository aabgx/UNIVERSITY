namespace Lab8_CSharp.model;

class EntityToFileMapping
{
    public static Elev createElev(string line)
    {
        string[] fields = line.Split(',');
        Elev elev = new Elev()
        {
            ID = fields[0],
            Nume = fields[1],
            Scoala = fields[2]
        };
        return elev;
    }

    
    public static Echipa createEchipa(string line)
    {
        string[] fields = line.Split(',');
        Echipa echipa = new Echipa()
        {
            ID = fields[0],
            Nume = fields[1]
        };
        return echipa;
    }

    
    public static JucatorActiv createJucatorActiv(string line)
    {
        string[] fields = line.Split(',');
        JucatorActiv jucatorActiv = new JucatorActiv()
        {
            ID= fields[0],
            idMeci = fields[1],
            nrPuncteInscrise = Int32.Parse(fields[2]),
            tip = (PlayerType)Enum.Parse(typeof(PlayerType), fields[3])
        };
        return jucatorActiv;
    }
}
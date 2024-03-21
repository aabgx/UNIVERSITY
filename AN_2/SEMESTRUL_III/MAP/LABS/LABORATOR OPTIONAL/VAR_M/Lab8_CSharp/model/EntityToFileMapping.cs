using Lab8_CSharp.model.Validator;

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
        //ElevValidator validatorElev = new ElevValidator();
        //validatorElev.validate(elev);
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
        //EchipaValidator validatorEchipa = new EchipaValidator();
        //validatorEchipa.validate(echipa);
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
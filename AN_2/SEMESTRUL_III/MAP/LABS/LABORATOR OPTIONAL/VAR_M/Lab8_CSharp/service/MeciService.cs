using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;
using Lab8_CSharp.repository;

namespace Lab8_CSharp.service;

class MeciService
{
    private IRepository<string, Meci> repository;

    public MeciService(IRepository<string, Meci> repository)
    {
        this.repository = repository;
    }

    public List<Meci> findAllMeciuri()
    {
        return repository.findAll().ToList();
    }

    public List<Meci> returneazaMeciuriPerioadaCalendaristica(string date1,string date2)
    {
        DateTime firstDay, lastDay;
        try
        {
            firstDay =DateTime.ParseExact(date1, "d/M/yyyy", System.Globalization.CultureInfo.InvariantCulture);
            lastDay =DateTime.ParseExact(date2, "d/M/yyyy", System.Globalization.CultureInfo.InvariantCulture);
        }
        catch (Exception e)
        {
            throw new Exception("Eroare, una sau ambele zile nu au fost furnizate in formatul dorit.");
        }
        if (lastDay < firstDay)
            throw new Exception("Interval calendaristic eronat. Zilele trebuie date in ordine.");
        List<Meci> meciuri = findAllMeciuri();
        List<Meci> meciuriInterval=new List<Meci>();
        foreach(Meci m in meciuri)
            if(m.Date>=firstDay && m.Date<=lastDay)
                meciuriInterval.Add(m);
        return meciuriInterval;
    }

    public string returneazaScorMeci(string ech1Name, string ech2Name, string strDate)
    {
        string scor = "";
        
        //1.CAUT ECHIPELE CU NUMELE DATE
        string fileName1 = "..\\..\\..\\data\\echipe.txt";
        EchipaValidator validator1 = new EchipaValidator();
        IRepository<string, Echipa> repository1= new EchipeFileRepository(validator1,fileName1);
        EchipaService service1 = new EchipaService(repository1);
        List<Echipa> echipe = service1.findAllEchipe();
        string echipa1Id=echipe.Find(x => x.Nume.Equals(ech1Name)).ID;
        string echipa2Id=echipe.Find(x => x.Nume.Equals(ech2Name)).ID;
        
        //2.CAUT JUCATORII DIN CELE 2 ECHIPE
        string fileName2 = "..\\..\\..\\data\\jucatori.txt";
        JucatorValidator validator2 = new JucatorValidator();
        IRepository<string, Jucator> repository2= new JucatoriFileRepository(validator2,fileName2);
        JucatorService service2 = new JucatorService(repository2);
        List<Jucator> jucatori=service2.findAllJucatori();
        var jucatoriEchipa1 = new Dictionary<string, string>();
        foreach(Jucator player in jucatori)
            if(ech1Name.Equals(player.Echipa.Nume))
                jucatoriEchipa1[player.ID]=player.Nume;
        var jucatoriEchipa2 = new Dictionary<string, string>();
        foreach(Jucator player in jucatori)
            if(ech2Name.Equals(player.Echipa.Nume))
                jucatoriEchipa2[player.ID]=player.Nume;
        
        //3.CALCULEZ SCOR TOTAL PT FIECARE ECHIPA
        string gameId;
        if(echipa1Id.CompareTo(echipa2Id)>0)
            gameId = echipa2Id + echipa1Id + "." + strDate;
        else
            gameId = echipa1Id + echipa2Id + "." + strDate;
        int scorE1=0;
        int scorE2=0;
        string fileName3 = "..\\..\\..\\data\\jucatoriActivi.txt";
        JucatorActivValidator validator3 = new JucatorActivValidator();
        IRepository<string, JucatorActiv> repository3= new JucatoriActiviFileRepository(validator3,fileName3);
        JucatorActivService service3 = new JucatorActivService(repository3);
        List<JucatorActiv> jucatoriActivi = service3.findAllJucatoriActivi();

        foreach (JucatorActiv j in jucatoriActivi)
        {
            if (j.idMeci.Equals(gameId) && jucatoriEchipa1.ContainsKey(j.ID))
                scorE1=scorE1+j.nrPuncteInscrise;
            else if (j.idMeci.Equals(gameId) && jucatoriEchipa2.ContainsKey(j.ID))
                scorE2=scorE2+j.nrPuncteInscrise;
        }
        scor=scor + ech1Name + ":" + scorE1.ToString() + " ; " + ech2Name + ":" + scorE2.ToString();
        return scor;
    }
}
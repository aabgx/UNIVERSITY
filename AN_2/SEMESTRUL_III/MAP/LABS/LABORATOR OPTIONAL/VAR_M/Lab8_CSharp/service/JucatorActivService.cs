using Lab8_CSharp.model;
using Lab8_CSharp.model.Validator;
using Lab8_CSharp.repository;

namespace Lab8_CSharp.service;

class JucatorActivService
{
    private IRepository<string, JucatorActiv> repository;

    public JucatorActivService(IRepository<string, JucatorActiv> repository)
    {
        this.repository = repository;
    }

    
    public List<JucatorActiv> findAllJucatoriActivi()
    {
        return repository.findAll().ToList();
    }

    
    public List<JucatorActiv> returneazaJucatoriActiviEchipa(string ech1Name,string ech2Name,string strDate)
    {
        //CAUT ECHIPELE DATE 
        string fileName = "..\\..\\..\\data\\echipe.txt";
        EchipaValidator validator1 = new EchipaValidator();
        IRepository<string, Echipa> repository= new EchipeFileRepository(validator1,fileName);
        EchipaService serviceEchipa = new EchipaService(repository);
        List<Echipa> echipe = serviceEchipa.findAllEchipe();
        bool echipa1ok = false, echipa2ok = false;


        //String id_ech1=echipe.Find(e => e.Nume.Equals(ech1Name)).ID;
        //if (id_ech1 != null) echipa1ok = true;

        //String id_ech2=echipe.Find(e => e.Nume.Equals(ech2Name)).ID;
        //if (id_ech2 != null) echipa2ok = true;

        IEnumerable<Echipa> query1 = echipe.Where(e => e.Nume.Equals(ech1Name));
        if (query1.Count() > 0) echipa1ok = true;

        IEnumerable<Echipa> query2 = echipe.Where(e => e.Nume.Equals(ech2Name));
        if (query2.Count() > 0) echipa2ok = true;


        if ((echipa1ok && echipa2ok)!=true)
            throw new Exception("Nu s-a gasit meciul cautat.Datele introduse sunt incorecte.");


        //CAUT JOCUL DINTRE ECHIPELE RESPECTIVE SI DATA
        string echipa1Id=echipe.Find(x => x.Nume.Equals(ech1Name)).ID;
        string echipa2Id=echipe.Find(x => x.Nume.Equals(ech2Name)).ID;
        string gameId;
        if(echipa1Id.CompareTo(echipa2Id)>0)
            gameId = echipa2Id + echipa1Id + "." + strDate;
        else
            gameId = echipa1Id + echipa2Id + "." + strDate;
        //get all activePlayers involved in the desired game
        List<JucatorActiv> jucatoriActivi =findAllJucatoriActivi();
        List<JucatorActiv> jucatoriMeci = new List<JucatorActiv>();
        foreach(JucatorActiv player in jucatoriActivi)
            if(player.idMeci.Equals(gameId))
                jucatoriMeci.Add(player);
        if (jucatoriMeci.Count == 0)
            throw new Exception("Nu s-a gasit meciul cautat.Datele introduse sunt incorecte.");


        //CAUT JUCATORII (SA LE IAU SI NUMELE) PRINTRE TOTI JUCATORII
        string fileName2 = "..\\..\\..\\data\\jucatori.txt";
        JucatorValidator validator2 = new JucatorValidator();
        IRepository<string, Jucator> repository2= new JucatoriFileRepository(validator2,fileName2);
        JucatorService serviceJucatori = new JucatorService(repository2);
        List<Jucator> jucatori = serviceJucatori.findAllJucatori();
        //create a map with player ID and team ID
        var mapJucatorEchipa = new Dictionary<string, string>();
        foreach (Jucator j in jucatori)
            mapJucatorEchipa[j.ID] = j.Echipa.ID;
        //select all players in the desired team
        List<JucatorActiv> jucatoriEchipa = new List<JucatorActiv>();
        foreach(JucatorActiv j in jucatoriMeci)
            if(mapJucatorEchipa[j.ID].Equals(echipa1Id))
                jucatoriEchipa.Add(j);
        return jucatoriEchipa;
    }
}